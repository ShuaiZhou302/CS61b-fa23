package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int size;
    private double loadFactor;
    private int curBks;

    private final int defaultCapa = 16;
    private final double defaultLoadFactor = 0.75;
    @Override
    public void put(K key, V value) {
        if (containsKey(key)) {
            valueChanger(key, value);
            return;
        }
        int bucket = bucketsFinder(key.hashCode());
        Node newnode = new Node(key, value);
        buckets[bucket].add(newnode);
        size += 1;
        if (((double) size / curBks) >= loadFactor) {
            resize();
        }
    }

    public void valueChanger(K key, V value) {
        int bucket = bucketsFinder(key.hashCode());
        for (Node n:buckets[bucket]) {
            if ((n.key).equals(key)) {
                n.value = value;
                return;
            }
        }
    }

    public void resize() {
        Collection<Node>[] newbuckets = new Collection[2 * curBks];
        for (int i = 0; i < newbuckets.length; i++) {
            newbuckets[i] = createBucket();
        }
        curBks = 2 * curBks;
        for (Collection<Node> bucket : buckets) {
            for (Node n : bucket) {
                int newbucket = bucketsFinder(n.key.hashCode());
                newbuckets[newbucket].add(n);
            }
        }
        buckets = newbuckets;
    }

    @Override
    public V get(K key) {
        V returnvalue = null;
        if (!containsKey(key)) {
            return returnvalue;
        }
        int bucket = bucketsFinder(key.hashCode());
        for (Node n:buckets[bucket]) {
            if ((n.key).equals(key)) {
                returnvalue = n.value;
                break;
            }
        }
        return returnvalue;
    }

    private int bucketsFinder(int hashcode) {
        if (hashcode >= 0) {
            return hashcode % curBks;
        } else {
            while (hashcode < 0) {
                hashcode += curBks;
            }
            return hashcode; // return until the hashcode turn positive
        }
    }

    @Override
    public boolean containsKey(K key) {
        int bucket = bucketsFinder(key.hashCode());
        boolean flag = false;
        for (Node n:buckets[bucket]) {
            if ((n.key).equals(key)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        buckets = new Collection[curBks];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> re = new HashSet<>(size);
        for (Collection<Node> cl: buckets) {
            for (Node n: cl) {
                re.add(n.key);
            }
        }
        return re;
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        int bucket = bucketsFinder(key.hashCode());
        V returnvalue = null;
        for (Node n: buckets[bucket]) {
            if ((n.key).equals(key)) {
                returnvalue = n.value;
                buckets[bucket].remove(n);
                break;
            }
        }
        size -= 1;
        if ((double) size / curBks < loadFactor) {
            resizedown();
        }
        return returnvalue;
    }

    private void resizedown() {
        Collection<Node>[] newbuckets = new Collection[curBks / 2];
        for (int i = 0; i < newbuckets.length; i++) {
            newbuckets[i] = createBucket();
        }
        curBks = curBks / 2;
        for (Collection<Node> bucket : buckets) {
            for (Node n : bucket) {
                int newbucket = bucketsFinder(n.key.hashCode());
                newbuckets[newbucket].add(n);
            }
        }
        buckets = newbuckets;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[defaultCapa];
        curBks = defaultCapa;
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        this.loadFactor = defaultLoadFactor;
        size = 0;
    }

    public MyHashMap(int initialCapacity) {
        buckets = new Collection[initialCapacity];
        curBks = initialCapacity;
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
        this.loadFactor = defaultLoadFactor;
        size = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        curBks = initialCapacity;
        buckets = new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
        this.loadFactor = loadFactor;
        size = 0;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<Node>();
    }

}
