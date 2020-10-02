package ie.cct._2018316.cunstructs;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import ie.cct._2018316.dev.Books;
import ie.cct._2018316.dev.Readers;

public interface FactoryInterface {
	
	/**
	 * Method for loading data
	 * 
	 * @param in of the type bufferedReader
	 * @return an instance of Books
	 * @throws IOException
	 */
	public Collection<Books> createBookDB(BufferedReader in) throws IOException;
	
	
	/**
	 * Method for loading data
	 * 
	 * @param in of the type bufferedReader
	 * @return an instance of Readers
	 * @throws IOException
	 */
	public Collection<Readers> createReaderDB(BufferedReader in) throws IOException;
  
}