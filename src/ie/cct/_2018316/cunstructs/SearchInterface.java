package ie.cct._2018316.cunstructs;

import java.util.List;

import ie.cct._2018316.dev.Books;

public interface SearchInterface {
	
	public boolean equalsCustomBook(List<Books> b, String keyword);
	
	/**
	 * Returns the number of elements
	 * @return
	 */
	public int size();
	
	/**
	 * 
	 * @return false if empty
	 */
	public boolean isEmpty();

}
