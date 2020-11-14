package ie.cct._2018316.cunstructs;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ie.cct._2018316.dev.Books;

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
				+ "\nEnter the reader ID and/or the name";
	}
	
//	public static String printReaderSearchOptionMenu() {
//		return "___________________________________________________________________________"
//				+ "\nSelect one of the following options for searching for a reader. "
//				+ "\n\n1: search for a book by multiple search keywords"
//				+ "\n2: search for a book with the extact title name & author match."
//				+ "\n   (This option requires accurate inputs of a specific book)"
//				+ "\n\nq: go back to menu ";
//	}
	
	public static String printBookSearchOptionMenu() {
		return "___________________________________________________________________________"
				+ "\nSelect one of the following options for searching for a book. "
				+ "\n\n1: search for a book by multiple search keywords"
				+ "\n2: search for a book with the exact title name & author match."
				+ "\n   (This option requires accurate inputs of a specific book)"
				+ "\n\nq: go back to menu ";
	}
	
	public static String printReaderSearchOptionMenu() {
		return "___________________________________________________________________________"
				+ "\nSelect one of the following options for searching for a reader. "
				+ "\n\n1: search for a reader by multiple search keywords"
				+ "\n2: search for a reader with the exact ID & name(firstname, last name) match."
				+ "\n   (This option requires accurate inputs of a specific reader)"
				+ "\n\nq: go back to menu ";
	}
	
	public static String printBookSortOptionMenu() {
		return "___________________________________________________________________________"
				+ "\nSelect one of the following options for listing books in alphabetical order. "
				+ "\n\n1: list title"
				+ "\n2: list author."
				+ "\n3: list title and author"
				+ "\n\nq: go back to menu ";
	}
	
	public static String printReaderSortOptionMenu() {
		return "___________________________________________________________________________"
				+ "\nSelect one of the following options for listing readers in alphabetical order. "
				+ "\n\n1: list reader IDs"
				+ "\n2: list first names."
				+ "\n3: list last names"
				+ "\n4: list reader IDs and firstname, last name"
				+ "\n\nq: go back to menu ";
	}
	
	public static String printWaitingQueueMenu(List<Books> books, String bookID) {
		
		int index = 0;
		index = Integer.parseInt(bookID.substring(1))-1;
		
		if(books.get(index).getMQ() == null) {
			return "___________________________________________________________________________"
					+ "\n* The following book is in rent "
					+ "\n* Otherwise, it has the existing preceding waiting queue "
					+ "\n  for readers to have chance to rent the book in order of the waiting queue. "
//					+ "\n___________________________________________________________________________"
					+ "\n" + books.get(index)
					+ "\n" + books.get(index).getRentalState().toString()
					+ "\n___________________________________________________________________________"
					+ "\n* Would the reader like to be in the waiting queue for renting the book?"
					+ "\nEnter y if so"
					+ "\nEnter any other key(s) to go back to the main menu";
		}
		
		return "___________________________________________________________________________"
				+ "\n* The following book is in rent "
				+ "\n* Otherwise, it has the existing preceding waiting queue "
				+ "\n  for readers to have chance to rent the book in order of the waiting queue. "
//				+ "\n___________________________________________________________________________"
				+ "\n" + books.get(index)
				+ "\n" + books.get(index).getMQ().toString()
				+ "\n___________________________________________________________________________"
				+ "\n* Would the reader like to be in the waiting queue for renting the book?"
				+ "\nEnter y if so"
				+ "\nEnter any other key(s) to go back to the main menu";
	}
	
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

	private static boolean matchesInput(String input, String regx) {
		p = Pattern.compile(regx);
		m = p.matcher(input);
		return m.find();
	}
}
