package app.action;

import app.shipper.BasicShipper;

import java.awt.*;

public class GoToShippingPosition extends BasicShipperAction {
	private Dimension location;

	public GoToShippingPosition(BasicShipper target, Dimension location) {
		super(target);
		this.location = location;
	}


	public Dimension getLocation() {
		return location;
	}

}