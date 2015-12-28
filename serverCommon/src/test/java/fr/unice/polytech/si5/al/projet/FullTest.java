package fr.unice.polytech.si5.al.projet;

import fr.unice.polytech.si5.al.projet.algorithm.WeightedWaypoint;
import fr.unice.polytech.si5.al.projet.algorithm.clustering.Cluster;
import fr.unice.polytech.si5.al.projet.algorithm.clustering.SimpleClustering;
import fr.unice.polytech.si5.al.projet.algorithm.sequencing.Sequence;
import fr.unice.polytech.si5.al.projet.algorithm.sequencing.SimplePointSequencingAlgorithm;
import fr.unice.polytech.si5.al.projet.math.Vector2D;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 *
 */
public class FullTest extends TestCase {

	public void testFullProcess() throws Exception {
		List<Vector2D> points = PointGenerator.generateVector2D(200);

		// First cluster the points
		List<Cluster> clusters = new SimpleClustering().process(points, 50, 50);
		//System.out.println(clusters.size() + " Clusters got: "+ clusters);

		// Then sequence the clusters
		List<Sequence> clustersSequences = new SimplePointSequencingAlgorithm().process(clusters, 3, 15);
		System.out.println("\n\n\n");
		//System.out.println("Sequences got "+clustersSequences);

		JSONArray json = new JSONArray();
		for (Sequence clusterSeq: clustersSequences) {
			JSONObject seqJSON = new JSONObject();

			JSONArray seqPointsJSON = new JSONArray();
			seqJSON.put("points", seqPointsJSON);

			for (WeightedWaypoint wp : clusterSeq) {
				JSONObject clusterJSON = new JSONObject();
				Cluster cluster = (Cluster) wp;

				clusterJSON.put("points", cluster.toJSON());
				clusterJSON.put("anchor", cluster.computeAnchor().toJSON());
				seqPointsJSON.put(clusterJSON);
			}

			json.put(seqJSON);
		}

		System.out.println(json.toString());

	}
}
