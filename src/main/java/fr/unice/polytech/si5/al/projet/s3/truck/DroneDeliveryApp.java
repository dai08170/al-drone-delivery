package fr.unice.polytech.si5.al.projet.s3.truck;

import fr.unice.polytech.si5.al.projet.s3.truck.step.Sequence;

public class DroneDeliveryApp {
	private Sequence tour;

	public DroneDeliveryApp(Sequence tour) {
		this.tour = tour;
	}

	public void start() {
		while(!this.tour.isDone()) {
			tour.execute();
		}
	}
}