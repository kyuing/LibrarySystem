package ie.cct._2018316.dev;

import java.util.ArrayList;
import java.util.List;

import ie.cct._2018316.cunstructs.IO;

public class Books {

	private String id, title, author, rentalState, readerInQ;
	private MyQueue mq;
	private List<Readers> readers;
	private Node node;
	private boolean isRented, isAvailable;	
	
	//waiting reader number should be dynamic value
	//rentalState 
	public Books(String id, String title, String author, String rentalState, String readerInQ/* , MyQueue mq */) {
		
		this.id = id;
		this.title = title;
		this.author = author;
		initRentalState(rentalState);
		this.readerInQ = readerInQ;
		this.node = null;
//		this.mq = null;
//		cloneReadersDB();
		mq = new MyQueue();
	}
	
	public void cloneReadersDB(List<Readers> readers) {
		
//		this.readers = new ArrayList<>();
		this.readers = readers;
	}

	public boolean retrieveEqualsInQueue(String readerIdInNode) {
		
		return this.mq.equalsCustom(readerIdInNode);
	}
	
	public Node getFirstInQueue() {
		
//		return this.mq.getFirst().getID();
		return this.mq.getFirst();
	}

	public boolean getQueueIsEmpty() {
		
		return mq.isEmpty();
		//may need to do mq = null;
	}
	
	public String getQueueToString() {
		
		String toReturn = "";
		toReturn = IO.printUnderLine() 
				+ "\n[The book info]"
				+ toString()
				+ "\n\n[The queue in the book]"
				+ this.mq.toString();
		
		
		return toReturn;
	}
	
//	public String getFirstInQueueToString() {
//		
//		String toReturn = "";
//		toReturn = IO.printUnderLine()
//				+ "\n[The book info]"
//				+ toString()
//				+ "\n\n[The first available reader in the book's queue]"
//				+ this.mq.getFirst().toString();		
//		
//		return toReturn;
//	}
	
	public String getFirstInQueueToString() {
		
		return mq.firstToString();
	}

	public void setEnQueue(int readerIndex) {
		
		node = new Node(readers.get(readerIndex));
//		mq = new MyQueue();
		mq.enQueue(node);
		this.readerInQ = mq.getFirst().getID();
	}
	
	public void setDeQueue() {
		
		mq.deQueue();
		
		if(!mq.isEmpty()) {
			this.readerInQ = mq.getFirst().getID();	
		}
		this.readerInQ = "none";
	}


	public String getReaderInQ() {
		return readerInQ;
	}

	public void setReaderInQ(String readerInQ) {
		if(this.mq.isEmpty() && readerInQ.equals("none")) {
			
			this.readerInQ = readerInQ;	
		}
		this.readerInQ = mq.getFirst().getID();
	}


	public String getRentalState() {
		return rentalState;
	}

	
	public void initRentalState(String rentalState) {
		
		this.rentalState = rentalState;
		if(rentalState.equalsIgnoreCase("Rented")) {
			this.isRented = true;	//rented
			this.isAvailable = false;
		}else {
			this.isRented = false;
			this.isAvailable = true;	
		}
		
	}

//	public void setRentalState(String rentalState) {
//		//validation can be done outside of this by accessing to its state only...?
//		this.rentalState = rentalState;	
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isRented() {
		return isRented;
	}

	public void setRented(boolean isRented) {
		//check isRented in controller
		this.isRented = isRented;
		if(this.isRented == true) {
			this.rentalState = "Rented";
		}
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		
		this.isAvailable = isAvailable;
		if(this.isAvailable == true) {
			this.rentalState = "Available";	
		}
//		else {
//			this.rentalState = "Rented";	
//		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
//		return "\n" + title + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState + "]\n";
		return "\n" + id + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState + "]\n";
	}
	
	
}
