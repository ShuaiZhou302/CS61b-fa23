package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> implements Deque<T> {

    private Comparator<T> comp;
    public MaxArrayDeque() {
        super();
    }
    /** New constructor*/
    public MaxArrayDeque(Comparator<T> c) {
        this.comp = c;
    }

    public T max() {
        if (this.size() == 0) {
            return null;
        }
        T max = get(0); // set max as the first one;
        for (T i: this) {
            if (comp.compare(max, i) < 0) {
                max = i;
            }
        }
        return max;
    }

    public T max(Comparator<T> c) {
        if (this.size() == 0) {
            return null;
        }
        T max = get(0); // set max as the first one;
        for (T i: this) {
            if (c.compare(max, i) < 0) {
                max = i;
            }
        }
        return max;
    }
}
