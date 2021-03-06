package fr.unice.polytech.si5.al.projet.truck;

/**
 * @author Etienne Strobbe (02/11/2015).
 */
public class Box {

    private String destination;
    private double weight;
    private BoxID ID;

    public Box(String destination, double weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public BoxID getID() {
        return ID;
    }
}
