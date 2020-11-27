package DataStructures;

public class Set<T extends Comparable<T>> extends List<T>{
    @Override
    public void add(T element){
        if (first == null) {
            first = new Node<T>(element);
            ++size;
            return;
        }
        Node<T> pointer = first;
        while(pointer.getNext() != null && pointer.getNext().getElement().compareTo(element) < 0)
            pointer = pointer.getNext();

        if (pointer.getNext() == null) {
            pointer.setNext(new Node<T>(element));
            ++size;
            return;
        }

        if (pointer.getNext().getElement().compareTo(element) == 0)
            return;

        Node<T> node = new Node<T>(element);
        node.setNext(pointer.getNext());
        pointer.setNext(node);
        ++size;
    }
    
}
