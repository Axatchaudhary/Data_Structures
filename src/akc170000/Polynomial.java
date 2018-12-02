/**
 * Addition of polynomial
 * Author: Axat Kamleshkumar Chaudhari (akc170000)
 * */
package akc170000;

import java.util.Iterator;

public class Polynomial {
    SinglyLinkedList<Integer> poly;

    Polynomial(int[] a) {
        // polynomials are stored in decreasing order of degree: e.g. x^2 + 4x + 1 >> 1->4->1
        poly = new SinglyLinkedList<>();
        for (int i : a) {
            poly.add(i);
        }
    }
    Polynomial(){}

    Polynomial add(Polynomial otherPoly){

        int d1 = poly.size-1; // degree of polynomial 1
        int d2 = otherPoly.poly.size - 1; // degree of polynomial 2
        int md = d1 > d2 ? d1 : d2; // max of both degrees
        int mind = d1 > d2 ? d2 : d1;
        int i = 0;
        int[] result = new int[md+1]; // resultant array of degree = max(d(poly1), d(poly2)) in decreasing order of degree

        Iterator<Integer> p1 = poly.iterator();
        Iterator<Integer> p2 = otherPoly.poly.iterator();

        //whichever polynomial is the higher degree, copy coefficients of that polynomial to result array
        if (d1 > d2){
            for(i = 0; i < d1 - d2; i++){
                result[i] = p1.next();
            }
        }else if(d1 < d2) {
            for (i = 0; i < d2 - d1; i++) {
                result[i] = p2.next();
            }
        }
        // now iterator of both polynomials are at same degree point
        while (p1.hasNext()){
            result[i] = p1.next() + p2.next();
            i++;
        }

        return new Polynomial(result); // make and return result polynomial
    }
        public String toString(){
            String s = " ";
            int i = poly.size-1;
            for (int e : poly){
                s+=e+"x^"+i+"+ ";
                i--;
            }
            return s;
        }


    public static void main(String[] args){
        int[] a = {4,0,0,1,2};
        int[] b = {2,1,3};
        Polynomial p1 = new Polynomial(a);
        Polynomial p2 = new Polynomial(b);
        Polynomial ans = p1.add(p2);
        ans.poly.printList();

    }
}
