package DataStructures;

public class Set<T extends Comparable<T>> extends List<T> {
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
