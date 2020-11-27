package NetworkComponents;

import DataStructures.Set;
import Connections.*;
public class Person implements Comparable<Person> {
    private String name;
    private Set <Interest> interests;
    private Set<Friendship> friendships;
    public Person(String name) {
        this.name = name;
        interests = new Set<Interest>();
        friendships = new Set<Friendship>();
    }

    public void addFriend(Person person, double degree, Network network){
        friendships.add(new Friendship(person, degree, network));
    }

    public void addInterest(Subject subject, double degree, Network network){
        interests.add(new Interest(subject, degree, network));
    }

    public Set<Interest> getInterests(){
        return interests;
    }
    public Set<Friendship> getFriendships(){
        return friendships;
    }
    public String getName(){
        return name;
    }

    @Override
    public int compareTo(Person person) {
        return this.name.compareTo(person.getName());
    }
}