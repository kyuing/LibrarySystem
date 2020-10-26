package ie.cct._2018316.cunstructs;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import ie.cct._2018316.dev.Books;
import ie.cct._2018316.dev.Readers;
import ie.cct._2018316.dev.Rent;

public interface FactoryInterface {
	
//	/**
//	 * Method for loading data
//	 * 
//	 * @param in of the type bufferedReader
//	 * @return an instance of Books
//	 * @throws IOException
//	 */
//	public Collection<Books> createBookDB(BufferedReader in) throws IOException;
	
	
//	/**
//	 * Method for loading data
//	 * 
//	 * @param in of the type bufferedReader
//	 * @return an instance of Readers
//	 * @throws IOException
//	 */
//	public Collection<Readers> createReaderDB(BufferedReader in) throws IOException;
  
	/**
	 * Method for loading data
	 * 
	 * @param in of the type bufferedReader
	 * @return an instance of Rent
	 * @throws IOException
	 */
	public Collection<Rent> createRentDB(BufferedReader in) throws IOException;


	/**
	 * Method for loading data
	 * 
	 * @param in of the type bufferedReader, List<Rent> rent
	 * @return an instance of Readers
	 * @throws IOException
	 */
	Collection<Readers> createReaderDB(BufferedReader in, List<Rent> rent) throws IOException;

	/**
	 * Method for loading data
	 * 
	 * @param in of the type bufferedReader
	 * @return an instance of Books, , List<Readers> readers
	 * @throws IOException
	 */
	Collection<Books> createBookDB(BufferedReader in, List<Readers> readers) throws IOException;
	
	/**
	 * Method for validating reader input between a book's queue and the existing reader DB
	 * @param readers
	 * @param rID
	 * @return true if reader ID in List<Readers> readers exists
	 */
	public boolean validReeaderID(List<Readers> readers, String rID);
}