/**
 * SP_1 Submission: DoublyLinkedList and ListIterator
 *
 * @Authors:    Koul Maleeha Shabeer (msk180001)
 *              Axat Kamleshkumar Chaudhari (akc170000)
 * Course:		CS 5V81.001 Implementation of Dala Structures & Algorithms
 * Date:		Aug 30, 2018
 */
package akc170000;

import java.util.Iterator;

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {
    DoublyLinkedList() {
        super();
    }

    @Override
    public void add(T x) {
        Entry<T> newEntry = new Entry<T>(x, null, null);
        // Typecast newEntry to access prev Entry
        newEntry.prev = tail;
        super.add(newEntry);

    }

    public DoublyLinkedListIterator<T> iterator() {
        return new DLLIterator();
    }

    public interface DoublyLinkedListIterator<T> extends Iterator<T> {
        boolean hasNext();

        boolean hasPrevious();

        T next();

        T previous();

        void add(T x);

        void remove();
    }

    /**
     * Class Entry holds a single node for Doubly Linked List
     */
    static class Entry<E> extends SinglyLinkedList.Entry<E> {
        // 'prev' is of type SinglyLinkedList, same as 'next' for consistancy purposes
        SinglyLinkedList.Entry<E> prev;

        Entry(E x, SinglyLinkedList.Entry<E> nxt, SinglyLinkedList.Entry<E> prv) {
            super(x, nxt);
            this.prev = prv;
        }
    }

    /**
     * Addition to SLLIterator. Adds more methods to iterator: add(), hasPrevious() and prev()
     */
    protected class DLLIterator extends SLLIterator implements DoublyLinkedListIterator<T> {

        DLLIterator() {
            super();
        }

        public boolean hasPrevious() {
            // Cast to DoublyLinkedList to access prev Entry
            // head is still singlyLinkedList type which cannot be cast into DoublyLinkedList.Entry type
            // when cursor is at the first element, it still points prev link to dummy head
            return cursor != head && ((Entry<T>) cursor).prev != head;
        }

        public T previous() {    // Type cast cursor to DoublyLinkedList.Entry to access prev Entry

            cursor = ((Entry<T>) cursor).prev;
            if (prev != head) {
                // update previous only if it has not reached head
                // otherwise keep it there
                prev = ((DoublyLinkedList.Entry<T>) prev).prev;
            }
            ready = true;
            return cursor.element;
        }

        /**
         * Adds new element after the cursor and before the element that would be returned by next()
         * updates 'ready' to false because remove() function should only remove just after calling
         * next() or prev()
         */
        public void add(T x) {


            if (cursor == tail) {
                // If cursor is at last, just add ast the last and update tail
                DoublyLinkedList.this.add(x);

                // update cursor and previous of iterator
                prev = cursor;
                cursor = tail;
                return;
            }

            // Else add new element between cursor and next of cursor
            Entry<T> newElement = new Entry<>(x, cursor.next, cursor);
            ((Entry<T>) cursor.next).prev = newElement;
            cursor.next = newElement;

            // update the position of cursor and previous of iterator
            prev = cursor;
            cursor = cursor.next;

            //only next() or previous() can make iterator to be ready to remove element from list
            ready = false;
            size++;

        }

        public void remove() {
            super.remove();

            // super.remove() takes care of updating next element, but
            // prev Entry should also be handled for doubly linked list
            if (cursor != tail) {
                ((Entry<T>) cursor.next).prev = cursor;
            }

            // Do not update 'prev' if it is head
            if (prev != head) {
                prev = ((DoublyLinkedList.Entry<T>) prev).prev;
            }
        }

    }


}
