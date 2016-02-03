package app;

import app.action.*;
import app.demonstrator.DemonstratorSpy;
import app.shipper.BasicShipper;
import app.shipper.CompositeShipper;
import app.shipper.Drone;
import app.shipper.HumanShipper;

import java.awt.*;

public class Main {

	Main() {

		DemonstratorSpy output = new DemonstratorSpy();

		//	Building shippers
		CompositeShipper truck = new CompositeShipper("Truck");
		BasicShipper droneA = new Drone("DroneA");
		BasicShipper droneB = new HumanShipper("DroneB");

		output.register(truck);
		output.register(droneA);
		output.register(droneB);


		//	Build nodes that schedules action's execution
		Action gotoDropPointTruck1 = new GoToDropPoint(truck, new Dimension(80, 20));
		Action gotoDropPointTruck2 = new GoToDropPoint(truck, new Dimension(40, 22));
		Action gotoDropPointTruck3 = new GoToDropPoint(truck ,new Dimension(20, 80));
		gotoDropPointTruck1.addObserver(output);
		gotoDropPointTruck2.addObserver(output);
		gotoDropPointTruck3.addObserver(output);
		Node truckGo1Node = new Node(gotoDropPointTruck1);
		Node truckGo2Node = new Node(gotoDropPointTruck2);
		Node truckGo3Node = new Node(gotoDropPointTruck3);


		Action sendDroneA = new SendDrone(truck, droneA);
		Action gotoDroneA = new GoToShippingPosition(droneA, new Dimension(20, 30));
		Action pickDroneA = new Pick(droneA);
		Action gotoTruckDroneA =new GoToShippingPosition(droneA, new Dimension(40, 22));
		sendDroneA.addObserver(output);
		gotoDroneA.addObserver(output);
		pickDroneA.addObserver(output);
		gotoTruckDroneA.addObserver(output);
		Node droneASendNode = new Node(sendDroneA);
		Node droneAGoToShippingPositionNode = new Node(gotoDroneA);
		Node droneAPickNode = new Node(pickDroneA);
		Node droneAGoToTruck = new Node(gotoTruckDroneA);
		Node droneACollect = new Node(new Collect(truck, droneA));

		Node droneBSendNode = new Node(new SendDrone(truck, droneB));
		Node droneBGoToShippingPositionNode = new Node(new GoToShippingPosition(droneB, new Dimension(60, 15)));
		Node droneBDropNode = new Node(new Drop(droneB));
		Node droneBGoToTruck = new Node(new GoToShippingPosition(droneB, new Dimension(40, 22)));
		Node droneBCollect = new Node(new Collect(truck, droneB));

		// Build action dependency graph
		//
		droneASendNode.addDependency(truckGo1Node);
		droneBSendNode.addDependency(truckGo1Node);
		truckGo2Node.addDependency(truckGo1Node);

		droneAGoToShippingPositionNode.addDependency(droneASendNode);
		droneBGoToShippingPositionNode.addDependency(droneBSendNode);

		droneAPickNode.addDependency(droneAGoToShippingPositionNode);
		droneBDropNode.addDependency(droneBGoToShippingPositionNode);

		droneAGoToTruck.addDependency(droneAPickNode);
		droneBGoToTruck.addDependency(droneBDropNode);

		droneACollect.addDependency(droneAGoToTruck);
		droneACollect.addDependency(truckGo2Node);

		droneBCollect.addDependency(droneBGoToTruck);
		droneBCollect.addDependency(truckGo2Node);

		truckGo3Node.addDependency(droneACollect);
		truckGo3Node.addDependency(droneBCollect);

		/*Node lastActionForDroneA = new Node(currentNodeForEachShipper, droneAPick, null);
		Node firstActionForDroneA = new Node(currentNodeForEachShipper, droneAGoToShippingPosition, lastActionForDroneA);
		*/

		Node root = truckGo1Node;
		//truck.setCurrentAction(root);
		root.queueAction();

		//	Fake drone msg reception
		waitForDemo();
		truck.endAction();	// finish goto
		waitForDemo();
		truck.endAction();	// finish first send
		waitForDemo();
		truck.endAction();  //  finish 2nd send

		waitForDemo();
		droneA.endAction(); // end of goto location
		waitForDemo();
		droneA.endAction();	// end of pick
		waitForDemo();
		droneA.endAction();	// end of goto meeting point

		waitForDemo();
		System.out.println("==== TRUCK REACHES MEETING POINT ====");
		truck.endAction(); // end of truck go to meeting point. Should queue CollectA in truck

		waitForDemo();
		droneB.endAction(); // end of goto location
		waitForDemo();
		droneB.endAction();    // end of drop
		waitForDemo();
		droneB.endAction();    // end of goto meeting point. Should queue CollectB in truck

		waitForDemo();

		truck.endAction(); // end of collect A
		waitForDemo();
		truck.endAction(); // end of collect B

		waitForDemo();
		truck.endAction(); // end of goto away
		waitForDemo();
	}

	public static void main(String[] args) {
		new Main();
	}

	private void waitForDemo(){
		System.out.println();

		// To allow control
		// new Scanner(System.in).nextLine();

		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
