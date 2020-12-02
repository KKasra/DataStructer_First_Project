package NetworkComponents;

import DataStructures.*;
import DataStructures.Map;

public class NetworkSet {
    // private Map<Person, Integer> people;
    // private Map<Subject, Integer> subjects;
    private Map<Integer, Subject> indexToSubject;
    private Map<Integer, Person> indexToPerson;
    private List<Network> networks;

    public Person addPerson(String name) {    
        Node <Pair <Integer, Person>>  pointer = indexToPerson.getFirst();
        while (pointer != null)  {
            if (pointer.getElement().getSecond().getName().equals(name))
                return pointer.getElement().getSecond();
            pointer = pointer.getNext();
        }
        
           
        Person person = new Person(name, indexToPerson.getSize());
        indexToPerson.put(person.getIdInNetworkSet(), person);
        // people.put(person, person.getIdInNetworkSet());
        return person;
    }

    public Subject addSubject(String name) {
        Node <Pair <Integer, Subject>>  pointer = indexToSubject.getFirst();
        while (pointer != null)  {
            if (pointer.getElement().getSecond().getName().equals(name))
                return pointer.getElement().getSecond();
            pointer = pointer.getNext();
        }
        
        Subject subject = new Subject(name, indexToSubject.getSize());
        indexToSubject.put(subject.getIdInNetworkSet(), subject);
        // subjects.put(subject, subject.getIdInNetworkSet());
        return subject;
    }
    public Person getPersonWithIndex(int index) {
        try {
            return indexToPerson.get(index);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    public Subject getSubjectWithIndex(int index) {
        try {
            return indexToSubject.get(index);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    public void addNetwork(Network network) {
        networks.add(network);
    }

    public List<Network> getNetworks() {
        return networks;
    }
    public Map<Integer, Person> getPeople() {
        return indexToPerson;
    }
    public Map<Integer, Subject> getSubjects() {
        return indexToSubject;
    }

     private NetworkSet() {
        // people = new Map<Person, Integer>();
        // subjects = new Map<Subject, Integer>();
        networks = new List<Network>();
        indexToPerson = new Map<Integer, Person>();
        indexToSubject = new Map<Integer, Subject>();
    }
    private static NetworkSet instance;
    public static NetworkSet getInstance() {
        if (instance == null)
            instance = new NetworkSet();
        return instance;
    }


    
    

}
