import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

	public Controller() throws IOException {

		factory = new Factory(); // declare & init factory

		String rt = "Rent.txt";
		rent_in = new BufferedReader(new FileReader(rt));
		rent = (List<Rent>) factory.createRentDB(rent_in);
		//System.out.println(rent);

		String r = "Readers.txt";
		readers_in = new BufferedReader(new FileReader(r));
		readers = (List<Readers>) factory.createReaderDB(readers_in, rent);
		//System.out.println(readers);

		// read input and store them
		String b = "Books.txt";
		books_in = new BufferedReader(new FileReader(b));
		books = (List<Books>) factory.createBookDB(books_in, readers);
		//System.out.println(books);

		if(this.rent != null) {
			System.out.println("### Rent.txt is successfully loaded intto system ###");
		}else {
			System.out.println("!!! Error ocurred while loading 'Rent.txt' file. !!!");
			System.out.println("!!! Please check the input file and the file path. !!!");
			System.out.println("!!! System terminates.. !!!");
			System.exit(0);
		}
		
		if(this.readers != null) {
			System.out.println("### Readers.txt is successfully loaded intto system ###");
		}else {
			System.out.println("!!! Error ocurred while loading 'Readers.txt' file. !!!");
			System.out.println("!!! Please check the input file and the file path. !!!");
			System.out.println("!!! System terminates.. !!!");
			System.exit(0);
		}
		
		if(this.books != null) {
			System.out.println("### Books.txt is successfully loaded intto system ###");
		}else {
			System.out.println("!!! Error ocurred while loading 'Books.txt' file. !!!");
			System.out.println("!!! Please check the input file and the file path. !!!");
			System.out.println("!!! System terminates.. !!!");
			System.exit(0);
		}
		
		//inform user if there's 1st queue of reader that exists in any book OBJs
		for(int i=0; i<books.size(); i++) {
			if (books.get(i).getMQ() != null) {
				if (!books.get(i).getMQ().isEmpty()) {
					System.out.println(IO.printUnderLine() + "\n!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!"
							+ "\n<The book info>\n" + books.get(i).toString()
							+ "\n\n<The first available reader in the book's queue for renting>\n"
							+ books.get(i).getMQ().firstToString());
				}
			}	
		}
		
		menu();
	}

	private void menu() {

		String op = IO.menu(IO.printMenu(), "^[1|2|3|4|5|6|7|8|9]$");

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
			printDBs();
			menu();

		case "9":
			System.out.println(IO.printUnderLine() + "\nBYE :)");
			System.exit(0);

		default:
			menu();
		}
	}

	private void printDBs() {
		
		String op = IO.menu(IO.printDB(), "^[1|2|3|q|Q]$");
		Sort sort = new Sort();
		
		//just print each of the lists in its order created
		switch (op) {

		case "1":
			
			System.out.println(IO.printUnderLine() + "\nRent");
			System.out.println("* The number of result listed is " + this.rent.size());
			sort.printSortedResult(this.rent, true);
			
			break;

		case "2":
			
			System.out.println(IO.printUnderLine() + "\nReaders");
			System.out.println("* The number of result listed is " + this.readers.size());
			sort.printSortedResult(this.readers, true);
			
			break;

		case "3":
			
			System.out.println(IO.printUnderLine() + "\nBooks");
			System.out.println("* The number of result listed is " + this.books.size());
			sort.printSortedResult(this.books, true);
			
			break;
			
		default:
			System.out.println("going back to menu...");
		}
	}

	/**
	 * method to list rent histories depending on an option user selects
	 */
	private void listRentHistoryOfReaders() {

		String op = IO.menu(IO.printForListingRentHistoryOfReaders(), "^[1|2|3|q|Q]$");
		String advOp = "";
		String readerID = "";
		int bookIndex, readerIndex, rentIndex;
		
		switch (op) {
		
		case "1": // all rent record 
			System.out.println(IO.printUnderLine() 
					+ "\n### All of the rent records ###"
					+ "\n\n* Quick reminder for the field value of 'state' on a rent record"
					+ "\n  'state=Rented' represents the book(titleID) is being rented by a reader(readerID)"
					+ "\n  'state=Normal' represents the reader(readerID) has returned the book(titleID)");
			

			for (int i = 0; i < rent.size(); i++) {
				System.out.print(rent.get(i));

			}
			System.out.println();
			
			//ask user if wanting to check all the rent records in detail
			advOp = IO.menu(IO.printUnderLine()	+ "\nWould you like to see all the rent record in detail(y/n)?", "^[a-zA-Z]$");
			
				if(advOp.equalsIgnoreCase("y")) {
					
					bookIndex = 0;
					readerIndex = 0;
					
					System.out.println(IO.printUnderLine()
							+ "\n* The following result(s) is/are the breakdown represented by"
							+ "\n\n  Line 1 == a specific rent record found in the previous step."
							+ "\n            'state=Rented' represents the book(titleID) is being rented by the reader(readerID)"
							+ "\n            'state=Normal' represents the reader(readerID) has returned the book(titleID)"
							+ "\n\n  Line 2 == the book info belonged to the rent record"
							+ "\n  Line 3 == the reader info belonged to the rent record"
							+ "\n\n\n[NOTE]"
							+ "\n\n* The field 'rental_state' of any book record will be shown"
							+ "\n  only when a book is being rented by the reader specified at the moment.\n\n");
								
					for (int i = 0; i < rent.size(); i++) {
						
						bookIndex = Integer.parseInt(rent.get(i).getTitleID().substring(1)) - 1;
						readerIndex = Integer.parseInt(rent.get(i).getReaderID().substring(1)) - 1;
						
						if(this.rent.get(i).isRented() || this.rent.get(i).getState().equals("Rented")) {
						
							System.out.println(IO.printHyphen()
									+ "\n* The rent record with ID \"" + this.rent.get(i).getRentID() + "\" "
									+ "has its record with book ID \"" + this.rent.get(i).getTitleID() + "\" "
									+ "and reader ID \"" + this.rent.get(i).getReaderID() + "\"."
									+ "\n\n* The book with ID \"" + this.rent.get(i).getTitleID() + "\" "
									+ "is being rented by the reader with ID \"" + this.rent.get(i).getReaderID() + "\"."
									+ "\n\n" + rent.get(i)
									+ books.get(bookIndex).menu7_1_toString(rent, i, readers, readerIndex)
									+ readers.get(readerIndex));	
							
							
						}else {

							System.out.println(IO.printHyphen()
									+ "\n* The rent record with ID \"" + this.rent.get(i).getRentID() + "\" "
									+ "has its record with book ID \"" + this.rent.get(i).getTitleID() + "\" "
									+ "and reader ID \"" + this.rent.get(i).getReaderID() + "\"."
									+ "\n\n* The book with ID \"" + this.rent.get(i).getTitleID() + "\" "
									+ "has been rented by the reader with ID \"" + this.rent.get(i).getReaderID() + "\" before, "
									+ "\n  it has successfully been returned."
									+ "\n\n" + rent.get(i)
									+ books.get(bookIndex).menu7_1_toString(rent, i, readers, readerIndex)
									+ readers.get(readerIndex));	
							
						}

					}	
					
				}else  {
					System.out.println("going back to menu...");
				}
			
			break;

		case "2": // all rent record of a specific reader

			int objCounter = 0;
			readerID = searchForReader();
			
			if (readerID != null) {

				for (int i = 0; i < rent.size(); i++) {

					if (rent.get(i).getReaderID().equals(readerID)) {
						objCounter++;
					}
				}
				
				if(objCounter > 0) {
					
					System.out.println(IO.printUnderLine() 
							+ "\n### All of the rent records that hold the reader (with ID \"" + readerID + "\") and books the reader have rented/returned ###"
							+ "\n\n* Quick reminder for the field value of 'state' on a rent record"
							+ "\n  'state=Rented' represents the book(titleID) is being rented by a reader(readerID)"
							+ "\n  'state=Normal' represents the reader(readerID) has returned the book(titleID)");
					
					for (int i = 0; i < rent.size(); i++) {
						if (rent.get(i).getReaderID().equals(readerID)) {
							System.out.print(rent.get(i));
						}
					}
					
					//ask user if wanting to check all the rent records in detail
					advOp = IO.menu(IO.printUnderLine()	+ "\nWould you like to see all the rent record in detail(y/n)?", "^[a-zA-Z]$");
					if(advOp.equalsIgnoreCase("y")) {
						
						bookIndex = 0;
						readerIndex = 0;
						
						System.out.println(IO.printUnderLine()
								+ "\n* The following result(s) is/are the breakdown represented by"
								+ "\n\n  Line 1 == a specific rent record found in the previous step."
								+ "\n            'state=Rented' represents the book(titleID) is being rented by the reader(readerID)"
								+ "\n            'state=Normal' represents the reader(readerID) has returned the book(titleID)"
								+ "\n\n  Line 2 == the book info belonged to the rent record"
								+ "\n  Line 3 == the reader info belonged to the rent record"
								+ "\n\n\n[NOTE]"
								+ "\n\n* The field 'rental_state' of any book record will be shown"
								+ "\n  only when a book is being rented by the reader specified at the moment.\n\n");
						
						readerIndex = Integer.parseInt(readerID.substring(1)) - 1;
						
						for (int i = 0; i < rent.size(); i++) {
							
							if (rent.get(i).getReaderID().equals(readerID)) {
								
								bookIndex = Integer.parseInt(rent.get(i).getTitleID().substring(1)) - 1;
								
								if(this.rent.get(i).isRented() || this.rent.get(i).getState().equals("Rented")) {
									
									System.out.println(IO.printHyphen()
											+ "\n* The rent record with ID \"" + this.rent.get(i).getRentID() + "\" "
											+ "has its record with book ID \"" + this.rent.get(i).getTitleID() + "\" "
											+ "and reader ID \"" + this.rent.get(i).getReaderID() + "\"."
											+ "\n\n* The book with ID \"" + this.rent.get(i).getTitleID() + "\" "
											+ "is being rented by the reader with ID \"" + this.rent.get(i).getReaderID() + "\"."
											+ "\n\n" + rent.get(i)
											+ books.get(bookIndex).menu7_1_toString(rent, i, readers, readerIndex)
											+ readers.get(readerIndex));	
									
									
								}else {
									//Not Rented == Returned == "Normal"
									System.out.println(IO.printHyphen()
											+ "\n* The rent record with ID \"" + this.rent.get(i).getRentID() + "\" "
											+ "has its record with book ID \"" + this.rent.get(i).getTitleID() + "\" "
											+ "and reader ID \"" + this.rent.get(i).getReaderID() + "\"."
											+ "\n\n* The book with ID \"" + this.rent.get(i).getTitleID() + "\" "
											+ "has been rented by the reader with ID \"" + this.rent.get(i).getReaderID() + "\" before, "
											+ "\n  it has successfully been returned."
											+ "\n\n" + rent.get(i)
											+ books.get(bookIndex).menu7_1_toString(rent, i, readers, readerIndex)
											+ readers.get(readerIndex));	
									
								}

							}
	
						}
						
						
					}else  {
						System.out.println("going back to menu...");
					}
			
				}else {
					System.out.println("!!! No rent history belonged to the reader ID "
							+ "\"" + readerID + "\""
							+ " found !!!");
				}
					
			}else {
				System.out.println("!!! No valid reader info found. Please recheck the input and then try again. !!!");
			}
			
			break;

		case "3": // all record that a specific reader is CURRENTLY renting

			readerID = searchForReader();

			if (readerID != null) {
				
				readerIndex = 0;
				readerIndex = Integer.parseInt(readerID.substring(1)) - 1;
				
				if(readers.get(readerIndex).getMyRent() != null) {
					
					System.out.println(IO.printUnderLine() 
							+ "\n### All of the rent records that hold the reader (with ID \"" + readerID + "\") and books the reader is renting currently###"
							+ "\n\n* Quick reminder for the field value of 'state' on a rent record"
							+ "\n  'state=Rented' represents the book(titleID) is being rented by a reader(readerID)"
							+ "\n  'state=Normal' represents the reader(readerID) has returned the book(titleID)");
								
					for (int i = 0; i < readers.get(readerIndex).getMyRent().size(); i++) {
						System.out.print(readers.get(readerIndex).getMyRent().get(i));
					}
					
					//ask user if wanting to check all the rent records in detail
					advOp = IO.menu(IO.printUnderLine()	+ "\nWould you like to see all the rent record in detail(y/n)?", "^[a-zA-Z]$");
					if(advOp.equalsIgnoreCase("y")) {

						bookIndex = 0;
						rentIndex = 0;
						
						System.out.println(IO.printUnderLine()
								+ "\n* The following result(s) is/are the breakdown represented by"
								+ "\n\n  Line 1 == a specific rent record found in the previous step."
								+ "\n            'state=Rented' represents the book(titleID) is being rented by the reader(readerID)"
								+ "\n            'state=Normal' represents the reader(readerID) has returned the book(titleID)"
								+ "\n\n  Line 2 == the book info belonged to the rent record"
								+ "\n  Line 3 == the reader info belonged to the rent record"
								+ "\n\n\n[NOTE]" +
								"\n\n* The field 'rental_state' of any book record returned represents that" +
								"\n  (a) book(s) is/are being rented by the reader specified at the moment.\n\n");
						
						for (int i = 0; i < readers.get(readerIndex).getMyRent().size(); i++) {
								
							bookIndex = Integer.parseInt(readers.get(readerIndex).getMyRent().get(i).getTitleID().substring(1)) - 1;
							rentIndex = Integer.parseInt(readers.get(readerIndex).getMyRent().get(i).getRentID().substring(2)) - 1;
								
							System.out.println(IO.printHyphen()
									+ "\n* The rent record with ID \"" + readers.get(readerIndex).getMyRent().get(i).getRentID() + "\" "
									+ "has its record with book ID \"" + readers.get(readerIndex).getMyRent().get(i).getTitleID() + "\" "
									+ "and reader ID \"" + readers.get(readerIndex).getMyRent().get(i).getReaderID() + "\"."
									+ "\n\n* The book with ID \"" + readers.get(readerIndex).getMyRent().get(i).getTitleID() + "\" "
									+ "is being rented by the reader with ID \"" + readers.get(readerIndex).getMyRent().get(i).getReaderID() + "\"."
									+ "\n\n" + readers.get(readerIndex).getMyRent().get(i)
									+ books.get(bookIndex).menu7_1_toString(rent, rentIndex, readers, readerIndex)
									+ readers.get(readerIndex));					

						}
						
					}else  {
						System.out.println("going back to menu...");
					}
					
					
				}else {
					System.out.println("!!! The reader with ID \"" + readerID + "\" is not renting any books currently. !!!");
				}
				
			}else {
				System.out.println("!!! No valid reader info found. Please recheck the input and then try again. !!!");
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
	 *        бщ                            бщ 
	 *	Abril[id=R01, fname=Dulce, lname=Abril, Current Rent=RT02 RT03 RT04]
	 *	
	 *                       2nd sorted	
	 *                           бщ
	 *	Arcelia[id=R11, fname=Arcelia, lname=Bouska, Current Rent=none]
     *
     *                                      3rd sorted
     *                                           бщ   
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
		boolean isRequiredToRemoveTempPrefixAndSuffix = false, isHundreadUnit;

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
				
				/* When OBJs in the list > 99 or > 999, the unique ID(alphanumeric ID) of an OBJ in the list won't be sorted as expected,
				 * Since .compareTo() is based on UNICODE/ASCII while a sorting execution. 
				 * Thus, deal with that by setting a temporary values.
				 * (It's briefly tested up to 1000 records/rows. Please note that no strict validation with a large DB was performed)
				 * 
				 * More details can be found at the methods in Sort class */
				if(this.readers.size() > 99 && this.readers.size() < 1000) {
					isHundreadUnit = true;
					result = sort.setTempPrefixAndSuffix(result, this.readers, isHundreadUnit);
					isRequiredToRemoveTempPrefixAndSuffix = true;
					//System.out.println("before sorting but after: result = sort.setTempPrefixAndSuffix(result, this.readers.size(), isHundreadUnit);");
					//sort.printArrayInColumn(result);
				}
				if (this.readers.size() > 999 && this.readers.size() < 10000) {
					isHundreadUnit = true;
					result = sort.setTempPrefixAndSuffix(result, this.readers, isHundreadUnit);
					result = sort.setTempPrefixAndSuffix(result, this.readers, !isHundreadUnit);
					isRequiredToRemoveTempPrefixAndSuffix = true;
					//System.out.println("before sorting but after: result = sort.setTempPrefixAndSuffix(result, this.books, isHundreadUnit);");
					//sort.printArrayInColumn(result);
				}
				
				/* if wanted the result using merge sort, uncomment the code below out */
				//result = sort.mergeSort(result);	
				
				result = sort.bubbleSort(result);	//comment this out if used merge sort above
				
				if(isRequiredToRemoveTempPrefixAndSuffix) {
					result = sort.removeTempPrefixAndSuffix(result);
					//System.out.println("after sorting and after: result = sort.removeTempPrefixAndSuffix(result);");
					//sort.printArrayInColumn(result);
				}
				
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
     *                      бщ  
     *	R01[id=R01, fname=Dulce, lname=Abril, Current Rent=RT02 RT03 RT04]        
     **************************************************************************************************
	 *  
	 *  Search option "2" returns the exact match if found, 
	 *  allowing one search keyword for each of fields(readerId, first name, last name) at a time
	 *  
	 **************************************************************************************************
	 * [NOTE]
	 * Basically, the main function of the method is to print the result searched rather than returning something.
	 * When searching for a reader ID is necessary, the method is called and returns a reader ID as a string   
	 * 
	 */
	private String searchForReader() {

		String keyword = "", askUserOp = "", toReturn = "";

		askUserOp = IO.menu(IO.printReaderSearchOptionMenu(), "^[1|2|q|Q]$");
		
		//binarySearch
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
					//not found
					System.out.println("No match found with the keyword");
					toReturn = null;

				} else {
					//found
					System.out.println(this.readers.get(found));
					toReturn = this.readers.get(found).getId();
					
				}
			} else {
				System.out.println("No match found with the keyword");
				toReturn = null;
			}
		}

		//linear search
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
				//not found
				System.out.println("No match found with the keyword");
				toReturn = null;
				

			} else {
				
				System.out.println(this.readers.get(found));	//found
				toReturn = this.readers.get(found).getId();
		
			}
		}

		if (askUserOp.equalsIgnoreCase("q")) {
			System.out.println("going back to menu...");
			toReturn = null;
		}
		
		return toReturn;
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
	 *        бщ                                                        бщ 
	 *	Anneke Preusig[id=B06, title=World Leathernecks, author=Anneke Preusig, rental_state=Available, readerInQ=none]
	 *	
	 *                    2nd sorted	
	 *                        бщ
	 *	Beast[id=B12, title=Beast, author=Patricio Bridgland, rental_state=Available, readerInQ=none]
     *
     *                                                         3rd sorted
     *                                                            бщ   
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
		boolean isRequiredToRemoveTempPrefixAndSuffix = false, isHundreadUnit;


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

				/* When OBJs in the list > 99 or > 999, the unique ID(alphanumeric ID) of an OBJ in the list won't be sorted as expected,
				 * Since .compareTo() is based on UNICODE/ASCII while a sorting execution. 
				 * Thus, deal with that by setting a temporary values.
				 * (It's briefly tested up to 1000 records/rows. Please note that no strict validation with a large DB was performed)
				 * 
				 * More details can be found at the methods in Sort class */
				if(this.books.size() > 99 && this.books.size() < 1000) {
					isHundreadUnit = true;
					result = sort.setTempPrefixAndSuffix(result, this.books, isHundreadUnit);
					isRequiredToRemoveTempPrefixAndSuffix = true;
					//System.out.println("before sorting but after: result = sort.setTempPrefixAndSuffix(result, this.books, isHundreadUnit);");
					//sort.printArrayInColumn(result);
				}
				if (this.books.size() > 999 && this.books.size() < 10000) {
					isHundreadUnit = true;
					result = sort.setTempPrefixAndSuffix(result, this.books, isHundreadUnit);
					result = sort.setTempPrefixAndSuffix(result, this.books, !isHundreadUnit);
					isRequiredToRemoveTempPrefixAndSuffix = true;
					//System.out.println("before sorting but after: result = sort.setTempPrefixAndSuffix(result, this.books, isHundreadUnit);");
					//sort.printArrayInColumn(result);
				}
				
				/* if wanted the result using bubble sort, uncomment the code below out */
				//result = sort.bubbleSort(result);	
				
				result = sort.mergeSort(result);	//comment this out if used bubble sort above
				
				if(isRequiredToRemoveTempPrefixAndSuffix) {
					result = sort.removeTempPrefixAndSuffix(result);
					//System.out.println("after sorting and after: result = sort.removeTempPrefixAndSuffix(result);");
					//sort.printArrayInColumn(result);
				}
				
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
     *          бщ
     *  B01[id=B01, title=Hunger Game, author=George, rental_state=Rented, readerInQ=R01 R02 R03]
     *
     *  CASE 2)
     *  For example, user's keyword == "wrong input hunger b01"
     *  
	 *  [Partial match]
	 *  Hunger is found in the book index at 0
	 *  
	 *                      бщ
	 *  B01[id=B01, title=Hunger Game, author=George, rental_state=Rented, readerInQ=R01 R02 R03]
     **************************************************************************************************
	 * 
	 * Search option "2" allows one search keyword for each of fields(title, author) at a time 
	 * and returns the exact match if found.
	 * 
	 **************************************************************************************************
	 * [NOTE]
	 * Basically, the main function of the method is to print the result searched rather than returning something.
	 * When searching for a book ID is necessary, the method is called and returns a book ID as a string 
	 */
	private String searchForBook() {
		
		String keyword = "", askUserOp = "", toReturn = "";
		boolean isForAdvancedSearch;

		askUserOp = IO.menu(IO.printBookSearchOptionMenu(), "^[1|2|q|Q]$");
		
		//binary search
		if (askUserOp.equals("1")) {	//search for a book by multiple keywords from user 
			
			Sort sort = new Sort();	//declare & init a new sort 
			String[] arr =  null;
				
			if ((arr = sort.collectFieldsOfBooks(this.books, 5)) != null) {
				// now, arr has got bookId, title and author collected
				
				/* if necessary, uncomment the code below out to check the process of merge sort of arr*/
				//System.out.println("--------------before sorting----------");
				//sort.printArrayInColumn(arr);
				
				arr = sort.mergeSort(arr);	 //now, arr has got bookId, title and author sorted in ASC
				
				/* if necessary, uncomment the code below out to check the process of merge sort of arr*/
				//System.out.println("\n--------------after sorting-----------");
				//sort.printArrayInColumn(arr);

			}
			
			Search s = new Search();
			keyword = IO.menu(IO.printBookSearchMenu(), "[a-zA-Z0-9]++");
			String[] keys;			//array to split up keyword
			List<Books> temp = null;
			isForAdvancedSearch = false;
			
			//create a temporary book list
			temp = s.createTempBooksList(arr, this.books, isForAdvancedSearch);
			
			//phase1) execute a binary search for a book with the user's original keyword input
			int found = 0;
			if(keyword != null) {
				
				keys = null;	//not in use for now
				found = s.binarySearch(temp, keyword.trim(), keys, 0, 0, temp.size()-1);
				
				if (found != -1) {
					
					System.out.println(this.books.get(found));	//found
					toReturn = this.books.get(found).getId();
					
				} else {
					
					/* nothing is found by the user's original keyword input.
					 * prepare to execute a binary search for a book again with the user's keyword input split up
					 */
					
					//collect bookId, titles split up and authors split up
					if ((arr = sort.collectFieldsOfBooks(this.books)) != null) {
						
						/* if necessary, uncomment the code below out to check the process of merge sort of arr*/
						//System.out.println("--------------before sorting----------");
						//sort.printArrayInColumn(arr);
						
						arr = sort.mergeSort(arr);	//now, arr has got bookId, title split and author split sorted in ASC
						
						/* if necessary, uncomment the code below out to check the process of merge sort of arr*/
						//System.out.println("\n--------------after sorting----------");
						//sort.printArrayInColumn(arr);
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
						toReturn = this.books.get(found).getId();
						
					} else {
						//not found
						System.out.println("No match found with the keyword");
						toReturn = null;
					}
					
				}
				
			}else {
				System.out.println(IO.printUnderLine() + "\nPlease check search keyword and try it again");
				toReturn = null;
			}
					
		}

		//linear search
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
				//not found
				System.out.println("No match found with the keyword");
				toReturn = null;

			} else {
				System.out.println(this.books.get(found));	//found
				toReturn = this.books.get(found).getId();
			}
		}

		if (askUserOp.equalsIgnoreCase("q")) {
			System.out.println("Going back to menu...");
			toReturn = null;
		}

		return toReturn;
	}

	/**
	 * method to register a return of books
	 */
	private void registerReturn() {
		
		String rtID = "", bID = "";
		int rentIndex = 0, bookIndex = 0, readerIndex = 0;

		rtID = checkRentID(rtID);

		if (rtID != null) {

			/* rtID is validated and it's sure that its state is "Rented" */
			
			//extract rent index and reader index
			rentIndex = Integer.parseInt(rtID.substring(2)) - 1;
			readerIndex = Integer.parseInt(rent.get(rentIndex).getReaderID().substring(1)) - 1;

			//update the state of the specific rent record in the Rent list
			rent.get(rentIndex).setRented(false);
			rent.get(rentIndex).setNormal(true);
			
			//extract bID and book index from bID
			bID = rent.get(rentIndex).getTitleID();
			bookIndex = Integer.parseInt(bID.substring(1)) - 1;
			
			//clone the state of the specific rent record in the Rent list 
			//into the specific book's rental state 
			books.get(bookIndex).setRented(rent.get(rentIndex).isRented());
			books.get(bookIndex).setAvailable(rent.get(rentIndex).isNormal());
			
			//remove the specific rent record from the reader's current rent list
			readers.get(readerIndex).removeRent(rtID);

			boolean isNew = false;	//not a new record to write(append) into Rent.txt 
			
			// update any changes to files
			factory.writeRentToFile(rent, isNew);
			factory.writeBooksToFile(books);
			factory.writeReadersToFile(readers);

			System.out.println("### Successfully returned###");
			System.out.println(rent.get(rentIndex));
			System.out.println(books.get(bookIndex));
			System.out.println(readers.get(readerIndex));
			
			
			/* even though a book is returned and it has a queue of reader,
			 * it doesn't mean that the next reader waiting for that book
			 * automatically rent that book. 
			 * 
			 * the book is now available to be rented.
			 * 
			 * */

			//display to the user the next reader waiting for that book
			if (books.get(bookIndex).getMQ() != null) {
				if (!books.get(bookIndex).getMQ().isEmpty()) {
					System.out.println(IO.printUnderLine() + "\n!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!"
							+ "\n<Book info>\n" + books.get(bookIndex).toString()
							+ "\n\n<The first available reader in the book's queue for renting>\n"
							+ books.get(bookIndex).getMQ().firstToString());
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
		
		// the book whose ID is parameterized as bID is in the state of "Available" 
		// whether its queue is empty or not 
		
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
						
						//nothing is in this book's queue, keep going
						
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
						
						System.out.println(IO.printUnderLine());
						System.out.println(rent.get(rent.size() - 1));
						System.out.println(books.get(Integer.parseInt(bID.substring(1)) - 1));
						System.out.println(readers.get(rIdToIndex));
						

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
								
								System.out.println(IO.printUnderLine());
								System.out.println(rent.get(rent.size() - 1));
								System.out.println(books.get(Integer.parseInt(bID.substring(1)) - 1));
								System.out.println(readers.get(rIdToIndex));

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
									/* + "\n* Please check the following detail." */);
									
									
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

				} /*
					 * else { System.out.println("the reader does not exist. Try again"); }
					 */
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

		String op = "";
		boolean isGoBackToMenu = false;
		
		op = IO.menu(IO.askUserReaderIdWithOp(), "^[1|2|q|Q]$");
		
		if(op.equalsIgnoreCase("1")) {
			rID = IO.menu(IO.printReaderIDMenu(), "[a-zA-Z0-9]");
		}
		
		if(op.equalsIgnoreCase("2")) {
			rID = searchForReader();
		}
		
		if(op.equalsIgnoreCase("q")) {
			
			isGoBackToMenu = true;
			System.out.println("going back to menu...");
		}
		
		if(rID != null) {
			
			for (int i = 0; i < readers.size(); i++) {
				if (readers.get(i).getId().equalsIgnoreCase(rID)) {
					
					return rID.toUpperCase();
				}
			}
			
			if (!isGoBackToMenu) {
				
				System.out.println("The reader ID not found. Try again.");
			}
			
		}
	
		return null;
	}

	/**
	 * method to return the valid rent id
	 * 
	 * As an accurate search for a rent record is not the main requirement,
	 * no dedicated search function such as using reader ID or bookID is added.
	 * 
	 * Thus, in this method, a rent ID is an input and return it 
	 * if the rent record found is in the state of "Rented" 
	 * 
	 * @param rtID, rID
	 * @return valid rtID
	 */
	public String checkRentID(String rtID) {
		
		String tempBookID = "", tempReaderId = "";
		int rentIndex = 0;

		rtID = IO.menu(IO.printRentIDMenu(), "[a-zA-Z0-9]"); 
		rtID = rtID.toUpperCase();
		
		for (int i = 0; i < rent.size(); i++) {
			if (rent.get(i).getRentID().equalsIgnoreCase(rtID)) {

				rentIndex = Integer.parseInt(rtID.substring(2)) - 1;
				if (rent.get(rentIndex).isRented() || rent.get(rentIndex).getState().equals("Rented")) {
					
					tempBookID = rent.get(rentIndex).getTitleID();
					tempReaderId = rent.get(rentIndex).getReaderID();

					System.out.println(IO.printUnderLine());
					System.out.println("One rent record found as follows");
					System.out.println(rent.get(rentIndex));
					System.out.println(rent.get(rentIndex).getTitleID() + ": "
							+ books.get(Integer.parseInt(tempBookID.substring(1)) - 1).getTitle());
					System.out.println(rent.get(rentIndex).getReaderID() + ": "
							+ readers.get(Integer.parseInt(tempReaderId.substring(1)) - 1).getFname() + " "
							+ readers.get(Integer.parseInt(tempReaderId.substring(1)) - 1).getLname());
					
					String yn = IO.menu(IO.printRendRecordCheckOption(rtID, tempBookID, tempReaderId), "^[y|Y|n|N]$");

					if (yn.equalsIgnoreCase("y")) {
						
						System.out.println(IO.printUnderLine());
						System.out.println(rent.get(rentIndex));
						System.out.println(books.get(Integer.parseInt(tempBookID.substring(1)) - 1));
						System.out.println(readers.get(Integer.parseInt(tempReaderId.substring(1)) - 1));
					}
					
					String yOrQuit = IO.menu(IO.printRendRecordCheckOption(), "^[y|Y|q|Q]$");
					if (yOrQuit.equalsIgnoreCase("y")) {
						
						return rtID;
					}
					
					if (yOrQuit.equalsIgnoreCase("q")) {
						System.out.println("going back to menu...");
					}
					
				}else {
					
					System.out.println("* The rent record exists but the book on the rent record has been returned");
					System.out.println(rent.get(rentIndex));
					System.out.println("going back to menu...");
					return null;
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
		
		String op = "";
		boolean isGoBackToMenu = false;
		
		op = IO.menu(IO.askUserBookIdWithOp(), "^[1|2|q|Q]$");
		
		if(op.equalsIgnoreCase("1")) {
			bID = IO.menu(IO.printBookIDMenu(), "[a-zA-Z0-9]");
		}
		
		if(op.equalsIgnoreCase("2")) {
			bID = searchForBook();
		}
		
		if(op.equalsIgnoreCase("q")) {
			
			isGoBackToMenu = true;
			System.out.println("going back to menu...");
		}
		
		if(bID != null) {
			
			System.out.println(bID);
			
			for (int i = 0; i < books.size(); i++) { 
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
			
			if (!isGoBackToMenu) {
				
				System.out.println("The book ID not found. Try again.");
			}
			
		}

		return null;
	}

	/**
	 * method to manage all the enqueue processes
	 * @param bID
	 * @param tempIndex
	 * @param rID
	 * @return appropriate message after dealing with an enqueue execution whether successful or not 
	 */
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
				//System.out.println("The reader ID does not exist in Readers db. Try again");
				return null;
			}

		}
		
		bID = bID.toUpperCase();
		rID = rID.toUpperCase();

		//init fields
		rentIdToIndex = 0;
		bIdToIndex = 0;
		rIdToIndex = 0;
		rIdToIndex = Integer.parseInt(rID.substring(1)) - 1;
		
		/* [A quick note for the processes commented & numbered as "1." and "2."]
		 * 
		 * No direct validation through the original Rent list is considered.
		 * Because a reader only holds one whole rent OBJ cloned in the reader's current rent list during "renting" ONLY.
		 * In addition, no the same bookId can be in reader's current rent list at the same time.
		 * */

		// 1. filter the input reader that is asking for being in the book's queue -the book whose ID is parameterized as bID- 
		//    if the reader has already been renting the book.  
		if (this.readers.get(rIdToIndex).getMyRent() != null) {

			for (int i = 0; i < readers.get(rIdToIndex).getMyRent().size(); i++) {

				if (this.readers.get(rIdToIndex).getMyRent().get(i).getTitleID().equalsIgnoreCase(bID)) {
					
					// reader who has already been renting this book at the moment is asking for being in the queue
					
					rentIdToIndex = Integer.parseInt(this.readers.get(rIdToIndex).getMyRent().get(i).getRentID().substring(2)) - 1;
					bIdToIndex = Integer.parseInt(this.readers.get(rIdToIndex).getMyRent().get(i).getTitleID().substring(1)) - 1;
					isAskReaderQEntry = false;	
					
					System.out.println(IO.printWaitingQueueMenu(this.rent, rentIdToIndex, this.books, bIdToIndex, this.readers, rIdToIndex, null, isAskReaderQEntry));
					return null;

				}
			}

		}
		
			
		// 2. the input reader is not renting the book with ID parameterized as bID.  
		//    so the input reader can be added into the book's queue if wanted.
		
		int tmep_rentIdToIndex = 0, tmep_bIdToIndex = 0, tmep_rIdToIndex = 0;
		boolean isBreak = false;
		
		//this FOR-LOOP doesn't care about the input reader and find a reader who is renting the book with ID parameterized as bID.  
		for(int i=0; i<this.readers.size(); i++) {
			if (this.readers.get(i).getMyRent() != null) {
				for (int j=0; j<readers.get(i).getMyRent().size(); j++) {
					if (readers.get(i).getMyRent().get(j).getTitleID().equalsIgnoreCase(bID)) {
						
						
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
			
		
		// 3. An actual step of enqueue 	
		if (books.get(tempIndex).getMQ() != null) {
			if (!books.get(tempIndex).getMQ().isEmpty() && !books.get(tempIndex).getMQ().equalsCustom(rID)) {
				
				//the book you are looking at has its queue and has no the same node(readerID) as rID's value
				
				isAskReaderQEntry = true;
				yn = IO.menu(IO.printWaitingQueueMenu(this.books, tempIndex, rID, isAskReaderQEntry), "[a-zA-Z0-9]");
				// ask reader if waning to be in the book's queue
				if ( !(yn.equalsIgnoreCase("y"))) {
					
					System.out.println("Back to main menu....");
					return null;

				}
				
				enQueueManager(rID, bID);	//enQueueManager(rID, bID);
				
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

			// if not meeting conditions just above, the input reader is in the book's queue
			System.out.println(IO.printUnderLine());
			System.out.println("* The reader with ID \"" + rID + "\" is already in the book's queue");
			System.out.println("\n<Book info>" + books.get(tempIndex));
			System.out.println(books.get(tempIndex).getMQ().equalsCustomToString(rID));
			return null;

		} else {
			
			// the book you are looking at does not have any queue of Readers
			
			enQueueManager(rID, bID);	//enQueueManager(rID, bID);

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

	}


	/**
	 * method to perform enqueue
	 * @param rIDTobeInQ
	 * @param bIDPassed
	 */
	private void enQueueManager(String rIDTobeInQ, String bIDPassed) {

		int readerIndex = Integer.parseInt(rIDTobeInQ.substring(1)) - 1;
		int bookIndex = Integer.parseInt(bIDPassed.substring(1)) - 1;

		books.get(bookIndex).setEnQueue(readers, readerIndex);

	}


}
