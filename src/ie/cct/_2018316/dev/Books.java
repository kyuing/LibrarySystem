package ie.cct._2018316.dev;

public class Books {

	private String id, title, author, rentalState, readerInQ;
	private MyQueue mq;
	private boolean isRented, isInQueue, isAvailable;	
	
	//waiting reader number should be dynamic value
	//rentalState 
	public Books(String id, String title, String author, String rentalState, String readerInQ) {
		
		this.id = id;
		this.title = title;
		this.author = author;
		initRentalState(rentalState);
		this.readerInQ = readerInQ;
	}


	public String getReaderInQ() {
		return readerInQ;
	}

//	public void initReaderInQ(String readerInQ) {
//		this.readerInQ = readerInQ;
//		if(this)
//	}
	

	public void setReaderInQ(String readerInQ) {
		this.readerInQ = readerInQ;
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
