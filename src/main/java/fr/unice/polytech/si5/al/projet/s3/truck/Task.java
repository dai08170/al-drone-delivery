package fr.unice.polytech.si5.al.projet.s3.truck;

import fr.unice.polytech.si5.al.projet.s3.drone.Drone;
import fr.unice.polytech.si5.al.projet.s3.shipping.Shipping;

public class Task {
	private Drone drone;
	private Shipping shipping;
	private TaskStatus taskStatus;

	public Task(Drone drone, Shipping shipping) {
		this.drone = drone;
		this.shipping = shipping;
		this.taskStatus = TaskStatus.PENDING;
	}

	public String getTaskDescription() {
		return "Prendre le drone "  + drone.getName() + " et le paquet ";
	}
}