package core.mapGenerate;

public class UnionFind {
    /**
     * DO NOT DELETE OR MODIFY THIS, OTHERWISE THE TESTS WILL NOT PASS.
     * You can assume that we are only working with non-negative integers as the items
     * in our disjoint sets.
     */

    private int[] data;
    private boolean[] setToChange;

    public int[] getData() {
        return data;
    }

    public boolean[] getSetToChange() {
        return setToChange;
    }

    /* Creates a UnionFind data structure holding N items. Initially, all
           items are in disjoint sets. */
    public UnionFind(int N) {
        data = new int[N];
        setToChange = new boolean[N];
        for (int i = 0; i < N; i += 1) {
            data[i] = -1;
            setToChange[i] = false;
        }

    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return -data[find(v)];

    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        //        int r = v;
        //        if (data[v] != -1) {
        //            return data[v];
        //        }
        //        return -1;
        return data[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        if (find(v1) == find(v2)) {
            return true;
        }
        return false;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v >= data.length || v < 0) {
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }
        //        else if(v < data.length) {
        //            if (parent(v) > -1) {
        //                setToChange[v] = true ;
        //                v = parent(v);
        //                return find(v) ;
        //            }
        //            if (parent(v) <= -1) {
        //                data = setToChange(v);
        //                return v;
        //            }
        //        }
        //        return 999;
        if (parent(v) < 0) {
            return v;
        }
        int root = find(parent(v));
        data[v] = root;
        return root;
    }

    public int[] setToChange(int v) {
        for (int i = 0; i < data.length; i += 1) {
            if (setToChange[i]) {
                data[i] = v;
            }
        }
        for (int i = 0; i < data.length; i += 1) {
            setToChange[i] = false;
        }
        return data;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        if (find(v1) != find(v2)) {
            if (sizeOf(v1) > sizeOf(v2)) {
                int a = data[find(v2)];
                data[find(v2)] = find(v1);
                data[find(v1)] = -sizeOf(v1) + a;
            } else if (sizeOf(v1) < sizeOf(v2)) {
                int a = data[find(v1)];
                data[find(v1)] = find(v2);
                data[find(v2)] = a - sizeOf(v2);
            } else if (sizeOf(v1) == sizeOf(v2)) {
                int a = data[find(v1)];
                data[find(v1)] = find(v2);
                data[find(v2)] = a - sizeOf(v2);
            }
        }
    }

    /**
     * DO NOT DELETE OR MODIFY THIS, OTHERWISE THE TESTS WILL NOT PASS.
     */
    public int[] returnData() {
        return data;
    }
}

