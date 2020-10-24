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
				+ "\n7: Close program";
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
	
	public static String printWaitingQueueMenu(List<Books> books, String bookID) {
		
		int index = 0;
		index = Integer.parseInt(bookID.substring(1))-1;
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
