
/** Starter code for AVL Tree
 */
package akc170000;

import java.util.Comparator;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    static class Entry<T> extends BinarySearchTree.Entry<T> {
        int height;
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            height = 0;
        }
    }

    // LL >> R(x)
    private BinarySearchTree.Entry case1(BinarySearchTree.Entry x){
        BinarySearchTree.Entry y = x.left;
        x.left = y.right;
        y.right = x;
        return y;
    }

    // LR >> L(y) R(x)
    private BinarySearchTree.Entry case2(Entry x){
        BinarySearchTree.Entry y = x.left;
        BinarySearchTree.Entry z = y.right;
        x.left = z.right;
        y.right = z.left;
        z.left = y;
        z.right = x;
        return z;
    }

    // RR >> L(x)
    private BinarySearchTree.Entry case3(Entry x){
        BinarySearchTree.Entry y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }

    // RL >> R(y) L(x)
    private BinarySearchTree.Entry case4(Entry x){
        BinarySearchTree.Entry y = x.right;
        BinarySearchTree.Entry z = y.left;
        x.right = z.left;
        y.left = z.right;
        z.right = y;
        z.left = x;
        return z;
    }
    AVLTree() {
        super();
    }


    private void fixAVL(BinarySearchTree.Entry x){
        BinarySearchTree.Entry parent = parentStack.pop();
    }

    @Override
    public boolean add(T k) {
        BinarySearchTree.Entry x = new BinarySearchTree.Entry(k,null,null);
        ((Entry) x).height = 0;
        if(super.add(x)){
            BinarySearchTree.Entry p_t = parentStack.pop(); // parent of the node x
            BinarySearchTree.Entry g_t =parentStack.pop(); // grand parent of node x

            return true;
        }else{
            // adjust the height of current node
        }
        return false;
    }
}
