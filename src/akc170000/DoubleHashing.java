/** @author Axat Chaudhary (akc170000), Shreeya Girish Degaonkar (sxd174830)
 *  Implementation of Double Hashing algorithm with methods add, contains and remove
 *  with the objective of comparing its performance against java's implementation of hashing
 */
package akc170000;


public class DoubleHashing<T> {
	private static final float LF_THRESHOLD = 0.5f;
	private static final int PRIME = 11;
	Object[] table;
	boolean deleted[];
	int size;
	int loc;
	public DoubleHashing() {
		size =0;
		table = new Object[32];
		deleted = new boolean[32];
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

    /**
     *
     * @return int, the size of table
     */
    public int size() {
    	return size;
    }

    /**
     * @return float, the value of loadFactor
     */
    private float loadFactor() {
        return ((float) size) / table.length;
    }

    /**
     * method to rehash, when the load factor is above 0.5
     */
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

    /**
     *
     * @param x
     * @return int, the value of h2 calculated using h(x)
     */
	private int h2(T x) {
		return (PRIME - (h(x) % PRIME));
	}

	/**
	 *
	 * @param k
	 * @param x
	 * @return int, the value of i calculated using h(x), h2(x) and k
	 */
	public int nextProbSeqNum(int k, T x) {
		return ((h(x) + (k*h2(x)))%table.length);
	}

	/**
     * @param x
     * @return int, the index of the element x
     */
	private int find(T x) {
		int index;
		int k=0;
		int i;
		while(true) {
			i=nextProbSeqNum(k,x);
			if (table[i] == null || (table[i].equals(x) && !deleted[i]))
			{
				return i;
			}
			else if(deleted[i]) {
				break;
			}
				k++;

		}
		index=nextProbSeqNum(k,x);
		while(true) {
			i = nextProbSeqNum(++k,x);
			if(x.equals(table[i]))
				return i;
			if(table[i]==null) {
				return index;
			}
		}
	}

	/**
     *
     * @param x
     * @return boolean, true if element present in table, false otherwise
     */
	public boolean contains(T x) {
		loc = find(x);
		if(x.equals(table[loc]) && !deleted[loc])
			return true;
		return false;
	}

	/**
     *
     * @param x
     * @return boolean value true if new element is added, false otherwise
     */
	public boolean add(T x) {
		if (loadFactor() >= LF_THRESHOLD) {
            rehash();
        }
		loc = find(x);
		if(x.equals(table[loc]) && !deleted[loc])
			return false;
		else {
			table[loc]=x;
			deleted[loc] = false;
			size++;
			return true;
		}
	}

	/**
	    *
	    * @param x
	    * @return T, the element if successfully deleted, null otherwise
	    */
	public T remove(T x) {
		T res;
		loc = find(x);
		if(x.equals(table[loc]) && !deleted[loc]) {
			res = (T) table[loc];
			deleted[loc] = true;
			size--;
			return res;
		}
		return null;
	}

}
