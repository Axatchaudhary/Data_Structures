
/** Starter code for AVL Tree
 */
package akc170000;

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

    private void fixAVL(){

        while(!parentStack.isEmpty()) {
            BinarySearchTree.Entry parent = parentStack.pop();
            get(parent).height = 1 + Math.max(get(parent.left).height, get(parent.right).height);
            int leftHeight = parent.left == null ? -1 : get(parent.left).height;
            int rightHeight = parent.right == null ? -1 : get(parent.right).height;
            if (Math.abs(rightHeight - leftHeight) > 1){

            }else{
                break;
            }

        }
    }

    Entry get(BinarySearchTree.Entry x){
        return (Entry) x;
    }

    @Override
    public boolean add(T k){
        BinarySearchTree.Entry x = new BinarySearchTree.Entry(k,null,null);
        ((Entry) x).height = 0;
        if(super.add(x)){
            fixAVL();
            return true;
        }else{
            // adjust the height of current node
        }
        return false;
    }
}
