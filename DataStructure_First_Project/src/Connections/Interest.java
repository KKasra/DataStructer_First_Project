package Connections;

import NetworkComponents.Subject;

public class Interest {
    private Subject subject;
    private double degree;

    public Interest(Subject subject, double degree) {
        this.subject = subject;
        this.degree = degree;
    }

    public Subject getSubject() {
        return subject;
    }

    public double getDegree() {
        return degree;
    }
  
}
