/**
 * SP_2 Submission: Main Function for demonstration
 *
 * @Authors: Jaiminee Kataria (msk180001)
 * Axat Kamleshkumar Chaudhari (akc170000)
 * Course:		CS 5V81.001 Implementation of Dala Structures & Algorithms
 * Date:		Sept 9, 2018
 */
package akc170000;

import java.util.Scanner;

public class BoundedQueueMain {

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
                    System.out.println("isEmpty: " + q.isEmpty());
                    break;
                case 5:
                    q.clear();
                    System.out.println(q);
                    break;
                case 6:
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
