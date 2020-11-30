package ie.cct._2018316.cunstructs;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ie.cct._2018316.dev.Books;
import ie.cct._2018316.dev.Readers;
import ie.cct._2018316.dev.Rent;

/**
 * Class that manages all the text-based input and output interactions
 * @author Kyu
 *
 */
public final class IO {
	
	static Scanner sc;
	private static Pattern p = null;
    private static Matcher m = null;

    public static String printUnderLine() {
    	return "___________________________________________________________________________";
    }
    
    public static String printHyphen() {
    	return "---------------------------------------------------------------------------";
    }
    
    public static String printRendRecordCheckOption() {
    	return "___________________________________________________________________________" 
    			+ "\nAre you sure that the rent record above is correct?"
    			+ "\nEnter y if so"
    			+ "\nEnter n to double check the reader ID"
    			+ "\nEnter q to go back to main menu";
    }
	public static String printMenu() {
		return "___________________________________________________________________________"
				+ "\n[Please select menu]"
				+ "\n1: Search for books"
				+ "\n2: List books"
				+ "\n3: Search for readers"
				+ "\n4: List readers"
				+ "\n5: Register a rent"
				+ "\n6: Register a return"
				+ "\n7: List the books that readers have borrowed"
				+ "\n8: Close program";
	}
	
	public static String printReaderIDMenu() {
		return "___________________________________________________________________________"
				+ "\nEnter the reader ID";
	}
	
	public static String printBookIDMenu() {
		return "___________________________________________________________________________"
				+ "\nEnter the book ID";
	}
	
	public static String printRentIDMenu() {
		return "___________________________________________________________________________"
				+ "\nEnter the rent Id";
	}
	
	public static String printForListingRentHistoryOfReaders() {
		return "___________________________________________________________________________"
				+ "\nSelect one of the following options for listing the books that readers have borrowed. "
				+ "\n\n1: list all of books that all of readers have borrowed."
				+ "\n   (This is based on the order of each of the rent records created)"
				+ "\n\n2: list all of books that a specific reader have borrowed."
				+ "\n\n3: list all of books that a specific reader is borrowing currently."
				+ "\n\nq: go back to menu ";
	}

	
	public static String printBookSearchMenu() {
		return "___________________________________________________________________________"
				+ "\nEnter the book ID, the title name or the author. ";
	}
	
	public static String printReaderSearchMenu() {
		return "___________________________________________________________________________"
				+ "\nEnter the reader ID, first name or last name";
	}
	
	public static String printBookSearchOptionMenu() {
		return "___________________________________________________________________________"
				+ "\nSelect one of the following options for searching for a book. "
				+ "\n\n1: search for a book by multiple search keywords"
				+ "\n2: search for a book by title and author"
				+ "\n   (This option requires accurate inputs of a specific book)"
				+ "\n\nq: go back to menu ";
	}
	
	public static String printReaderSearchOptionMenu() {
		return "___________________________________________________________________________"
				+ "\nSelect one of the following options for searching for a reader. "
				+ "\n\n1: search for a reader by multiple search keywords"
				+ "\n2: search for a reader by reader ID and first name and last name"
				+ "\n   (This option requires accurate input of a specific reader)"
				+ "\n\nq: go back to menu ";
	}
	
	public static String printBookSortOptionMenu() {
		return "___________________________________________________________________________"
				+ "\nSelect one of the following options for listing books in alphabetical order. "
				+ "\n\n1: list IDs"
				+ "\n2: list titles"
				+ "\n3: list authors"
				+ "\n4: list titles and authors"
				+ "\n5: list book IDs, titles and authors"
				+ "\n\nq: go back to menu ";
	}
	
	public static String printReaderSortOptionMenu() {
		return "___________________________________________________________________________"
				+ "\nSelect one of the following options for listing readers in alphabetical order. "
				+ "\n\n1: list reader IDs"
				+ "\n2: list first names"
				+ "\n3: list last names"
				+ "\n4: list first and last names"
				+ "\n5: list reader IDs and first names, last names"
				+ "\n\nq: go back to menu ";
	}
	
	public static String printWaitingQueueMenu(List<Books> books, int bIndex,/* List<Readers> r, int rIndex, */ String userInput, boolean isAskReaderQEntry) {
		
		String toReturn = "";
		if(isAskReaderQEntry) {

				toReturn = "___________________________________________________________________________"
						//+ "\n* The book with ID \"" + books.get(bIndex).getId() + "\" is being rented by the reader with ID " + "\"" + r.get(rIndex).getId() + "\""					
						+ "\n<Book info>" + books.get(bIndex)
						+ books.get(bIndex).getMQ().toString()
						+ "\n___________________________________________________________________________"
						+ "\n* Would the reader with the input ID \"" + userInput + "\" like to be in the waiting queue for renting the book(\"" + books.get(bIndex).getId() + "\")?"
						+ "\nEnter y if so"
						+ "\nEnter any other key(s) to go back to the main menu";
			
		}
		
		return toReturn;
		
	}
	
	public static String printWaitingQueueMenu(List<Rent> rent, int rentIndex, List<Books> books, int bIndex, List<Readers> r, int rIndex, String userInput, boolean isAskReaderQEntry) {
		
		if(isAskReaderQEntry) {
			
			if(books.get(bIndex).getMQ() == null) {
				return "___________________________________________________________________________"
						+ "\n* The book with ID \"" + books.get(bIndex).getId() + "\" is being rented by the reader with ID " + "\"" + r.get(rIndex).getId() + "\""					
						+ "\n\n<Rent info>" + rent.get(rentIndex)
						+ "\n\n<Book info>" + books.get(bIndex)
						+ "\n\n<Reader info>" 
						+ "\nThe following reader with ID \"" + r.get(rIndex).getId() + "\" has a current rent record with the rent ID \"" + rent.get(rentIndex).getRentID() + "\","
						+ "\nwhose book ID(title ID) is \"" + books.get(bIndex).getId() + "\"" + "\n" + r.get(rIndex) 
						+ "\n___________________________________________________________________________"
						+ "\n* Would the reader with the input ID \"" + userInput + "\" like to be in the waiting queue for renting the book(\"" + books.get(bIndex).getId() + "\")?"
						+ "\nEnter y if so"
						+ "\nEnter any other key(s) to go back to the main menu";
			}
			
			return "___________________________________________________________________________"
					+ "\n* The book with ID \"" + books.get(bIndex).getId() + "\" is being rented by the reader with ID " + "\"" + r.get(rIndex).getId() + "\"" 
					+ "\n  The book also has a queue of reader(s) waiting for renting."
					+ "\n\n<Rent info>" + rent.get(rentIndex)
					+ "\n\n<Book info>" + books.get(bIndex)
					+ books.get(bIndex).getMQ().toString()
					+ "\n\n\n<Reader info>" 
					+ "\nThe following reader with ID \"" + r.get(rIndex).getId() + "\" has a current rent record with the rent ID \"" + rent.get(rentIndex).getRentID() + "\","
					+ "\nwhose book ID(title ID) is \"" + books.get(bIndex).getId() + "\"" + "\n" + r.get(rIndex) 
					+ "\n___________________________________________________________________________"
					+ "\n* Would the reader with the input ID \"" + userInput + "\" like to be in the waiting queue for renting the book(\"" + books.get(bIndex).getId() + "\")?"
					+ "\nEnter y if so"
					+ "\nEnter any other key(s) to go back to the main menu";
		}else {
			
			if(books.get(bIndex).getMQ() == null) {
				return "___________________________________________________________________________"
						+ "\n* The book with ID \"" + books.get(bIndex).getId() + "\" is being rented by the reader with ID " + "\"" + r.get(rIndex).getId() + "\""					
						+ "\n\n* Please infrom the reader that returing the book is FIRST for the reader to be in the book's queue"
						+ "\n\n<Rent info>" + rent.get(rentIndex)
						+ "\n\n<Book info>" + books.get(bIndex)
						+ "\n\n<Reader info>" 
						+ "\nThe following reader with ID \"" + r.get(rIndex).getId() + "\" has a current rent record with the rent ID \"" + rent.get(rentIndex).getRentID() + "\","
						+ "\nwhose book ID(title ID) is \"" + books.get(bIndex).getId() + "\"" + "\n" + r.get(rIndex) ;
			}
			
			return "___________________________________________________________________________"
					+ "\n* The book with ID \"" + books.get(bIndex).getId() + "\" is being rented by the reader with ID " + "\"" + r.get(rIndex).getId() + "\"" 
					+ "\n  The book also has a queue of reader(s) waiting for renting."
					+ "\n\n* Please infrom the reader that returing the book is FIRST for the reader to be in the book's queue"
					+ "\n\n<Rent info>" + rent.get(rentIndex)
					+ "\n\n<Book info>" + books.get(bIndex)
					+ books.get(bIndex).getMQ().toString()
					+ "\n\n\n<Reader info>" 
					+ "\nThe following reader with ID \"" + r.get(rIndex).getId() + "\" has a current rent record with the rent ID \"" + rent.get(rentIndex).getRentID() + "\","
					+ "\nwhose book ID(title ID) is \"" + books.get(bIndex).getId() + "\"" + "\n" + r.get(rIndex) ;
		}
		
		
	}
	
	/**
	 * method to prompt menu and return a valid input user enters
	 * @param prompt
	 * @param regx
	 * @return valid input
	 */
	public static String menu(String prompt, String regx) {
		
		boolean isValid = false;
		String input = "";
		sc = new Scanner(System.in);
		
		do {	
			System.out.println(prompt);
			try {
				input = sc.nextLine();
				if(matchesInput(input, regx)) {
					isValid = true;
				}
			} catch (Exception e) {
				System.out.println("Invalid input. Try again");
				isValid = false;
			}
		} while (!isValid);
		
		return input;
	}

	/**
	 * method to validate user's input
	 * @param input
	 * @param regx
	 * @return true if @param input matches with @param regx defined
	 */
	private static boolean matchesInput(String input, String regx) {
		p = Pattern.compile(regx);
		m = p.matcher(input);
		return m.find();
	}
}
