package SearchEngine;



import Connections.Friendship;
import Connections.Interest;
import LinearAlgebra.Matrix;
import NetworkComponents.NetworkSet;
import NetworkComponents.Person;
import NetworkComponents.Subject;

import DataStructures.*;

public class SearchEngine {
    private Matrix peopleToSubjects;
    private Matrix peopleToPeople;
    private NetworkSet networkSet;

    public void respondeToQuary(Query query)  {
        List<Integer> indexOfSubjects = new List<Integer>();
        query.subjects.iterateOnList(node -> {
            try {
                indexOfSubjects.add(networkSet.getIndexOfSubject(node.getElement()));
            } catch (Exception ignore) {
            }
        });

        List<Integer> indexOfFirstPeople = getListOfFirstPeople(indexOfSubjects);

        Pair<Matrix, Map<Integer, Integer>> data = getPathOfPeopleToFirstPeople(query, indexOfFirstPeople);
        Matrix weight = data.getFirst();
        Map<Integer, Integer> appeured = data.getSecond();

        Matrix addedWeight = getcolumnOfWeightOfFirstPeople(query, indexOfFirstPeople);

        addedWeight = weight.product(addedWeight);

        printResaults(appeured, addedWeight);
    }

    private void printResaults(Map<Integer, Integer> appeured, Matrix addedWeight) {
        Set<ResaultLine> resaults = new Set<ResaultLine>();
        appeured.iterateOnMap(node -> {
            Pair<Integer, Integer> pair = node.getElement();
            
            resaults.add(new ResaultLine(networkSet.getPersonWithIndex(pair.getFirst()).getName(),
                    addedWeight.getValueOf(pair.getFirst(), 0), pair.getSecond()));
       });

    //    System.out.println();

       resaults.iterateOnList(node -> {
           ResaultLine line = node.getElement();
           String plus = "";
           for (int i = 0; i < line.distance; i++) {
               plus += "+";
           }
           System.out.println(line.name + " " + String.format("%.6f", line.weight) + " " + plus);
       });

    }

    class ResaultLine implements Comparable<ResaultLine>{
        String name;
        double weight;
        int distance;
        ResaultLine(String name, double weight, int distance){
            this.name = name;
            this.weight = weight;
            this.distance = distance;
        }

        @Override
        public int compareTo(ResaultLine resault) {
            if (Integer.compare(distance, resault.distance) != 0)
                return Integer.compare(distance, resault.distance);
            if (Double.compare(weight, resault.weight) != 0)
                return -Double.compare(weight, resault.weight);
            if (name.compareTo(resault.name) != 0)
                return name.compareTo(resault.name);
            return 0;
        }

        @Override
        public String toString() {
            return "ResaultLine [distance=" + distance + ", name=" + name + ", weight=" + weight + "]";
        }
        
    }

    private Matrix getcolumnOfWeightOfFirstPeople(Query query, List<Integer> indexOfFirstPeople) {
        Matrix addedWeight = new Matrix();
        indexOfFirstPeople.iterateOnList(node -> {
            int i = node.getElement();
            try {
                addedWeight.setValue(i, 0, 0);
            } catch (Exception e1) {}
            query.subjects.iterateOnList(node1 -> {
                try {
                    addedWeight.setValue(i, 0, addedWeight.getValueOf(i, 0)
                            + peopleToSubjects.getValueOf(i, networkSet.getIndexOfSubject(node1.getElement())));
                } catch (Exception e) {}
            });
        });
        return addedWeight;
    }

    private List<Integer> getListOfFirstPeople(List <Integer> indexOfSubjects)  {
        List<Integer> indexOfFirstPeople = new List<Integer>();
        for (int i = 0; i < networkSet.getPeople().getSize(); i++) {
            boolean flag = true;
            Node<Integer> pointer = indexOfSubjects.getFirst();
            while (pointer != null) {
                if (peopleToSubjects.getValueOf(i, pointer.getElement()) == 0)
                    flag = false;
                pointer = pointer.getNext();
            }
            if (flag)
                indexOfFirstPeople.add(i);
        }

        return indexOfFirstPeople;
    }

    private Pair<Matrix, Map<Integer, Integer>> getPathOfPeopleToFirstPeople(Query query, List<Integer> indexOfFirstPeople) {
        Matrix status = new Matrix();
        for (int j = 0; j < networkSet.getPeople().getSize(); j++) {
            status.setValue(j, j, 1);
        }
        Matrix weight = new Matrix();
        Map<Integer, Integer> appeured = new Map<Integer, Integer>();
        for (int level = 0; level <= query.depth; ++level, status = status.product(peopleToPeople)) {
            for (int i = 0; i < networkSet.getPeople().getSize(); ++i) {
                if (appeured.containsKey(i))
                    continue;
                Node<Integer> node = indexOfFirstPeople.getFirst();
                while (node != null) {
                    int j = node.getElement();
                    if (status.getValueOf(i, j) > 0) {
                        appeured.put(i, level);
                        weight.setValue(i, j, weight.getValueOf(i, j) + status.getValueOf(i, j));
                    }
                    node = node.getNext();
                }
            }

        }
        return new Pair<Matrix, Map<Integer, Integer>>(weight, appeured);
    }

    public void getInfoOFNetworkSet(NetworkSet networkSet) {
        this.networkSet = networkSet;
        try {
            initPeopleToPeopleMatrix(networkSet);
            initPeopleToSubjectsmatrix(networkSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPeopleToPeopleMatrix(NetworkSet networkSet) throws Exception {
        peopleToPeople = new Matrix();

        for (int i = 0; i < networkSet.getPeople().getSize(); i++) {
            Person person = networkSet.getPersonWithIndex(i);
            Node <Friendship> friendshipNode = person.getFriendships().getFirst();
            while(friendshipNode != null) {
                int j = networkSet.getIndexOfPerson(friendshipNode.getElement().getPerson());
                peopleToPeople.setValue(i, j, peopleToPeople.getValueOf(i, j) + friendshipNode.getElement().getDegree());
                friendshipNode = friendshipNode.getNext();
            }

            peopleToPeople.setValue(i, i, 1);
        } 
    }

    private void initPeopleToSubjectsmatrix(NetworkSet networkSet) throws Exception {
        peopleToSubjects = new Matrix();
        
        for (int i = 0; i < networkSet.getPeople().getSize(); i++) {
            Person person = networkSet.getPersonWithIndex(i);
            Node <Interest> interestNode = person.getInterests().getFirst();
            while(interestNode != null) {
                int j = networkSet.getIndexOfSubject(interestNode.getElement().getSubject());
                peopleToSubjects.setValue(i, j, peopleToSubjects.getValueOf(i, j) + interestNode.getElement().getDegree());
                interestNode = interestNode.getNext();
            }
        } 

    }


    private SearchEngine(){
        peopleToSubjects = null;
        peopleToPeople = null;
    };
    private static SearchEngine instance;
    public static SearchEngine getInstance() {
        if (instance == null)
            instance = new SearchEngine();
        return instance;
    }

    public static class Query{
        public List<Subject> subjects = new List<Subject>();  
        public int depth;
    }
}
