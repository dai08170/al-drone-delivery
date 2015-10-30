package fr.unice.polytech.si5.al.projet.s3.warehouse;

import fr.unice.polytech.si5.al.projet.s3.central.*;
import java.util.*;
import fr.unice.polytech.si5.al.projet.s3.drone.*;
import fr.unice.polytech.si5.al.projet.s3.truck.Truck;
import fr.unice.polytech.si5.al.projet.s3.truck.TruckDriver;

public abstract class Warehouse {

	private final GPSLocation location;
	private List<Truck> trucks;
	private List<TruckDriver> drivers;
	private CentralWarehouse central;
	private Collection<ShippingRequest> shippingRequest;
	private Collection<Drone> drones;
	private ShippingBalancer shppingBalancer;
	private Map<String,String> mapShippingRequestIDToShippingID = new HashMap<String,String>();
	private List<Order> currentDayOrders;

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

	public void assignCurrentDayOrder(Order o) {
		this.currentDayOrders.add(o);
	}
}