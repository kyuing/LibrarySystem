package ie.cct._2018316.dev;

import java.io.BufferedReader;
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
}
