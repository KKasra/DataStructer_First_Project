package DataStructures;

public class Pair <T1, T2> implements Comparable<Pair<T1, T2>>{
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
