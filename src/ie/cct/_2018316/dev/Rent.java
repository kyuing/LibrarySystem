package ie.cct._2018316.dev;
//may need to define booleans for state checking and send the state into books
public class Rent {
	
	private String rentID, titleID, readerID, state, waitingQ;
	private boolean isRented, isNormal;

	public Rent() {
		
	}
	//specific constructor for loading data
	public Rent(String rentID, String titleID, String readerID, String state/* , String waitingQ */) {
		
		this.rentID = rentID;
		this.titleID = titleID;
		this.readerID = readerID;
		initState(state);
	}

	private void initState(String state) {
		this.state = state;
		if(state.equalsIgnoreCase("Rented")) {
			this.isRented = true;	//rented
			this.isNormal = false;
		}else {
			this.isRented = false;
			this.isNormal = true;	
		}	
		
	}
	
	public boolean isRented() {
		return isRented;
	}
	
	public void setRented(boolean isRented) {
		//check isRented in controller
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

	public String getWaitingQ() {
		return waitingQ;
	}

	public void setWaitingQ(String waitingQ) {
		this.waitingQ = waitingQ;
	}

	@Override
	public String toString() {
		return "\n" + rentID + "[rentID=" + rentID + ", titleID=" + titleID + ", readerID=" + readerID + ", state=" + state
				+ "]\n";
	}

	public String menu7_1_toString() {
		return "\n" + rentID + "\n[rentID=" + rentID + ", titleID=" + titleID + ", readerID=" + readerID + ", state=" + state
				+ "]\n";
	}
}
