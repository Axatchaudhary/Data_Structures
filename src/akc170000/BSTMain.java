package akc170000;

public class BSTMain {

    public static void main(String[] args) {
	// write your code here
        BinarySearchTree<Double> bst = new BinarySearchTree<>();
        Double[] a = {5.0,3.0,10.0,1.0,7.0,6.0,8.0,15.0,13.0,22.0,11.0,14.0,12.0};
        for (Double i : a){
            bst.add(i);
            bst.printTree();
        }

        for (Double i = 0.0; i < 30.0; i+=1.0 ){
            System.out.println(i+": "+bst.successor(i));
        }

        BSTMap<Double,Double> bstm = new BSTMap<>();
        for (Double i : a){
            bstm.put(i,i*i);
        }
    }
}
