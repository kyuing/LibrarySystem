import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import ie.cct._2018316.cunstructs.FactoryInterface;
import ie.cct._2018316.cunstructs.IO;
import ie.cct._2018316.dev.Books;
import ie.cct._2018316.dev.Factory;
import ie.cct._2018316.dev.Readers;

public class Controller {

	BufferedReader books_in, readers_in;
	private List<Books> books;
	private List<Readers> readers;
	
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

		menu();
	}

	private void menu() {
		
		IO.menu(IO.printMenu(), "^[1|2|3|4]$");
		//need rent entity for sure?
		
		
	}
}
