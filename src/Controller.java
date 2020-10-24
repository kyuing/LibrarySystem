import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import ie.cct._2018316.cunstructs.FactoryInterface;
import ie.cct._2018316.cunstructs.IO;
import ie.cct._2018316.dev.Books;
import ie.cct._2018316.dev.Factory;
import ie.cct._2018316.dev.MyQueue;
import ie.cct._2018316.dev.Node;
import ie.cct._2018316.dev.Readers;
import ie.cct._2018316.dev.Rent;

public class Controller {

	BufferedReader books_in, readers_in, rent_in;
	private List<Books> books;
	private List<Readers> readers;
	private List<Rent> rent;
	
	public Controller() throws IOException {
		
		//the structure of reading txt-input followed and edited the code (in main method & factoryInterface class) 
		//from the sample solution of CA1 in the semester4. 
		FactoryInterface factory = new Factory();	//declare & init factory

		String rt = "Rent.txt";
		rent_in = new BufferedReader(new FileReader(rt));
		rent = (List<Rent>) factory.createRentDB(rent_in);
		System.out.println(rent);
		
		
		String r = "Readers.txt";
		readers_in = new BufferedReader(new FileReader(r));
		readers = (List<Readers>) factory.createReaderDB(readers_in, rent);
		System.out.println(readers);
		
		//read input and store them
		String b = "Books.txt";
		books_in = new BufferedReader(new FileReader(b));
    	books = (List<Books>) factory.createBookDB(books_in, readers);
		System.out.println(books);
		
		
		
		//add some dummy queue into some of the books
		//this for-loop can be commented out if you want to start the program with no queue in books
//		books.get(0).setEnQueue(readers, 0);
//		books.get(0).setEnQueue(readers, 1);
		System.out.println(books.get(0).getQueueToString());
//		books.get(2).setEnQueue(readers, 2);
//		books.get(2).setEnQueue(readers, 3);
//		books.get(2).setEnQueue(readers, 4);
		System.out.println(books.get(2).getQueueToString());
		
		menu();
	}

	private void menu() {
		
		String op = IO.menu(IO.printMenu(), "^[1|2|3|4|5|6|7]$");
		//test to add rental record first.
		switch(op) {
		  case "5":
		    registerRent();
		    menu();
		    
		  case "6":
			registerReturn();  
			
			
			//may add to check all the queue's belonged to a book		    
		  default:
		    menu();
		}
	}

	private void registerReturn() {
		String rtID = "", bID = "";	//don't think i really need book id here since rent id holds everything
		int rtIdToInt = 0, bIdToInt = 0, rIdToInt = 0;

		rtID = checkRentID(rtID);
		
		if(rtID != null) {	//here, the rentID returned after all possible validation.
			
			rtIdToInt = Integer.parseInt(rtID.substring(2))-1;
			rIdToInt = Integer.parseInt(rent.get(rtIdToInt).getReaderID().substring(1))-1;
			
			rent.get(rtIdToInt).setRented(false);
			rent.get(rtIdToInt).setNormal(true);
			bID = rent.get(rtIdToInt).getTitleID();
			bIdToInt = Integer.parseInt(bID.substring(1))-1;
			books.get(bIdToInt).setRented(rent.get(rtIdToInt).isRented());
			books.get(bIdToInt).setAvailable(rent.get(rtIdToInt).isNormal());
			readers.get(rIdToInt).removeRent(rtID);			
			System.out.println("### Successfully returned###");
			System.out.println(rent);
			System.out.println(books);
			System.out.println(readers);
			
			if(!books.get(bIdToInt).getMQ().isEmpty()) {
				System.out.println(IO.printUnderLine()
									+ "\n!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!"
									+ "\n[The book info]\n" + books.get(bIdToInt).toString()
									+ "\n\n[The first available reader in the book's queue for renting]\n" 
									+ books.get(bIdToInt).getMQ().firstToString());
			}

			
		}

	}

	/**
	 * void method for registering rent record
	 */
	private void registerRent() {
		
		String bID = "", rID = "";
		bID = availableBookToLend(bID);
		if(bID != null) {	
			
			if(bID.equals("Successfully added to the reader into the book's queue")) {
				//this line is printed only when a reader is added to queue
				System.out.println("\n###### Successfully added to the reader into the book's queue ######");
				
			}else {// need to update myRent in Readers once a new rent is created in Rent class
				rID = checkReeaderID(rID);
				if(rID != null) {	//if !null, the reader exists
					int bIdToIndex = Integer.parseInt(bID.substring(1))-1;
					int rIdToIndex = Integer.parseInt(rID.substring(1))-1;
					if(books.get(bIdToIndex).getReaderInQ().equals("none")) {
						//if nothing is in this book's queue, keep going
						rent.add(new Rent());
						rent.get(rent.size()-1).setRentID("RT" + String.valueOf(rent.size()));
						rent.get(rent.size()-1).setTitleID(bID); 
						rent.get(rent.size()-1).setReaderID(rID);
						rent.get(rent.size()-1).setRented(true);
						rent.get(rent.size()-1).setNormal(false);
						books.get(Integer.parseInt(bID.substring(1))-1).setRented(rent.get(rent.size()-1).isRented());
						books.get(Integer.parseInt(bID.substring(1))-1).setAvailable(rent.get(rent.size()-1).isNormal());
//						readers.get(rIdToIndex).getMyRent().add(rent.get(rent.size()-1));
						readers.get(rIdToIndex).setMyRent(rent);
//						System.out.println(readers.get(rIdToIndex).getMyRent());
						
						System.out.println("### A new rent has just been recorded successfully ###");
						System.out.println(rent);
						System.out.println(books);
						System.out.println(readers);
					}else {
						
						//if wanted, here, you can validate with mq referece in a book 
						if(books.get(bIdToIndex).getReaderInQ().equals(rID)) {
							//if the reader who is in the 1st queue == rID, process a rent
							rent.add(new Rent());
							rent.get(rent.size()-1).setRentID("RT" + String.valueOf(rent.size()));
							rent.get(rent.size()-1).setTitleID(bID); 
							rent.get(rent.size()-1).setReaderID(rID);
							rent.get(rent.size()-1).setRented(true);
							rent.get(rent.size()-1).setNormal(false);
							books.get(Integer.parseInt(bID.substring(1))-1).setRented(rent.get(rent.size()-1).isRented());
							books.get(Integer.parseInt(bID.substring(1))-1).setAvailable(rent.get(rent.size()-1).isNormal());
							readers.get(rIdToIndex).setMyRent(rent);
							
							System.out.println("### A new rent has just been recorded successfully ###");
							System.out.println(rent);
							books.get(bIdToIndex).setDeQueue();	//remove the 1st node from queue
							System.out.println(books);
							System.out.println(readers);
							
							if(!books.get(bIdToIndex).getMQ().isEmpty()) {
								System.out.println(IO.printUnderLine()
													+ "\n!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!"
													+ "\n[The book info]\n" + books.get(bIdToIndex).toString()
													+ "\n\n[The first available reader in the book's queue for renting]\n" 
													+ books.get(bIdToIndex).getMQ().firstToString());
							}
							
						}
						
						else {
							//the book's queue has the first node(a reader) that is not equals to this reader  
							//the queue should go in order
							
							if(books.get(bIdToIndex).getMQ().equalsCustom(rID.toUpperCase())) {
								System.out.println(IO.printUnderLine());
								System.out.println("* The reader is in the book's queue, but is not in the first queue.");
								System.out.println(books.get(bIdToIndex).getMQ().equalsCustomToString(rID));	
								System.out.println("\n\n* Please inform the reader that the reader that \n  he/she is not in the first queue to rent the book");
								
							}else {
								//when book is available but has the existing queue
								System.out.println("* The book is avaiiable to be rented. "
												+ "\n  However, the book has the existing waiting queue of the reader(s) "
												+ "\n  who has/have already been in the book's queue for renting.");
								System.out.println("\n* The reader must be registered in the book's queue"
												+ "\n  in order for he/she to be able to rent the book."
												+ "\n\n  Please refer to the following prompt.");
								
								
								if(waitingQueueManager(bID, bIdToIndex, rID).equals("Successfully added to the reader into the book's queue")) {
									//this line is printed only when a reader is added to queue
									System.out.println("\n###### Successfully added to the reader into the book's queue ######");
									
								}

							}
							
						}
				
					}

				}else {
					System.out.println("the reader does not exist. Try again");
				}
			}
		}
		
	}
	
	/**
	 * method for checking to see if a reader exists
	 * @param rID
	 * @return rID or null
	 */
	public String checkReeaderID(String rID) {
		
		rID = IO.menu(IO.printReaderIDMenu(), "[a-zA-Z0-9]");
		//may need to add some search function to search for reader id
		for(int i=0; i<readers.size(); i++) {
			if (readers.get(i).getId().equalsIgnoreCase(rID)) {
					return rID.toUpperCase();
			  }
		 }
		return null;
	}
	
	/**
	 * method to return the valid rent id
	 * @param rtID, rID
	 * @return
	 */
	public String checkRentID(String rtID) {
		//may need some other approach so as to aske only rent id or reader id in Rent class
		String tempBookID = "", tempReaderId = "";
		int rtIdToInt = 0;
		
		rtID = IO.menu(IO.printRentIDMenu(), "[a-zA-Z0-9]");	//this needs to be changed. for just put for loop
		for(int i=0; i<rent.size(); i++) {
			if (rent.get(i).getRentID().equalsIgnoreCase(rtID)) {
				  
				rtIdToInt = Integer.parseInt(rtID.substring(2));
				tempBookID = rent.get(rtIdToInt-1).getTitleID();
				tempReaderId = rent.get(rtIdToInt-1).getReaderID();
						
				System.out.println(IO.printUnderLine());
				System.out.println("One rent record found as follows");
				System.out.println(rent.get(rtIdToInt-1));
				System.out.println(rent.get(rtIdToInt-1).getTitleID() 
						+ ": " + books.get(Integer.parseInt(tempBookID.substring(1))-1).getTitle());
				System.out.println(rent.get(rtIdToInt-1).getReaderID() 
						+ ": " + readers.get(Integer.parseInt(tempReaderId.substring(1))-1).getFname()
						+ " " + readers.get(Integer.parseInt(tempReaderId.substring(1))-1).getLname());	
				
				String yn = IO.menu(IO.printRendRecordCheckOption(), "^[y|Y|n|N|q|Q]$");
				
				if(yn.equalsIgnoreCase("y")) {
				    if(rent.get(rtIdToInt-1).isRented()) {
					  return rtID.toUpperCase();
				    }
				}
				
				if(yn.equalsIgnoreCase("n")) {
					String rID = "";
				    rID = IO.menu(IO.printReaderIDMenu(), "[a-zA-Z0-9]");
				    int rIdIndex = 0;
				    rIdIndex = Integer.parseInt(rID.substring(1))-1;
				    if((rent.get(rtIdToInt-1).isRented()) 
				  	  && (rent.get(rtIdToInt-1).getReaderID().equalsIgnoreCase(rID))
				  	  | (readers.get(rIdIndex).equalsRentInMyRents(rent.get(rtIdToInt-1).getRentID())) ) {
				      
				    	System.out.println(IO.printUnderLine());
						System.out.println("One rent record found as follows");
						System.out.println(rent.get(rtIdToInt-1));
						System.out.println(rent.get(rtIdToInt-1).getTitleID() 
								+ ": " + books.get(Integer.parseInt(tempBookID.substring(1))-1).getTitle());
						System.out.println(rent.get(rtIdToInt-1).getReaderID() 
								+ ": " + readers.get(Integer.parseInt(rID.substring(1))-1).getFname()
								+ " " + readers.get(Integer.parseInt(rID.substring(1))-1).getLname());	
						System.out.println();

					  return rtID.toUpperCase();
					
				    }
				    
				}
				
				if(yn.equalsIgnoreCase("q")) {
					System.out.println("Back to main menu....");
				}
				
			}
			  
		 }
		System.out.println("The rent ID not found. Try again");
		return null;
	}

	/**
	 * method for checking to see if a book is available to lend 
	 * @param bID
	 * @return bID or null
	 */
	public String availableBookToLend(String bID) {
		String yn = "";
		int bIdToInt = 0;
		bID = IO.menu(IO.printBookIDMenu(), "[a-zA-Z0-9]");	
		//may need to add some search function to search for book id
		for(int i=0; i<books.size(); i++) { //this needs to be working for all indexes in books
			if (books.get(i).getId().equalsIgnoreCase(bID)) {//need to use i
					
				if(books.get(i).isAvailable()) {
					  //if available, the book is returned so it's available		  
					return bID.toUpperCase();
					
				}else {
					//the book is not available to rent since somebody is renting it at the moment.
					//a reader can be added to the book's queue 
					//or a book's queue can be created and the reader will be the first queue in it.
					String rID = null;
					return waitingQueueManager(bID, i, rID);
								
				}
			}

		}
		System.out.println("The book ID not found. Try again");
		return null;	
				  		
	}

	public String waitingQueueManager(String bID, int tempIndex, String rID) {
		
		String yn ="";
//		String rID;
		int rIdToIndex = 0;
		
		yn = IO.menu(IO.printWaitingQueueMenu(books, bID), "[a-zA-Z0-9]");
		//ask reader if waning to be in the book's queue
		if(!(yn.equalsIgnoreCase("y"))) {
			System.out.println("Back to main menu....");
			return null;
		}
		
		if(rID == null) {
			rID = "";
			if((rID = checkReeaderID(rID)) == null) {
				System.out.println("The reader ID does not exist in Readers db. Try again");
				return null;
			}
			
		}
		
		
		rIdToIndex = Integer.parseInt(rID.substring(1))-1;
		
		if (readers.get(rIdToIndex).getMyRent() != null) {
			
			for(int j=0; j<readers.get(rIdToIndex).getMyRent().size(); j++) {
				
				if(readers.get(rIdToIndex).getMyRent().get(j).getTitleID().equalsIgnoreCase(bID)) {	
					
					//the reader is who has been renting this book at the moment is asking for being in the queue
					System.out.println(IO.printUnderLine());
					System.out.println("* The reader has already been renting this book"
							+ "\n" + readers.get(rIdToIndex)
							+ "\n\n* The bookks that are being rented by the reader"
							+ "\n" + readers.get(rIdToIndex).getMyRent());
					System.out.println("\n\n* Please infrom the reader that returning the book is first"
							+ "\n  so that the reader can be added into the book's queue "
							+ "\n  if the book is being rented by another reader");
					return null;	
				
				}		
			}
		
		}
		
		if(!books.get(tempIndex).getMQ().isEmpty()
				&& !books.get(tempIndex).getMQ().equalsCustom(rID)) {
			//there's no the existing same reader info(ID) in the book's queue
			enQueueManager(rID, bID);
			System.out.println(IO.printUnderLine());
			System.out.println("!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!");
			System.out.println("* A new update is processed with the following reader and book");
			System.out.println("\n[The reader info]");
			System.out.println(readers.get(rIdToIndex).toString().replaceAll("\n", ""));
			
			System.out.println(books.get(tempIndex).getQueueToString());	
			return "Successfully added to the reader into the book's queue";
		}
		
		//if not meeting the two conditions just above, the reader is in the book's queue 
		System.out.println("The reader is already in the book's queue");
//		if(books.get(i).getMQ().equalsCustom(rID)) {
			System.out.println(books.get(tempIndex).getMQ().equalsCustomToString(rID));	
//		}
		return null;

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
	
	//queue implementation issue
	//queue doesnt distinghuish book....
	//it needs to be an attribute in Books
	
	//rent and return issue
	//decide if Readers needs to have all the list of books being rented (allow multiple rents)or
	//if one book only (allow only one rent)
	private void enQueueManager(String rIDTobeInQ, String bIDPassed) {

		String first = ""; 
		int readerIndex = Integer.parseInt(rIDTobeInQ.substring(1))-1;
		int bookIndex = Integer.parseInt(bIDPassed.substring(1))-1;

		books.get(bookIndex).setEnQueue(readers, readerIndex);
//		System.out.println(books.get(bookIndex).getQueueToString());

		
//		if(!books.get(bookIndex).getReaderInQ().equals("none")) {
			//set current reader ID into the book record.
			//a book record only holds the first queue of the current reader ID
//			books.get(bookIndex).setReaderInQ("none");
			
			
			//create a node only if the reader is not currently in the book's queue
//			books.get(bookIndex).setEnQueue(readerIndex);
//			System.out.println(books.get(bookIndex).getQueueToString());

//		}
		
		//create a node only if the reader is not currently in the book's queue
//		books.get(bookIndex).setEnQueue(readerIndex);
//		mq.enQueue(node);	//add a node to the last
		
//		System.out.println(books.get(bookIndex).getQueueToString());
		
		
		
//		if(mq == null) {
//			node = new Node(readers.get(readerIndex));
//			mq.enQueue(node);	//add a node to the last
//			
//			System.out.println(mq);
//			
//			if(books.get(bookIndex).getReaderInQ().equals("none")) {
//				//set current reader ID into the book record.
//				//a book record only holds the first queue of the current reader ID
//				books.get(bookIndex).setReaderInQ(mq.getFirst().getID());	
//			}
//		}else {
//			if(mq.equalsCustom(rIDTobeInQ)) {
//				//the reader has been already in queue
//				System.out.println("The reader is already in the book's queue. Please wait for your turn");
//			}else {
//				node = new Node(readers.get(readerIndex));	//create a node
//				mq.enQueue(node);	//add a node to the last
//				
//				System.out.println(mq);
//				
//				if(books.get(bookIndex).getReaderInQ().equals("none")) {
//					//set current reader ID into the book record.
//					//a book record only holds the first queue of the current reader ID
//					books.get(bookIndex).setReaderInQ(mq.getFirst().getID());	
//				}
//			}
//		}
			
//		}
		

//		mq.findElementByPosition(position)

	}
}
