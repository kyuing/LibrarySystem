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
		String line, id, title, author, rentalState;
		
		in.readLine();
		line = in.readLine();

		while(line != null) {	
			temp = line.split("#");
			id = temp[0];
			title = temp[1];
			author = temp[2];
			rentalState = temp[3]; 
			b.add(new Books(id, title, author, rentalState));
			
			line = in.readLine();
		}
		return b;
	}

	@Override
	public Collection<Readers> createReaderDB(BufferedReader in) throws IOException {
		List<Readers> r = new ArrayList<>();
		String[] temp = null;
		String line, id, fname, lname, address;
		
		in.readLine();
		line = in.readLine();

		while(line != null) {	
			temp = line.split("#");
			id = temp[0];
			fname = temp[1];
			lname = temp[2];
			address = temp[3];
			r.add(new Readers(id, fname, lname, address));
			line = in.readLine();
		}
		return r;
	}

}
