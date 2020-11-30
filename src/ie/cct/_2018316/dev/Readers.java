package ie.cct._2018316.dev;

import java.util.ArrayList;
import java.util.List;


public class Readers {

	/* [The field currentRent info in detail]
	 * The value of it has two possible states
	 * "none" == this reader is not renting any books
	 * i.e. "RT1 RT2..." == this reader is renting a book.
	 *                      rent ID(s) that contain related book ID and this reader ID is stored/represented as a string 
	 */
	
	//main fields
	private String id, fname, lname, currentRent;
	private List<Rent> myRent;
	
	//fields used temporarily when searching for readers 
	private String nameTag;
	private int rIndex, indexToRemove;
	private Readers r;
	
	
	/**
	 * specific constructor for loading Readers.txt into the system and creating reader OBJs
	 * @param id
	 * @param fname
	 * @param lname
	 * @param myRent
	 */
	Readers(String id, String fname, String lname, List<Rent> myRent) {
		
		//init fields
		this.id = id;
		this.fname = fname;
		this.lname = lname;		
		this.myRent = myRent;
		setCurrentState();	//init the field currentRent
	}
	
	/**
	 * specific constructor to clone current states of each of Readers for searching a reader
	 * @param r
	 * @param index
	 */
	Readers(Readers r, int index) {
		
		this.r = r;
		this.rIndex = index;
		this.nameTag = null;
		
		//clone value of the fields from the original Reader obj
		this.id = r.getId();
		this.fname = r.getFname();
		this.lname = r.getLname();		
		this.myRent = r.getMyRent();
		setCurrentState();		//clone the field currentRent
	}

	/**
	 * method to initialize/update the field currentRent
	 */
	public void setCurrentState() {

		String toReturn = "";
		if (this.myRent == null || this.myRent.size() == 0) {
			toReturn = "none";
			this.currentRent = toReturn;	
		
		}else {
			
			for(int i=0; i<myRent.size(); i++) {
				
				toReturn += myRent.get(i).getRentID() + " ";	//append with a space
				
				if(i == myRent.size()-1) {

					//remove a space from the end of the string
					toReturn = toReturn.substring(0, toReturn.length() - 1);
				}
			}
			this.currentRent = toReturn;
		}		
	}
	
	/**
	 * method to add a rent info into this book's current rent list
	 * @param currRentRecord
	 */
	public void setMyRent(List<Rent> currRentRecord) {
		
		String toReturn = "";
		
		if(this.myRent == null) {
			this.myRent = new ArrayList<>();
			this.myRent.add(currRentRecord.get(currRentRecord.size()-1));	
			this.currentRent = this.myRent.get(0).getRentID();	
			
		}else {

			this.myRent.add(currRentRecord.get(currRentRecord.size()-1));
			toReturn = this.currentRent + " " + this.myRent.get(this.myRent.size()-1).getRentID(); //append

			this.currentRent = toReturn;	//update the field
		}
		
	}
	
	/**
	 * method to remove a rent from this reader's current rent list 
	 * @param rentIdToRemove
	 */
	public void removeRent(String rentIdToRemove) {
		
		//uncomment out it if wanting to check the state of myRent before executing removing
		//System.out.println("before removing: " + myRent);
		
		if(equalsRentInMyRents(rentIdToRemove)) {
		
			myRent.remove(this.indexToRemove);
			
			//uncomment out it if wanting to check the state of myRent after executing removing
			//System.out.println("after removing: " + myRent);
			
			if(myRent.size() == 0) {
				myRent = null;
			}
			setCurrentState();	//update the field currentRent
		}
	}
	
	/**
	 * method to compare a rent ID in the myRent list of this reader with the parameter rentIdTemp 
	 * @param rentIdTemp
	 * @return true if equals 
	 *         Also, assign the index number found into the field indexToRemove
	 */
	public boolean equalsRentInMyRents(String rentIdTemp) {
		
		this.indexToRemove = 0;	//init it every time the method is called.
				
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
	
	
	//getters & setters
	public List<Rent> getMyRent() {
		return this.myRent;
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
	
	//used when preparing to execute .compareTo() during binary search 
	public void setNameTag(String s) {
		this.nameTag = s;
		
	}	
	
	//used when executing .compareTo() during binary search
	public String getNameTag() {
		return this.nameTag;
	}
	
	
	/************************* toString methods as needed *********************************************/
	
	//used when printing sorted result 
	public String nameTagToString(String s) {
		
		this.nameTag = s;
		return "\n" + nameTag + "[id=" + id + ", fname=" + fname + ", lname=" + lname + ", Current Rent=" + currentRent + "]\n";
	}
	
	//used for testing when executing a binary search for a reader
	public String sortedAllInAcendingToString() {
		return "\n" + "temp.get(" + this.rIndex + ")"  + this.r.nameTagToString(this.nameTag);
	}
	
	public String menu7_1_toString() {
		return "\n" + id + "\n[id=" + id + ", fname=" + fname + ", lname=" + lname + ", Current Rent=" + currentRent + "]\n";
	}
	
	@Override
	public String toString() {
		return "\n" + id + "[id=" + id + ", fname=" + fname + ", lname=" + lname + ", Current Rent=" + currentRent + "]\n";
	}
	
	
}
