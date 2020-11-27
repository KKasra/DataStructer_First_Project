package NetworkComponents;

public class Subject implements Comparable<Subject>{
    private String name;
    public String getName(){
        return name;
    }

    public Subject(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Subject subject) {
        return name.compareTo(subject.name);
    }
    
    
}