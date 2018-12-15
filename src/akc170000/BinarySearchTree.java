/**
 * Short Project 4: Binary Search Tree
 *
 * @author Axat Chaudhari (akc170000)
 * @author jaiminee kataria (jxk172330)
 **/

package akc170000;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * BST where each node in the tree has two child left and right.
 * @param <T> generic type key/element which is comparable
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    protected Entry<T> root;
    protected int size; // #elements present in tree
    protected Stack<Entry<T>> parentStack; // parent stack to keep track of search path
    public BinarySearchTree() {
        root = null;
        size = 0;
        parentStack = new Stack<>();
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int x = in.nextInt();
            if (x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                System.out.println(t.height());
//                t.printTree();
                System.out.println(t);
            } else if (x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
//                t.printTree();
                System.out.println(t.height());
                System.out.println(t);
            } else {
                Comparable[] arr = t.toArray();
                System.out.println(t.height());
                System.out.print("Final: ");
                for (int i = 0; i < t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }
        }
    }

    /**
     * find element in the tree from the root
     * @param x desired element to be found
     * @return node containing element if element was present. Else parent node where search failed
     */
    public Entry<T> find(T x) {
        parentStack.clear(); // clear stack and start fresh
        parentStack.push(null); // in case if tree is empty
        return find(root, x); // find from root
    }

    /**
     * Find desired element in the tree.
     * @param t start finding desired element from this node
     * @param x desired element to be found
     * @return If element is present in the tree return the element else return it's parent element (where search failed)
     */
    public Entry<T> find(Entry<T> t, T x) {
        if (t == null || t.element == x) return t;

        while (true) {
            if (x.compareTo(t.element) < 0) { // x is smaller. go left
                if (t.left == null) break;
                else {
                    parentStack.push(t);
                    t = t.left;
                }
            } else if (x.compareTo(t.element) == 0) break; // found element
            else {   // x is bigger. go right
                if (t.right == null) break;
                else {
                    parentStack.push(t);
                    t = t.right;
                }
            }

        }
        return t;
    }

    /**
     * bypass (replace) current element to element's child
     * pre-conditions: t has at most one child and parentStack.peek() is t's parent
     * @param t element to be bypassed
     * */
    private void bypass(Entry<T> t) {
        Entry<T> parent = parentStack.peek();
        Entry<T> child = t.left == null ? t.right : t.left;

        if (parent == null) { // t is the root of the tree
            root = child;
        } else {
            if (parent.left == t) parent.left = child;
            else parent.right = child;
        }
    }

    /**
     * see if element is in the tree
     * @param x desired element to be checked if it is in tree
     */
    public boolean contains(T x) {
        Entry<T> t = find(x);
        return t != null && t.element == x;
    }

    /** get element from tree if it is there
     *  Element in tree that is equal to x is returned, null otherwise.
     * @param x desired element to get
     */
    public T get(T x) {
        Entry<T> t = find(x);
        return t == null || t.element != x ? null : t.element; // if element found then return it else null
    }

    /** Adds element in the BST
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
     * @param x element to be added
     * @return true if element was successfully added. false if element was already present in the tree
     */
    public boolean add(T x) {
        return add(new Entry<>(x, null, null));
    }

    protected boolean add(Entry<T> newEntry) {
        T x = newEntry.element;
        if (size == 0) { // empty tree
            root = newEntry; //initialize root
        } else {
            Entry<T> t = find(x); // go to correct position where element should be added
            if (t.element.compareTo(x) == 0) { // element was already there
//                t.element = x; // replace

                replaceChild(parentStack.peek(), t, newEntry);
                return false;
            } else if (t.element.compareTo(x) < 0) { // element where find stopped is less than current element to be added
                t.right = newEntry; // put it in right side
            } else {
                t.left = newEntry; // else left side
            }
        }
        size++;
        return true;
    }

    private void replaceChild(Entry<T> parent, Entry<T> oldchild, Entry<T> newChild) {
        // handle parent
        if (oldchild == root) {
            root = newChild;
        } else if (parent.left == oldchild) {
            parent.left = newChild;
        } else {
            parent.right = newChild;
        }

        //handle child
        if (oldchild.left != null) {
            newChild.left = oldchild.left;
        }
        if (oldchild.right != null) {
            newChild.right = oldchild.right;
        }
    }


    /** Removes element from tree if tree has that element
     *  Return x if found, otherwise return null
     * @param x element to be removed
     * @return value of removed node
     */
    public T remove(T x) {
        if (root == null) return null; // empty tree
        Entry<T> t = find(x); // find the desired element
        if (t == null || t.element.compareTo(x) != 0) return null; // element to remove not found
        T result = t.element;
        if (t.left == null || t.right == null) { // element to remove has only one child
            bypass(t);
        } else { // element to remove has two childrens
            parentStack.push(t); // push it as a parent in stack
            Entry<T> minRight = find(t.right, x); // get right successor
            t.element = minRight.element; // put successor in place of element to be removed
            bypass(minRight); // by pass successor from below
        }
        size--;
        return result;
    }

    /**
     * find min element in tree
     * @return min element
     */
    public T min() {
        if (root == null) return null;
        Entry<T> t = root;
        while (t.left != null) t = t.left; // go to left most node which is min of entire tree
        return t.element;
    }

    /**
     * find max element in tree
     * @return max element
     */
    public T max() {
        if (root == null) return null;
        Entry<T> t = root;
        while (t.right != null) t = t.right; // go to right most node which is max of entire tree
        return t.element;
    }

    /**
     * convert BST to array in with in-order traversal
     * @return array containing entire tree in in-order
     */
    public Comparable[] toArray() {
        if (root == null) {
            return null;
        }
        Comparable[] arr = new Comparable[size];
        int i = 0;
        Stack<Entry<T>> s = new Stack<>();
        Entry<T> t = root.left;
        s.push(root);

        while (!s.isEmpty() || t != null) {
            while (t != null) {
                s.push(t);
                t = t.left;
            }
            t = s.pop();
            arr[i++] = t.element;
            t = t.right;
        }
        return arr;
    }


// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
     Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
        return new TreeIterator();
    }

    // Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
    // TODO: find floor without recursion
    public T floor(T x) {
        return floor(root, x);
    }

    public T floor(Entry<T> t, T x) {
        if (t == null) return null;
        if (t.element.compareTo(x) == 0) return t.element; // found floor
        if (t.element.compareTo(x) > 0) return floor(t.left, x);

        T f = floor(t.right, x);
        return f == null ? t.element : f;
    }

    // Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    // TODO: find ceiling without recursion
    public T ceiling(T x) {
        return ceiling(root, x);
    }

    private T ceiling(Entry<T> t, T x) {
        if (t == null) return null;
        if (t.element.compareTo(x) == 0) return t.element; // ceiling == current node
        if (t.element.compareTo(x) < 0)
            return ceiling(t.right, x); // if current node is less than x then ceiling is in right subtree
        T c = ceiling(t.left, x);
        return c == null ? t.element : c;
    }

    // Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        Entry<T> t = find(x);
        if (t == null) return null;
        if (t.element.compareTo(x) == 0) {
            t = find(t.left, x);
            return t == null ? null : t.element;
        }
        return floor(x);
    }

    // Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        Entry<T> t = find(x);
        if (t == null) return null;
        if (t.element.compareTo(x) == 0) {
            t = find(t.right, x);
            return t == null ? null : t.element;
        }
        return ceiling(x);
    }

    //Additional methods
    public int size() {
        return size;
    }

// End of Optional problem 2

    public int height() {
        return height(root);
    }

    protected int height(Entry<T> t) {
        return t == null ? -1 : 1 + Math.max(height(t.left), height(t.right));
    }

    public String toString() {
        StringBuilder s = new StringBuilder("[" + size() + "] ");
        if (size() != 0) {
            for (Comparable i : toArray()) {
                s.append(i).append(" ");
            }
        }
        return s + "\n";
    }

    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

    /**
     * node class of BST
     * @param <T> generic type element
     */
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
    }

    protected class TreeIterator implements Iterator<T> {

        BoundedStack<Entry<T>> s;
        Entry<T> current;
        T elementToReturn;

        TreeIterator() {
            int h = height(root);
            s = new BoundedStack<>(h + 1);
            if (root != null) {
                s.push(root);
                current = root.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !s.isEmpty() || current != null;
        }

        @Override
        public T next() {
            if (!s.isEmpty() || current != null) {
                while (current != null) {
                    s.push(current);
                    current = current.left;
                }
                current = s.pop();
                elementToReturn = current.element;
                current = current.right;
                return elementToReturn;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

}
/*
Sample input
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0
Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10

*/