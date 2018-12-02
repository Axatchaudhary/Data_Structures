package akc170000;
/** Sample starter code for SP9.
 *  @author Axat Chaudhari (akc170000)
 *  @author Shreeya Girish Degaonkar (sxd174830)
 */

import java.util.Random;

import rbk.Shuffle;
import rbk.Timer;

public class Sorting {
    public static Random random = new Random();
    public static int numTrials = 100;
    public static int T = 17;
    public static void main(String[] args) {
        int n = 10;  int choice = 1 + random.nextInt(4);
        if(args.length > 0) { n = Integer.parseInt(args[0]); }
        if(args.length > 1) { choice = Integer.parseInt(args[1]); }
        int[] arr = new int[n];
        for(int i=0; i<n; i++) {
            arr[i] = i;
        }
        Timer timer = new Timer();
        switch(choice) {
            case 1:
                Shuffle.shuffle(arr);
                numTrials = 1;
                insertionSort(arr);
                break;
            case 2:
                for(int i=0; i<numTrials; i++) {
                    Shuffle.shuffle(arr);
                    mergeSort1(arr);
                }
                break;
            case 3:
                for(int i=0; i<numTrials; i++) {
                    Shuffle.shuffle(arr);
                    mergeSort2(arr);
                }
                break;
            case 4:
                for(int i=0; i<numTrials; i++) {
                    Shuffle.shuffle(arr);
                    mergeSort3(arr);
                }
                break;
        }
        timer.end();
        timer.scale(numTrials);

        System.out.println("Choice: " + choice + "\n" + timer);
    }

    /**
     *
     * @param arr
     */
    public static void insertionSort(int[] arr) {
        insertionSort(arr, 0, arr.length);
    }

    /**
     * method to sort elements using insertion sort algorithm
     * @param arr
     * @param p
     * @param r
     */
    private static void insertionSort(int[] arr, int p, int r) {
        for(int i=p;i<r;i++) {
            int key = arr[i];
            int j = i-1;
            while(j>=p && arr[j]>key) {
                arr[j+1] = arr[j];
                j=j-1;
            }
            arr[j+1] =key;
        }
    }

    /**
     * MergeSort: Take1
     * @param arr
     */
    public static void mergeSort1(int[] arr) {
        mergeSort1(arr, 0, (arr.length-1));
    }

    private static void mergeSort1(int[] arr, int p, int r) {
        int q;
        if(p<r) {
            q = (p + r)/2;
            mergeSort1(arr, p, q);
            mergeSort1(arr, q+1, r);
            merge1(arr, p, q, r);
        }
    }

    private static void merge1(int[] arr, int p, int q, int r) {
        int [] l = new int[q-p+1];
        int[] m = new int[r-q];
        System.arraycopy(arr, p, l, 0, q-p+1);
        System.arraycopy(arr, q+1, m, 0, r-q);
        int i,j;
        i=0; j=0;
        for(int k=p; k<r;k++) {
            if(j>=m.length || (i<l.length && (l[i]<=m[j])))
                arr[k]= l[i++];
            else
                arr[k]=m[j++];

        }
    }
    //End of mergeSort: take1


    /**
     * MergeSort: take2
     * Avoid allocating l, m each time. Also, to stop using recursion when array size is below a threshold
     * @param arr
     */
    public static void mergeSort2(int[] arr) {
        mergeSort2(arr, 0, (arr.length-1));
    }

    private static void mergeSort2(int[] arr, int p, int r) {
        int q;
        int[] B = new int[arr.length];
        if(r-p+1 < T)
            insertionSort(arr, p, r);
        else
        {
            q = (r+p)/2;
            mergeSort2(arr, p, q);
            mergeSort2(arr,q+1,r);
            merge2(arr, B, p, q, r);
        }
    }

    private static void merge2(int[] arr, int[] B, int p, int q, int r) {
        System.arraycopy(arr, p, B, p, r-p+1);
        int i=p; int j=q+1;
        for(int k=p;k<r;k++) {
            if(j>r || (i<=q && (B[i]<=B[j])))
                arr[k] = B[i++];
            else
                arr[k] = B[j++];

        }
    }
    //End of mergeSort: take2

    /**
     * MergeSort: take3
     * To avoid unnecessary copying of values between the arrays
     * Also, to use while loop and make the program more efficient
     * @param arr
     */
    public static void mergeSort3(int[] arr) {
        int[] B = new int[arr.length];
        System.arraycopy(arr, 0, B, 0, arr.length);
        mergeSort3(arr, B, 0, arr.length);

    }

    private static void mergeSort3(int[] arr, int[] B, int left, int n) {
        if(n<T) {
            insertionSort(arr,left,left+n-1); //Call insertion sort if size of array is below the threshold value
        }
        else {
            int ln = (n)/2;
            mergeSort3(B, arr, left, ln);
            mergeSort3(B, arr, left+ln, n-ln);
            merge3(arr, B, left, left+ln-1, left+ln-1);
        }
    }

    private static void merge3(int[] arr, int[] B, int p, int q, int r) {
        int i, j, k;
        i=p; j=q+1; k=p;
        while(i<=q && j<=r) {
            if(B[i]<=B[j])
                arr[k++] = B[i++];
            else
                arr[k++] = B[j++];
        }
        while(i<=q)
            arr[k++] = B[i++];
        while(j<=r)
            arr[k++] = B[j++];
    }
    //End of mergeSort: take3



}

