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
import ie.cct._2018316.cunstructs.IO;

public class Factory implements FactoryInterface {

	@Override
	public Collection<Books> createBookDB(BufferedReader in, List<Readers> readers) throws IOException {
		
		List<Books> b = new ArrayList<>();
		String[] temp = null, myQueueTemp = null;
		String line, id, title, author, rentalState, readerInQ;
		
		in.readLine();
		line = in.readLine();

		
		while(line != null) {	
			temp = line.split("#");
			id = temp[0];
			title = temp[1];
			author = temp[2];
			rentalState = temp[3];
			readerInQ = temp[4];
			myQueueTemp = readerInQ.split(" ");
			b.add(new Books(id, title, author, rentalState, myQueueTemp));
			
			line = in.readLine();
		}
		
		
		
//		for(int i=0; i<readers.size(); i++) {
//			
//			for(int j=0; j<b.get(i).getTestingQueueDB().length; j++) {
//				
//				if(readers.get(i).getId().equals(anObject))
//		
//			}
//		}

		int index = 0;
		String s = ""/*, rIdToString = ""*/;
		for(int i=0; i<b.size(); i++) {
			
			for(int j=0; j<b.get(i).getTestingQueueDB().length; j++) {
				
				if(!b.get(i).getTestingQueueDB()[j].toString().equals("none")) {
					
					s = b.get(i).getTestingQueueDB()[j].toString();
					if(validReeaderID(readers, s)) {
						//ONLY take the valid existing reader ID from the book's queue whatever is there.
						s = s.substring(1);
						index = Integer.parseInt(s)-1;
						b.get(i).setEnQueue(readers, index);
						index = 0;
					} /*
						 * else { System.out.println("wrong wrong wrong"); }
						 */
					
				}else {
					b.get(i).setReaderInQ("none");
				}
		
			}
		}
		
		return b;
	}
	
	
	@Override
	public boolean validReeaderID(List<Readers> readers, String rID) {
		
		
		//may need to add some search function to search for reader id
		for(int i=0; i<readers.size(); i++) {
			if (readers.get(i).getId().equalsIgnoreCase(rID)) {
					return true;
			  }
		 }
		return false;
	}
	

	@Override
	public Collection<Readers> createReaderDB(BufferedReader in, List<Rent> rent) throws IOException {
		List<Readers> r = new ArrayList<>();
		List<Rent> myRent = null;
		String[] temp = null, myRentTemp = null; 
		String line, id, fname, lname, myRentLine;		
		
		in.readLine();
		line = in.readLine();

		while(line != null) {	
			temp = line.split("#");
			id = temp[0];
			fname = temp[1];
			lname = temp[2];
			myRentLine = temp[3];
			if(myRentLine.equals("none")) {
				r.add(new Readers(id, fname, lname, null));
			
			}else {
				myRentTemp = myRentLine.split(" ");
				
				if (myRentTemp != null /* && myRentTemp.length > 1 */) {
					
					myRent = new ArrayList<>();
					
					for(int i=0; i<rent.size(); i++) {
						
						for(int j=0; j<myRentTemp.length; j++) {
							if(rent.get(i).getRentID().equals(myRentTemp[j].toString())) {
								
								myRent.add(rent.get(i));
							}
						}
					}
				}
				
				r.add(new Readers(id, fname, lname, myRent));
				myRent = null;
			}

			line = in.readLine();
		}
		return r;
	}

	@Override
	public Collection<Rent> createRentDB(BufferedReader in) throws IOException {
		List<Rent> rt = new ArrayList<>();
		String[] temp = null;
		String line, id, bID, rID, state;
		
		in.readLine();
		line = in.readLine();

		while(line != null) {	
			temp = line.split("#");
			id = temp[0];
			bID = temp[1];
			rID = temp[2];
			state = temp[3];
			rt.add(new Rent(id, bID, rID, state));
			line = in.readLine();
		}
		return rt;
	}
	
	@Override
	public void writeNewRentToFile (List<Rent> rent, boolean isNew) {
		
		String lineZero = "";	
		
		try {	//use try-catch in case there some errors
			//BufferedReader br = new BufferedReader(new FileReader("Rent.txt"));
			
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
				bw.close();
				
			}else {
				
				BufferedReader br = new BufferedReader(new FileReader("Rent.txt"));
				lineZero = br.readLine();	//store the field description on line 0 in the txt file
				br.close();

				BufferedWriter bw = new BufferedWriter(new FileWriter("Rent.txt", false));	//overwrite
				
				bw.write(lineZero);
				
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
				
				bw.close();
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
	
	@Override
	public void writeBooksToFile(List<Books> b) {
		
		String lineZero = "";	

		try {	//use try-catch in case there some errors
			
			BufferedReader br = new BufferedReader(new FileReader("Books.txt"));
			lineZero = br.readLine();	//store the field description on line 0 in the txt file
			br.close();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter("Books.txt", false));	//overwrite
			bw.write(lineZero);
//			bw.newLine();
			
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
//				bw.newLine();
			}
				
			bw.close();
			
			
		}catch (IOException ioe) {	//it's possible that there were unexpected input & output errors 
			
			System.out.println("IO Error while writing to a file.");
		}		
		
		System.out.println("### Books.txt is updated ###");
	}
	
	@Override
	public void writeReadersToFile(List<Readers> r) {
		
		String lineZero = "";	
		
		try {	//use try-catch in case there some errors
			
			BufferedReader br = new BufferedReader(new FileReader("Readers.txt"));
			lineZero = br.readLine();	//store the field description on line 0 in the txt file
			br.close();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter("Readers.txt", false));	//overwrite
			bw.write(lineZero);
			
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
				
			bw.close();
			
			
		}catch (IOException ioe) {	//it's possible that there were unexpected input & output errors 
			
			System.out.println("IO Error while writing to a file.");
		}		
		
		System.out.println("### Readers.txt is updated ###");
	}
}
