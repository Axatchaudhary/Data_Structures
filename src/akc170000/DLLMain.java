/**
 *  SP_1 Submission: Main class for demonstration of DoublyLinkedList implementation
 *  @Authors: 	Koul Maleeha Shabeer (msk180001)
 *  			Axat Kamleshkumar Chaudhari (akc170000)
 * 	Course:		CS 5V81.001 Implementation of Dala Structures & Algorithms
 * 	Date:		Aug 30, 2018
 */

package akc170000;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class DLLMain
{

	    public static void main(String[] args) throws NoSuchElementException 
	    {
			int n = 10;
			if(args.length > 0) {
				n = Integer.parseInt(args[0]);
			}

			DoublyLinkedList<Integer> ll = new DoublyLinkedList<>();
	        
	        for (int i = 0;i < n; i++) {
				ll.add(Integer.valueOf(i));
			}
	        ll.printList();
	        DoublyLinkedList.DoublyLinkedListIterator<Integer> it = ll.iterator();
	        Scanner in = new Scanner(System.in);
			System.out.println("1 >> Print next element");
			System.out.println("2 >> Remove element");
			System.out.println("3 >> Print previous element");
			System.out.println("4 >> Add element");
	        whileloop:
	        while(in.hasNext())
	        {
	            int com = in.nextInt();
	            switch(com)
	            {
	                case 1:  	// Move to next element and print it
	                    if (it.hasNext())
	                    {
	                        System.out.println("Next: "+it.next());
	                    } else {
	                        break whileloop;
	                    }
	                    break;
	                case 2:  	// Remove element
	                    it.remove();
						ll.printList();
	                    break;
	                case 3:		// Move to previous element and print it
	                	 if (it.hasPrevious())
	                	 {
	                	 	System.out.println("Previous: "+it.previous());
		                 } else {
	                	 	break whileloop;
		                 }
	                	break;
	                case 4:		// Add element to the next of current in iterator
						System.out.print("Enter the value: ");
	                	int value = in.nextInt();
	                	it.add(Integer.valueOf(value));
						ll.printList();
	                	break;
	                	
	                default:  // Exit loop
	                    break whileloop;
	            }
	        }

	        System.out.println("Program ended!!");
	    }
	}

