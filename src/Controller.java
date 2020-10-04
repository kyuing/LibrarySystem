import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import ie.cct._2018316.cunstructs.FactoryInterface;
import ie.cct._2018316.cunstructs.IO;
import ie.cct._2018316.dev.Books;
import ie.cct._2018316.dev.Factory;
import ie.cct._2018316.dev.Readers;
import ie.cct._2018316.dev.Rent;

public class Controller {

	BufferedReader books_in, readers_in, rent_in;
	private List<Books> books;
	private List<Readers> readers;
	private List<Rent> rent;
	
	public Controller() throws IOException {
		
		//the structure of reading txt input followed and edited the code (in main method & factoryInterface class) from the sample solution of CA1 in the semester4. 
		FactoryInterface factory = new Factory();	//declare & init factory
	    
		//read input and store them
		String b = "Books.txt";
		books_in = new BufferedReader(new FileReader(b));
    	books = (List<Books>) factory.createBookDB(books_in);
		System.out.println(books);
		
		String r = "Readers.txt";
		readers_in = new BufferedReader(new FileReader(r));
		readers = (List<Readers>) factory.createReaderDB(readers_in);
		System.out.println(readers);
		
		String rt = "Rent.txt";
		rent_in = new BufferedReader(new FileReader(rt));
		rent = (List<Rent>) factory.createRentDB(rent_in);
		System.out.println(rent);

		menu();
	}

	private void menu() {
		
		String op = IO.menu(IO.printMenu(), "^[1|2|3|4|5|6|7]$");
		//test to add rental record first.
		switch(op) {
		  case "5":
		    registerRent();
		    
		  
		  default:
		    menu();
		}
	}

	/**
	 * void method for registering rent record
	 */
	private void registerRent() {
		
		String bID = "", rID = "";
		bID = availableBookToLend(bID);
		if(bID != null) {	//available to lend the book to the reader
			rID = checkReeaderID(rID);
			if(rID != null) {	//the reader exists
				rent.add(new Rent());
				rent.get(rent.size()-1).setRentID("RT" + String.valueOf(rent.size()));
				rent.get(rent.size()-1).setTitleID(bID);
				rent.get(rent.size()-1).setReaderID(rID);
				rent.get(rent.size()-1).setRented(true);
				rent.get(rent.size()-1).setNormal(false);
//				rent.get(rent.size()-1).setState("Rented");	//this part need to be done with booleans in Rent
				books.get(Integer.parseInt(bID.substring(1))-1).setRented(rent.get(rent.size()-1).isRented());
				books.get(Integer.parseInt(bID.substring(1))-1).setAvailable(rent.get(rent.size()-1).isNormal());
				System.out.println(rent);
				System.out.println(books);
				
			}else {
				System.out.println("the reader does not exist. Try again");
			}
			
		} /*
			 * else { System.out.println("null"); }
			 */
	}
	
	/**
	 * method for checking to see if a reader exists
	 * @param rID
	 * @return rID or null
	 */
	public String checkReeaderID(String rID) {
		
		rID = IO.menu(IO.printReaderIDMenu(), "[a-zA-Z0-9]");
		//may need to add some search function to search for reader id
		for(int i=0; i<readers.size(); i++) {
			if (readers.get(i).getId().equals/* IgnoreCase */(rID)) {
					return rID;
			  }
		 }
		return null;
	}

	/**
	 * method for checking to see if a book is available to lend 
	 * @param bID
	 * @return bID or null
	 */
	public String availableBookToLend(String bID) {
		
		bID = IO.menu(IO.printBookIDMenu(), "[a-zA-Z0-9]");	
		//may need to add some search function to search for book id
		for(int i=0; i<books.size(); i++) {
			if ((books.get(i).getId().equals/* IgnoreCase */(bID))) {
				  int bookID = Integer.parseInt(bID.substring(1));
				  if(books.get(bookID-1).isAvailable()) {
					  //if available, the book is returned so it's available
					  //here need to check if there's waiting queue
					  //return the reader ID to create new Rent?
					  
					  
					  //so if there's waiting quoue which will be a reader ID,
					  //that reader should be informed with its available to rent
					  //and current input id should go to else statement
					  
					  
					  //if there's no quoue, return bID
					return bID;
					
				  }else {
					System.out.println("the book is in rent");
					//maybe add by who here
					//need to ask if wanna have a waiting queue
					//and put it into queue titleID, reader Id in order
				  }  
			  }  
		 }
		return null;
	}
}
