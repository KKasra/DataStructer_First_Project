package Connections;

import NetworkComponents.Network;
import NetworkComponents.Person;

public class Friendship{
    private Person person;
    private double degree;
    private Network network;
    public Person getPerson() {
        return person;
    }

    public double getDegree() {
        return degree;
    }

    public Network getNetwork(){
        return network;
    }

    public Friendship(Person person, double degree, Network network) {
        this.person = person;
        this.degree = degree;
        this.network = network;
    }
}
