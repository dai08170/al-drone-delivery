package fr.unice.polytech.si5.al.projet.s3.truck.step;


import fr.unice.polytech.si5.al.projet.s3.truck.DroneDeliveryApp;

public class GoToStep extends ExecutableStep {
	private String location;

	public GoToStep(String name, String location) {
		super(name);
		this.location = location;
	}

	@Override
	public void execute(DroneDeliveryApp app) {
		System.out.println("\t+ Bitch go to " + location + "...");
		this.status = TaskStatus.DONE;
	}

	@Override
	String getDescription() {
		return "(Go To " + location + ")";
	}
}
