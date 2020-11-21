package ie.cct._2018316.dev;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ie.cct._2018316.cunstructs.FactoryInterface;

public class Factory implements FactoryInterface {

	/**
	 * Method for loading data
	 * 
	 * @param in of the type bufferedReader, List<Readers> readers
	 * @return an instance of Collection<Books>
	 * @throws IOException
	 */
	@Override
	public Collection<Books> createBookDB(BufferedReader in, List<Readers> readers) throws IOException {
		
		List<Books> b = new ArrayList<>();
		String[] temp = null, myQueueTemp = null;
		String line, id, title, author, rentalState, readerInQ;
		
		in.readLine();	//skip the description on the top line
		line = in.readLine();

		
		while(line != null) {	
			temp = line.split("#");
			id = temp[0];
			title = temp[1];
			author = temp[2];
			rentalState = temp[3];
			readerInQ = temp[4];
			
			//the variable readerInQ contains reader IDs by the unit of A SPACE or none.
			myQueueTemp = readerInQ.split(" ");	//split readerInQ 
			b.add(new Books(id, title, author, rentalState, myQueueTemp));	//create a new book obj
			
			line = in.readLine();	//read the next line
		}
		
		//OBJs of Books list are just created. 
		//now, deal with queue DB using the array queueDB that is a clone of myQueueTemp above.
		int index = 0;
		String s = "";
		for(int i=0; i<b.size(); i++) {
			
			for(int j=0; j<b.get(i).getQueueDB().length; j++) {
				
				if(!b.get(i).getQueueDB()[j].toString().equals("none")) {
					
					s = b.get(i).getQueueDB()[j].toString();	//the book has at least one queue of reader ID
					
					/* All of text files are exposed to such a risk that 
					 * users can accidentally write wrong-formatted value in them,
					 * which cannot be 100% prevented.
					 * Since the queue field stores a bit more dynamic values, a simple filtering is added.
					 * Check the presence of Reader IDs 
					 * between one that is in a book obj and the original one that is in a reader obj.
					 * (This logic works because Reader DB is loaded before loading the book DB) */
					if(validReeaderID(readers, s)) {
					
						s = s.substring(1);	//ONLY take the valid existing reader ID from the book's queue.
						index = Integer.parseInt(s)-1;	//extract index
						b.get(i).setEnQueue(readers, index);	//enqueue it at a system level
						index = 0;	//flush
						
					}else {
						//wrong-formatted value found from the field value of queue in the Books.txt
						//e.g. B1#Hunger Roof#Georgi Facello#Rented#R1 R2 R3 333   //333 is wrong one
						//So ignore loading it into the system.
						System.out.println("!!! wrong formatted input found from the queue field in the Books.txt !!!"
								+ "\nthe value '" + s + "' won't be loaded into the system."
										+ "\nPleade delete it from the Books.txt");
					}
					
				}else {
					//no queue found
					b.get(i).setReaderInQ("none");
				}
		
			}
		}
		
		return b;
	}
	
	/**
	 * method for checking the presence of queue(reader ID) between the loaded reader DB and the Books.txt
	 * 
	 * return true if equals. 
	 * that means a specific queue(reader ID) written on a specific row in Books.txt before, 
	 * is valid record/info based on the actual reader DB and is ready to be loaded into the system.
	 * */
	@Override
	public boolean validReeaderID(List<Readers> readers, String rID) {
		
		for(int i=0; i<readers.size(); i++) {
			if (readers.get(i).getId().equalsIgnoreCase(rID)) {
					return true;
			  }
		 }
		return false;
	}
	
	/**
	 * method for checking the presence of rent ID between the loaded reader DB and the Readers.txt
	 *
	 * return an index number of the actual rent DB if equals. 
	 * that means a specific current rent ID(s) written on a specific row in Readers.txt before, 
	 * is valid record/info based on the actual rent DB and is ready to be loaded into the system.
	 */
	@Override
	public int validRentID(List<Rent> rent, String rentID) {
		
		for(int i=0; i<rent.size(); i++) {
			if (rent.get(i).getRentID().equalsIgnoreCase(rentID)) {
					return i;
			  }
		 }
		return -1;
	}

	
	/**
	 * Method for loading data
	 * 
	 * @param in of the type bufferedReader, List<Rent> rent
	 * @return an instance of Collection<Readers>
	 * @throws IOException
	 */
	@Override
	public Collection<Readers> createReaderDB(BufferedReader in, List<Rent> rent) throws IOException {
		List<Readers> r = new ArrayList<>();
		List<Rent> myRent = null;
		String[] temp = null, myRentTemp = null; 
		String line, id, fname, lname, myRentLine;		
		int rentIndexToReturn;
		
		in.readLine();
		line = in.readLine();

		while(line != null) {	
			temp = line.split("#");
			id = temp[0];
			fname = temp[1];
			lname = temp[2];
			myRentLine = temp[3];
			if(myRentLine.equals("none")) {
				r.add(new Readers(id, fname, lname, null));	//create a new obj of Readers without current rent
			
			}else {
				//a reader has at least one rent currently
				myRentTemp = myRentLine.split(" ");
				
				if (myRentTemp != null /* && myRentTemp.length > 1 */) {
					
					myRent = new ArrayList<>();	//init myRent
					
					for(int i=0; i<myRentTemp.length; i++) {
						
						
						/* If current rent value(rentID) on a specific row in Readers.txt == a rentID in Rent list,
						 * take an index of Rent list 
						 * while filtering wrong-formatted value of the current rent field in the Readers.txt
						 * (if necessary)  
						 * e,g, R1#Dulce#Abril#RT2 RT3 RT4 999
						 */
						rentIndexToReturn = validRentID(rent, myRentTemp[i]);
						
						if(rentIndexToReturn != -1) {
							myRent.add(rent.get(rentIndexToReturn));	//clone a rent obj found into myRent; a temp list
						
						}else {
							System.out.println("!!! wrong formatted value found from the current rent field in the Readers.txt !!!"
									+ "\nthe value '" + myRentTemp[i] + "' won't be loaded into the system."
									+ "\nPleade delete it from the Readers.txt\n");
													
							}
						
					}
					
					r.add(new Readers(id, fname, lname, myRent));	//create a new obj of Readers with current rent
					myRent = null;	//flush
				}

			}
			line = in.readLine();	//read the next line
			
		}
		return r;
	}

	/**
	 * Method for loading data
	 * 
	 * @param in of the type bufferedReader
	 * @return an instance of Collection<Rent>
	 * @throws IOException
	 */
	@Override
	public Collection<Rent> createRentDB(BufferedReader in) throws IOException {
		
		List<Rent> rt = new ArrayList<>();
		String[] temp = null;
		String line, id, bID, rID, state;
		
		in.readLine();	//skip field description
		line = in.readLine();

		while(line != null) {	
			temp = line.split("#");
			id = temp[0];
			bID = temp[1];
			rID = temp[2];
			state = temp[3];
			rt.add(new Rent(id, bID, rID, state));	//create a new obj of Rent list
			line = in.readLine();
		}
		return rt;
	}
	
	/**
	 * Method for writing Rent list to a file
	 * @param rent; ref of List<Rent>
	 * @param isNew; return true if new record-related
	 */
	@Override
	public void writeRentToFile (List<Rent> rent, boolean isNew) {
		
		String lineZero = "";	
		
		try {	//use try-catch in case there some errors
			//BufferedReader br = new BufferedReader(new FileReader("Rent.txt"));
			
			//new record of Rent
			if(isNew) {
				BufferedWriter bw = new BufferedWriter(new FileWriter("Rent.txt", true));	//append
				bw.newLine();
				bw.write(rent.get(rent.size()-1).getRentID());
				bw.write("#");
				bw.write(rent.get(rent.size()-1).getTitleID());
				bw.write("#");
				bw.write(rent.get(rent.size()-1).getReaderID());
				bw.write("#");
				bw.write(rent.get(rent.size()-1).getState());
				bw.close();	//save
				
			}else {
				//update Rent.txt
				BufferedReader br = new BufferedReader(new FileReader("Rent.txt"));
				lineZero = br.readLine();	//store the field description on line 0 in the txt file
				br.close();

				BufferedWriter bw = new BufferedWriter(new FileWriter("Rent.txt", false));	//overwrite
				
				bw.write(lineZero);	//write the field info
				
				for(int i=0; i<rent.size(); i++) {
					bw.newLine();	
					bw.write(rent.get(i).getRentID());
					bw.write("#");
					bw.write(rent.get(i).getTitleID());
					bw.write("#");
					bw.write(rent.get(i).getReaderID());
					bw.write("#");
					bw.write(rent.get(i).getState());
				}
				
				bw.close();	//save
			}
			
			
		}catch (IOException ioe) {	//it's possible that there were unexpected input & output errors 
			
			System.out.println("IO Error while writing to a file.");
		}		
		
		if(isNew) {
			System.out.println("### A new rent record has just been written into Rent.txt ###");
	
		}else {
			System.out.println("### Rent.txt is updated ###");
		}
		
	}
	
	/**
	 * Method for writing Books list to a file
	 * @param b; ref of List<Books> 
	 */
	@Override
	public void writeBooksToFile(List<Books> b) {
		
		String lineZero = "";	

		try {	//use try-catch in case there some errors
			
			BufferedReader br = new BufferedReader(new FileReader("Books.txt"));
			lineZero = br.readLine();	//store the field description on line 0 in the txt file
			br.close();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter("Books.txt", false));	//overwrite
			bw.write(lineZero);
			//bw.newLine();
			
			
			//update Books.txt
			for(int i=0; i<b.size(); i++) {
				bw.newLine();	
				bw.write(b.get(i).getId());
				bw.write("#");
				bw.write(b.get(i).getTitle());
				bw.write("#");
				bw.write(b.get(i).getAuthor());
				bw.write("#");
				bw.write(b.get(i).getRentalState());
				bw.write("#");
				bw.write(b.get(i).getReaderInQ());
				//bw.newLine();
			}
				
			bw.close();	//save
			
			
		}catch (IOException ioe) {	//it's possible that there were unexpected input & output errors 
			
			System.out.println("IO Error while writing to a file.");
		}		
		
		System.out.println("### Books.txt is updated ###");
	}
	
	/**
	 *  Method for writing Readers list to a file
	 * @param r; ref of List<Readers>
	 */
	@Override
	public void writeReadersToFile(List<Readers> r) {
		
		String lineZero = "";	
		
		try {	//use try-catch in case there some errors
			
			BufferedReader br = new BufferedReader(new FileReader("Readers.txt"));
			lineZero = br.readLine();	//store the field description on line 0 in the txt file
			br.close();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter("Readers.txt", false));	//overwrite
			bw.write(lineZero);	//write the field info
			
			//update Readers.txt
			for(int i=0; i<r.size(); i++) {
				bw.newLine();	
				bw.write(r.get(i).getId());
				bw.write("#");
				bw.write(r.get(i).getFname());
				bw.write("#");
				bw.write(r.get(i).getLname());
				bw.write("#");
				bw.write(r.get(i).getCurrentRent());
			}
				
			bw.close();	//save
			
			
		}catch (IOException ioe) {	//it's possible that there were unexpected input & output errors 
			
			System.out.println("IO Error while writing to a file.");
		}		
		
		System.out.println("### Readers.txt is updated ###");
	}
}
