package fr.unice.polytech.si5.al.projet.truck;

import fr.unice.polytech.si5.al.projet.truck.domain.Deployment;
import fr.unice.polytech.si5.al.projet.truck.domain.DropPoint;
import fr.unice.polytech.si5.al.projet.truck.domain.GoToStep;
import fr.unice.polytech.si5.al.projet.truck.domain.Tour;
import fr.unice.polytech.si5.al.projet.truck.domain.delivery.Delivery;
import fr.unice.polytech.si5.al.projet.truck.domain.delivery.DeliveryID;
import fr.unice.polytech.si5.al.projet.truck.domain.drone.Drone;
import fr.unice.polytech.si5.al.projet.truck.domain.drone.DroneID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

/**
 * Created by Benjamin on 06/11/2015.
 */
public class Controller {
	private Tour model;
	private View view;

	public Controller() throws IOException {


		StringBuilder result = new StringBuilder();
		URL url = new URL("http://localhost");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		System.out.printf(" HTTP GET " + result.toString());



		List<Drone> drones = new ArrayList<>();
		Drone packito = new Drone("7","Packito");
		Drone geraldo = new Drone("3", "Geraldo");
		Drone helperado = new Drone("4", "The helper");

		Delivery delivery1OfPackito = new Delivery(new DeliveryID("d1"), "-82.55N 78.79E");
		Delivery delivery2OfPackito = new Delivery(new DeliveryID("d2"), "-82.50N 77.19E");

		Delivery delivery1OfGeraldo = new Delivery(new DeliveryID("d3"), "-81.55N 75.79E");

		packito.addDelivery(delivery1OfPackito);
		packito.addDelivery(delivery2OfPackito);

		geraldo.addDelivery(delivery1OfGeraldo);

		drones.add(packito);
		drones.add(geraldo);
		drones.add(helperado);

		Map<DeliveryID, List<Drone>> altDrones = new HashMap<>();
		List<Drone> listAltDrones = new ArrayList<>();
		listAltDrones.add(helperado);
		altDrones.put(delivery2OfPackito.getID(), listAltDrones);

		GoToStep goToStep = new GoToStep("Super U", "-82.5588N 78.787E");
		Deployment deployment = new Deployment(drones, altDrones);

		DropPoint dp = new DropPoint(goToStep, deployment);
		List<DropPoint> dps = new ArrayList<>();
		dps.add(dp);

		this.model = new Tour(dps);
		this.view = new ConsoleView(this);
		this.getGlobalTourDescription();
	}

	public void getGlobalTourDescription() {
		Map<String, Object> globalTourDescription;

		try {
			globalTourDescription = this.model.getGlobalDeliveriesDescription();

			this.view.displayTourState(globalTourDescription);
		} catch (IllegalAccessException e) {
			this.view.displayStartTour();
		}


	}

	public void startTour() {
		GoToStep goToStep = model.start();
		this.view.tourHasStarted(goToStep);
	}

	public Map<Drone, Delivery> getCurrentDeliveriesDescription() throws IllegalAccessException {
		return this.model.getCurrentDeliveriesDescription();
	}

	public static void main(String[] args) throws IOException {
		Controller controller = new Controller();

	}

	public void isArrivedAtLocation() {
		DropPoint dropPoint = null;
		try {
			dropPoint = this.model.truckDriverIsArrivedAtLocation();
			this.getGlobalTourDescription();
		} catch (Exception e) {
			// TODO display error
			e.printStackTrace();
		}
	}

	public void checkPair(String droneID, String packageID) {
		try {
			if(this.model.checkAssociation(new DroneID(droneID), new DeliveryID(packageID))) {
				this.view.askIfDroneGone(droneID);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			this.view.refuseAssociation();
			this.getGlobalTourDescription();
		}
	}

	public void declareDroneProblem(String droneID) {
		System.out.println("DRONE PB");
		this.model.droneDead(new DroneID(droneID));
		this.getGlobalTourDescription();
	}

	public void droneGone(String droneID) {
		this.model.droneGone(new DroneID(droneID));
		this.getGlobalTourDescription();
	}

	public void droneBack(String droneID) {
		try {
			this.model.droneBack(new DroneID(droneID));
			this.getGlobalTourDescription();
		}
		catch(IllegalArgumentException e) {
			this.view.displayDroneNotFound(droneID);
			this.getGlobalTourDescription();
		}
	}

}
