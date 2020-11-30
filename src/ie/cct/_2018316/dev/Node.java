package ie.cct._2018316.dev;

/**
 * Class to define for a book's queue
 * 
 * This class references the queue & linked-list course contents
 * that were lectured by Professor Amilcar   
 * 
 * @author Kyu
 */
public class Node {

	private Readers element;	//element with type Readers
	private Node next;	
	
	public Node(Readers readers) {
		this.element = readers;
		this.next = null;
	}

	public Readers getElement() {
		return element;
	}

	public void setElement(Readers element) {
		this.element = element;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}
	
	@Override 
	public String toString() {
		return this.element.toString();

	}

	public String getID() {
		return this.element.getId();
	}
	
}
