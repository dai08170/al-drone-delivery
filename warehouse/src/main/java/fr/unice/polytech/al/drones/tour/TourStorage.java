package fr.unice.polytech.al.drones.tour;

import fr.unice.polytech.si5.al.projet.central.Address;
import fr.unice.polytech.si5.al.projet.shipping.Dimensions;
import fr.unice.polytech.si5.al.projet.shipping.PackageToShip;
import fr.unice.polytech.si5.al.projet.shipping.Weight;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by user on 06/11/2015.
 */
public class TourStorage {

    private static LinkedList<Tour> tourList = new LinkedList<Tour>();


    public static Tour getLast(){
        return tourList.getLast();
    }

    static {

        ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
        deliveries.add(new Delivery("drone1", "package1","drone3"));

        tourList.add(new Tour(new DropPoint("here",deliveries)));
    }
}
