package ie.cct._2018316.dev;

public class Readers {

//	private int id;
	private String id, fname, lname, address;
	
	Readers(String id, String fname, String lname, String address) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.address = address;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
//		return "\n" + "Readers [id=" + id + ", fname=" + fname + ", lname=" + lname + ", address=" + address + "]\n";
		return "\n" + id + "[id=" + id + ", fname=" + fname + ", lname=" + lname + ", address=" + address + "]\n";
	}
	
	
}
