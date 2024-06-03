package deque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private int first;
    private int last;
    private int capacity; // initial capacity
    private final int specialCheckpoint = 16;

    private T[] items;

    public ArrayDeque() {
        items =  (T[]) new Object[8];
        this.capacity = items.length;
        first = this.capacity - 1;
        last = 0;
        size = 0;
    }

    private void resize(int newCapacity) {
        T[] temp = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            temp[i] = items[((first + 1 + i) % capacity)];
        }
        this.capacity = temp.length;
        first = this.capacity - 1;
        last = size;
        items = temp;
    }
    @Override
    public void addFirst(T x) {
        if (size == this.capacity) {
            resize(2 * this.capacity);
        }
        items[first] = x;
        size++;
        first = first == 0 ? this.capacity - 1 : first - 1;
    }


    @Override
    public void addLast(T x) {
        if (size == this.capacity) {
            resize(2 * this.capacity);
        }
        items[last] = x;
        size++;
        last = (last + 1) % this.capacity;
    }

    @Override
    public List<T> toList() {
        ArrayList<T> listTo = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            listTo.add(items[((first + 1 + i) % capacity)]);
        }
        return listTo;
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
        }
        first = (first + 1) % this.capacity;
        T temp = items[first];
        items[first] = null;
        size -= 1;
        if (isEmpty()) {
            last = 0;
            first = this.capacity - 1;
            size = 0;
        }
        if (size < this.capacity / 4 && this.capacity > specialCheckpoint) {
            resize(this.capacity / 2);
        }
        return temp;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        last = last == 0 ? this.capacity - 1 : last - 1;
        T temp = items[last];
        items[last] = null;
        size -= 1;
        if (isEmpty()) {
            last = 0;
            first = this.capacity - 1;
            size = 0;
        }
        if (size < this.capacity / 4 && this.capacity > specialCheckpoint) {
            resize(this.capacity / 2);
        }
        return temp;
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index < size) {
            return items[((first + 1 + index) % capacity)];
        } else {
            return null;
        }
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    /** create iterator for arrayDeque*/
    private class ArrayDequeIterator implements Iterator<T> {
        private int wispos = (first + 1) % capacity;
        @Override
        public boolean hasNext() {
            return wispos != last;
        }

        @Override
        public T next() {
            T returnItem = items[wispos];
            wispos = (wispos + 1) % capacity;
            return  returnItem;
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
