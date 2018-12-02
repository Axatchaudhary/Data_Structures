/**
 * Bounded Stack implementation
 * Author: Axat Kamleshkumar Chaudhari (akc170000)
 * */
package akc170000;

import java.util.Scanner;

public class BoundedStack<T> {

    Object[] stack;
    int maxsize, top; // top pointer of the stack

    BoundedStack(int maxsize) {
        this.maxsize = maxsize;
        stack = new Object[maxsize];
        top = 0;
    }


    boolean push(T x) {
        if (top == maxsize) { // stack full
            return false;
        }
        stack[top] = x;
        top++; // increase the pointer for future entry
        return true;
    }

    // retrieve the top element of the stack
    T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T) stack[top]; // just return the element indexed at 'top'
    }

    T pop() {
        // return the top element only if the stack is not empty
        if (isEmpty()) {
            return null;
        }
        return (T) stack[--top];
    }

    int size(){
        return top;// value of top is the current size of the stack
    }

    void clear(){
        top = 0; // just resetting the top abstractly clears the stack. No need to assign new empty array. Good for memory management.
    }
    public boolean isEmpty() {
        return top == 0;
    }

    public String toString() {
        String s = "";
        System.out.println("Stack: ");
        for (int i = 0; i < maxsize; i++) {
            s += stack[i] + " ";
        }
        s += "\n";
        return s;
    }

    public static void main(String[] args) {

        int n = 5;
        BoundedStack myStack = new BoundedStack(n);
        Scanner in = new Scanner(System.in);

        whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1:
                    System.out.print("Enter value: ");
                    System.out.println(myStack.push(in.nextInt()));
                    System.out.println(myStack);
                    break;
                case 2:
                    Object removedItem = myStack.pop();
                    if (removedItem != null) {
                        System.out.println("Removed element: " + (int) removedItem);
                    } else {
                        System.out.println("Empty Stack!");
                    }

                    System.out.println(myStack);
                    break;
                case 3:
                    Object topItem = myStack.pop();
                    if (topItem != null) {
                        System.out.println("Top element: " + (int) topItem);
                    } else {
                        System.out.println("Empty Stack!");
                    }
                    break;
                case 4:
                    System.out.println("isEmpty: " + myStack.isEmpty());
                    break;

                default:
                    break whileloop;

            }
        }


    }
}

