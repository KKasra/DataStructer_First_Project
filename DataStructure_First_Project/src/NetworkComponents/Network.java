package NetworkComponents;

import DataStructures.Map;

public class Network {
    private Map<Person, Integer> peopleIndex = new Map<Person, Integer>();
    private Map<Subject, Integer> subjectsIndex = new Map<Subject, Integer>();

    private Map<Integer, Person> indexToPerson;
    private Map<Integer, Subject> indextToSubject;

    public Network(){
        peopleIndex = new Map<Person, Integer>();
        subjectsIndex = new Map<Subject, Integer>();
        indexToPerson = new Map<Integer, Person>();
        indextToSubject = new Map<Integer, Subject>();
    }

    public void addPerson(Person person) {
        if (peopleIndex.containsKey(person))
            return;
        peopleIndex.put(person, peopleIndex.getSize());
        indexToPerson.put(indexToPerson.getSize(), person);
    }

    public void addSubject(Subject subject) {
        if (subjectsIndex.containsKey(subject))
            return;
        subjectsIndex.put(subject, subjectsIndex.getSize());
        indextToSubject.put(indextToSubject.getSize(), subject);
    }

    public Subject getSubjectWithIndex(int index) {
        try {
            return indextToSubject.get(index);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Person getPersonWithIndex(int index) {
        try {
            return indexToPerson.get(index);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Map<Integer, Person> getIndexToPerson() {
        return indexToPerson;
    }


    public Map<Integer, Subject> getIndextToSubject() {
        return indextToSubject;
    }

 

    
}