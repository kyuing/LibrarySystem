package ie.cct._2018316.dev;

public class Rent {
	
	private String rentID, titleID, readerID, waitingQ;

	public Rent(String rentID, String titleID, String readerID, String waitingQ) {
		
		this.rentID = rentID;
		this.titleID = titleID;
		this.readerID = readerID;
		this.waitingQ = waitingQ;
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
		return "Rent [rentID=" + rentID + ", titleID=" + titleID + ", readerID=" + readerID + ", waitingQ=" + waitingQ
				+ "]";
	}

}
