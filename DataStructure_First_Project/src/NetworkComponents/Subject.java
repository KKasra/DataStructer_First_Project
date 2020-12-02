package NetworkComponents;

public class Subject implements Comparable<Subject>{
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