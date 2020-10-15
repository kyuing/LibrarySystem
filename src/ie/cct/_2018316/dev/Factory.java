package ie.cct._2018316.dev;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ie.cct._2018316.cunstructs.FactoryInterface;

public class Factory implements FactoryInterface {

	@Override
	public Collection<Books> createBookDB(BufferedReader in) throws IOException {
		
		List<Books> b = new ArrayList<>();
		String[] temp = null;
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
			b.add(new Books(id, title, author, rentalState, readerInQ));
			
			line = in.readLine();
		}
		return b;
	}

	@Override
	public Collection<Readers> createReaderDB(BufferedReader in) throws IOException {
		List<Readers> r = new ArrayList<>();
		String[] temp = null;
		String line, id, fname, lname, currentRent;
		
		in.readLine();
		line = in.readLine();

		while(line != null) {	
			temp = line.split("#");
			id = temp[0];
			fname = temp[1];
			lname = temp[2];
			currentRent = temp[3];
			r.add(new Readers(id, fname, lname, currentRent));
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
