// Starter code for LP5

// Change to your netid
package akc170000;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class BinaryHeap<T extends Comparable<? super T>> {
    Comparable[] pq;
    int size;
    int maxCapacity;
    // Constructor for building an empty priority queue using natural ordering of T
    public BinaryHeap(int maxCapacity) {
        this.pq = new Comparable[maxCapacity];
        this.size = 0;
        this.maxCapacity = maxCapacity;
    }

    // add method: resize pq if needed
    public boolean add(T x) {
        if (size == maxCapacity){
            return false;
        }
        pq[size++] = x;
        percolateUp(size-1);
        return true;
    }

    public boolean offer(T x) {
        return add(x);
    }

    // throw exception if pq is empty
    public T remove() throws NoSuchElementException {
        T result = poll();
        if(result == null) {
            throw new NoSuchElementException("Priority queue is empty");
        } else {
            return result;
        }
    }

    // return null if pq is empty
    public T poll() {
        if (size == 0) {
            return null;
        }
        T temp = (T) pq[0];
        pq[0] = pq[--size];
        percolateDown(0);
        return temp;
    }

    public T min() {
        return peek();
    }

    // return null if pq is empty
    public T peek() {
        return size == 0 ? null : (T) pq[0];
    }

    int parent(int i) {
        return (i-1)>>1;
    }

    int leftChild(int i) {
        return (i<<1) + 1;
    }

    /** pq[index] may violate heap order with parent */
    void percolateUp(int index) {
        Comparable temp = pq[index]; // store value of ith element

        // go up and bring down elements while they are greater than 'temp'
        while (index > 0 && compare(temp, pq[parent(index)]) < 0){
//            pq[index] = pq[parent(index)]; // bringing down parent to one level below
            move(index, pq[parent(index)]);
            index = parent(index);
        }
        move(index, temp); // move saved element to empty position of heap
    }

    /** pq[index] may violate heap order with children */
    void percolateDown(int index) {
        Comparable temp = pq[index]; // save current element
        int c = leftChild(index); // left child
        // go down while current element is greater than its children
        while (c < size){ // if left child exists
            if (c < size - 1 && compare(pq[c + 1], pq[c]) < 0){ // if right child exists and it is less than left one
                c++; // change child to right child
            }
            // if current element is less than child then we have found the place for current element
            if (compare(temp, pq[c]) <= 0) break;
//            pq[index] = pq[c];
            move(index, pq[c]);
            index = c;
            c = leftChild(index); // update to next left child
        }
        move(index, temp); // move current element to its place
    }

    void move(int dest, Comparable x) {
        pq[dest] = x;
    }

    int compare(Comparable a, Comparable b) {
        return ((T) a).compareTo((T) b);
    }

    /** Create a heap.  Precondition: none. */
    void buildHeap() {
        for(int i=parent(size-1); i>=0; i--) {
            percolateDown(i);
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    // Resize array to double the current size
    void resize() {
        pq = Arrays.copyOf(pq, pq.length<<1);
    }

    public interface Index {
        public void putIndex(int index);
        public int getIndex();
    }

    public static class IndexedHeap<T extends Index & Comparable<? super T>> extends BinaryHeap<T> {
        /** Build a priority queue with a given array */
        public IndexedHeap(int capacity) {
            super(capacity);
        }

        /** restore heap order property after the priority of x has decreased */
        public void decreaseKey(T x) {
            pq[x.getIndex()] = x;
            percolateUp(x.getIndex());
        }

        @Override
        void move(int i, Comparable x) {
            super.move(i, x);
            ((T) x).putIndex(i);
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {0,9,7,5,3,1,8,6,4,2};
        BinaryHeap<Integer> h = new BinaryHeap(arr.length);

        System.out.print("Before:");
        for(Integer x: arr) {
            h.offer(x);
            System.out.print(" " + x);
        }
        System.out.println();

        for(int i=0; i<arr.length; i++) {
            arr[i] = h.poll();
        }

        System.out.print("After :");
        for(Integer x: arr) {
            System.out.print(" " + x);
        }
        System.out.println();
    }
}