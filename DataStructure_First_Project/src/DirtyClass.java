import java.util.Scanner;




public class DirtyClass {
    public static void main(String[] args) throws Exception {
      
    
        Scanner scanner = new Scanner(System.in);
        int numberOfNetworks = scanner.nextInt();
        for (int i = 0; i < numberOfNetworks; i++) {
            Network network = new Network();
            NetworkSet.getInstance().addNetwork(network);
            getNetworkData(network, scanner);
        }
        

        //  checkData();

        SearchEngine.getInstance().getInfoOFNetworkSet(NetworkSet.getInstance());

        int numberOfQueries = scanner.nextInt();
        for (int i = 0; i < numberOfQueries; i++) {
            SearchEngine.Query query = new SearchEngine.Query();
            int numberOfQuerySubjects = scanner.nextInt();
            while (numberOfQuerySubjects-- > 0) {
                String subjectName = scanner.next();
                query.subjects.add(NetworkSet.getInstance().addSubject(subjectName));
            }

            query.depth = scanner.nextInt();
            try {
                SearchEngine.getInstance().respondeToQuary(query);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }

            scanner.close();

    }

    private static void getNetworkData(Network network, Scanner scanner) {
        int numberOfSubjects = scanner.nextInt();
        for (int j = 0; j < numberOfSubjects; j++) {
            Subject subject = NetworkSet.getInstance().addSubject(scanner.next());
            network.addSubject(subject);
        }
        int numberOfPeople = scanner.nextInt();
        for (int j = 0; j < numberOfPeople; j++) {
            Person person = NetworkSet.getInstance().addPerson(scanner.next());
            network.addPerson(person);

            int numberOfInterests = scanner.nextInt();

            for (int k = 0; k < numberOfInterests; k++) {
                String interestData = scanner.next();
                int indexOfSubject = Integer.valueOf(interestData.substring(0, interestData.indexOf(":")));
                double degreeOfInterest = Double.valueOf(interestData.substring(interestData.indexOf(":") + 1));
                person.addInterest(network.getSubjectWithIndex(indexOfSubject), degreeOfInterest, network);
            }
        }

        int numberOfFriends = scanner.nextInt();
        for (int k = 0; k < numberOfFriends; k++) {
            Person person = network.getPersonWithIndex(scanner.nextInt());
            int numberOfFriendships = scanner.nextInt();
            for (int j = 0; j < numberOfFriendships; j++) {
                String friendshipData = scanner.next();
                int indexOfPerson = Integer.valueOf(friendshipData.substring(0, friendshipData.indexOf(":")));
                double degreeOfFriendship = Double.valueOf(friendshipData.substring(friendshipData.indexOf(":") + 1));
                person.addFriend(network.getPersonWithIndex(indexOfPerson), degreeOfFriendship, network);
            }
        }

       
    }

    private static void checkData(){
        NetworkSet.getInstance().getPeople().iterateOnMap(node -> {
            Person person  = node.getElement().getSecond();
            System.err.println(person.getName() + ":");
            person.getFriendships().iterateOnList(node1 -> {
                Friendship friendship = node1.getElement();
                System.err.print(friendship.getPerson().getName() + "(" + friendship.getDegree() + ") ");
            });
            System.err.println();
            person.getInterests().iterateOnList(node1 -> {
                Interest interest = node1.getElement();
                System.err.print(interest.getSubject().getName() + "(" + interest.getDegree() + ") "); 
            });
            System.err.println("\n//");

        });     
    }

}



class Network {
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

class NetworkSet {
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

class Person implements Comparable<Person> {
    private String name;
    private Set <Interest> interests;
    private Set<Friendship> friendships;
    private int idInNetworkSet;
    Person(String name, int idInNetworkSet) {
        this.name = name;
        this.idInNetworkSet = idInNetworkSet;
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

    @Override
    public String toString() {
        return "Person [name=" + name + "]";
    }

	public Integer getIdInNetworkSet() {
		return idInNetworkSet;
	}
    
}
class Subject implements Comparable<Subject>{
    private String name;
    private int idInNetworkSet;
    public String getName(){
        return name;
    }

    Subject(String name, int idInNetworkSet) {
        this.name = name;
        this.idInNetworkSet = idInNetworkSet;
    }

    @Override
    public int compareTo(Subject subject) {
        return name.compareTo(subject.name);
    }

    @Override
    public String toString() {
        return "Subject [name=" + name + "]";
    }

    public int getIdInNetworkSet(){
        return idInNetworkSet;
    }
    
}
class Matrix {
    private Map<Integer, Map<Integer, Double>> columns;

    public Matrix() {
        columns = new Map<Integer, Map<Integer, Double>>();
    }
    public Matrix(Map<Integer, Map<Integer, Double>> map) {
        this.columns = map;
    }

    public Matrix getTranspose() throws Exception {
        Matrix res = new Matrix();

        Node<Pair<Integer, Map<Integer, Double>>> pointer = columns.getFirst();
        while (pointer != null) {
            Map<Integer, Double> column = pointer.getElement().getSecond();

            int i = pointer.getElement().getFirst();

            Node<Pair<Integer, Double>> pointer1 = column.getFirst();
            while (pointer1 != null) {
                int j = pointer1.getElement().getFirst();
                double value = pointer1.getElement().getSecond();

                if (!res.columns.containsKey(j))
                    res.columns.put(j, new Map<Integer, Double>());
                if (!res.columns.get(j).containsKey(i))
                    res.columns.get(j).put(i, 0D);

                res.columns.get(j).put(i, res.columns.get(j).get(i) + value);

                pointer1 = pointer1.getNext();
            }
            pointer = pointer.getNext();
        }

        return res;
    }

    public Matrix product(Matrix B) {
        Matrix res = new Matrix();
        Node<Pair<Integer, Map<Integer, Double>>> ACP = this.columns.getFirst();
        if (ACP == null)
            return res;
        Node<Pair<Integer, Map<Integer, Double>>> RCP = new Node<Pair<Integer, Map<Integer, Double>>>(
                new Pair<Integer, Map<Integer, Double>>(ACP.getElement().getFirst(), new Map<Integer, Double>()));

        while (ACP != null) {
            Node<Pair<Integer, Double>> AIJ = ACP.getElement().getSecond().getFirst();
            Node<Pair<Integer, Map<Integer, Double>>> BCP = B.columns.getFirst();
            while (AIJ != null && BCP != null) {
                // getting two pointers on same place
                while (AIJ != null && AIJ.getElement().getFirst().compareTo(BCP.getElement().getFirst()) < 0)
                    AIJ = AIJ.getNext();
                if (AIJ == null)
                    break;
                while (BCP != null && BCP.getElement().getFirst().compareTo(AIJ.getElement().getFirst()) < 0)
                    BCP = BCP.getNext();
                if (BCP == null)
                    break;
                // ---------------

                Node<Pair<Integer, Double>> BIJ = BCP.getElement().getSecond().getFirst();
                while (BIJ != null) {
                    int i = ACP.getElement().getFirst();
                    int j = BIJ.getElement().getFirst();
                    res.setValue(i, j,
                            res.getValueOf(i, j) + AIJ.getElement().getSecond() * BIJ.getElement().getSecond());
                    BIJ = BIJ.getNext();
                }

                // moving further
                AIJ = AIJ.getNext();
                BCP = BCP.getNext();
            }

            ACP = ACP.getNext();
            if (ACP != null)
                RCP.setNext(new Node<Pair<Integer, Map<Integer, Double>>>(new Pair<Integer, Map<Integer, Double>>(
                        ACP.getElement().getFirst(), new Map<Integer, Double>())));
            RCP = RCP.getNext();
        }
        return res;
       
    }

    public void iterateOnMatrix(Preformer preformer) {
        Node<Pair<Integer, Map<Integer, Double>>> ACP = columns.getFirst();
        while (ACP != null) {

            int i = ACP.getElement().getFirst();
            Node<Pair<Integer, Double>> ARP = ACP.getElement().getSecond().getFirst();
            while (ARP != null) {
                int j = ARP.getElement().getFirst();

                preformer.preform(i, j, ARP.getElement().getSecond());

                ARP = ARP.getNext();
            }

            ACP = ACP.getNext();
        }

    }

    public static interface Preformer {
        void preform(int i, int j, double value);
    }

    public Matrix optimizedProduct(Matrix B) {
        try {
            B = B.getTranspose();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Matrix res = new Matrix();

        Node<Pair<Integer, Map<Integer, Double>>> pointerOnColumnsOfA = columns.getFirst();
        while (pointerOnColumnsOfA != null) {
            int i = pointerOnColumnsOfA.getElement().getFirst();
            res.columns.put(i, new Map<Integer, Double>());

            Node<Pair<Integer, Map<Integer, Double>>> pointerOnColumnsOfB = B.columns.getFirst();
            while (pointerOnColumnsOfB != null) {
                int j = pointerOnColumnsOfB.getElement().getFirst();
                double sum = 0;

                Node<Pair<Integer, Double>> pointerOnRowsOfA = pointerOnColumnsOfA.getElement().getSecond().getFirst();
                Node<Pair<Integer, Double>> pointerOnRowsOfB = pointerOnColumnsOfB.getElement().getSecond().getFirst();

                while (pointerOnRowsOfA != null && pointerOnRowsOfB != null) {
                    while (pointerOnRowsOfA != null
                            && pointerOnRowsOfA.getElement().getFirst() < pointerOnRowsOfB.getElement().getFirst())
                        pointerOnRowsOfA = pointerOnRowsOfA.getNext();

                    if (pointerOnRowsOfA == null)
                        break;

                    while (pointerOnRowsOfB != null
                            && pointerOnRowsOfB.getElement().getFirst() < pointerOnRowsOfA.getElement().getFirst())
                        pointerOnRowsOfB = pointerOnRowsOfB.getNext();

                    if (pointerOnRowsOfB == null)
                        break;

                    sum += pointerOnRowsOfA.getElement().getSecond() * pointerOnRowsOfB.getElement().getSecond();

                    pointerOnRowsOfA = pointerOnRowsOfA.getNext();
                    pointerOnRowsOfB = pointerOnRowsOfB.getNext();
                }
                if (sum > 0)
                    try {
                        res.columns.get(i).put(j, sum);
                    } catch (Exception e) {
                    }
                pointerOnColumnsOfB = pointerOnColumnsOfB.getNext();
            }
            pointerOnColumnsOfA = pointerOnColumnsOfA.getNext();
        }
        return res;
    }

    public double getValueOf(int i, int j) {
        if (!columns.containsKey(i))
            return 0;
        try {
            if (!columns.get(i).containsKey(j))
                return 0;
            return columns.get(i).get(j);

        } catch (Exception e) {
            return 0;
        }
    }

    public void setValue(int i, int j, double value) {
        if (!columns.containsKey(i))
            columns.put(i, new Map<Integer, Double>());
        try {
            columns.get(i).put(j, value);
        } catch (Exception e) {
        }
    }

    @Override
    public String toString() {
        return "Matrix [columns=" + columns + "]";
    }

}

class Set<T extends Comparable<T>> extends List<T> {
    @Override
    public void add(T element) {
        if (contains(element))
            return;
        if (first == null) {
            first = new Node<T>(element);
            ++size;
            return;
        }
        if(first.getElement().compareTo(element) > 0) {
            Node<T> node = new Node<T>(element);
            node.setNext(first);
            first = node;
            ++size;
            return;
        }
        Node<T> pointer = first;
        while (pointer.getNext() != null && pointer.getNext().getElement().compareTo(element) < 0)
            pointer = pointer.getNext();

        Node<T> node = new Node<T>(element);
        node.setNext(pointer.getNext());    
        pointer.setNext(node);
        ++size;
        
    }

}


class Pair <T1, T2> implements Comparable<Pair<T1, T2>>{
    private T1 first;
    private T2 second;
    public Pair(T1 first, T2 second){
        this.first = first;
        this.second = second;
    }

    public T1 getFirst(){
        return first;
    }
    public T2 getSecond(){
        return second;
    }

    public void setFirst(T1 first) {
        this.first = first;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "Pair [first=" + first + ", second=" + second + "]";
    }

    @Override
    public int compareTo(Pair<T1, T2> arg0) {
        int r = ((Comparable)first).compareTo(arg0.first);
        if (r == 0)
            return ((Comparable)second).compareTo(arg0.second);
        return r;
    }

    
}


class Node<T> {
    private T element;
    private Node<T> next;
    public Node(T element){
        this.element = element;
    }
    public T getElement() {
        return element;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
    
}

class Map<T1 extends Comparable<T1>, T2> {
    private Node<Pair<T1, T2>> first = null;
    private int size = 0;

    public void put(T1 key, T2 value) {
        if (containsKey(key)) {
            iterateOnMap(node -> {
                if (node.getElement().getFirst().equals(key))
                    node.getElement().setSecond(value);
            });
            return;
        }
        Node<Pair<T1, T2>> node = new Node<Pair<T1, T2>>(new Pair<T1, T2>(key, value));
        if (first == null) {
            first = node;
        } else {
            if (first.getElement().getFirst().compareTo(key) >= 0) {
                node.setNext(first);
                first = node;
            } else {
                Node<Pair<T1, T2>> pointer = first;
                while (pointer.getNext() != null && pointer.getNext().getElement().getFirst().compareTo(key) < 0)
                    pointer = pointer.getNext();

                node.setNext(pointer.getNext());
                pointer.setNext(node);
            }

        }
        ++size;
    }

    public T2 get(T1 key) throws Exception {
        Node<Pair<T1, T2>> pointer = first;
        while (pointer != null && pointer.getElement().getFirst().equals(key) == false)
            pointer = pointer.getNext();

        if (pointer == null)
            throw new Exception("no such key in the map: " + this);

        return pointer.getElement().getSecond();
    }

    public boolean containsKey(T1 key) {
        Node<Pair<T1, T2>> pointer = first;
        while (pointer != null && pointer.getElement().getFirst().equals(key) == false)
            pointer = pointer.getNext();

        if (pointer == null)
            return false;

        return true;

    }

    public void removeKey(T1 key) {
        if (!containsKey(key))
            return;
        Node<Pair<T1, T2>> pointer = first;
        while (pointer != null) {
            if (pointer.getNext() != null && pointer.getNext().getElement().getFirst().equals(key)) {
                pointer.setNext(pointer.getNext().getNext());
                size--;
                return;
            }
            pointer = pointer.getNext();
        }
    }

    public void iterateOnMap(Preformer<T1, T2> preformer) {
        Node<Pair<T1, T2>> pointer = first;
        while (pointer != null) {
            preformer.preform(pointer);
            pointer = pointer.getNext();
        }
    }

    public interface Preformer<T1, T2> {
        void preform(Node<Pair<T1, T2>> node);
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        String res = "{";
        Node<Pair<T1, T2>> pointer = first;
        while (pointer != null) {
            res += pointer.getElement() + ", ";
            pointer = pointer.getNext();
        }
        res += "}";
        return res;

    }

    public Node<Pair<T1, T2>> getFirst() {
        return first;
    }
}


class List<T> {
    protected Node<T> first = null;
    protected int size = 0;
    public void add(T element){
        if (first == null) {
            first = new Node<T>(element);
            ++size;
            return;
        }
        Node<T> pointer = first;
        while(pointer.getNext() != null)
            pointer = pointer.getNext();

        pointer.setNext(new Node<T>(element));
        size++;
    }

    public boolean contains(T element){
        Node<T> pointer = first;
        while (pointer != null) {
            if (pointer.getElement().equals(element))
                return true;
            pointer = pointer.getNext();
        }
        return false;
    }

    public void removeFirst(T element){
        Node<T> pointer = first;
        while (pointer != null) {
            if (pointer.getNext() != null && pointer.getNext().getElement().equals(element)) {
                pointer.setNext(pointer.getNext().getNext());
                size--;
                return;
            }
        }
    }

    public void iterateOnList(Preformer<T> preformer) {
        Node<T> pointer = first;
        while(pointer != null) {
            preformer.preform(pointer);
            pointer = pointer.getNext();
        }   
    }

    public interface Preformer<T>{
        void preform(Node<T> node);
    }
    public int getSize(){
        return size;
    }
    public Node<T> getFirst() {
        return first;
    }

    @Override
    public String toString() {
        String res = "{";
        Node<T> pointer = first;
        while (pointer != null) {
            res += pointer.getElement() + ", ";
            pointer = pointer.getNext();
        }
        return res + "}";
    }

    
}

class Friendship implements Comparable<Friendship>{
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

class Interest implements Comparable<Interest> {
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

class SearchEngine {
    private Matrix peopleToSubjects;
    private Matrix peopleToPeople;
    private NetworkSet networkSet;

    public void respondeToQuary(Query query)  {
        List<Integer> indexOfSubjects = new List<Integer>();
        query.subjects.iterateOnList(node -> {
            try {
                indexOfSubjects.add(node.getElement().getIdInNetworkSet());
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
                            + peopleToSubjects.getValueOf(i, node1.getElement().getIdInNetworkSet()));
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
        Map<Integer, Map<Integer, Double>> map = new Map<Integer, Map<Integer, Double>>();
        map.put(0, new Map<Integer, Double>());
        Node<Pair<Integer, Map<Integer, Double>>> column = map.getFirst();
        Node<Pair<Integer, Person>> node = networkSet.getPeople().getFirst();
        while(node != null) {
            Person person = node.getElement().getSecond();
            int i = person.getIdInNetworkSet();
            Node <Friendship> friendshipNode = person.getFriendships().getFirst();
            while(friendshipNode != null) {
                int j = friendshipNode.getElement().getPerson().getIdInNetworkSet();

                // peopleToPeople.setValue(i, j, peopleToPeople.getValueOf(i, j) + friendshipNode.getElement().getDegree());
                try{
                    column.getElement().getSecond().put(j, column.getElement().getSecond().get(j) + friendshipNode.getElement().getDegree());
                }catch(Exception e) {
                    column.getElement().getSecond().put(j, friendshipNode.getElement().getDegree());
                }
                friendshipNode = friendshipNode.getNext();
            }
           column.getElement().getSecond().put(i, 1D);
            column.setNext(new Node<Pair<Integer,Map<Integer, Double>>>(new Pair<Integer, Map<Integer, Double>>(i + 1, new Map<Integer, Double>())));
            column = column.getNext();
            // peopleToPeople.setValue(i, i, 1);
            node = node.getNext();
        }

        peopleToPeople = new Matrix(map);
    }

    private void initPeopleToSubjectsmatrix(NetworkSet networkSet) throws Exception {
        peopleToSubjects = new Matrix();
        
        for (int i = 0; i < networkSet.getPeople().getSize(); i++) {
            Person person = networkSet.getPersonWithIndex(i);
            Node <Interest> interestNode = person.getInterests().getFirst();
            while(interestNode != null) {
                int j = interestNode.getElement().getSubject().getIdInNetworkSet();
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
