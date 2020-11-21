package ie.cct._2018316.cunstructs;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import ie.cct._2018316.dev.Books;
import ie.cct._2018316.dev.Readers;
import ie.cct._2018316.dev.Rent;

public interface FactoryInterface {
	
	/**
	 * Method for loading data
	 * 
	 * @param in of the type bufferedReader
	 * @return an instance of Collection<Rent>
	 * @throws IOException
	 */
	public Collection<Rent> createRentDB(BufferedReader in) throws IOException;


	/**
	 * Method for loading data
	 * 
	 * @param in of the type bufferedReader, List<Rent> rent
	 * @return an instance of Collection<Readers>
	 * @throws IOException
	 */
	Collection<Readers> createReaderDB(BufferedReader in, List<Rent> rent) throws IOException;

	/**
	 * Method for loading data
	 * 
	 * @param in of the type bufferedReader, List<Readers> readers
	 * @return an instance of Collection<Books>
	 * @throws IOException
	 */
	Collection<Books> createBookDB(BufferedReader in, List<Readers> readers) throws IOException;
	
	/**
	 * Method for validating reader input between a book's queue and the existing reader DB
	 * @param readers
	 * @param rID
	 * @return true if equals
	 */
	public boolean validReeaderID(List<Readers> readers, String rID);
	
	/**
	 * Method for validating rent ID between the existing rent DB and reader's current rent
	 * @param rent
	 * @param rentID
	 * @return index num of the existing rent list if equals
	 */
	public int validRentID(List<Rent> rent, String rentID);
	

	/**
	 * Method for writing Rent list to a file
	 * @param rent; ref of List<Rent>
	 * @param isNew; return true if new record-related
	 */
	public void writeRentToFile(List<Rent> rent, boolean isNew);
	
	/**
	 * Method for writing Books list to a file
	 * @param b; ref of List<Books> 
	 */
	public void writeBooksToFile(List<Books> b);
	
	
	/**
	 *  Method for writing Readers list to a file
	 * @param r; ref of List<Readers>
	 */
	public void writeReadersToFile(List<Readers> r);
	
}