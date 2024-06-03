package deque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {

    private int size;
    private Node<T> sentinel = new Node<>(null);

    /**
     * iterator method
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    /**
     * private iterator to linked list deque iterator
     */
    private class LinkedListDequeIterator implements Iterator<T> {
        private Node<T> wizpos;

        public LinkedListDequeIterator() {
            wizpos = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return wizpos != sentinel;
        }

        @Override
        public T next() {
            T returnItem = wizpos.item;
            wizpos = wizpos.next;
            return returnItem;
        }
    }

    private class Node<T> {
        T item;
        Node<T> pre;
        Node<T> next;

        private Node() {
        }

        private Node(T item) {
            this.item = item;
        }
    }

    public LinkedListDeque() {
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
        size = 0;
    } // null constructor


    @Override
    public void addFirst(T x) {
        Node<T> temp = new Node<T>(x); // make it an item for this list
        if (isEmpty()) {
            /*first = temp;
            last = temp;*/
            sentinel.next = temp;
            sentinel.pre = temp;
            temp.pre = sentinel;
            temp.next = sentinel;
        } else {
            /*temp.next = first;
            first.pre = temp;
            first = temp;*/
            temp.next = sentinel.next;
            sentinel.next.pre = temp;
            sentinel.next = temp;
            temp.pre = sentinel;
        }
        size += 1;
    }

    @Override
    public void addLast(T x) {
        Node<T> temp = new Node<T>(x);
        if (isEmpty()) {
            /*first = temp;
            last = temp;*/
            sentinel.next = temp;
            sentinel.pre = temp;
            temp.pre = sentinel;
            temp.next = sentinel;
        } else {
            /*temp.pre = last;
            last.next = temp;
            last = temp;*/
            temp.pre = sentinel.pre;
            sentinel.pre.next = temp;
            sentinel.pre = temp;
            temp.next = sentinel;
        }
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        if (size > 0) {
            for (Node<T> c = sentinel.next; c.item != null; c = c.next) {
                returnList.add(c.item);
            }
            return returnList;
        } else {
            return returnList;
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else if (size == 1) {
            T remove = sentinel.next.item;
            sentinel.next = sentinel;
            sentinel.pre = sentinel;
            size--;
            return remove;
        } else {
            T remove = sentinel.next.item;
            sentinel.next.next.pre = sentinel;
            sentinel.next = sentinel.next.next;
            size--;
            return remove;
        }
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else if (size == 1) {
            T remove = sentinel.pre.item;
            sentinel.pre = sentinel;
            sentinel.next = sentinel;
            size--;
            return remove;
        } else {
            T remove = sentinel.pre.item;
            sentinel.pre.pre.next = sentinel;
            sentinel.pre = sentinel.pre.pre;
            size--;
            return remove;
        }
    }

    @Override
    public T get(int index) {
        if ((index < 0 || index > size) || size == 0) {
            return null;
        } else {
            Node<T> c = sentinel.next;
            for (int i = 0; i < index; i++) {
                c = c.next;
            }
            return c.item;
        }
    }


    @Override
    public T getRecursive(int index) {
        if ((index < 0 || index > size) || size == 0) {
            return null;
        } else {
            return getRecursiveHelper(sentinel.next, index);
        }
    }

    // A method helps to get the item.
    private T getRecursiveHelper(Node<T> current, int index) {
        if (index == 0) {
            return current.item;
        } else {
            return getRecursiveHelper(current.next, index - 1);
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Deque tempList) {
            // check size.
            if (this.size != tempList.size()) {
                return false;
            }
            // check items are the same.
            for (int i = 0; i < size; i++) {
                if (!(this.get(i).equals(tempList.get(i)))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    /** help methods for equals*/
    public boolean contains(T x) {
        for (T i : this) {
            if (i == x) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String returnList = "[";
        for (int i = 0; i < this.size() - 1; i++) {
            returnList += get(i).toString();
            returnList += ", ";
        }
        returnList += get(this.size() - 1).toString();
        returnList += "]";
        return  returnList;
    }
}
