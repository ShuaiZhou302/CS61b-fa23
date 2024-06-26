public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given 2-3 TREE. */
    public RedBlackTree(TwoThreeTree<T> tree) {
        Node<T> ttTreeRoot = tree.root;
        root = buildRedBlackTree(ttTreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if (r == null) {
            return null;
        }

        if (r.getItemCount() == 1) {
            RBTreeNode<T> left = buildRedBlackTree(r.getChildAt(0));
            RBTreeNode<T> right = buildRedBlackTree(r.getChildAt(1));
            return new RBTreeNode<T>(true, r.getItemAt(0), left, right);
        } else {
            RBTreeNode<T> subRoot = new RBTreeNode<T>(true, r.getItemAt(1));
            subRoot.left = new RBTreeNode<T>(false, r.getItemAt(0));
            subRoot.left.left = buildRedBlackTree(r.getChildAt(0));
            subRoot.left.right = buildRedBlackTree(r.getChildAt(1));
            subRoot.right = buildRedBlackTree(r.getChildAt(2));
            return subRoot;
        }
    }

    /* Flips the color of node and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {
        if (node.isBlack) {
            if (isRed(node.left)) {
                node.left.isBlack = true; // turn it to black
                node.isBlack = false; // turn itself to red
            }
            if (isRed(node.right)) {
                node.right.isBlack = true;
                node.isBlack = false;
            }
        } else {
            if (node.left.isBlack) {
                node.isBlack = true;
                node.left.isBlack = false;
            }
            if (node.right.isBlack) {
                node.isBlack = true;
                node.right.isBlack = false;
            }
        }
    }

    /* Rotates the given node to the right. Returns the new root node of
       this subtree. For this implementation, make sure to swap the colors
       of the new root and the old root!*/
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        node.isBlack = false;
        RBTreeNode<T> newroot = node.left;
        newroot.isBlack = true;
        node.left = newroot.right;
        newroot.right = node;
        return newroot;
    }

    /* Rotates the given node to the left. Returns the new root node of
       this subtree. For this implementation, make sure to swap the colors
       of the new root and the old root! */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        node.isBlack = false;
        RBTreeNode<T> newroot = node.right;
        newroot.isBlack = true;
        node.right  = newroot.left;
        newroot.left = node;
        return newroot;
    }

    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /* Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class! */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // Insert (return) new red leaf node.
        if (node == null) {
            return new RBTreeNode<>(false, item);
        }

        // Handle normal binary search tree insertion.
        int comp = item.compareTo(node.item);
        if (comp == 0) {
            return node; // do nothing.
        } else if (comp < 0) {
            node.left = insert(node.left, item);
        } else {
            node.right = insert(node.right, item);
        }

        RBTreeNode<T> newRoot = node;
        if (node.isBlack) {
            if (isRed(node.right) && !(isRed(node.left))) {
                newRoot = rotateLeft(node);
            } else if (isRed(node.right) && isRed(node.left)) {
                flipColors(node);
            } else if (isRed(node.left)) {
                if (isRed(node.left.left)) {
                    newRoot = rotateRight(node);
                    flipColors(newRoot);
                } else if (isRed(node.left.right)) {
                    node.left = rotateLeft(node.left);
                    newRoot = rotateRight(node);
                    flipColors(newRoot);
                }
            } else if (isRed(node.right)) {
                if (isRed(node.right.right)) {
                    newRoot = rotateLeft(node);
                    flipColors(newRoot);
                } else if (isRed(node.right.left)) {
                    node.right = rotateRight(node.right);
                    newRoot = rotateLeft(node);
                    flipColors(newRoot);
                }
            }
        }
        return newRoot; //fix this return statement
    }

    /* Returns whether the given node is red. Null nodes (children of leaf
       nodes) are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

}
