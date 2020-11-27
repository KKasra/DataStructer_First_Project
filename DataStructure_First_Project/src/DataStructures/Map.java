package DataStructures;

public class Map<T1 extends Comparable<T1>, T2>{
    private Node<Pair<T1, T2>> first = null;
    private int size = 0;
    public void put(T1 key, T2 value){
        if (first == null){
            first = new Node<Pair <T1, T2>>(new Pair<T1, T2>(key, value));
            ++size;
            return;
        }
        if (!containsKey(key)){
            Node <Pair <T1, T2>> newNode = new Node <Pair <T1, T2>>(new Pair<T1, T2>(key, value));
            newNode.setNext(first);
            first = newNode;
            ++size;
        }

        iterateOnMap(node -> {
            if (node.getElement().getFirst().equals(key))
                 node.getElement().setSecond(value);
        });
    }

    public T2 get(T1 key) throws Exception{
        Node<Pair<T1, T2>> pointer = first;
        while(pointer != null && pointer.getElement().getFirst().equals(key) == false)
            pointer = pointer.getNext();

        
        if (pointer == null)
            throw new Exception("no such key in the map: " + this);
        
        return pointer.getElement().getSecond();
    }

    public boolean containsKey(T1 key) {
        Node<Pair<T1, T2>> pointer = first;
        while(pointer != null && pointer.getElement().getFirst().equals(key) == false)
            pointer = pointer.getNext();

        if (pointer == null)
            return false;

        return true;

    }
    public void removeKey(T1 key) {
        if (!containsKey(key))
            return;
        Node<Pair <T1, T2>> pointer = first;
        while(pointer != null){
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
        while(pointer != null) {
            preformer.preform(pointer);
            pointer = pointer.getNext();
        }   
    }

    public interface Preformer<T1, T2>{
        void preform(Node<Pair<T1, T2>> node);
    }

    public int getSize(){
        return size;
    }  
    
    @Override
    public String toString() {
        String res = "{";
        Node<Pair<T1, T2>> pointer = first;
        while(pointer != null){
            res += pointer.getElement() + ", ";
            pointer = pointer.getNext();
        }
        res += "}";
        return res;

    }
    public Node<Pair <T1, T2>> getFirst() {
        return first;
    }
}
