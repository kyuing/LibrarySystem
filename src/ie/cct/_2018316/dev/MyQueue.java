package ie.cct._2018316.dev;

/**
 * Class that manages queue of each of books
 * This custom queue implementation references/has modified/has improved the queue & linked list course contents
 * that was lectured by Professor Amilcar   
 * 
 * @author Kyu
 *
 */
public class MyQueue {

	private Node first;
	private Node last;
	private int size;
	
	public MyQueue() {
		
		//init fields
		this.first = null;
		this.last = null;
		this.size = 0;
	}

	/**
	 * @return size of queue
	 */
	public int size() {
		return size;
	}

	/**
	 * @return true if size is 0
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * getter for this.first
	 * @return first node in queue
	 */
	public Node getFirst() {
		return this.first;
	}
	
	
	/**
	 * method to put a new element into queue as the last node 
	 * @param newElement
	 */
	public void enQueue(Node newElement) {
		if(size == 0) {
			//if size == 0, first is also last
			first = newElement;
			last = first;
		}else {
			last.setNext(newElement);	//set the next node into the current last node	
			last = newElement;	//re-init last
		}
		
		size++;	//increment size of queue
	}
	
	/**
	 * method to return/remove the first node from queue
	 * @return first node
	 */
	public Node deQueue() {
		if(size == 0) {
			return null;
		}
		if (size == 1) {
			last = null;
			first = last;
		}
		Node toReturn = first;	//point and temp-store the current first node
		first = first.getNext();	//assign the next node to be first
		size--;	//decrement size as deQueued
		return toReturn;
	}
	
	/**************************************************************************************************
	 * not in use.
	 * However, all of methods below modifies/improves its functionality 
	 * based on this method's logic to change the pointer at a time
	
	public Node findElementByPosition(int position) {
		if(size == 0 || position >= size -1) {
			return null;
		}
		Node current = first;
		int counter = 0;
		while(counter < position) {
			current = current.getNext();
			counter++;
		}
		return current;
	}
	
	***************************************************************************************************/
	
	/**
	 * method to compare @param readerID with readerID inside queue 
	 * @param readerID
	 * @return true if queue has readerID
	 */
	public boolean equalsCustom(String readerID) {
		
		if(size == 0 ) {
			
			return false;
			
		}else {
			Node current = first;
			int length = size;	
			int counter = 0;
			while(counter < length) {
				//if current node's readerID in the queue == readerID
				if(current.getID().equals(readerID)) {
					return true;
				}
				
				//point the next node of the current node to be a new current node
				current = current.getNext();	
				counter++;	//increment counter
			}	
		}
		
		return false;
	}
		
	
	/**
	 * method to return a node in the queue by comparing @param readerID with readerID inside queue 
	 * @param readerID
	 * @return the node found as string
	 */
	public String equalsCustomToString (String readerID) {
		
		if(size != 0 | size > 0) {
			
			Node current = first;
			int length = size;
			int qNum = 0;
			String toReturn = "\n\nQueue\tReader info";	//print a temp column info for printout
			
			while(qNum < length) {
				
				qNum++;	//increment qNum; the number of times WHILE-LOOP is executed
				//if current node's readerID in the queue == readerID
				if(current.getID().equals(readerID)) {
					//the node that matches with the parameter readerID is found
					toReturn += "\n" + (qNum) + current.toString().replaceAll("\n", "\t");
					return toReturn;	
				}
				
				//point the next node of the current node to be a new current node
				current = current.getNext();

			}	
		}
		return null;
	}
	
	/**
	 * method to return the first node 
	 * @return first node in the queue as string
	 */
	public String firstToString() {
		
		String toReturn = "";
		int qNum = 0;	//queue numbering
		Node current = first;
		
		if(current != null) {
			qNum++;
			//if current != null, this book's queue has at least one node
			toReturn = "\nQueue\tReader info\n" 
						+ (qNum) + current.toString().replaceAll("\n", "\t");
		}/*else {
			toReturn = null;
		}*/
		return toReturn;
	}
	
	/**
	 * method to return all of nodes
	 * @return all of readerIDs in nodes of the queue as string on a line/row
	 */
	public String readerInQueueToString() {
		
		String toReturn = "";
		Node current = first;
		
		while(current != null) {
			toReturn += current.getID() + " ";   //store current node's readerID adding a space
			current = current.getNext();	//point the next of the current node to be current node	
		}
		
		//substring toReturn by removing a space that is at the end of toReturn
		toReturn = toReturn.substring(0, toReturn.length() - 1);
		
		//this will be used for each of book OBJs to represent the value of the field 'queue' 
		//on a line/row if not empty. 
		return toReturn;
	}
	
	/**
	 * method to return all of nodes 
	 * @return all of nodes in the queue as string
	 */
	@Override
	public String toString() {
		
		String toReturn = "";
		int qNum = 0;	//queue numbering
		Node current = first;
		
		toReturn = "\n\nQueue\tReader info";
		while(current != null) {
			qNum++;
			toReturn += "\n" + (qNum) + current.toString().replaceAll("\n", "\t");
			current = current.getNext();
		}
		return toReturn;		
		
	}
}
