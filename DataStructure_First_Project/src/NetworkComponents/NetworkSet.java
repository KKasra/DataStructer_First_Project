package NetworkComponents;

import DataStructures.*;
import DataStructures.Map;

public class NetworkSet {
    private Map<Person, Integer> people;
    private Map<Subject, Integer> subjects;
    private Map<Integer, Subject> indexToSubject;
    private Map<Integer, Person> indexToPerson;
    private List<Network> networks;

    public Person addPerson(String name) {
        Person person = new Person(name);
        
        Node <Pair <Person, Integer>>  pointer = people.getFirst();
        while (pointer != null)  {
            if (pointer.getElement().getFirst().getName().equals(name))
                return pointer.getElement().getFirst();
            pointer = pointer.getNext();
        }
        
            
        indexToPerson.put(people.getSize(), person);
        people.put(person, people.getSize());
        return person;
    }

    public Subject addSubject(String name) {
        Subject subject = new Subject(name);
        
        Node <Pair <Subject, Integer>>  pointer = subjects.getFirst();
        while (pointer != null)  {
            if (pointer.getElement().getFirst().getName().equals(name))
                return pointer.getElement().getFirst();
            pointer = pointer.getNext();
        }
        
            
        indexToSubject.put(subjects.getSize(), subject);
        subjects.put(subject, subjects.getSize());
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
    public Map<Person, Integer> getPeople() {
        return people;
    }
    public Map<Subject, Integer> getSubjects() {
        return subjects;
    }

    public int getIndexOfPerson(Person person) throws Exception {
        return people.get(person);
    }
    public int getIndexOfSubject(Subject subject) throws Exception {
        return subjects.get(subject);
    }

     private NetworkSet() {
        people = new Map<Person, Integer>();
        subjects = new Map<Subject, Integer>();
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
