package akc170000;

public class RobinHoodHashing<T> {
    private static final float LF_THRESHOLD = 0.5f;
    Object[] table;
    boolean deleted[];
    private int size; // #elements in the hash table

    RobinHoodHashing() {
        table = new Object[32];
        deleted = new boolean[32];
        size = 0;
    }

    private static int hash(int h) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private static int indexFor(int h, int length) {
        return h & (length - 1);
    }

    private int h(T x) {
        return indexFor(hash(x.hashCode()), table.length);
    }

    private int displacement(T x, int loc) {
        int ha = h(x);
        return loc >= ha ? loc - ha : table.length + loc - ha;
    }

    private void rehash() {
        Object[] oldTable = table;
        boolean[] oldDeleted = deleted;
        table = new Object[table.length * 2];
        deleted = new boolean[table.length];
        size = 0;
        for (int i = 0; i < oldTable.length; i++) {
            if (!oldDeleted[i] && oldTable[i] != null) {
                add((T) oldTable[i]);
            }
        }
    }

    public boolean remove(T x) {
        int loc = find(x);
        if (deleted[loc] || table[loc] == null) return false;
        deleted[loc] = true;
        size--;
        return true;
    }

    public int size() {
        return size;
    }

    public boolean add(T x) {
        if (contains(x)) return false;
        if (loadFactor() >= LF_THRESHOLD) {
            rehash();
        }
        int loc = h(x);
        int d = 0;
        while (true) {
            if (table[loc] == null || deleted[loc]) {
                table[loc] = x;
                deleted[loc] = false;
                size++;
                return true;
            }
            if (displacement((T) table[loc], loc) >= d) {
                d++;
                loc = loc < table.length - 1 ? (loc + 1) : 0;
            } else {
                T temp = (T) table[loc];
                table[loc] = x;
                x = temp;
                loc = loc < table.length - 1 ? (loc + 1) : 0;
                d = displacement(x, loc);
            }
        }
    }

    private float loadFactor() {
        return ((float) size) / table.length;
    }

    private int find(T x) {
        int i = h(x);
        while (true) {
            if (table[i] == null || ((T) table[i]).equals(x) && !deleted[i]) { // table[i] is free or equals x
                return i;
            } else if (deleted[i]) { // deleted.
                break;
            }
            i = i < table.length - 1 ? (i + 1) : 0; // next index in probing sequence. in RobinHood it is just next index
        }
        int xspot = i;
        while (true) {
            i = i < table.length - 1 ? (i + 1) : 0; // next index in probing sequence. in RobinHood it is just next index
            if (table[i] == null) {
                return xspot;
            }
            if (table[i].equals(x)) {
                return i;
            }
        }

    }

    public T get(T x) {
        int loc = find(x);
        if (contains(x)) {
            if (table[loc].equals(x)) {
                return (T) table[loc];
            }
        }
        return null;
    }

    public boolean contains(T x) {
        int loc = find(x);
        return x.equals((T) table[loc]) && !deleted[loc];
    }

}

