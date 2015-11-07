package fr.unice.polytech.si5.al.projet.truck;


public class Delivery{
	private DeliveryID ID;

	protected State state;
	private Box box;


	public Delivery(DeliveryID ID, String name, Box box) {
		this.ID = ID;
		this.box = box;

		this.state.pending();
	}

	public DeliveryID getID() {
		return this.ID;
	}

	public void start() {
		this.state.start();
	}

	public void doing() {
		this.state.doing();
	}

	public void fail() {
		this.state.fail();
	}

	public boolean isDoing() {
		return this.state.isDoing();
	}

	public void done() {
		this.state.done();
	}

	public boolean validate(DroneID droneID, String boxID) {
		//TODO CHECK IF DRONE AND BOX ARE OK
		//	if(checkOK) validate();
		return true;
	}

	String getDescription() {
		return "Deliver box " + box;
	}

	public Box getBox() {
		return box;
	}

	public boolean isPending() {
		return this.isPending();
	}
}