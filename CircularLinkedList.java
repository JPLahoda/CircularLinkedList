package circularlinkedlist;
import java.util.Iterator;
public class CircularLinkedList<E> implements Iterable<E> {
    Node<E> head;
    Node<E> tail;
    int size;

    // Implement a constructor
    public CircularLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    // Return Node<E> found at the specified index
    // Be sure to check for out of bounds cases
    private Node<E> getNode(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index + " is out of bounds");
        }
        Node<E> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    // Add a node to the end of the list
    // HINT: Use the overloaded add method as a helper method
    public boolean add(E item) {
        add(size, item);
        return true;
    }

    // Cases to handle:
    //      Out of bounds
    //      Adding an element to an empty list
    //      Adding an element to the front
    //      Adding an element to the end
    //      Adding an element in the middle
    // HINT: Remember to keep track of the list's size
    public void add(int index, E item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index + " is out of bounds");
        }
        Node<E> node = new Node<E>(item);
        if (size == 0) {
            head = node;
            tail = node;
            tail.next = head;
        } else if (index == 0) {
            node.next = head;
            head = node;
            tail.next = head;
        } else if (index == size) {
            tail.next = node;
            tail = node;
            tail.next = head;
        } else {
            Node<E> result = getNode(index);
            Node<E> prior = getNode(index - 1);
            node.next = prior.next;
            prior.next = node;
        }
        size++;
    }

    // Cases to handle:
    //      Out of bounds
    //      Removing the last element in the list
    //      Removing the first element in the list
    //      Removing the last element
    //      Removing an element from the middle
    // HINT: Remember to keep track of the list's size
    public E remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index + " is out of bounds");
        }
        E element = null;
        if (size == 1) {
            element = head.item;
            head = null;
            tail = null;
        } else if (index == 0) {
            element = head.item;
            head = head.next;
            tail.next = head;
        } else if (index == size) {
            Node<E> prevTail = getNode(size - 1);
            element = tail.item;
            tail = prevTail.next;
            tail.next = head;
        } else {
            Node<E> node = getNode(index);
            Node<E> prevNode = getNode(index - 1);
            prevNode.next = node.next;
            element = node.item;
        }
        size--;
        return element;
    }

    // Stringify your list
    // Cases to handle:
    //      Empty list
    //      Single element list
    //      Multi-element list
    // Use "==>" to delimit each node
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node<E> current = head;
        if (size == 0) {
            return "";
        }
        else if (size == 1) {
            return head.item.toString();
        }
        else {
            result.append(current.item);
            result.append(" ==> ");
            current = current.next;
            while (current != head){
                result.append(current.item);
                result.append(" ==> ");
                current = current.next;
            }
        }
        return result.toString();
    }

    public Iterator<E> iterator() {
        return new ListIterator<E>();
    }

    // This class is not static because it needs to reference its parent class
    private class ListIterator<E> implements Iterator<E> {
        Node<E> nextItem;
        Node<E> prev;
        int index;
        @SuppressWarnings("unchecked")

        // Creates a new iterator that starts at the head of the list
        public ListIterator() {
            nextItem = (Node<E>) head;
            index = 0;
        }

        // Returns true if there is a next node
        public boolean hasNext() {
            return size != 1;
        }

        // Advances the iterator to the next item
        // Should wrap back around to the head
        public E next() {
            prev =  nextItem;
            nextItem = nextItem.next;
            index =  (index + 1) % size;
            return prev.item;
        }

        // Remove the last node visited by the .next() call
        // For example, if we had just created an iterator,
        // the following calls would remove the item at index 1 (the second person in the ring)
        // next() next() remove()
        // Use CircularLinkedList.this to reference the CircularLinkedList parent class
        public void remove() {
            int integer = index;
            if(nextItem == head) {
                integer = size - 1;
            }
            else {
                if(index != 0) {
                    integer = index - 1;
                }
                index--;
            }
            CircularLinkedList.this.remove(integer);
        }
    }

    // The Node class
    private static class Node<E>{
        E item;
        Node<E> next;
        public Node(E item) {
            this.item = item;
        }
    }

    public static void main(String[] args) {

        //SEQUENCE 1
        CircularLinkedList<Integer> list =  new CircularLinkedList<Integer>();
        int n = 5;
        int k = 2;
        for(int i = 1; i < n+1; i++) {
            list.add(i);
        }
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()) {
            System.out.println(list);
            for(int i = 0; i < k; i++) {
                iterator.next();
            }
            iterator.remove();
        }

        System.out.println("");

        //SEQUENCE 2
        CircularLinkedList<Integer> list2 =  new CircularLinkedList<Integer>();
        int n2 = 13;
        int k2 = 2;
        for(int i = 1; i < n2+1; i++) {
            list2.add(i);
        }
        Iterator<Integer> iterator2 = list2.iterator();
        while(iterator2.hasNext()) {
            System.out.println(list2);
            for(int i = 0; i < k2; i++) {
                iterator2.next();
            }
            iterator2.remove();
        }
    }
}