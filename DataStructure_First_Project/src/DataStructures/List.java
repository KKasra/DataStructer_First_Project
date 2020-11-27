package DataStructures;

public class List<T> {
    protected Node<T> first = null;
    protected int size = 0;
    public void add(T element){
        if (first == null) {
            first = new Node<T>(element);
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

}
