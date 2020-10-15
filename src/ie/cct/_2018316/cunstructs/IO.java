package ie.cct._2018316.cunstructs;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public static String printWaitingQueueMenu() {
		return "___________________________________________________________________________"
				+ "\nThe book is in rent"
				+ "\nWould the reader like to be in the waiting queue for renting the book?"
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
