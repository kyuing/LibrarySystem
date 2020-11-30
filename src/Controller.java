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
/**
 * Class that controls the program.
 * 
 * @author Kyu
 *
 */
public class Controller {

	BufferedReader books_in, readers_in, rent_in;
	private List<Books> books;
	private List<Readers> readers;
	private List<Rent> rent;
	private FactoryInterface factory;
	private String readerIDFoundForMenu7_2 = null;
	private int readerIndexFoundForMenu7_3;

	public Controller() throws IOException {

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

		//inform user if there's 1st queue of reader that exists in any book OBJs
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

	/**
	 * method to sort readers in alphabetical order by a field (or fields) of reader selected 
	 * Fields involved with sorting are readerID, first name and last name.
	 * 
	 *************************************************************************************************************** 
	 * The form of sorted result will be as follows
	 * 
	 * For example, user wants to sort readers by first name and last name in alphabetical order
	 * 
	 *  The name tag 
	 *  sorted each		               1st sorted
	 *        ��                            �� 
	 *	Abril[id=R01, fname=Dulce, lname=Abril, Current Rent=RT02 RT03 RT04]
	 *	
	 *                       2nd sorted	
	 *                           ��
	 *	Arcelia[id=R11, fname=Arcelia, lname=Bouska, Current Rent=none]
     *
     *                                      3rd sorted
     *                                           ��   
	 *	Ascencio[id=R13, fname=Sherron, lname=Ascencio, Current Rent=none]
	 *  
	 *  and so on
	 *  .
	 *  .
	 *  .
	 *  
	 *  [NOTE]
	 *  - As explained above, a temporary name tag that demonstrates the sorted option -field(s) selected- and its sorted order(result) 
	 *    will be represented at the beginning of each row of resulting OBJs instead of returning the original form of OBJ record
	 *    such as original ID[fields:value....]
	 *    
	 *  - If more than one field is selected as a sort option,
	 *    the same OBJs can be referenced repeatedly 
	 *    and the number of sorted result can be greater than the number of OBJs in the original Readers list. 
	 *    	 
	 */
	private void sortReaders() {

		Sort sort = new Sort();
		String[] result = null;

		String op = IO.menu(IO.printReaderSortOptionMenu(), "^[1|2|3|4|5|q|Q]$");
		switch (op) {
		
		case "1":
			// id sort == no specific execution is needed.
			System.out.println(IO.printUnderLine() + "\n[ID of all the readers sorted in alphabetical order.]\n");
			System.out.println("* The number of result listed is " + this.readers.size());
			
			sort.printSortedResultOfReaders(null, this.readers, 1);
			
			break;
			
		case "2":
			// first name sort
			if ((result = sort.collectFieldsOfReaders(this.readers, 2)) != null) {
				
				/* if wanted the result using merge sort, uncomment the code below out */
				//result = sort.mergeSort(result);	
				
				result = sort.bubbleSort(result);	//comment this out if used merge sort above
				System.out.println(
						IO.printUnderLine() + "\n[First names of all the readers sorted in alphabetical order.]\n");
				System.out.println("* The number of result listed is " + result.length);
				sort.printSortedResultOfReaders(result, this.readers, 2);
			}
			break;
			
		case "3":
			// last name sort
			if ((result = sort.collectFieldsOfReaders(this.readers, 3)) != null) {
				
				/* if wanted the result using merge sort, uncomment the code below out */
				//result = sort.mergeSort(result);	
				
				result = sort.bubbleSort(result);	//comment this out if used merge sort above
				System.out.println(
						IO.printUnderLine() + "\n[Last names of all the readers sorted in alphabetical order.]\n");
				System.out.println("* The number of result listed is " + result.length);
				sort.printSortedResultOfReaders(result, this.readers, 3);
			}
			break;
			
		case "4":
			// name sort (first name + last name) 
			if ((result = sort.collectFieldsOfReaders(this.readers, 4)) != null) {
				
				/* if wanted the result using merge sort, uncomment the code below out */
				//result = sort.mergeSort(result);	
				
				result = sort.bubbleSort(result);	//comment this out if used merge sort above
				System.out.println(
						IO.printUnderLine() + "\n[First and Last names of all the readers sorted in alphabetical order.]\n");
				System.out.println("* The number of result listed is " + result.length);
				sort.printSortedResultOfReaders(result, this.readers, 4);
			}
			break;

		case "5":
			// id + first name + last name sort
			if ((result = sort.collectFieldsOfReaders(this.readers, 5)) != null) {
				
				/* if wanted the result using merge sort, uncomment the code below out */
				//result = sort.mergeSort(result);	
				
				result = sort.bubbleSort(result);	//comment this out if used merge sort above
				System.out.println(
						IO.printUnderLine() + "\n[ID, names of all the readers sorted in alphabetical order.]\n");
				System.out.println("* The number of result listed is " + result.length);
				sort.printSortedResultOfReaders(result, this.readers, 5);
			}
			break;

		default:
			System.out.println("going back to menu...");

		}

	}

	/**
	 * method for searching a reader.
	 * There're two search options and fields involved are readerID, first name and last name for both options
	 * 
	 * Searching for a reader is in a simpler form than searching for a book
	 * since readerID, reader's first name and reader's last name all is one word form.
	 * 
	 *
	 *************************************************************************************************** 
	 * Search option "1" returns the exact match if found, allowing one or multiple search keywords. 
	 * 
	 * See the following example.
	 *  
	 *  For example, user's keyword == "hello dulce r04 and so on.."
	 *  
	 *  [The exact match]
	 *	dulce is found in the reader index at 0
     *		
     *                      ��  
     *	R01[id=R01, fname=Dulce, lname=Abril, Current Rent=RT02 RT03 RT04]        
     **************************************************************************************************
	 *  
	 *  Search option "2" returns the exact match if found, 
	 *  allowing one search keyword for each of fields(readerId, first name, last name) at a time
	 * 
	 */
	private void searchForReader() {

		String keyword = "", askUserOp = "";

		askUserOp = IO.menu(IO.printReaderSearchOptionMenu(), "^[1|2|q|Q]$");
		
		if (askUserOp.equals("1")) {	//search for reader allowing multiple keywords
			keyword = IO.menu(IO.printReaderSearchMenu(), "[a-zA-Z0-9]++");

			if (keyword != null) {

				Sort sort = new Sort();
				String[] arr = null; 
				
				arr = sort.collectFieldsOfReaders(readers, 5);	//collect the fields readerID, fname and lname
				
				/* if wanted the arr result using bubble sort, uncomment the code below out */
				//arr = sort.bubbleSort(arr);	
				
				//sort the fields collected
				arr = sort.mergeSort(arr);	//comment this out if used bubble sort above
				
				Search search = new Search();
				List<Readers> temp = null;
				
				//create a temporary reader list
				temp = search.createTempReadersList(arr, readers);
				
				
				int found = 0;
				found = search.binarySearch(temp, keyword.trim()); // return an index number of a reader if found

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
			id = IO.menu(IO.printUnderLine() + "\nEnter reader ID", "[a-zA-Z0-9]++");
			f = IO.menu(IO.printUnderLine() + "\nEnter first name", "[a-zA-Z0-9]++");
			l = IO.menu(IO.printUnderLine() + "\nEnter last name", "[a-zA-Z0-9]++");

			Search search = new Search();
			keyword = "";
			keyword = "+" + id.trim() + "&" + f.trim() + "&" + l.trim();

			int found = 0;

			found = search.linearSerch(this.readers, keyword.trim());	//generic method approach

			if (found == -1) {
				
				System.out.println("No match found with the keyword");
				
				readerIDFoundForMenu7_2 = null;
				readerIndexFoundForMenu7_3 = found;

			} else {
				System.out.println(this.readers.get(found));	//found
				
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

	/**
	 * method to sort books in alphabetical order by a field (or fields) of book selected 
	 * Fields involved with sorting are bookId, title and author.
	 * 
	 *************************************************************************************************************** 
	 * The form of sorted result will be as follows
	 * 
	 * For example, user wants to sort books by title and author in alphabetical order
	 * 
	 *  The name tag 
	 *  sorted each		                    					   1st sorted
	 *        ��                                                        �� 
	 *	Anneke Preusig[id=B06, title=World Leathernecks, author=Anneke Preusig, rental_state=Available, readerInQ=none]
	 *	
	 *                    2nd sorted	
	 *                        ��
	 *	Beast[id=B12, title=Beast, author=Patricio Bridgland, rental_state=Available, readerInQ=none]
     *
     *                                                         3rd sorted
     *                                                            ��   
	 *	Berni Genin[id=B14, title=Grail Frankenstein, author=Berni Genin, rental_state=Available, readerInQ=none]
	 *  
	 *  and so on
	 *  .
	 *  .
	 *  
	 *  [NOTE]
	 *  - As explained above, a temporary name tag that demonstrates the sorted option -field(s) selected- and its sorted order(result) 
	 *    will be represented at the beginning of each row of resulting OBJs instead of returning the original form of OBJ record
	 *    such as original ID[fields:value....]
	 *    
	 *  -If more than one field is selected as a sort option,
	 *    the same OBJs can be referenced repeatedly 
	 *    and the number of sorted result can be greater than the number of OBJs in the original Books list. 
	 * 
	 */
	private void sortBooks() {

		Sort sort = new Sort();
		String[] result = null;

		String op = IO.menu(IO.printBookSortOptionMenu(), "^[1|2|3|4|5|q|Q]$");
		switch (op) {
		
		case "1":
			// ID sort	
			System.out.println(IO.printUnderLine() + "\n[IDs of all the books sorted in alphabetical order.]\n");
			System.out.println("* The number of result listed is " + this.books.size());
			sort.printSortedResultOfBooks(null, this.books, 1);
			
			break;
			
		case "2":
			// title sort				
			if ((result = sort.collectFieldsOfBooks(this.books, 2)) != null) {
				
				/* if wanted the result using bubble sort, uncomment the code below out */
				//result = sort.bubbleSort(result);	
				
				result = sort.mergeSort(result);	//comment this out if used bubble sort above
				System.out.println(IO.printUnderLine() + "\n[Titles of all the books sorted in alphabetical order.]\n");
				System.out.println("* The number of result listed is " + result.length);
				sort.printSortedResultOfBooks(result, this.books, 2);
			}
			break;
		
		case "3":
			// author sort
			if ((result = sort.collectFieldsOfBooks(this.books, 3)) != null) {

				/* if wanted the result using bubble sort, uncomment the code below out */
				//result = sort.bubbleSort(result);	
				
				result = sort.mergeSort(result);	//comment this out if used bubble sort above
				System.out.println(IO.printUnderLine() + "\n[Authors of all the books sorted in alphabetical order.]\n");
				System.out.println("* The number of result listed is " + result.length);
				sort.printSortedResultOfBooks(result, this.books, 3);
			}
			break;
		
		case "4":
			// title + author sort
			if ((result = sort.collectFieldsOfBooks(this.books, 4)) != null) {

				/* if wanted the result using bubble sort, uncomment the code below out */
				//result = sort.bubbleSort(result);	
				
				result = sort.mergeSort(result);	//comment this out if used bubble sort above
				System.out.println(IO.printUnderLine()
						+ "\n[Titles and authors of all the books sorted in alphabetical order.]\n");
				System.out.println("* The number of result listed is " + result.length);
				sort.printSortedResultOfBooks(result, this.books, 4);
			}
			break;
			
		case "5":
			// bookID + title + author sort
			if ((result = sort.collectFieldsOfBooks(this.books, 5)) != null) {

				/* if wanted the result using bubble sort, uncomment the code below out */
				//result = sort.bubbleSort(result);	
				
				result = sort.mergeSort(result);	//comment this out if used bubble sort above
				System.out.println(IO.printUnderLine()
						+ "\n[BookID, Titles and authors of all the books sorted in alphabetical order.]\n");
				System.out.println("* The number of result listed is " + result.length);
				sort.printSortedResultOfBooks(result, this.books, 5);
			}
			break;

		default:
			System.out.println("going back to menu...");

		}
	}

	/**
	 * method for searching a book.
	 * There're two search options.
	 * Fields involved with search option "1" are bookID, title and author
     * Fields involved with search option "2" are title and author
	 *
	 ***************************************************************************************************
	 * Search option "1" allows one search keyword or multiple keywords.
	 * It then returns the exact match or partial match if found. See the following examples.
	 *  
	 *  CASE 1)
	 *  For example, user's keyword == "b01"
	 *  
	 *  [The exact match] 
	 *  b01 is found in the book index at 0
     *  
     *          ��
     *  B01[id=B01, title=Hunger Game, author=George, rental_state=Rented, readerInQ=R01 R02 R03]
     *
     *  CASE 2)
     *  For example, user's keyword == "wrong input hunger b01"
     *  
	 *  [Partial match]
	 *  Hunger is found in the book index at 0
	 *  
	 *                      ��
	 *  B01[id=B01, title=Hunger Game, author=George, rental_state=Rented, readerInQ=R01 R02 R03]
     **************************************************************************************************
	 * 
	 * Search option "2" allows one search keyword for each of fields(title, author) at a time 
	 * and returns the exact match if found.
	 */
	private void searchForBook() {
		
		String keyword = "", askUserOp = "";

		askUserOp = IO.menu(IO.printBookSearchOptionMenu(), "^[1|2|q|Q]$");
		
		if (askUserOp.equals("1")) {	//search for a book by multiple keywords from user 
			
			Sort sort = new Sort();	//declare & init a new sort 
			String[] arr =  null;
				
			if ((arr = sort.collectFieldsOfBooks(this.books, 5)) != null) {
				// now, arr has got bookId, title and author collected
				
				/* if necessary, uncomment the code below out to check the process of merge sort of arr*/
				//System.out.println("--------------before merging----------");
				//sort.printArray(arr);
				
				arr = sort.mergeSort(arr);	 //now, arr has got bookId, title and author sorted in ASC
				
				/* if necessary, uncomment the code below out to check the process of merge sort of arr*/
				//System.out.println("\n--------------after merging-----------");
				//sort.printArray(arr);

			}
			
			Search s = new Search();
			keyword = IO.menu(IO.printBookSearchMenu(), "[a-zA-Z0-9]++");
			String[] keys;			//array to split up keyword
			List<Books> temp = null;
			boolean isForAdvancedSearch = false;
			
			//create a temporary book list
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
						
						/* if necessary, uncomment the code below out to check the process of merge sort of arr*/
						//sort.printArrayInColumn(arr);
						
						/* if necessary, uncomment the code below out to check the process of merge sort of arr*/
						//System.out.println("--------------before merging----------");
						//sort.printArray(arr);
						
						arr = sort.mergeSort(arr);	//now, arr has got bookId, title split and author split sorted in ASC
						
						/* if necessary, uncomment the code below out to check the process of merge sort of arr*/
						//System.out.println("\n--------------after merging----------");
						//sort.printArray(arr);
					}
					
					temp = null;	//re-init the previous temporary Books list
					isForAdvancedSearch = true;
					
					//create a temporary book list
					temp = s.createTempBooksList(arr, this.books, isForAdvancedSearch);
					keys = keyword.trim().split(" ");	// split up the keyword input
				
					found = 0;	//re-init it
					
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
			
			found = s.linearSerch(this.books, keyword);	//generic method approach

			if (found == -1) {
				System.out.println("No match found with the keyword");

			} else {
				System.out.println(this.books.get(found));	//found
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

			} else {
				
				rID = checkReeaderID(rID);
				
				if (rID != null) { // if !null, the reader exists
					
					int bIdToIndex = Integer.parseInt(bID.substring(1)) - 1;
					int rIdToIndex = Integer.parseInt(rID.substring(1)) - 1;
					
					if (books.get(bIdToIndex).getReaderInQ().equals("none")) {
						// if nothing is in this book's queue, keep going
						rent.add(new Rent());
						
						if(this.rent.size() < 10) {
							
							rent.get(rent.size() - 1).setRentID("RT0" + String.valueOf(rent.size()));
							
						}else {
							
							rent.get(rent.size() - 1).setRentID("RT" + String.valueOf(rent.size()));
						}
						
						
						rent.get(rent.size() - 1).setTitleID(bID);
						rent.get(rent.size() - 1).setReaderID(rID);
						rent.get(rent.size() - 1).setRented(true);
						rent.get(rent.size() - 1).setNormal(false);
						books.get(Integer.parseInt(bID.substring(1)) - 1).setRented(rent.get(rent.size() - 1).isRented());
						books.get(Integer.parseInt(bID.substring(1)) - 1).setAvailable(rent.get(rent.size() - 1).isNormal());
						readers.get(rIdToIndex).setMyRent(rent);

						boolean isNew = true;
						// update any changes to a file
						factory.writeRentToFile(rent, isNew);
						factory.writeBooksToFile(books);
						factory.writeReadersToFile(readers);
						
						System.out.println("### A new rent has just been recorded successfully ###");
						System.out.println(rent);
						System.out.println(books);
						System.out.println(readers);
						


						

					} else {

						if (books.get(bIdToIndex).getMQ() != null) {
							if (books.get(bIdToIndex).getMQ().getFirst().getID().equals(rID)) {
								// if the reader who is in the 1st queue == rID, process a rent
								rent.add(new Rent());

								if(this.rent.size() < 10) {
									
									rent.get(rent.size() - 1).setRentID("RT0" + String.valueOf(rent.size()));
									
								}else {
									
									rent.get(rent.size() - 1).setRentID("RT" + String.valueOf(rent.size()));
								}
								
								rent.get(rent.size() - 1).setTitleID(bID);
								rent.get(rent.size() - 1).setReaderID(rID);
								rent.get(rent.size() - 1).setRented(true);
								rent.get(rent.size() - 1).setNormal(false);
								books.get(Integer.parseInt(bID.substring(1)) - 1).setRented(rent.get(rent.size() - 1).isRented());
								books.get(Integer.parseInt(bID.substring(1)) - 1).setAvailable(rent.get(rent.size() - 1).isNormal());
								books.get(bIdToIndex).setDeQueue(); // remove the 1st node from queue
								readers.get(rIdToIndex).setMyRent(rent);	

								boolean isNew = true;
								// update any changes to a file
								factory.writeRentToFile(rent, isNew);
								factory.writeBooksToFile(books);
								factory.writeReadersToFile(readers);

								System.out.println("### A new rent has just been recorded successfully ###");
								System.out.println(rent);
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
								// that is not equals to this reader input.
								// the queue should go in order

								if (books.get(bIdToIndex).getMQ().equalsCustom(rID.toUpperCase())) {
									System.out.println(IO.printUnderLine());
									System.out.println("* The book with ID \"" + books.get(bIdToIndex).getId() + "\" is available to be rented.");
									System.out.println("\n* The reader with ID \"" + rID.toUpperCase() + "\" is in the book's queue, but is not in the first queue."
											+ "\n\n* Please inform the reader that renting a book is available when the reader is in the first queue.");
									System.out.println("\n<Book info>" + books.get(bIdToIndex) 
											+ books.get(bIdToIndex).getMQ().equalsCustomToString(rID));
									
								} else {
									// book is available to rent but has the existing queue and the reader you are looking at is not in it.
									
									System.out.println("* The book with ID \"" + books.get(bIdToIndex).getId() + "\" is available to be rented.");
									System.out.println("* However, the book has the existing waiting queue of the reader(s) "
											+ "\n* The reader with ID \"" + rID.toUpperCase() + "\" is not in the book's queue"
											+ "\n* Please check the following detail.");
									
									
									String temp = "";
									if ((temp = waitingQueueManager(bID, bIdToIndex, rID)) != null) {

										if (temp.equals("Successfully added to the reader into the book's queue")) {
											// this line is printed only when a reader is added to queue
											System.out.println("\n###### Successfully added to the reader into the book's queue ######");

										}

									}
									
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
		
		bID = IO.menu(IO.printBookIDMenu(), "[a-zA-Z0-9]");
		
		for (int i = 0; i < books.size(); i++) { // this needs to be working for all indexes in books
			if (books.get(i).getId().equalsIgnoreCase(bID)) {

				if (books.get(i).isAvailable()) {
					
					return bID.toUpperCase();	// the book is available to be rented

				} else {
					// the book is NOT available to rent since somebody is renting it at the moment.
					// a reader can be added to the book's queue
					String rID = null;
					return waitingQueueManager(bID, i, rID);

				}
			}

		}
		System.out.println("The book ID not found. Try again."
				+ "\nPlease use menu \"1\" or menu \"2\" to get any book-related info. ");
		return null;

	}

	public String waitingQueueManager(String bID, int tempIndex, String rID) {

		String yn = "";
		int rentIdToIndex, bIdToIndex, rIdToIndex;
		boolean isAskReaderQEntry = false;

		/* if rId == null, this waitingQueueManager() method is called 
		 * from else statement in the method availableBookToLend(); 
		 * the book with the Id in the parameter bID is being rented == NOT AVAILABLE 
		 * 
		 * if rId != null, this waitingQueueManager() method is called 
		 * from nested else statement in the method registerRent();
		 * the book with the Id in the parameter bID is returned == AVAILABLE
		 */
		if (rID == null) {
			rID = "";
			if ((rID = checkReeaderID(rID)) == null) {
				System.out.println("The reader ID does not exist in Readers db. Try again");
				return null;
			}

		}
		
		
		/* the book with the Id in the parameter bID is being rented and could also has the existing queue of readers
		 * extract any info as necessary */

		/* An index of the book with the Id in the parameter bID can be extracted outside for-loop below as well */
		//bIdToIndex = Integer.parseInt(bID.substring(1)) - 1; //this also works
		
		//the state between "Rented" and "Normal" of an OBJ in the original list is not important
		//because a reader only holds a rent ID in its current rent list during "renting"
		//In addition, no the same bookId can be in a reader's current rent list
		
		
		rentIdToIndex = 0;
		bIdToIndex = 0;
		rIdToIndex = 0;
		rIdToIndex = Integer.parseInt(rID.substring(1)) - 1;

		
		if (this.readers.get(rIdToIndex).getMyRent() != null) {

			for (int i = 0; i < readers.get(rIdToIndex).getMyRent().size(); i++) {

				if (this.readers.get(rIdToIndex).getMyRent().get(i).getTitleID().equalsIgnoreCase(bID)) {
					
					rentIdToIndex = Integer.parseInt(this.readers.get(rIdToIndex).getMyRent().get(i).getRentID().substring(2)) - 1;
					bIdToIndex = Integer.parseInt(this.readers.get(rIdToIndex).getMyRent().get(i).getTitleID().substring(1)) - 1;
					isAskReaderQEntry = false;
					// reader who has already been renting this book at the moment is asking for being in the queue
					
					System.out.println(IO.printWaitingQueueMenu(this.rent, rentIdToIndex, this.books, bIdToIndex, this.readers, rIdToIndex, null, isAskReaderQEntry));
					return null;

				}
			}

		}else {
			
			int tmep_rentIdToIndex = 0, tmep_bIdToIndex = 0, tmep_rIdToIndex = 0;
			boolean isBreak = false;
			
			for(int i=0; i<this.readers.size(); i++) {
				if (this.readers.get(i).getMyRent() != null) {
					for (int j=0; j<readers.get(i).getMyRent().size(); j++) {
						if (readers.get(i).getMyRent().get(j).getTitleID().equalsIgnoreCase(bID)) {
							
							/* A specific reader you are looking at has at least one rent record cloned from the original Rent list,
							 * represented as a rent ID (a specific rent OBJ copied which is the same as dealing with one specific record in the original Rent list)
							 * 
							 * If any current rent records is in a Reader OBJ, 
							 * that literally means the reader OBJ you are looking at has 
							 * a current rent info that corresponds to one that is the same states(rentID, bookID, readerID and "Rented" in particular) and is in the original Rent list.
							 * 
							 * Thus, by looking at current rent state in a Reader OBJ,    
							 * an access to rentId, bookID or readerId of a specific record in the original Rent list is possible.  
							 */
							tmep_rentIdToIndex = Integer.parseInt(this.readers.get(i).getMyRent().get(j).getRentID().substring(2)) - 1;
							tmep_bIdToIndex = Integer.parseInt(this.readers.get(i).getMyRent().get(j).getTitleID().substring(1)) - 1;
							tmep_rIdToIndex = Integer.parseInt(this.readers.get(i).getMyRent().get(j).getReaderID().substring(1)) - 1;
							isAskReaderQEntry = true;
							
							yn = IO.menu(IO.printWaitingQueueMenu(this.rent, tmep_rentIdToIndex, this.books, tmep_bIdToIndex, this.readers, tmep_rIdToIndex, rID, isAskReaderQEntry), "[a-zA-Z0-9]");
							// ask reader if waning to be in the book's queue
							if ((yn.equalsIgnoreCase("y"))) {
								
								isBreak = true;
								break;

							}else {
								System.out.println("Back to main menu....");
								return null;
							}
						}
					}	
				}
				
				if(isBreak) {
					break;
				}
			}
		}
		
		System.out.println("rIdToIndex: " + rIdToIndex);
		
//		boolean isAskReaderQEntry = true;
		//print a state related to a rent record

//		yn = IO.menu(IO.printWaitingQueueMenu(this.rent, rentIdToIndex, this.books, bIdToIndex, this.readers, rIdToIndex, isAskReaderQEntry), "[a-zA-Z0-9]");
//		// ask reader if waning to be in the book's queue
//		if (!(yn.equalsIgnoreCase("y"))) {
//			System.out.println("Back to main menu....");
//			return null;
//		}

//		if (rID == null) {
//			//if rId == null, this waitingQueueManager() method is called from else statement in the method availableBookToLend(); 
//			//the book with the Id in the parameter bID is being rented == NOT AVAILABLE
//			rID = "";
//			if ((rID = checkReeaderID(rID)) == null) {
//				System.out.println("The reader ID does not exist in Readers db. Try again");
//				return null;
//			}
//
//		}



		if (books.get(tempIndex).getMQ() != null) {
			if (!books.get(tempIndex).getMQ().isEmpty() && !books.get(tempIndex).getMQ().equalsCustom(rID)) {
				
				isAskReaderQEntry = true;
				yn = IO.menu(IO.printWaitingQueueMenu(this.books, tempIndex, rID, isAskReaderQEntry), "[a-zA-Z0-9]");
				// ask reader if waning to be in the book's queue
				if ( !(yn.equalsIgnoreCase("y"))) {
					
					System.out.println("Back to main menu....");
					return null;

				}
				
				// there's no the existing same reader info(ID) in the book's queue
				enQueueManager(rID, bID);

				// update any changes to a file
				factory.writeBooksToFile(books);

				System.out.println(IO.printUnderLine());
				System.out.println("!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!");
				System.out.println("* The reader with ID \"" + rID + "\" has just been added into the queue of the book whose ID is \"" + bID + "\"");
				System.out.println("\n<Reader info>");
				System.out.println(readers.get(rIdToIndex).toString().replaceAll("\n", ""));
				System.out.println(IO.printHyphen());
				System.out.println(books.get(tempIndex).getQueueToString());
				return "Successfully added to the reader into the book's queue";
			}

			// if not meeting conditions just above, the reader is in the book's queue
			System.out.println(IO.printUnderLine());
			System.out.println("* The reader with ID \"" + rID + "\" is already in the book's queue");
			System.out.println("\n<Book info>" + books.get(tempIndex));
			System.out.println(books.get(tempIndex).getMQ().equalsCustomToString(rID));
			return null;

		} else {
			// this book does not have any waiting queue of Readers
			enQueueManager(rID, bID);

			// update any changes to a file
			factory.writeBooksToFile(books);

//			System.out.println(IO.printUnderLine());
//			System.out.println("!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!");
//			System.out.println("* A new update is processed with the following reader and book");
//			System.out.println("\n[The reader info]");
//			System.out.println(readers.get(rIdToIndex).toString().replaceAll("\n", ""));
//
//			System.out.println(books.get(tempIndex).getQueueToString());
			
			System.out.println(IO.printUnderLine());
			System.out.println("!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!");
			System.out.println("* The reader with ID \"" + rID + "\" has just been added into the queue of the book whose ID is \"" + bID + "\"");
			System.out.println("\n<Reader info>");
			System.out.println(readers.get(rIdToIndex).toString().replaceAll("\n", ""));
			System.out.println(IO.printHyphen());
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
