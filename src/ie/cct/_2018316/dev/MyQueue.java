package ie.cct._2018316.dev;


public class MyQueue /* implements MyQueueInterface */ {

	private Node first;
	private Node last;
	private int size;
	
	public MyQueue() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}

	//return the first node of element
	public Node getFirst() {
		return this.first;
	}
	
//	public void addFirst(Node newElement) {
//		if(size == 0) {
//			last = newElement;
//		}
//		//while newElement is a value and is also a reference to acesss Node class?
//		//when there is one node with a value, it's the first node and the last node at the same time
//		newElement.setNext(first);	//assign the first node to be next
//		first = newElement;	//assign the new element to be the first
//		size++;
//	}
	
	public void enQueue(Node newElement) {
		if(size == 0) {
			first = newElement;
			last = first;
		}else {
			last.setNext(newElement);	//next one for first
			last = newElement;	
		}
		
		size++;
	}
	
	public Node deQueue() {
		if(size == 0) {
			return null;
		}
		if (size == 1) {
			last = null;
			first = last;
//			size--;
//			return first;
		}
		Node toReturn = first;
		first = first.getNext();	//assign the next node to first
		size--;
		return toReturn;
	}
	
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
	
	
	public boolean equalsCustom(String readerID) {
		if(size == 0 ) {
			return false;
		}else {
			Node current = first;
			int length = size;
			int counter = 0;
			while(counter < length) {
				
				if(current.getID().equals(readerID)) {
					return true;
				}
				current = current.getNext();
				counter++;
			}	
		}
		
		return false;
	}
	
//	public Node removeLast() {
//		
//		Node toReturn = last;
//		if(size == 0) {
//			return toReturn;
//		}
//		if(size == 1) {
//			first = null;
//			last = null;
//			size--;
//			return toReturn;
//		}
//		
//		Node secondLast = findElementByPosition(size - 2);
//		secondLast.setNext(null);
//		last = secondLast;
//		size--;	
//		return toReturn;
//		
//	}
	
	/**
	 * 
	 * @return size of queue
	 */
	public int size() {
		return size;
	}

	/**
	 * 
	 * @return true if size is 0
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	public String equalsCustomToString (String readerID) {
		if(size != 0 | size > 0) {
			
			Node current = first;
			int length = size;
			int counter = 0;
			String toReturn = "\n\nQueue\tReader info";
			
			while(counter < length) {
				
				if(current.getID().equals(readerID)) {
					
					toReturn += "\n" + (counter+1) + current.toString().replaceAll("\n", "\t");
					return toReturn;	
				}
				current = current.getNext();
				counter++;
			}	
		}
		return null;
	}
	
	public String firstToString() {
		
		String toReturn = "";
		int qNum = 0;	//queue numbering
		Node current = first;
		
		if(current != null) {
			qNum++;
			toReturn = "\nQueue\tReader info\n" 
						+ (qNum) + current.toString().replaceAll("\n", "\t");
		}
		return toReturn;
	}
	
	public String readerInQueueToString() {
		
		String toReturn = "";
		Node current = first;
		
		while(current != null) {
			toReturn += current.getID() + " ";
			current = current.getNext();	
		}
		toReturn = toReturn.substring(0, toReturn.length() - 1);
		
		return toReturn;
	}
	
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
