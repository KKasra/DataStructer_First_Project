package Connections;

import NetworkComponents.Network;
import NetworkComponents.Subject;

public class Interest implements Comparable<Interest> {
    private Subject subject;
    private double degree;
    private Network network;
    public Interest(Subject subject, double degree, Network network){
        this.subject = subject;
        this.degree = degree;
        this.network = network;
    }

    public Subject getSubject() {
        return subject;
    }

    public double getDegree() {
        return degree;
    }
    public Network getNetwork() {
        return network;
    }

    @Override
    public int compareTo(Interest interest) {
        int i = subject.getName().compareTo(interest.getSubject().getName());
        if (i == 0)
            return Double.compare(degree, interest.getDegree());
        return i;
    }
  
}
