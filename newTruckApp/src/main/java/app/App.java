package app;

import app.action.Action;
import app.demonstrator.DemonstratorSpy;
import app.modelFactory.ModelFactory;
import app.shipper.Shipper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Benjamin on 30/01/2016.
 */
public class App {
	public Map<String, Shipper> shipperMap = new HashMap<>();
	public List<Action> listActions = new ArrayList<>();
	public Node root;

	private ModelFactory modelFactory = new ModelFactory();
	
	public App() throws Exception {
		JSONParser parser = new JSONParser();
		String nodeJsonDescription = modelFactory.getFile("template_main.json");
		Object objNodeJson = parser.parse(nodeJsonDescription);
		JSONObject rootJsonObject = (JSONObject) objNodeJson;
		JSONObject nodes = (JSONObject) rootJsonObject.get("nodes");

		shipperMap = modelFactory.buildShippers("shippers.json");
		Map<String, Node> tempHashMapOfNodes = modelFactory.buildNodes(shipperMap, nodes);

		JSONObject graph = (JSONObject) rootJsonObject.get("graph");
		root = modelFactory.buildDependencies(graph, tempHashMapOfNodes);

		for (Node currentNode : tempHashMapOfNodes.values()) {
			Action currentAction = currentNode.getAction();
			listActions.add(currentAction);
		}

		addDemonstratorSpy();

		root.queueAction();
	}

	public void addDemonstratorSpy() {
		DemonstratorSpy demonstratorSpy = new DemonstratorSpy();

		for (Shipper currentShipper : shipperMap.values()) {
			try {
				demonstratorSpy.getClass().getMethod("register", currentShipper.getClass()).invoke(demonstratorSpy, currentShipper);
			} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		for (Action currentAction : listActions) {
			currentAction.addObserver(demonstratorSpy);
		}
	}

	public void entityEndAction(String entityName) {
		shipperMap.get(entityName).endAction();
	}

	public static void main(String[] args) throws Exception {
		App app = new App();

		while(true)
			app.play();

	}

	private void play(){

		String res = new Scanner(System.in).next();
		Shipper s = null;
		if(res.contains("t")) s = this.shipperMap.get("Truck");
		else if (res.contains("h")) s = this.shipperMap.get("Human");
		else if (res.contains("a")) s = this.shipperMap.get("DroneA");
		else if (res.contains("b")) s = this.shipperMap.get("DroneB");

		if(s != null){
			try{
				s.endAction();
			} catch (Throwable t){
				System.err.println("Waiting a dependency");
			}
		}

	}
}
