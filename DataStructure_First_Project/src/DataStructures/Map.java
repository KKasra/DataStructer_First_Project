package DataStructures;

public class Map<T1 extends Comparable<T1>, T2>{
    private Node<Pair<T1, T2>> first = null;
    private int size = 0;
    public void put(T1 key, T2 value){
        if (!containsKey(key)){
            iterateOnMap(node -> {
                if (node.getElement().getFirst().compareTo(key) < 0 &&
                    node.getNext().getElement().getFirst().compareTo(key) > 0){
                        
                        Node<Pair <T1, T2>> newNode = new Node<Pair <T1, T2>>(new Pair<T1, T2>(key, value));
                        
                        newNode.setNext(node.getNext());
                        node.setNext(newNode);
                 }
            });
            size++;
            return;
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
            throw new Exception("no such key in the map: " + this.hashCode());
        
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
}
