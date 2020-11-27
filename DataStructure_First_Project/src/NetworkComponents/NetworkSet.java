package NetworkComponents;

import DataStructures.List;
import DataStructures.Map;

public class NetworkSet {
    private Map<Person, Integer> people;
    private Map<Subject, Integer> subjects;
    private Map<Integer, Subject> indexToSubject;
    private Map<Integer, Person> indexToPerson;
    private List<Network> networks;

    public void addPerson(Person person) {
        if (people.containsKey(person))
            return;
        indexToPerson.put(people.getSize(), person);
        people.put(person, people.getSize());
    }

    public void addSubject(Subject subject) {
        if (subjects.containsKey(subject))
            return;
        indexToSubject.put(subjects.getSize(), subject);
        subjects.put(subject, subjects.getSize());
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
