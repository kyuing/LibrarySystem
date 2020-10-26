package ie.cct._2018316.dev;

import java.util.ArrayList;
import java.util.List;

import ie.cct._2018316.cunstructs.IO;

public class Books {

	private String id, title, author, rentalState, readerInQ;
	private MyQueue mq;
	private String[] testingQueueDB/* = null */;
	private Node node;
	private boolean isRented, isAvailable;	
	
	//waiting reader number should be dynamic value
	//rentalState 
	public Books(String id, String title, String author, String rentalState, String[] testingQueueDB/* , String readerInQ, MyQueue mq */) {
		
		this.id = id;
		this.title = title;
		this.author = author;
		initRentalState(rentalState);
		this.testingQueueDB = null;
		this.testingQueueDB = testingQueueDB;
		this.readerInQ = "";
		this.node = null;
		this.mq = null;

	}
	
	public String[] getTestingQueueDB() {
		return this.testingQueueDB;
	}
	
	public MyQueue getMQ() {
		
		return this.mq;
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

	public void setEnQueue(List<Readers> readers, int index) {
		
		node = new Node(readers.get(index));
		if(this.mq == null) {
			this.mq = new MyQueue();
		}
		mq.enQueue(node);
//		this.readerInQ = mq.getFirst().getID();
		
		String toReturn = "";
		if (this.mq == null) {
			toReturn = "none";
			this.readerInQ = toReturn;	
		
		}else {
			//set all the queue elements as a string in a line
			toReturn = this.mq.readerInQueueToString();
			this.readerInQ = toReturn;
		}
	}
	
	public void setDeQueue() {
		
		mq.deQueue();
		
		if(!mq.isEmpty()) {
//			this.readerInQ = mq.getFirst().getID();
			this.readerInQ = this.mq.readerInQueueToString();
		}else {
			this.readerInQ = "none";	
		}
		
	}


	public void setReaderInQ(String none) {

		if(this.mq == null ) {			
			this.readerInQ = none;	
		}else {
//			this.readerInQ = mq.getFirst().getID();	
			this.readerInQ = this.mq.readerInQueueToString();
		}
	}
	
	public String getReaderInQ() {
		return this.readerInQ;
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
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "\n" + id + "[id=" + id + ", title=" + title + ", author=" + author 
				+ ", rental_state=" + rentalState
				+ ", Queue=" + getReaderInQ() + "]\n";
		
	}
	
	
}
