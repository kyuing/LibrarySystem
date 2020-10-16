package ie.cct._2018316.dev;

public class Node {

//	private List<Readers> element;
	private Readers element;
	private Node next;	//reference/pointer for/to next
	
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
		// TODO Auto-generated method stub
		return this.element.getId();
	}
	
}
