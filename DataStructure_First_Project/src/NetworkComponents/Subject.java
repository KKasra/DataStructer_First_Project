package NetworkComponents;

public class Subject implements Comparable<Subject>{
    private String name;
    public String getName(){
        return name;
    }

    Subject(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Subject subject) {
        return name.compareTo(subject.name);
    }

    @Override
    public String toString() {
        return "Subject [name=" + name + "]";
    }

    
}