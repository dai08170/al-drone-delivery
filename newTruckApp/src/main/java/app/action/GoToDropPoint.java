package app.action;

import app.shipper.CompositeShipper;

public class GoToDropPoint extends CompositeShipperAction {
	public GoToDropPoint(CompositeShipper target) {
		super(target);
	}
}