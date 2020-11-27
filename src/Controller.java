import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ie.cct._2018316.cunstructs.FactoryInterface;
import ie.cct._2018316.cunstructs.IO;
import ie.cct._2018316.dev.Books;
import ie.cct._2018316.dev.Factory;
import ie.cct._2018316.dev.Readers;
import ie.cct._2018316.dev.Rent;
import ie.cct._2018316.dev.Search;
import ie.cct._2018316.dev.Sort;

public class Controller {

	BufferedReader books_in, readers_in, rent_in;
	private List<Books> books;
	private List<Readers> readers;
	private List<Rent> rent;
	private FactoryInterface factory;
	private String readerIDFoundForMenu7_2 = null;
	private int readerIndexFoundForMenu7_3;

	public Controller() throws IOException {

		// the structure of reading txt-input followed and edited the code (in main
		// method & factoryInterface class)
		// from the sample solution of CA1 in the semester4.
		factory = new Factory(); // declare & init factory

		String rt = "Rent.txt";
		rent_in = new BufferedReader(new FileReader(rt));
		rent = (List<Rent>) factory.createRentDB(rent_in);
		System.out.println(rent);

		String r = "Readers.txt";
		readers_in = new BufferedReader(new FileReader(r));
		readers = (List<Readers>) factory.createReaderDB(readers_in, rent);
		System.out.println(readers);

		// read input and store them
		String b = "Books.txt";
		books_in = new BufferedReader(new FileReader(b));
		books = (List<Books>) factory.createBookDB(books_in, readers);
		System.out.println(books);

		for(int i=0; i<books.size(); i++) {
			if (books.get(i).getMQ() != null) {
				if (!books.get(i).getMQ().isEmpty()) {
					System.out.println(IO.printUnderLine() + "\n!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!"
							+ "\n[The book info]\n" + books.get(i).toString()
							+ "\n\n[The first available reader in the book's queue for renting]\n"
							+ books.get(i).getMQ().firstToString());
				}
			}	
		}
		
		menu();
	}

	private void menu() {

		String op = IO.menu(IO.printMenu(), "^[1|2|3|4|5|6|7|8]$");

		switch (op) {

		case "1":
			searchForBook();
			menu();

		case "2":
			sortBooks();
			menu();

		case "3":
			searchForReader();
			menu();

		case "4":
			sortReaders();
			menu();

		case "5":
			registerRent();
			menu();

		case "6":
			registerReturn();
			menu();

		case "7":
			listRentHistoryOfReaders();
			menu();

		case "8":
			System.out.println(IO.printUnderLine() + "\nBYE :)");
			System.exit(0);

		default:
			menu();
		}
	}

	private void listRentHistoryOfReaders() {

		String op = IO.menu(IO.printForListingRentHistoryOfReaders(), "^[1|2|3|q|Q]$");
		switch (op) {
		case "1": // all record
			System.out.println(IO.printUnderLine() 
					+ "\n### All of the books that readers have borrowed ###"
					+ "\n\n* Quick reminder for the filed value of 'state' on a rent record"
					+ "\n  'state=Rented' represents the book(titleID) is being rented the reader(readerID)"
					+ "\n  'state=Normal' represents the reader(readerID) has returned the book(titleID)");
			

			for (int i = 0; i < rent.size(); i++) {
				System.out.print(rent.get(i));

			}
			System.out.println();
			
			String advOp = IO.menu(IO.printUnderLine() 
					+ "\nWould you like to see all the rent record in detail(y/n)?", "^[a-zA-Z]$");
			if(advOp.equalsIgnoreCase("y")) {
				
				System.out.println(IO.printUnderLine()
						+ "\n* The following is the breakdown represented by"
						+ "\n  Line 1 == a specific rent record found in the previous step."
						+ "\n            ->> [Quick reminder] for the filed value of 'state' on a rent record, Line 1"
						+ "\n                'state=Rented' represents the book(titleID) is being rented the reader(readerID)"
						+ "\n                'state=Normal' represents the reader(readerID) has returned the book(titleID)"
						+ "\n\n  Line 2 == the book info belonged to the rent record"
						+ "\n  Line 3 == the reader info belonged to the rent record");
				
//				System.out.println("\n* Quick reminder for the filed value of 'state' on a rent record"
//						+ "\n  'state=Rented' represents the book(titleID) is being rented the reader(readerID)"
//						+ "\n  'state=Normal' represents the reader(readerID) has returned the book(titleID)");
				
				//it doesnt work as expected.
				for (int i = 0; i < rent.size(); i++) {
					
					System.out.println(IO.printHyphen() + rent.get(i)/* .menu7_1_toString() */
					+ books.get(Integer.parseInt(rent.get(i).getTitleID().substring(1)) - 1).menu7_1_toString(rent, i, readers, Integer.parseInt(rent.get(i).getReaderID().substring(1)) - 1)/*.menu7_1_toString()*/
					+ readers.get(Integer.parseInt(rent.get(i).getReaderID().substring(1)) - 1)/*.menu7_1_toString()*/);
//							+ books.get(Integer.parseInt(rent.get(i).getTitleID().substring(1)) - 1)/*.menu7_1_toString()*/
//							+ readers.get(Integer.parseInt(rent.get(i).getReaderID().substring(1)) - 1)/*.menu7_1_toString()*/);

				}	
			}else  {
				System.out.println("going back to menu...");
			}
			
			break;

		case "2": // all record of a specific reader

			int objCounter = 0;
			searchForReader();

			if (this.readerIDFoundForMenu7_2 != null) {

				System.out.println(IO.printUnderLine() + "\n### all of books that a specific reader have borrowed ###\n");

				for (int i = 0; i < rent.size(); i++) {

					if (rent.get(i).getReaderID().equals(this.readerIDFoundForMenu7_2)) {
						objCounter++;
					}
				}
				
				if(objCounter > 0) {
					
					System.out.println("* Rent history found with the reader ID '" 
					+ this.readerIDFoundForMenu7_2 + "'");
					for (int i = 0; i < rent.size(); i++) {
						if (rent.get(i).getReaderID().equals(this.readerIDFoundForMenu7_2)) {
							System.out.print(rent.get(i));
						}
					}

					//here, since the rent histories 
					System.out.println(IO.printUnderLine()
							+ "\n* The following is the breakdown represented by"
							+ "\n  Line 1 == a specific rent record found'"
							+ "\n  Line 2 == the book info belonged to the rent record'"
							+ "\n  Line 3 == the reader info belonged to the rent record'");
					for (int i = 0; i < rent.size(); i++) {
						if (rent.get(i).getReaderID().equals(this.readerIDFoundForMenu7_2)) {
							System.out.println(IO.printHyphen()
									+ rent.get(i)/* .menu7_1_toString() */
									+ books.get(Integer.parseInt(rent.get(i).getTitleID().substring(1)) - 1)
									/* .menu7_2_toString() */
									+ readers.get(Integer.parseInt(rent.get(i).getReaderID().substring(1)) - 1)
							/* .menu7_2_toString() */);	
						}
					}
					this.readerIDFoundForMenu7_2 = null;
					
					
				}else {
					System.out.println("!!! No rent history belonged to the reader ID "
							+ "'" + this.readerIDFoundForMenu7_2 + "'"
							+ " found !!!");
					this.readerIDFoundForMenu7_2 = null;
				}
				
				
			}
			break;

		case "3": // all record that a specific reader is CURRENTLY renting

			searchForReader();

			if (readerIndexFoundForMenu7_3 != -1) {
				if(readers.get(readerIndexFoundForMenu7_3).getMyRent() != null) {
					System.out.print(IO.printUnderLine() + "\n### all of books that a specific reader is borrowing currently ###\n");
					System.out.println("\n* Reader info" + "\n" + readers.get(readerIndexFoundForMenu7_3));
					System.out.println(IO.printHyphen() + "\n* Current rent of books");
					for (int i = 0; i < readers.get(readerIndexFoundForMenu7_3).getMyRent().size(); i++) {
						System.out.print(readers.get(readerIndexFoundForMenu7_3).getMyRent().get(i));
					}
				}else {
					System.out.println("!!! The reader is not renting any books currently. !!!");
				}
				//readerIndexFoundForMenu7_3 = 0;
			}
			break;

		default:
			System.out.println("going back to menu...");
		}

	}

	private void sortReaders() {

		Sort s = new Sort();
		String[] result = null;

		String op = IO.menu(IO.printReaderSortOptionMenu(), "^[1|2|3|4|q|Q]$");
		switch (op) {
		case "1":
			// id sort
			if ((result = s.collectFieldsOfReaders(this.readers, 1)) != null) {
				result = s.bubbleSort(result);
				System.out.println(IO.printUnderLine() + "\n[ID of all the readers sorted in alphabetical order.]\n");
				s.printSortedResultOfReaders(result, this.readers, 1);
			}
			break;
		case "2":
			// first name sort
			if ((result = s.collectFieldsOfReaders(this.readers, 2)) != null) {
				result = s.bubbleSort(result);
				System.out.println(
						IO.printUnderLine() + "\n[First names of all the readers sorted in alphabetical order.]\n");
				s.printSortedResultOfReaders(result, this.readers, 2);
			}
			break;
		case "3":
			// last name sort
			if ((result = s.collectFieldsOfReaders(this.readers, 3)) != null) {
				result = s.bubbleSort(result);
				System.out.println(
						IO.printUnderLine() + "\n[Last names of all the readers sorted in alphabetical order.]\n");
				s.printSortedResultOfReaders(result, this.readers, 3);
			}
			break;

		case "4":
			// id, first name and last name
			if ((result = s.collectFieldsOfReaders(this.readers, 4)) != null) {
				result = s.bubbleSort(result);
				System.out.println(
						IO.printUnderLine() + "\n[ID, names of all the readers sorted in alphabetical order.]\n");
				s.printSortedResultOfReaders(result, this.readers, 4);
			}
			break;

		default:
			System.out.println("going back to menu...");

		}

	}

	private void searchForReader() {

		String keyword = "", askUserOp = "";

		askUserOp = IO.menu(IO.printReaderSearchOptionMenu(), "^[1|2|q|Q]$");
		
		if (askUserOp.equals("1")) {	//search for reader allowing multiple keywords on a line
			keyword = IO.menu(IO.printReaderSearchMenu(), "[a-zA-Z0-9]++");

			if (keyword != null) {

				Sort sort = new Sort();
				String[] arr = null; // arr for storing the values of reader ID, 1st name and 2nd name
				

				//the size of arr should be readers.size() * 3 since three fields(reader ID, 1st name and 2nd name) are involved.
				//arr = new String[readers.size() * 3]; // init size of arr
				
				// arr will have the sorted strings(reader ID, 1st name and 2nd name) retunred in alphabetical order
				arr = sort.collectFieldsOfReaders(readers, 4);
				arr = sort.bubbleSort(arr);
				
				// uncomment out this for-loop if wanting to check the state/result of arr
				// stored in ASC at run time
				/****************************************************
				 System.out.println("+++the sorted array arr+++ "); 
				 for (int i = 0; i <arr.length; i++) { System.out.println(arr[i]); } 
				 ***************************************************/
				
				Search s = new Search();
				List<Readers> temp = null;
				temp = s.createTempReadersList(arr, readers);
				
				
				int found = 0;
				found = s.binarySearch(temp, keyword.trim()); // return an index number of a reader

				if (found == -1) {
					System.out.println("No match found with the keyword");
					readerIDFoundForMenu7_2 = null;
					readerIndexFoundForMenu7_3 = found;

				} else {
					System.out.println(this.readers.get(found));
					readerIndexFoundForMenu7_3 =0;
					readerIndexFoundForMenu7_3 = found;
					readerIDFoundForMenu7_2 = "";
					readerIDFoundForMenu7_2 = readers.get(found).getId();
				}
			} else {
				System.out.println(
						"Please be aware that alphabet letters and numbers are allowed as an input. Try again");
			}
		}

		if (askUserOp.equals("2")) {	//search for reader with reader's Id, fname and lname

			//take accurate input from user and execute a linear search
			String id = "", f = "", l = "";
			id = IO.menu(IO.printUnderLine() + "\nEnter the reader ID", "[a-zA-Z0-9]++");
			f = IO.menu(IO.printUnderLine() + "\nEnter the reader's first name", "[a-zA-Z0-9]++");
			l = IO.menu(IO.printUnderLine() + "\nEnter the reader's last name", "[a-zA-Z0-9]++");

			Search s = new Search();
			keyword = "+" + id.trim() + "&" + f.trim() + "&" + l.trim();
			int found = 0;
			//found = s.linearSerchForReader(this.readers, keyword);	//parameterized approach
			found = s.linearSerch(this.readers, keyword);	//generic method approach

			if (found == -1) {
				System.out.println("No match found with the keyword");
				readerIDFoundForMenu7_2 = null;
				readerIndexFoundForMenu7_3 = found;

			} else {
				System.out.println(this.readers.get(found));
				readerIndexFoundForMenu7_3 =0;
				readerIndexFoundForMenu7_3 = found;
				readerIDFoundForMenu7_2 = "";
				readerIDFoundForMenu7_2 = readers.get(found).getId();
			}
		}

		if (askUserOp.equalsIgnoreCase("q")) {
			System.out.println("Going back to menu...");
		}
	}

	private void sortBooks() {

		Sort s = new Sort();
		String[] result = null;

		String op = IO.menu(IO.printBookSortOptionMenu(), "^[1|2|3|4|q|Q]$");
		switch (op) {
		
		case "1":
			// title sort
			if ((result = s.collectFieldsOfBooks(this.books, 1)) != null) {
				result = s.bubbleSort(result);
				System.out.println(IO.printUnderLine() + "\n[Titles of all the books sorted in alphabetical order.]\n");
				s.printSortedResultOfBooks(result, this.books, 1);
			}
			break;
		
		case "2":
			// author sort
			if ((result = s.collectFieldsOfBooks(this.books, 2)) != null) {
				result = s.bubbleSort(result);
				System.out
						.println(IO.printUnderLine() + "\n[Authors of all the books sorted in alphabetical order.]\n");
				s.printSortedResultOfBooks(result, this.books, 2);
			}
			break;
		
		case "3":
			// title + author sort
			if ((result = s.collectFieldsOfBooks(this.books, 3)) != null) {
				result = s.bubbleSort(result);
				System.out.println(IO.printUnderLine()
						+ "\n[Titles and authors of all the books sorted in alphabetical order.]\n");
				s.printSortedResultOfBooks(result, this.books, 3);
			}
			break;
			
		case "4":
			// bookID + title + author sort
			if ((result = s.collectFieldsOfBooks(this.books, 4)) != null) {
				result = s.bubbleSort(result);
				System.out.println(IO.printUnderLine()
						+ "\n[BookID, Titles and authors of all the books sorted in alphabetical order.]\n");
				s.printSortedResultOfBooks(result, this.books, 4);
			}
			break;

		default:
			System.out.println("going back to menu...");

		}
	}

	// method for searching a book
	private void searchForBook() {
		
		String keyword = "", askUserOp = "";

		askUserOp = IO.menu(IO.printBookSearchOptionMenu(), "^[1|2|q|Q]$");
		
		if (askUserOp.equals("1")) {	//search for a book by multiple keywords from user 
			
			Sort sort = new Sort();	//declare & init a new sort 
			String[] arr =  null;
				
			if ((arr = sort.collectFieldsOfBooks(this.books, 4)) != null) {
				
				//bookId, title and author are stored
				System.out.println("--------------before merging----------");
				sort.printArray(arr);
				
				arr = sort.mergeSort(arr);	//sort values in arr in ASC
				
				System.out.println("--------------after merging----------");
				sort.printArray(arr);

			}
			
			Search s = new Search();
			keyword = IO.menu(IO.printBookSearchMenu(), "[a-zA-Z0-9]++");
			String[] keys;			//array to split up keyword
			List<Books> temp = null;
			boolean isForAdvancedSearch = false;
			
			temp = s.createTempBooksList(arr, this.books, isForAdvancedSearch);
			
			//phase1) execute a binary search for a book with the user's original keyword input
			int found = 0;
			if(keyword != null) {
				
				keys = null;	//not in use for now
				found = s.binarySearch(temp, keyword.trim(), keys, 0, 0, temp.size()-1);
				
				if (found != -1) {
					System.out.println(this.books.get(found));	//found
				} else {
					
					/* nothing is found by the user's original keyword input.
					 * prepare to execute a binary search for a book again with the user's keyword input split up
					 */
					
					//collect bookId, titles split up and authors split up
					if ((arr = sort.collectFieldsOfBooks(this.books)) != null) {
						
						sort.printArrayInColumn(arr);
						
						//bookId, titles split up and authors split up are stored
						System.out.println("--------------before merging----------");
						sort.printArray(arr);
						
						arr = sort.mergeSort(arr);	//sort values in arr in ASC
						
						System.out.println("--------------after merging----------");
						sort.printArray(arr);
					}
					
					temp = null;	//re-init the previous temporary Books list
					isForAdvancedSearch = true;
					temp = s.createTempBooksList(arr, this.books, isForAdvancedSearch);
					keys = keyword.trim().split(" ");	// split up the keyword input
					//System.out.println("Array keys");
					//s.printArray(keys);
					found = 0;
					
					//phase 2) execute a binary search for a book again with the user's keyword input split up
					found = s.binarySearch(temp, null, keys, 0, 0, temp.size()-1);
				
					if (found != -1) {
						System.out.println(this.books.get(found));	//found
					} else {
						System.out.println("No match found with the keyword");
					}
					
				}
				
			}else {
				System.out.println(IO.printUnderLine() + "\nPlease check search keyword and try it again");
			}
					
		}

		if (askUserOp.equals("2")) {	//search for a book by title and author

			//take accurate input from user and execute a linear search
			String t = "", a = "";
			t = IO.menu(IO.printUnderLine() + "\nEnter the title name of the book", "[a-zA-Z0-9]++");
			a = IO.menu(IO.printUnderLine() + "\nEnter the author name of the book", "[a-zA-Z0-9]++");

			Search s = new Search();
			keyword = "+" + t.trim() + "&" + a.trim();
			int found = 0;
			
			//found = s.linearSerchForBook(this.books, keyword);	//parameterized approach
			found = s.linearSerch(this.books, keyword);	//generic method approach

			if (found == -1) {
				System.out.println("No match found with the keyword");

			} else {
				System.out.println(this.books.get(found));
			}
		}

		if (askUserOp.equalsIgnoreCase("q")) {
			System.out.println("Going back to menu...");
		}

	}

	private void registerReturn() {
		
		String rtID = "", bID = "";
		int rtIdToInt = 0, bIdToInt = 0, rIdToInt = 0;

		rtID = checkRentID(rtID);

		if (rtID != null) { // here, the rentID returned after all possible validation.

			rtIdToInt = Integer.parseInt(rtID.substring(2)) - 1;
			rIdToInt = Integer.parseInt(rent.get(rtIdToInt).getReaderID().substring(1)) - 1;

			rent.get(rtIdToInt).setRented(false);
			rent.get(rtIdToInt).setNormal(true);
			bID = rent.get(rtIdToInt).getTitleID();
			bIdToInt = Integer.parseInt(bID.substring(1)) - 1;
			books.get(bIdToInt).setRented(rent.get(rtIdToInt).isRented());
			books.get(bIdToInt).setAvailable(rent.get(rtIdToInt).isNormal());
			readers.get(rIdToInt).removeRent(rtID);

			boolean isNew = false;
			// update any changes to a file
			factory.writeRentToFile(rent, isNew);
			factory.writeBooksToFile(books);
			factory.writeReadersToFile(readers);

			System.out.println("### Successfully returned###");
			System.out.println(rent);
			System.out.println(books);
			System.out.println(readers);

			if (books.get(bIdToInt).getMQ() != null) {
				if (!books.get(bIdToInt).getMQ().isEmpty()) {
					System.out.println(IO.printUnderLine() + "\n!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!"
							+ "\n[The book info]\n" + books.get(bIdToInt).toString()
							+ "\n\n[The first available reader in the book's queue for renting]\n"
							+ books.get(bIdToInt).getMQ().firstToString());
				}
			}

		}

	}

	/**
	 * void method for registering rent record
	 */
	private void registerRent() {

		String bID = "", rID = "";
		bID = availableBookToLend(bID);
		if (bID != null) {

			if (bID.equals("Successfully added to the reader into the book's queue")) {
				// this line is printed only when a reader is added to queue
				System.out.println("\n###### Successfully added to the reader into the book's queue ######");

			} else {// need to update myRent in Readers once a new rent is created in Rent class
				rID = checkReeaderID(rID);
				if (rID != null) { // if !null, the reader exists
					int bIdToIndex = Integer.parseInt(bID.substring(1)) - 1;
					int rIdToIndex = Integer.parseInt(rID.substring(1)) - 1;
					if (books.get(bIdToIndex).getReaderInQ().equals("none")) {
						// if nothing is in this book's queue, keep going
						rent.add(new Rent());
						rent.get(rent.size() - 1).setRentID("RT" + String.valueOf(rent.size()));
						rent.get(rent.size() - 1).setTitleID(bID);
						rent.get(rent.size() - 1).setReaderID(rID);
						rent.get(rent.size() - 1).setRented(true);
						rent.get(rent.size() - 1).setNormal(false);
						books.get(Integer.parseInt(bID.substring(1)) - 1)
								.setRented(rent.get(rent.size() - 1).isRented());
						books.get(Integer.parseInt(bID.substring(1)) - 1)
								.setAvailable(rent.get(rent.size() - 1).isNormal());
						readers.get(rIdToIndex).setMyRent(rent);

						boolean isNew = true;
						// update any changes to a file
						factory.writeRentToFile(rent, isNew);
						factory.writeBooksToFile(books);
						factory.writeReadersToFile(readers);
//						factory.writeToFileGetRentalState(books, Integer.parseInt(bID.substring(1)) - 1);

						System.out.println("### A new rent has just been recorded successfully ###");
						System.out.println(rent);
						System.out.println(books);
						System.out.println(readers);

					} else {

						if (books.get(bIdToIndex).getMQ() != null) {
							if (books.get(bIdToIndex).getMQ().getFirst().getID().equals(rID)) {
								// if the reader who is in the 1st queue == rID, process a rent
								rent.add(new Rent());
								rent.get(rent.size() - 1).setRentID("RT" + String.valueOf(rent.size()));
								rent.get(rent.size() - 1).setTitleID(bID);
								rent.get(rent.size() - 1).setReaderID(rID);
								rent.get(rent.size() - 1).setRented(true);
								rent.get(rent.size() - 1).setNormal(false);
								books.get(Integer.parseInt(bID.substring(1)) - 1)
										.setRented(rent.get(rent.size() - 1).isRented());
								books.get(Integer.parseInt(bID.substring(1)) - 1)
										.setAvailable(rent.get(rent.size() - 1).isNormal());
								readers.get(rIdToIndex).setMyRent(rent);

								boolean isNew = true;
								// update any changes to a file
								factory.writeRentToFile(rent, isNew);
								factory.writeBooksToFile(books);
								factory.writeReadersToFile(readers);

								System.out.println("### A new rent has just been recorded successfully ###");
								System.out.println(rent);
								books.get(bIdToIndex).setDeQueue(); // remove the 1st node from queue
								System.out.println(books);
								System.out.println(readers);

								if (!books.get(bIdToIndex).getMQ().isEmpty()) {
									System.out.println(IO.printUnderLine()
											+ "\n!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!"
											+ "\n[The book info]\n" + books.get(bIdToIndex).toString()
											+ "\n\n[The first available reader in the book's queue for renting]\n"
											+ books.get(bIdToIndex).getMQ().firstToString());
								}

							}

							else {
								// the book's queue has the first node(a reader)
								// that is not equals to this reader
								// the queue should go in order

								if (books.get(bIdToIndex).getMQ().equalsCustom(rID.toUpperCase())) {
									System.out.println(IO.printUnderLine());
									System.out.println(
											"* The reader is in the book's queue, but is not in the first queue.");
									System.out.println(books.get(bIdToIndex).getMQ().equalsCustomToString(rID));
									System.out.println(
											"\n\n* Please inform the reader that the reader that \n  he/she is not in the first queue to rent the book");

								} else {
									// book is available to rent but has the existing queue
									System.out.println("* The book is avaiiable to be rented. "
											+ "\n  However, the book has the existing waiting queue of the reader(s) "
											+ "\n  who has/have already been in the book's queue for renting.");
									System.out.println("\n* The reader must be registered in the book's queue"
											+ "\n  in order for he/she to be able to rent the book."
											+ "\n\n  Please refer to the following prompt.");

									String temp = "";
									if ((temp = waitingQueueManager(bID, bIdToIndex, rID)) != null) {

										if (temp.equals("Successfully added to the reader into the book's queue")) {
											// this line is printed only when a reader is added to queue
											System.out.println(
													"\n###### Successfully added to the reader into the book's queue ######");

										}

									}
									/*
									 * else { System.out.println("SDfdsfsdfsdf"); }
									 */

								}

							}
						}

					}

				} else {
					System.out.println("the reader does not exist. Try again");
				}
			}
		}

	}

	/**
	 * method for checking to see if a reader exists
	 * 
	 * @param rID
	 * @return rID or null
	 */
	public String checkReeaderID(String rID) {

		rID = IO.menu(IO.printReaderIDMenu(), "[a-zA-Z0-9]");
		// may need to add some search function to search for reader id
		for (int i = 0; i < readers.size(); i++) {
			if (readers.get(i).getId().equalsIgnoreCase(rID)) {
				return rID.toUpperCase();
			}
		}
		return null;
	}

	/**
	 * method to return the valid rent id
	 * 
	 * @param rtID, rID
	 * @return
	 */
	public String checkRentID(String rtID) {
		// may need some other approach so as to aske only rent id or reader id in Rent
		// class
		String tempBookID = "", tempReaderId = "";
		int rtIdToInt = 0;

		rtID = IO.menu(IO.printRentIDMenu(), "[a-zA-Z0-9]"); // this needs to be changed. for just put for loop
		for (int i = 0; i < rent.size(); i++) {
			if (rent.get(i).getRentID().equalsIgnoreCase(rtID)) {

				rtIdToInt = Integer.parseInt(rtID.substring(2));
				tempBookID = rent.get(rtIdToInt - 1).getTitleID();
				tempReaderId = rent.get(rtIdToInt - 1).getReaderID();

				System.out.println(IO.printUnderLine());
				System.out.println("One rent record found as follows");
				System.out.println(rent.get(rtIdToInt - 1));
				System.out.println(rent.get(rtIdToInt - 1).getTitleID() + ": "
						+ books.get(Integer.parseInt(tempBookID.substring(1)) - 1).getTitle());
				System.out.println(rent.get(rtIdToInt - 1).getReaderID() + ": "
						+ readers.get(Integer.parseInt(tempReaderId.substring(1)) - 1).getFname() + " "
						+ readers.get(Integer.parseInt(tempReaderId.substring(1)) - 1).getLname());

				String yn = IO.menu(IO.printRendRecordCheckOption(), "^[y|Y|n|N|q|Q]$");

				if (yn.equalsIgnoreCase("y")) {
					if (rent.get(rtIdToInt - 1).isRented()) {
						return rtID.toUpperCase();
					}
				}

				if (yn.equalsIgnoreCase("n")) {
					String rID = "";
					rID = IO.menu(IO.printReaderIDMenu(), "[a-zA-Z0-9]");
					int rIdIndex = 0;
					rIdIndex = Integer.parseInt(rID.substring(1)) - 1;
					if ((rent.get(rtIdToInt - 1).isRented())
							&& (rent.get(rtIdToInt - 1).getReaderID().equalsIgnoreCase(rID)) | (readers.get(rIdIndex)
									.equalsRentInMyRents(rent.get(rtIdToInt - 1).getRentID()))) {

						System.out.println(IO.printUnderLine());
						System.out.println("One rent record found as follows");
						System.out.println(rent.get(rtIdToInt - 1));
						System.out.println(rent.get(rtIdToInt - 1).getTitleID() + ": "
								+ books.get(Integer.parseInt(tempBookID.substring(1)) - 1).getTitle());
						System.out.println(rent.get(rtIdToInt - 1).getReaderID() + ": "
								+ readers.get(Integer.parseInt(rID.substring(1)) - 1).getFname() + " "
								+ readers.get(Integer.parseInt(rID.substring(1)) - 1).getLname());
						System.out.println();

						return rtID.toUpperCase();

					}

				}

				if (yn.equalsIgnoreCase("q")) {
					System.out.println("Back to main menu....");
//					return null;
				}

			}

		}
		System.out.println("The rent ID not found. Try again");
		return null;
	}

	/**
	 * method for checking to see if a book is available to lend
	 * 
	 * @param bID
	 * @return bID or null
	 */
	public String availableBookToLend(String bID) {
		String yn = "";
		int bIdToInt = 0;
		bID = IO.menu(IO.printBookIDMenu(), "[a-zA-Z0-9]");
		// may need to add some search function to search for book id
		for (int i = 0; i < books.size(); i++) { // this needs to be working for all indexes in books
			if (books.get(i).getId().equalsIgnoreCase(bID)) {

				if (books.get(i).isAvailable()) {
					// if available, return the book id
					return bID.toUpperCase();

				} else {
					// the book is NOT available to rent since somebody is renting it at the moment.
					// a reader can be added to the book's queue
					// or a book's queue can be created and the reader will be the first queue in
					// it.
					String rID = null;
					return waitingQueueManager(bID, i, rID);

				}
			}

		}
		System.out.println("The book ID not found. Try again");
		return null;

	}

	public String waitingQueueManager(String bID, int tempIndex, String rID) {

		String yn = "";
//		String rID;
		int rIdToIndex = 0;

		yn = IO.menu(IO.printWaitingQueueMenu(books, bID), "[a-zA-Z0-9]");
		// ask reader if waning to be in the book's queue
		if (!(yn.equalsIgnoreCase("y"))) {
			System.out.println("Back to main menu....");
			return null;
		}

		if (rID == null) {
			// from else statement of availableBookToLend(); the book is NOT available to
			// rent
			// someone is renting the book at the moment
			rID = "";
			if ((rID = checkReeaderID(rID)) == null) {
				System.out.println("The reader ID does not exist in Readers db. Try again");
				return null;
			}

		}

		// otherwise, rId from ((temp = waitingQueueManager(bID, bIdToIndex, rID)) !=
		// null) of nested else statement registerRent()
		// the book is returned but has the existing queue of waiting reader

		rIdToIndex = Integer.parseInt(rID.substring(1)) - 1;

		if (readers.get(rIdToIndex).getMyRent() != null) {

			for (int i = 0; i < readers.get(rIdToIndex).getMyRent().size(); i++) {

				if (readers.get(rIdToIndex).getMyRent().get(i).getTitleID().equalsIgnoreCase(bID)) {

					// the reader is who has already been renting this book at the moment is asking
					// for
					// being in the queue
					System.out.println(IO.printUnderLine());
					System.out.println("* The reader has already been renting this book" + "\n"
							+ readers.get(rIdToIndex) + "\n\n* The bookks that are being rented by the reader" + "\n"
							+ readers.get(rIdToIndex).getMyRent());
					System.out.println("\n\n* Please infrom the reader that returning the book is first"
							+ "\n  so that the reader can be added into the book's queue "
							+ "\n  if the book is being rented by another reader");
					return null;

				}
			}

		}

		if (books.get(tempIndex).getMQ() != null) {
			if (!books.get(tempIndex).getMQ().isEmpty() && !books.get(tempIndex).getMQ().equalsCustom(rID)) {
				// there's no the existing same reader info(ID) in the book's queue
				enQueueManager(rID, bID);

				// update any changes to a file
				factory.writeBooksToFile(books);

				System.out.println(IO.printUnderLine());
				System.out.println("!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!");
				System.out.println("* A new update is processed with the following reader and book");
				System.out.println("\n[The reader info]");
				System.out.println(readers.get(rIdToIndex).toString().replaceAll("\n", ""));

				System.out.println(books.get(tempIndex).getQueueToString());
				return "Successfully added to the reader into the book's queue";
			}

			// if not meeting conditions just above, the reader is in the book's queue
			System.out.println("The reader is already in the book's queue");
			System.out.println(books.get(tempIndex).getMQ().equalsCustomToString(rID));
			return null;

		} else {
			// this book does not have any waiting queue of Readers
			enQueueManager(rID, bID);

			// update any changes to a file
			factory.writeBooksToFile(books);

			System.out.println(IO.printUnderLine());
			System.out.println("!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!");
			System.out.println("* A new update is processed with the following reader and book");
			System.out.println("\n[The reader info]");
			System.out.println(readers.get(rIdToIndex).toString().replaceAll("\n", ""));

			System.out.println(books.get(tempIndex).getQueueToString());
			return "Successfully added to the reader into the book's queue";

		}

//		return null;

	}

	// queue implementation issue
	// queue doesnt distinghuish book....
	// it needs to be an attribute in Books

	// rent and return issue
	// decide if Readers needs to have all the list of books being rented (allow
	// multiple rents)or
	// if one book only (allow only one rent)
	private void enQueueManager(String rIDTobeInQ, String bIDPassed) {

		int readerIndex = Integer.parseInt(rIDTobeInQ.substring(1)) - 1;
		int bookIndex = Integer.parseInt(bIDPassed.substring(1)) - 1;

		books.get(bookIndex).setEnQueue(readers, readerIndex);

	}

//	private Boolean checkReaderInRentHistory(String rIDTobeInQ, String bIDPassed) {
//		//this need to be fixed! the comparison item should be current myBooks in Reader list!
//		for(int i=0; i<rent.size(); i++) {
//			//if a book has already been rented by the reader who wanna be in the queue
//			//prevent it to give a chance to another reader first
////			if((rent.get(i).getReaderID().equals(rIDTobeInQ.toUpperCase()))) {
//			
//				while( !(rent.get(i).getReaderID().equals(rIDTobeInQ.toUpperCase())) 
//						&& 	!(rent.get(i).getTitleID().equals(bIDPassed.toUpperCase()))
//						&&	!(rent.get(i).isRented()) ) {
//						return true;
//				}
//			
////			}else {
////				System.out.println("The reader ID does not exist in the rent record");
////				
////			}
//			
////				System.out.println("The book has already been rented by the reader"
////						+ "\nPlease inform the reader that he/she should return the book first to borrow it again");
////				return false;
//			
//		}
//		System.out.println("The book has already been rented by the reader"
//				+ "\nPlease inform the reader that he/she should return the book first to borrow it again");
//	
//		return false;
//	
//	}

}
