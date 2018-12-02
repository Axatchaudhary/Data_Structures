/**
 * SP4_optional
 * @author Axat Chaudhari (akc160000)
 */

package akc170000;
import java.util.Iterator;
import java.util.Scanner;

public class BSTMap<K extends Comparable<? super K>, V> implements Iterable<K>{
    private BinarySearchTree<K> bst; // Binary Search Tree can be used to make tree map. Just need to modify Entry class

    /**
     * Entry class which stores key K and its value V
     */
    static class Entry<K,V> extends BinarySearchTree.Entry<K>{
        V value;
        public Entry(K k, V v, BinarySearchTree.Entry<K> left, BinarySearchTree.Entry<K> right){
            super(k, left, right);
            value = v;
        }
    }
    BSTMap() {
        bst = new BinarySearchTree<>();
    }

    /**
     * get value for given key
     * @param key
     * @return if key exists return the value associated with it else null
     */
    public V get(K key){
        Entry<K,V> t= (Entry<K, V>) bst.find(key);
        return t == null || t.element != key ? null : t.value;
    }

    /**
     * add or replace key given value pair
     * @param key if key exists in the tree replace its old value by 'value'
     * @param value new value associated with key
     * @return true if key already existed else false if new pair inserted
     */
    public boolean put(K key, V value) {
        Entry<K,V> n = new Entry<>(key,value, null,null);
        n.value = value;
        return bst.add(n);
    }

    /**
     * print keys in tree in in-order
     * @return string representation of list of keys
     */
    public String toString(){
        return bst.toString();
    }

    /**
     * Iterate over the keys stored in the map, in order
     * @return iterator for keys in sorted order
     */
    public Iterator<K> iterator() {
        return bst.iterator();
    }

    /**
     * print tree in sorted order (just like iterator)
     */
    void printTree(){
        System.out.print("["+bst.size()+"]");
        printTree(bst.root);
        System.out.println();
    }

    /**
     * print tree from given node
     * @param node this node acts as a root of the tree
     */
    void printTree(BinarySearchTree.Entry<K> node){
        if (node != null){
            printTree(node.left);
            System.out.print(" "+node.element+":"+((Entry<K,V>)node).value);
            printTree(node.right);
        }
    }


    public static void main(String[] args){
        BSTMap<Integer,String> t = new BSTMap<>();
        Scanner in = new Scanner(System.in);
        String s;
        while (in.hasNext()) {
            int x = in.nextInt();
            if (x > 0) {
                s = in.next();
                System.out.println("Add " + x + " : " + s);
                t.put(x,s);
                t.printTree();
            } else if (x < 0) {
                System.out.print("get " + x + " : ");
                System.out.print(t.get(-x));
                System.out.println();
            } else {
                return;
            }
        }
    }

}

/*
Sample input
1 a 3 c 5 e 7 g 9 i 2 b 4 d 6 f 8 h 10 j -1 -2 -6 1 aa 2 bb 6 ff -1 -2 -6
Add 1 : a
[1] 1:a
Add 3 : c
[2] 1:a 3:c
Add 5 : e
[3] 1:a 3:c 5:e
Add 7 : g
[4] 1:a 3:c 5:e 7:g
Add 9 : i
[5] 1:a 3:c 5:e 7:g 9:i
Add 2 : b
[6] 1:a 2:b 3:c 5:e 7:g 9:i
Add 4 : d
[7] 1:a 2:b 3:c 4:d 5:e 7:g 9:i
Add 6 : f
[8] 1:a 2:b 3:c 4:d 5:e 6:f 7:g 9:i
Add 8 : h
[9] 1:a 2:b 3:c 4:d 5:e 6:f 7:g 8:h 9:i
Add 10 : j
[10] 1:a 2:b 3:c 4:d 5:e 6:f 7:g 8:h 9:i 10:j
get -1 : a
get -2 : b
get -6 : f
Add 1 : aa
[10] 1:aa 2:b 3:c 4:d 5:e 6:f 7:g 8:h 9:i 10:j
Add 2 : bb
[10] 1:aa 2:bb 3:c 4:d 5:e 6:f 7:g 8:h 9:i 10:j
Add 6 : ff
[10] 1:aa 2:bb 3:c 4:d 5:e 6:ff 7:g 8:h 9:i 10:j
get -1 : aa
get -2 : bb
get -6 : ff

 */
