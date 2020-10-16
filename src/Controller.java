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
//	private MyQueue mq;
//	private Node node;
	
	public Controller() throws IOException {
		
		//the structure of reading txt-input followed and edited the code (in main method & factoryInterface class) 
		//from the sample solution of CA1 in the semester4. 
		FactoryInterface factory = new Factory();	//declare & init factory
	    
		//read input and store them
		String b = "Books.txt";
		books_in = new BufferedReader(new FileReader(b));
    	books = (List<Books>) factory.createBookDB(books_in);
		System.out.println(books);
		
		String r = "Readers.txt";
		readers_in = new BufferedReader(new FileReader(r));
		readers = (List<Readers>) factory.createReaderDB(readers_in);
		System.out.println(readers);
		
		String rt = "Rent.txt";
		rent_in = new BufferedReader(new FileReader(rt));
		rent = (List<Rent>) factory.createRentDB(rent_in);
		System.out.println(rent);

		//add some dummy queue into some of the books
		//this for-loop can be commented out if you want to start the program with no queue in books
		for(int i=0; i<books.size(); i++) {
			books.get(i).cloneReadersDB(readers);	
		}
		books.get(0).setEnQueue(0);
		books.get(0).setEnQueue(1);
		System.out.println(books.get(0).getQueueToString());
		books.get(2).setEnQueue(2);
		books.get(2).setEnQueue(3);
		books.get(2).setEnQueue(4);
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
			
			
			//add to check all the queue's belonged to a book
			//add numbering into queue record
		    
		  default:
		    menu();
		}
	}

	private void registerReturn() {
		String rtID = "", rID = "", bID;
		int rtIdToInt = 0, bIdToInt = 0;
		rtID = checkRentID(rtID, rID);
		
		if(rtID != null) {
			
			rtIdToInt = Integer.parseInt(rtID.substring(2))-1;
			
			
			rent.get(rtIdToInt).setRented(false);
			rent.get(rtIdToInt).setNormal(true);
			bID = rent.get(rtIdToInt).getTitleID();
			bIdToInt = Integer.parseInt(bID.substring(1))-1;
			books.get(bIdToInt).setRented(rent.get(rtIdToInt).isRented());
			books.get(bIdToInt).setAvailable(rent.get(rtIdToInt).isNormal());
			System.out.println("### Successfully returned###");
			System.out.println(rent);
			System.out.println(books);
			
			if(!books.get(bIdToInt).getQueueIsEmpty()) {
				System.out.println(IO.printUnderLine()
									+ "\n!!!!!!!!!!!!!!!!!! QUICK UPDATE !!!!!!!!!!!!!!!!"
									+ "\n[The book info]\n" + books.get(bIdToInt).toString()
									+ "\n\n[The first available reader in the book's queue for renting]\n" 
									+ books.get(bIdToInt).getFirstInQueueToString());
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
			
			//need to add something for waiting queue?
			if(bID.equals("Successfully added to the reader into the book's queue")) {
				//this line is printed only when a reader is added to queue
				System.out.println("\n###### Successfully added to the reader into the book's queue ######");
				
			}else {
				rID = checkReeaderID(rID);
				if(rID != null) {	//if !null, the reader exists
					int bIdToIndex = Integer.parseInt(bID.substring(1))-1;
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
						System.out.println("### A new rent has just been recorded successfully ###");
						System.out.println(rent);
						System.out.println(books);
						System.out.println(readers);
					}else {
						
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
							System.out.println("### A new rent has just been recorded successfully ###");
							System.out.println(rent);
							System.out.println(books);
							System.out.println(readers);
							
							books.get(bIdToIndex).setDeQueue();	//remove the 1st node from queue
							if(!books.get(bIdToIndex).getQueueIsEmpty()) {
								//if the book's queue is not empty, that means at least one node(reader ID) in queue
								//so here, set the first reader ID in queue to be the first reader in the book's waiting queue
								
								books.get(bIdToIndex).setReaderInQ(books.get(bIdToIndex).getFirstInQueue().getID());
								System.out.println(books.get(bIdToIndex).getReaderInQ());
							}else {
								//if the book's queue is empty, 
								//set "none" in the book's current reader-waiting-list state
								books.get(bIdToIndex).setReaderInQ("none");
								System.out.println(books.get(bIdToIndex).getReaderInQ());
							}
							
						}else {
							//the book's queue has the first node(a reader) that is not equals to this reader  
							//the queue should go in order
							System.out.println(IO.printUnderLine());
							System.out.println("The reader who is in the first queue to rent this book is as follows");
							System.out.println(books.get(bIdToIndex).getFirstInQueue());
							System.out.println(books.get(bIdToIndex).getQueueToString());
							System.out.println("Please wait for your turn");
						}
				
					}

				}else {
					System.out.println("the reader does not exist. Try again");
				}
			}
		}
//		else { 
//			System.out.println("The book ID not found or . Try again"); 
//		}
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
	public String checkRentID(String rtID, String rID) {
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
				    rID = IO.menu(IO.printReaderIDMenu(), "[a-zA-Z0-9]");
				    int rIdIndex = 0;
				    rIdIndex = Integer.parseInt(rID.substring(1))-1;
				    if((rent.get(rtIdToInt-1).isRented()) 
				  	  && (rent.get(rtIdToInt-1).getReaderID().equalsIgnoreCase(rID))
				  	  | (readers.get(rIdIndex).getCurrentRent().equalsIgnoreCase(rID)) ) {
				      
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
					
				    }else {
					    System.out.println("The rent Id exists but the reader ID seems not to be in the record."
					    		+ "\nOr the book in the rent record has already been returned. ");
					    return null;
				    } 
				}
				
				if(yn.equalsIgnoreCase("q")) {
					System.out.println("Back to main menu....");
				}
				
			}else {
				System.out.println("The rent ID not found. Try again");
				return null;
			}
			  
		 }
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
					
					yn = IO.menu(IO.printWaitingQueueMenu(), "[a-zA-Z0-9]");	  
					if(!(yn.equalsIgnoreCase("y"))) {
						System.out.println("Back to main menu....");
						return null;
					}
					
					String rID = "";
					int rIdToIndex = 0;
					
					if((rID = checkReeaderID(rID)) != null) {
						
						rIdToIndex = Integer.parseInt(rID.substring(1))-1;
						if(readers.get(rIdToIndex).getCurrentRent().equalsIgnoreCase(bID)) {
							
							//the reader is who has been renting this book at the moment
							System.out.println(IO.printUnderLine());
							System.out.println("The book is being rented by the reader "
									+ "who is waiting for queue at the moment.\n");
							System.out.println(readers.get(rIdToIndex));
							System.out.println("Please return the book first");
							return null;
						}
						
						if(!books.get(i).getQueueIsEmpty() 
								&& !books.get(i).retrieveEqualsInQueue(rID)) {
							//there's no the existing same reader info(ID) in the book's queue
							waitingListManager(rID, bID);
							return "Successfully added to the reader into the book's queue";
						}
						
						//if not meeting the two conditions just above, the reader is in the book's queue 
						System.out.println("The reader is already in the book's queue");
						return null;

					}else {
						System.out.println("The reader ID does not exist in Readers db. Try again");
						return null;
					}
								
				}
			}

		}
		System.out.println("The book ID not found. Try again");
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
	private void waitingListManager(String rIDTobeInQ, String bIDPassed) {

		String first = ""; 
		int readerIndex = Integer.parseInt(rIDTobeInQ.substring(1))-1;
		int bookIndex = Integer.parseInt(bIDPassed.substring(1))-1;

		books.get(bookIndex).setEnQueue(readerIndex);
		System.out.println(books.get(bookIndex).getQueueToString());

		
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
