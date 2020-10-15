package ie.cct._2018316.dev;


//really need to create collection to record rent history????
public class Readers {

//	private int id;
	private String id, fname, lname, currentRent;
	
	Readers(String id, String fname, String lname, String currentRent) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.currentRent = currentRent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getCurrentRent() {
		return currentRent;
	}

	public void setAddress(String currentRent) {
		this.currentRent = currentRent;
	}

	@Override
	public String toString() {
//		return "\n" + "Readers [id=" + id + ", fname=" + fname + ", lname=" + lname + ", address=" + address + "]\n";
		return "\n" + id + "[id=" + id + ", fname=" + fname + ", lname=" + lname + ", Current Rent=" + currentRent + "]\n";
	}
	
	
}
