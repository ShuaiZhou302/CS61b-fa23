public class UnionFind {
    /**
     * DO NOT DELETE OR MODIFY THIS, OTHERWISE THE TESTS WILL NOT PASS.
     * You can assume that we are only working with non-negative integers as the items
     * in our disjoint sets.
     */
    private int[] data;
    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        data = new int[N];
        for (int i = 0; i < N; i++) {
            data[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        if (v >= 0 && v < data.length) {
            if (data[v] < 0) {
                return -data[v];
            }
            return sizeOf(data[v]);
        } else {
            throw new IllegalArgumentException("Out of bounds input");
        }
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if (v >= 0 && v < data.length) {
            return data[v];
        } else {
            throw new IllegalArgumentException("Out of bounds input");
        }
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        if (v1 >= 0 && v1 < data.length && v2 >= 0 && v2 < data.length) {
            return find(v1) == find(v2);
        } else {
            throw new IllegalArgumentException("Out of bounds input");
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v >= 0 && v < data.length) {
            int root = findHelpermethod1(v);
            if (v != root) {
                findHelpermethod2(root, v);
            }
            return root;
        } else {
            throw new IllegalArgumentException("Cannot find an out of range vertex!");
        }
    }

    public int findHelpermethod1(int v) {
        if (data[v] < 0) {
            return v;
        }
        return findHelpermethod1(data[v]);
    }

    public void findHelpermethod2(int root, int v) {
        if (data[v] != root) {
            int next = data[v];
            data[v] = root;
            findHelpermethod2(root, next);
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        if (v1 >= 0 && v1 < data.length && v2 >= 0 && v2 < data.length) {
            int root1 = find(v1);
            int root2 = find(v2);
            if (root1 != root2) {
                int size1 = sizeOf(v1);
                int size2 = sizeOf(v2);
                if (size1 > size2) { // so v2 should be under v1
                    data[root2] = root1;
                    data[root1] = -(size1 + size2);
                } else {      // v1 should be tied to v2
                    data[root1] = root2;
                    data[root2] = -(size1 + size2);
                }
            }
        } else {
            throw new IllegalArgumentException("Cannot union with an out of range vertex!");
        }
    }

    /**
     * DO NOT DELETE OR MODIFY THIS, OTHERWISE THE TESTS WILL NOT PASS.
     */
    public int[] returnData() {
        return data;
    }
}
