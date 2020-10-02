package ie.cct._2018316.dev;

public class Books {

//	private int id;
	private String id, title, author, rentalState, wState, renter;
	private boolean isRentalState;
	
	//waiting reader number should be dynamic value
	//do you really need renterID here?
	public Books(String id, String title, String author, String rentalState/*, String renterID, String wState*/) {
		
		this.id = id;
		this.title = title;
		this.author = author;
		setRentalState(rentalState);
	}

//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	public String getRentalState() {
		return rentalState;
	}

	public void setRentalState(String rentalState) {
		
		this.rentalState = rentalState;
		if(rentalState.equalsIgnoreCase("y")) {
			this.isRentalState = true;	//rented
		}
		this.isRentalState = false;	//!rented	
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

	@Override
	public String toString() {
//		return "\n" + title + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState + "]\n";
		return "\n" + id + "[id=" + id + ", title=" + title + ", author=" + author + ", rental_state=" + rentalState + "]\n";
	}
	
	
}
