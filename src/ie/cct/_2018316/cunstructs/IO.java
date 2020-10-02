package ie.cct._2018316.cunstructs;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class IO {
	
	static Scanner sc;
	private static Pattern p = null;
    private static Matcher m = null;

	public static String printMenu() {
		return "\n++++++++++Please select menu+++++++++++++"
				+ "\n1: Search for books"
				+ "\n2: List all books"
				+ "\n3: Search for readers"
				+ "\n4: List all readers"
				+ "\n3: Close program";
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
