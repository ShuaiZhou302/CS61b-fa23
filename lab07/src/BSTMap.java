import java.util.Iterator;
import java.util.Set;

public class BSTMap <K extends Comparable<K>,V> implements Map61B<K,V>{

    int size ;
    BSTNode root;

    BSTMap () {
        size = 0;
        root = null;
    }

    private class BSTNode {
        K key;
        V value;
        BSTNode left, right;

        BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    @Override
    public void put(K key, V value) {
        if (size == 0) {
            root = new BSTNode(key, value);
            size += 1;
        } else {
            BSTNode theOne = searchByKey(key, root);
            if (theOne != null) {
                theOne.value = value;
            } else {
                setNewNode(key, value, root);
                size += 1;
            }
        }
    }

    @Override
    public V get(K key) {
        if (size == 0) {
            return null;
        }
        BSTNode theOne = searchByKey(key,root);
        return theOne.value;
    }

    // Help method 1
    public BSTNode searchByKey(K key, BSTNode curr) {
        if(curr == null ) {
            return null;
        } else if(key.compareTo(curr.key) > 0) {
            if (curr.right == null) {
                return null;
            }
            return  searchByKey(key, curr.right);
        } else if (key.compareTo(curr.key) < 0) {
            if (curr.left == null){
                return null;
            }
            return searchByKey(key, curr.left);
        }
        return curr;

    }

    // Help method 2
    public void setNewNode(K key,V value, BSTNode curr){
        if(key.compareTo(curr.key) > 0) {
            if (curr.right != null) {
                setNewNode(key,value,curr.right);
            } else {
                curr.right = new BSTNode(key, value);
            }
        } else if (key.compareTo(curr.key) < 0){
            if(curr.left != null) {
                setNewNode(key, value, curr.left);
            } else {
                curr.left = new BSTNode(key, value);
            }
        }
    }

    @Override
    public boolean containsKey(K key) {
        return (searchByKey(key,root) != null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
