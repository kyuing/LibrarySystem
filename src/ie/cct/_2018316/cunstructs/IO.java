package ie.cct._2018316.cunstructs;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class IO {
	
	static Scanner sc;
	private static Pattern p = null;
    private static Matcher m = null;

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
				+ "\nEnter the reader ID"
				+ "\n* This is case-sensitive. Please enter the reader ID as it is"
				+ "\n* i.g. R1  => correct"
				+ "\n* i.g. r1  => incorrect";
	}
	
	public static String printBookIDMenu() {
		return "___________________________________________________________________________"
				+ "\nEnter the book ID"
				+ "\n* This is case-sensitive. Please enter the book ID as it is"
				+ "\n* i.g. B1  => correct"
				+ "\n* i.g. b1  => incorrect";
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
