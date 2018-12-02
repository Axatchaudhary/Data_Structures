/**
 * SP_2 Submission: Bounded Queue
 *
 * @Authors:    Jaiminee Kataria
 *              Axat Kamleshkumar Chaudhari (akc170000)
 * Course:		CS 5V81.001 Implementation of Dala Structures & Algorithms
 * Date:		Sept 9, 2018
 */

package akc170000;

import java.util.Scanner;

public class BoundedQueue<T> {

    Object[] queue; // Generic array
    int front, rear; // front and back pointer
    int max_size, size; // size: current size of queue <= maxsize

    BoundedQueue(int m_size) {
        this.max_size = m_size;
        queue = new Object[m_size];
        front = rear = size = 0;
    }


    /** add a new element x at the rear of the queue
     *  returns false if the element was not added because the queue is full */
    boolean offer(T x) {
        if (size == max_size) {
            return false;
        }
        queue[rear] = x;
        size++;
        rear = (rear == max_size-1) ? 0 : rear + 1; // if reached to the end, turn back to the beginning
        return true;
    }

    /** remove and return the element at the front of the queue
     *  return null if the queue is empty */
    T poll() {
        if (isEmpty()) {
            return null;
        }
        T temp = (T) queue[front];
        size--;
        queue[front] = null;
        front = (front == max_size-1) ? 0 : front + 1; // if reached to the end, turn back to the beginning
        return temp;
    }

    /** return front element, without removing it (null if queue is empty) */
    T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T) queue[front];
    }

    int size(){
        return size;
    }
    /** check if queue is empty */
    boolean isEmpty() {
        return size == 0;
    }

    /** reset pointers and size to the starting position*/
    void clear() {
        // We do not have to allocate new empty object every-time because it puts load on memory management
        size = front = rear = 0;
    }

    /** just print the queue as it is so we can see the actual position of elements in queue */
    public String toString() {
        String s = " ";
        s += size + ": ";
        for (int i = 0; i < max_size; i++) {
            s += queue[i] + " ";
        }
        s += "\n";
        return s;
    }

    /** fill user supplied array with the elements of the queue, in queue order */
    void toArray(T[] a) {
        // TODO what if array size is smaller than queue?
        if (a.length < size){
            System.out.println("All elements cannot be accommodated in the array");
            return;
        }
        int c = front; // start of the queue
        for (int i = 0; i < size; i++) {
            a[i] = (T) queue[c];
            c = (c == max_size-1) ? 0 : c + 1; // reset to beginning if reached to the end of array
        }
    }

    public static void main(String[] args) {
        int n = 10;
        BoundedQueue q = new BoundedQueue(n);
        Scanner in = new Scanner(System.in);

        whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1:
                    System.out.print("Enter value: ");
                    System.out.println(q.offer(in.nextInt()));
                    System.out.println(q);
                    break;
                case 2:
                    Object removed = q.poll();
                    if (removed == null) {
                        System.out.println("Empty Queue");
                    } else {
                        System.out.println("removed: " + (int) removed);
                    }
                    System.out.println(q);
                    break;
                case 3:
                    Object front = q.peek();
                    if (front == null) {
                        System.out.println("Empty Queue");
                    } else {
                        System.out.println("peek: " + (int) front);
                    }
                    break;
                case 4:
                    System.out.println("Size of the queue: "+q.size());
                    break;
                case 5:
                    System.out.println("isEmpty: " + q.isEmpty());
                    break;
                case 6:
                    q.clear();
                    System.out.println(q);
                    break;
                case 7:
                    Integer[] newArray = new Integer[5];
                    q.toArray(newArray);
                    for (int i = 0; i < newArray.length; i++)
                        System.out.print(newArray[i] + " ");
                    System.out.println();
                    break;

                default:
                    break whileloop;

            }
        }


    }
}
