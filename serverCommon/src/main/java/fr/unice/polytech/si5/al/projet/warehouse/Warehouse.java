package fr.unice.polytech.si5.al.projet.warehouse;

import fr.unice.polytech.si5.al.projet.central.CentralWarehouse;
import fr.unice.polytech.si5.al.projet.drone.Drone;
import fr.unice.polytech.si5.al.projet.drone.GPSLocation;

import java.util.*;

import fr.unice.polytech.si5.al.projet.shipping.PackageToShip;
import fr.unice.polytech.si5.al.projet.truck.Truck;
import fr.unice.polytech.si5.al.projet.truck.TruckDriver;

public abstract class Warehouse {

	private final GPSLocation location;
	private List<Truck> trucks;
	private List<TruckDriver> drivers;
	private CentralWarehouse central;
	private Collection<Drone> drones;
	private ShippingBalancer shppingBalancer;
	private Map<String,String> mapShippingRequestIDToShippingID = new HashMap<String,String>();
	private List<PackageToShip> packagesToShip;

	public Warehouse(CentralWarehouse central, GPSLocation location) {
		this.central = central;
		this.location = location;

		this.trucks = new LinkedList<Truck>();
		this.drivers = new LinkedList<TruckDriver>();
	}

	public void addTruck(Truck t) {
		this.trucks.add(t);
	}

	public void addDriver(TruckDriver td) {
		this.drivers.add(td);
	}

	public GPSLocation getLocation() {
		return location;
	}

	public void addPackageToShip(PackageToShip p) {
		this.packagesToShip.add(p);
	}
}