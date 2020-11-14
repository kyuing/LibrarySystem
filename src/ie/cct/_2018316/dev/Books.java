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

	// waiting reader number should be dynamic value
	// rentalState
	public Books(String id, String title, String author, String rentalState,
			String[] testingQueueDB/* , String readerInQ, MyQueue mq */) {

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

		if (this.mq != null) {
			return this.mq;
		}
		return null;
	}

	public String getQueueToString() {

		String toReturn = "";
		toReturn = IO.printUnderLine() + "\n[The book info]" + toString() + "\n\n[The queue in the book]"
				+ this.mq.toString();

		return toReturn;
	}

	public void setEnQueue(List<Readers> readers, int index) {

		node = new Node(readers.get(index));
		if (this.mq == null) {
			this.mq = new MyQueue();
		}
		mq.enQueue(node);
//		this.readerInQ = mq.getFirst().getID();

		String toReturn = "";
		if (this.mq == null) {
			toReturn = "none";
			this.readerInQ = toReturn;

		} else {
			// set all the queue elements as a string in a line
			toReturn = this.mq.readerInQueueToString();
			this.readerInQ = toReturn;
		}
	}

	public void setDeQueue() {

		mq.deQueue();

		if (!mq.isEmpty()) {
//			this.readerInQ = mq.getFirst().getID();
			this.readerInQ = this.mq.readerInQueueToString();
		} else {
			this.readerInQ = "none";
		}

	}

	public void setReaderInQ(String none) {

		if (this.mq == null) {
			this.readerInQ = none;
		} else {
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
		if (rentalState.equalsIgnoreCase("Rented")) {
			this.isRented = true; // rented
			this.isAvailable = false;
		} else {
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
		// check isRented in controller
		this.isRented = isRented;
		if (this.isRented == true) {
			this.rentalState = "Rented";
//			this.isAvailable = false;
		}
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {

		this.isAvailable = isAvailable;
		if (this.isAvailable == true) {
			this.rentalState = "Available";
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String titleAndAuthorSortedToString(String s) {
		return "\n" + s + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState
				+ ", Queue=" + getReaderInQ() + "]\n";

	}

	public String authorSortedToString() {
		return "\n" + author + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState
				+ ", Queue=" + getReaderInQ() + "]\n";

	}

	public String titleSortedToString() {
		return "\n" + title + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState
				+ ", Queue=" + getReaderInQ() + "]\n";

	}

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
								+ ", rental_state=" + rentalState + ", Queue=" + getReaderInQ() + "]\n";
					
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
			toReturn = "\n" + id + "[id=" + id + ", title=" + title + ", author=" + author + ", Queue="
					+ getReaderInQ() + "]\n";
		}

		return toReturn;
	}

//	public String menu7_1_toString(List<Rent> rent/* , int rentIndex */, List<Readers> reader) {
//
//		String toReturn = "";
//
//		// it doesnt work as expected.
//		for (int i = 0; i < reader.size(); i++) {
//
//			// if a reader's current list of book rent is not null,
//			if (reader.get(i).getMyRent() != null) {
//
//				for (int j = 0; j < reader.get(i).getMyRent().size(); j++) {
////					
////					//if a rent ID in a reader's current rent list == the rent ID in a specific index of Rent list, 
////					if(reader.get(i).getMyRent().get(j).getRentID().equals(rent.get(rentIndex).getRentID())) {
////						
////					}
//					for (int k = 0; k < rent.size(); k++) {
//						if (reader.get(i).getMyRent().get(j).getRentID().equals(rent.get(k).getRentID())) {
////							System.out.println(reader.get(i).getMyRent().get(j).getRentID());
//							if (rent.get(k).getState().equals("Rented")) {
//								toReturn = "\n" + id + "[id=" + id + ", title=" + title + ", author=" + author
//										+ ", rental_state=" + rentalState + ", Queue=" + getReaderInQ() + "]\n";
//								return toReturn;
//							} else {
//								toReturn = "\n" + id + "[id=" + id + ", title=" + title + ", author=" + author
//										+ ", Queue=" + getReaderInQ() + "]\n";
//								return toReturn;
//							}
//						}
//					}
//
//				}
//			}
//
//		}
//
//		return null;
////		return toReturn;
//	}


	@Override
	public String toString() {
		return "\n" + id + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState
				+ ", Queue=" + getReaderInQ() + "]\n";

	}

}
