package ie.cct._2018316.dev;

/**
 * Class Rent
 * @author Kyu
 *
 */
public class Rent {
	
	/* The field state info in detail
	 * 
	 * the variable state takes one of the following value
	 * "Rented" == this book is in rent
	 * "Normal" == this book is not in rent so returned. 
	 *             thus, it's in 'Normal' state
	 * 
	 * Depending on the state of the variable state, 
	 * a state of the boolean variables isRented and isNormal each is assigned. 
	 * 
	 * */
	private String rentID, titleID, readerID, state;
	private boolean isRented, isNormal;

	//default constructor
	public Rent() {}
	
	/**
	 * specific constructor for loading data
	 * @param rentID
	 * @param titleID
	 * @param readerID
	 * @param state
	 */
	public Rent(String rentID, String titleID, String readerID, String state) {
		
		//init fields
		this.rentID = rentID;
		this.titleID = titleID;
		this.readerID = readerID;
		
		//init isRented and isNormal when a new rent record is created.
		initState(state);
	}

	/**
	 * method to initialize the boolean variables isRented and isNormal
	 * @param state that represents a rent state of an obj of Rent list as string
	 */
	private void initState(String state) {
		
		this.state = state;
		if(state.equalsIgnoreCase("Rented")) {
			this.isRented = true;	//rented
			this.isNormal = false;	//thus, not Normal
		}else {
			this.isRented = false;	//not rented == returned
			this.isNormal = true;	//thus, Normal
		}	
		
	}
	
	//getters and setters
	public boolean isRented() {
		return isRented;
	}
	
	public void setRented(boolean isRented) {
	
		this.isRented = isRented;
		if(this.isRented == true) {
			this.state = "Rented";
		}
	}
	
	public boolean isNormal() {
		return isNormal;
	}

	public void setNormal(boolean isNormal) {
		
		this.isNormal = isNormal;
		if(this.isNormal == true) {
			this.state = "Normal";	
		}
	}
	
	public String getState() {
		return state;
	}
	
	public String getRentID() {
		return rentID;
	}

	public void setRentID(String rentID) {
		this.rentID = rentID;
	}

	public String getTitleID() {
		return titleID;
	}

	public void setTitleID(String titleID) {
		this.titleID = titleID;
	}

	public String getReaderID() {
		return readerID;
	}

	public void setReaderID(String readerID) {
		this.readerID = readerID;
	}

	//toString methods as needed
	@Override
	public String toString() {
		return "\n" + rentID + "[rentID=" + rentID + ", titleID=" + titleID + ", readerID=" + readerID + ", state=" + state
				+ "]\n";
	}

	
}
