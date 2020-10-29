package ie.cct._2018316.dev;

import java.util.ArrayList;
import java.util.List;

//really need to create collection to record rent history????
public class Readers {

//	private int id;
	private String id, fname, lname, currentRent, nameTag;
	private int rIndex;
	private Readers r;
	private List<Rent> myRent;
	private int indexToRemove = 0;
	
	Readers(String id, String fname, String lname, List<Rent> myRent) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;		
		this.myRent = myRent;
		setCurrentState();
	}
	
	//specific constructor to clone current states of each of Readers for searching a reader
	Readers(Readers r, int index) {
		
		this.r = r;
		this.rIndex = index;
		this.nameTag = null;
		
		//clone att from the original Reader obj
		this.id = r.getId();
		this.fname = r.getFname();
		this.lname = r.getLname();		
		this.myRent = r.getMyRent();
		setCurrentState();		
	}

	public void setMyRent(List<Rent> currRentRecord) {
		
		String toReturn = "";
		
		if(this.myRent == null) {
			this.myRent = new ArrayList<>();
			this.myRent.add(currRentRecord.get(currRentRecord.size()-1));	
			this.currentRent = this.myRent.get(0).getRentID();	
			
		}else {

			this.myRent.add(currRentRecord.get(currRentRecord.size()-1));
			toReturn = this.currentRent + " " + this.myRent.get(this.myRent.size()-1).getRentID(); 

			this.currentRent = toReturn;
		}
		
	}
	
	public List<Rent> getMyRent() {
		return this.myRent;
	}
	
	public boolean equalsRentInMyRents(String rentIdTemp) {
		
		if(myRent != null) {
			for(int i=0; i<myRent.size(); i++) {
				if(myRent.get(i).getRentID().equalsIgnoreCase(rentIdTemp)) {
					this.indexToRemove = i;
					return true;
				}
			}	
		}

		return false;
	}
	
	public void removeRent(String rentIdToRemove) {
		
		System.out.println("before: " + myRent);
		if(equalsRentInMyRents(rentIdToRemove)) {
			myRent.remove(this.indexToRemove);
			System.out.println("after: " + myRent);
			this.indexToRemove = 0;
			if(myRent.size() == 0) {
				myRent = null;
			}
			setCurrentState();
		}
	}
	
	public void setCurrentState() {

		String toReturn = "";
		if (this.myRent == null /* | this.myRent.size() == 0 */) {
			toReturn = "none";
			this.currentRent = toReturn;	
		
		}else {
			
			for(int i=0; i<myRent.size(); i++) {
				toReturn += myRent.get(i).getRentID() + " ";
				if(i == myRent.size()-1) {

					//remove a space from the end of the string
					toReturn = toReturn.substring(0, toReturn.length() - 1);
				}
			}
			this.currentRent = toReturn;
		}		
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
	
	public void setNameTag(String s, int index) {
		this.rIndex = index;
		this.nameTag = s;
	}
	
	public void setNameTag(String s) {
		this.nameTag = s;
		
	}	
	
	public String getNameTag() {
		return this.nameTag;
	}
	
	public String nameTagToString(String s) {
		return "\n" + s + "[id=" + id + ", fname=" + fname + ", lname=" + lname + ", Current Rent=" + currentRent + "]\n";
	}
	
//	@Override
	public String sortedAllInAcendingToString() {
		return "\n" + "temp.get(" + this.rIndex + ")"  + this.r.nameTagToString(this.nameTag);
//		return "\n" + "temp.get(" + this.rIndex + ")"  + this.nameTagToString(this.nameTag);
//		return "\n" + "temp.get(" + this.tempNameTagIndex + ") " + this.tempNameTag + "[id=" + id + ", fname=" + fname + ", lname=" + lname + ", Current Rent=" + currentRent + "]\n";
//		return "\n" + "temp.get(" + this.tempNameTagIndex + ") " + getTempNameTag() + "[id=" + id + ", fname=" + fname + ", lname=" + lname + ", Current Rent=" + currentRent + "]\n";
	}
	

	@Override
	public String toString() {
		return "\n" + id + "[id=" + id + ", fname=" + fname + ", lname=" + lname + ", Current Rent=" + currentRent + "]\n";
	}
	
	
}
