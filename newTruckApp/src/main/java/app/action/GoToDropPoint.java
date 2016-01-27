package app.action;

import app.shipper.CompositeShipper;

import java.awt.*;

public class GoToDropPoint extends CompositeShipperAction {

	private Dimension location;

	public GoToDropPoint(CompositeShipper target, Dimension location) {
		super(target);
		this.location = location;
	}

	@Override
	public void execute() {
		setChanged();
		notifyObservers(getTarget());
	}

	public Dimension getLocation() {
		return location;
	}
}