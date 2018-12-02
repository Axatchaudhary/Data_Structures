/**
 * Set operations (union, intersection, difference) of lists
 * Author: Axat Kamleshkumar Chaudhari (akc170000)
 * */
package akc170000;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sets {



    public static <T extends Comparable<? super T>>
    void intersect(List<T> l1, List<T> l2, List<T> outList) {
        // Return elements common to l1 and l2, in sorted order.
        // outList is an empty list created by the calling
        // program and passed as a parameter.
        // Function should be efficient whether the List is
        // implemented using ArrayList or LinkedList.
        // Do not use HashSet/Map or TreeSet/Map or other complex
        // data structures.


        /**
         *  Algo: input l1 (list1), l2 (list2)
         *  while both list are not empty:
         *      if element of l1 is less than element of l2:
         *          get next element of l1
         *      else if element of l1 is greater than l2:
         *          get next element of l2
         *      else: //common entry exists:
         *          add it to result list
         * */
        Iterator<T> p = l1.iterator();
        Iterator<T> q = l2.iterator();

        T x = p.hasNext() ? p.next() : null;
        T y = q.hasNext() ? q.next() : null;
        while (x != null && y != null) {
            System.out.println(x + " " + y + "   P: " + p.hasNext() + " Q: " + q.hasNext());

            if (x.compareTo(y) < 0) {
                x = p.hasNext() ? p.next() : null;
            } else if (x.compareTo(y) > 0) {
                y = q.hasNext() ? q.next() : null;
            } else {
                outList.add(x);
                x = p.hasNext() ? p.next() : null;
                y = q.hasNext() ? q.next() : null;
            }

        }

    }

    public static <T extends Comparable<? super T>>
    void union(List<T> l1, List<T> l2, List<T> outList) {
        Iterator<T> p = l1.iterator();
        Iterator<T> q = l2.iterator();

        // get next elements
        T x = p.hasNext() ? p.next() : null;
        T y = q.hasNext() ? q.next() : null;

        while (x != null && y != null) { // while both of the lists are not empty


            if (x.compareTo(y) < 0) {
                // if x < y then, x goes first into result
                outList.add(x);
                x = p.hasNext() ? p.next() : null;
            } else if (x.compareTo(y) > 0) {
                // if x > y then y goes first in the result
                outList.add(y);
                y = q.hasNext() ? q.next() : null;
            } else {
                // both are same then add either of them in result and go next for both iterators
                outList.add(x);
                System.out.println((q.hasNext() && p.hasNext()));
                x = p.hasNext() ? p.next() : null;
                y = q.hasNext() ? q.next() : null;
            }
        }

        // if any list is remaining, add rest of the list to result
        while (x != null) {
            outList.add(x);
            x = p.hasNext() ? p.next() : null;
        }
        while (y != null) {
            outList.add(y);
            y = q.hasNext() ? q.next() : null;
        }
    }
    // Return the union of l1 and l2, in sorted order.
    // Output is a set, so it should have no duplicates.


    public static <T extends Comparable<? super T>>
    void difference(List<T> l1, List<T> l2, List<T> outList) {
        // only add non common element of list 1 to the result
        Iterator<T> p = l1.iterator();
        Iterator<T> q = l2.iterator();
        int i = l1.size();
        int j = l2.size();
        int k = i < j ? i : j;
        int c = 0;
        T x = p.hasNext() ? p.next() : null;
        T y = q.hasNext() ? q.next() : null;
        while (x != null && y != null) {
            System.out.println(x + " " + y + "   P: " + p.hasNext() + " Q: " + q.hasNext());

            if (x.compareTo(y) != 0) {
                // if x and y don't match, add x to list
                outList.add(x);
                x = p.hasNext() ? p.next() : null;
            } else{
                System.out.println((q.hasNext() && p.hasNext()));
                x = p.hasNext() ? p.next() : null;
                y = q.hasNext() ? q.next() : null;
            }
            c++;
        }
        while (x != null) {
            outList.add(x);
            x = p.hasNext() ? p.next() : null;
        }
        // Return l1 - l2 (i.e, items in l1 that are not in l2), in sorted order.
        // Output is a set, so it should have no duplicates.
    }

    public static void main(String[] args) {
        ArrayList<Integer> l1 = new ArrayList<>();
        ArrayList<Integer> l2 = new ArrayList<>();
        ArrayList<Integer> out = new ArrayList<>();
        int[] a = {1,2,3};
        int[] b = {1,2};
        for (int x : a)
            l1.add(x);
        for (int x : b)
            l2.add(x);

//        intersect(l1, l2, out);
//        union(l1, l2, out);
        difference(l1, l2, out);
        System.out.println(out.toString());


    }
}
