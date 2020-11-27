package Connections;

import NetworkComponents.Network;
import NetworkComponents.Person;

public class Friendship implements Comparable<Friendship>{
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

    @Override
    public int compareTo(Friendship friendship) {
        int i = person.getName().compareTo(friendship.getPerson().getName());
        if (i == 0)
            return Double.compare(degree, friendship.getDegree());
        return i;
    }
}
