package DataStructures;

public class Map<T1, T2>{
    private Node<Pair<T1, T2>> first = null;
    public void put(T1 key, T2 value){
        Node<Pair<T1, T2>> pointer = first;
        while(!pointer.getElement().getFirst().equals(key) && pointer.getNext() != null)
            pointer = pointer.getNext();

        if (pointer.getElement().getFirst().equals(key)){
            pointer.getElement().setSecond(value);
            return;
        }

        pointer.setNext(new Node<Pair<T1, T2>>(new Pair<T1, T2>(key, value)));
    }

    public T2 get(T1 key) throws Exception{
        Node<Pair<T1, T2>> pointer = first;
        while(pointer != null && pointer.getElement().getFirst().equals(key) == false)
            pointer = pointer.getNext();

        if (pointer == null)
            throw new Exception("no such key in the map:" + this.hashCode());
        
        return pointer.getElement().getSecond();
    }

    
}
