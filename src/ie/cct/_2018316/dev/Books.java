package ie.cct._2018316.dev;

import java.util.List;
import ie.cct._2018316.cunstructs.IO;

public class Books {

	/* [The field rentalState info in detail]
	 * 
	 * the variable rentalState references its rental state from OBJs in Rent list
	 * except when a book has never been rented.
	 * 
	 * It takes one of the following value once a book is rented/returned;
	 * "Rented" == this book is in rent
	 * "Available" == this book is not in rent so returned. 
	 *             thus, it's in 'Available' state
	 *             ('Available' state is also assigned to book OBJs that have never been rented)
	 *  
	 * 
	 * Depending on the states of the variable rentalState, 
	 * a state of the boolean variables isRented and isAvailable each is assigned. 
	 * 
	 * 
	 * [The field readerInQ info in detail]
	 * The value of it has two possible states
	 * "none" == this book has no queue of readers
	 * i.e. "R1 R2 R3 R4...." == this book has queue of readers
	 *                           the order of queue starts from the left side. 
	 *                           so R1 in this example is the first queue of readers
	 * 
	 * */
	private String id, title, author, rentalState, readerInQ;	//main fields
	
	private MyQueue mq;	//ref of the queue
	private Node node;	//ref of node
	
	private String[] queueDB;	//a temp array to store queue values from the text file
	
	//temp arrays used for an advanced search
	private String[] titleSplit;	
	private String[] authorSplit;	
	private String[] aggregateTitleAndAuthorSplit;	
	
	private boolean isRented, isAvailable;	//boolean variables
	
	//fields used temporarily when searching for books 
	private String nameTag;
	private int bIndex;
	private Books b;

	/**
	 * specific constructor for loading Books.txt into the system and creating book OBJs
	 * @param id
	 * @param title
	 * @param author
	 * @param rentalState
	 * @param queueDB
	 */
	public Books(String id, String title, String author, String rentalState, String[] queueDB) {

		//init fields
		this.id = id;
		this.title = title;
		this.author = author;
		
		/*every book's rental state on a row in Books.txt has one of the following value for its rental state
		 * "Rented" means this book is in rent
		 * "Available" means this book is not in rent. (In other word, returned or never been rented) 
		 *  
		 * Initialize the boolean variables isRented and isAvailable with those values
		 * (This step is needed when loading the book DB from Books.txt) */ 
		initRentalState(rentalState);
		
		//init an advanced search environment
		initAdvancedSearchEnv();	
		
		//init fields
		this.queueDB = null;
		this.queueDB = queueDB;
		this.readerInQ = "";
		this.node = null;
		this.mq = null;

	}
	
	/**
	 * specific constructor to clone current states of each of Books when searching for a book
	 * @param b
	 * @param index
	 */
	public Books(Books b, int index) {
		
		this.b = b;
		this.bIndex = index;
		this.nameTag = null;
		
		//clone value of the fields from the original Reader obj
		this.id = b.getId();
		this.title = b.getTitle();
		this.author = b.getAuthor();
		this.rentalState = b.getRentalState();
		this.readerInQ = b.getReaderInQ();
	}
	
	/**
	 * method to split up title and author each whether either one's string value is a word-based or it's more than a word-based.
	 */
	public void initAdvancedSearchEnv() {
		
		if(this.title != null) {
			this.titleSplit = this.title.split(" ");
		}
		
		if(this.author != null) {
			this.authorSplit = this.author.split(" ");
		}
		
		this.aggregateTitleAndAuthorSplit = new String[this.titleSplit.length + this.authorSplit.length];
		
		
		for (int i = 0; i < this.titleSplit.length; i++) {
			this.aggregateTitleAndAuthorSplit[i] = this.titleSplit[i];			
		}
		
		for (int i = 0; i < this.authorSplit.length; i++) {		
			this.aggregateTitleAndAuthorSplit[this.titleSplit.length + i] = this.authorSplit[i];
		}
	
	}
	
	//getters used when an advanced search is necessary
	public String[] getAggregateTitleAndAuthorSplit() {
		return this.aggregateTitleAndAuthorSplit;
	}
	public String[] getTitleSplit() {
		return this.titleSplit;
	}
	
	public String[] getAuthorSplit() {
		return this.authorSplit;
	}
	
	//method for initializing the boolean variables isRented and isAvailable
	public void initRentalState(String rentalState) {

		this.rentalState = rentalState;
		if (rentalState.equalsIgnoreCase("Rented")) {
			this.isRented = true; // rented
			this.isAvailable = false; //thus, not available
		} else {
			this.isRented = false;	//not rented == returned
			this.isAvailable = true;	//thus, available
		}

	}

	/**
	 * method for enqueue
	 * @param readers
	 * @param index
	 */
	public void setEnQueue(List<Readers> readers, int index) {

		node = new Node(readers.get(index)); //create a new node(a new reader)
		if (this.mq == null) {
			this.mq = new MyQueue();
		}
		mq.enQueue(node);	//enqueue the node
		
		String toReturn = "";
		if (this.mq == null) {
			toReturn = "none";
			this.readerInQ = toReturn;

		} else {
			// set all the queue nodes(reader IDs) as a string into the variable readerInQ
			toReturn = this.mq.readerInQueueToString();
			this.readerInQ = toReturn;
		}
	}

	//method for dequeue
	public void setDeQueue() {

		mq.deQueue();

		if (!mq.isEmpty()) {
			// set all the queue nodes(reader IDs) as a string into the variable readerInQ
			this.readerInQ = this.mq.readerInQueueToString();
		} else {
			this.readerInQ = "none";
		}

	}

	//getters & setters
	public void setReaderInQ(String none) {

		if (this.mq == null) {
			this.readerInQ = none;
		} else {
			this.readerInQ = this.mq.readerInQueueToString();
		}
	}
	
	public String[] getQueueDB() {
		return this.queueDB;
	}

	public MyQueue getMQ() {

		if (this.mq != null) {
			return this.mq;
		}
		this.mq = null;
		return this.mq;
	}
	
	public String getReaderInQ() {
		return this.readerInQ;
	}

	public String getRentalState() {
		return rentalState;
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
		
		this.isRented = isRented;
		if (this.isRented == true) {
			this.rentalState = "Rented";
		}
	}

	public void setAvailable(boolean isAvailable) {

		this.isAvailable = isAvailable;
		if (this.isAvailable == true) {
			this.rentalState = "Available";
		}
	}
	
	public boolean isAvailable() {
		return isAvailable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	//used when preparing .compareTo() during binary search 
	public void setNameTag(String s) {
		this.nameTag = s;
		
	}	
	
	//used when executing .compareTo() during binary search
	public String getNameTag() {
		return this.nameTag;
	}
		

	/************************* toString methods as needed *********************************************/
	
	public String nameTagToString(String s) {
		
		this.nameTag = s;
		return "\n" + nameTag + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState
				+ ", readerInQ=" + getReaderInQ() + "]\n";

	}
	
	//used for testing when executing a binary search for a reader
	public String sortedAllInAcendingToString() {
		return "\n" + "temp.get(" + this.bIndex + ")"  + this.b.nameTagToString(this.nameTag);
	}
	
	public String getQueueToString() {

		String toReturn = "";
		toReturn = "\n<Book info>" + toString() /* + "\n" */
				+ this.mq.toString();

		return toReturn;
	}
	
	public String booksSortedToString(String s) {
		
		//s is a temporary name tag that shows the field used for a sort at a time
		
		return "\n" + s + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState
				+ ", readerInQ=" + getReaderInQ() + "]\n";

	}
	
//	public String titleAndAuthorSortedToString(String s) {
//		return "\n" + s + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState
//				+ ", readerInQ=" + getReaderInQ() + "]\n";
//
//	}
//
//	public String authorSortedToString() {
//		return "\n" + author + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState
//				+ ", readerInQ=" + getReaderInQ() + "]\n";
//
//	}
//
//	public String titleSortedToString() {
//		return "\n" + title + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState
//				+ ", readerInQ=" + getReaderInQ() + "]\n";
//
//	}

	public String menu7_1_toString(List<Rent> rent, int rentIndex, List<Readers> reader, int readerIndex) {

		String toReturn = "";

		// if a reader's current list of book rent is not null,
		if (reader.get(readerIndex).getMyRent() != null) {

			for (int i=0; i<reader.get(readerIndex).getMyRent().size(); i++) {

				// if a rent ID in the reader's current rent list == the rent ID in a specific index of Rent list,
				if (reader.get(readerIndex).getMyRent().get(i).getRentID().equals(rent.get(rentIndex).getRentID())) {
					
					//if the rent state of Rent obj in Rent list is "Rented"
					if (rent.get(rentIndex).getState().equals("Rented")) {
						
						//the reader you are looking at is currently renting the book recorded in the row of the Rent list
						//Thus print rentalState together of this book
						toReturn = "\n" + id + "[id=" + id + ", title=" + title + ", author=" + author
								+ ", rental_state=" + rentalState + ", readerInQ=" + getReaderInQ() + "]\n";
					
					}
					
					/*if (rent.get(rentIndex).getState().equals("Normal")) {
						
						//if not, the reader has returned book and no need to print rentalState.
						toReturn = "\ndsfdsfsdfdsf" + id + "[id=" + id + ", title=" + title + ", author=" + author + ", Queue="
								+ getReaderInQ() + "]\n";
					
					}*/
					
					/* else {
						
						//if not, the reader has returned book and no need to print rentalState.
						toReturn = "\n" + id + "[id=" + id + ", title=" + title + ", author=" + author + ", Queue="
								+ getReaderInQ() + "]\n";
					
					}*/
				}

			}
		}else {
			//the reader has returned book and no need to print rentalState of this book.
			toReturn = "\n" + id + "[id=" + id + ", title=" + title + ", author=" + author + ", readerInQ="
					+ getReaderInQ() + "]\n";
		}

		return toReturn;
	}

	@Override
	public String toString() {
		return "\n" + id + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState
				+ ", readerInQ=" + getReaderInQ() + "]\n";

	}

}
