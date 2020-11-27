package ie.cct._2018316.dev;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ie.cct._2018316.cunstructs.IO;

public class Search {

	private List<Books> b;
	private List<Readers> r;

	//method for printing an array and that is used for testing some of the result
	public void printArray(String[] arr) {
		
		String toPrint = "[ ";
		for (int i = 0; i < arr.length; i++) {
			toPrint += arr[i] + ", ";
			
		}
		toPrint += "]";
		System.out.println(toPrint);
		
	}
		
	/** [In USE]
	 * method to search for a book or a reader with exact field values entered by user
	 * 
	 * Even though the type of the parameter t is surely determined at human-understandable-level when this method is called,
	 * JAVA throws a type safety warning.
	 * (It makes sense since no sophisticated type check is performed inside this method)
	 * Thus @SuppressWarnings("unchecked") is added on the top of this method signature
	 * 
	 * @param List<T> t
	 * @param keyword
	 * @return index number found
	 */
	@SuppressWarnings("unchecked")
	public <T> int linearSerch(List<T> t, String keyword) {

		int curLocation = 0;
		boolean isFound = false;

		if (t.get(0).getClass().getSimpleName().equals("Books")) {

			this.b = (List<Books>) t;

			if (keyword.startsWith("+")) { // keyword is title + author
				String titileAndAutor[] = null, title = "", author = "";
				titileAndAutor = keyword.substring(1).split("&");
				title = titileAndAutor[0];
				author = titileAndAutor[1];

				// execute linear search
				while (!(isFound = findBooksByTitleAndAuthor(b, curLocation, title, author))
						&& (curLocation < b.size() - 1)) {

					curLocation++; // move to next box
				}

			}
		}

		if (t.get(0).getClass().getSimpleName().equals("Readers")) {

			this.r = (List<Readers>) t;

			if (keyword.startsWith("+")) {
				String temp[] = null, id = "", f = "", l = "";
				temp = keyword.substring(1).split("&");
				id = temp[0];
				f = temp[1];
				l = temp[2];

				// execute linear search
				while (!(isFound = findReaderByIdAndFnameAndLname(r, curLocation, id, f, l))
						&& (curLocation < r.size() - 1)) {

					curLocation++; // move to next box
				}

			}
		}

		if (isFound == false) {
			return -1;
		}

		return curLocation; // return one specific result

	}

//	/**
//	 * method to search for a book with exact field values(title and author) entered by user
//	 * @param List<Books> b
//	 * @param keyword
//	 * @return index number found
//	 */
//	public int linearSerchForBook(List<Books> b, String keyword) {
//
//		int curLocation = 0;
//		boolean isFound = false;
//
//		if (keyword.startsWith("+")) { // keyword is title + author
//			String titileAndAutor[] = null, title = "", author = "";
//			titileAndAutor = keyword.substring(1).split("&");
//			title = titileAndAutor[0];
//			author = titileAndAutor[1];
//
//			// execute linear search
//			while (!(isFound = findBooksByTitleAndAuthor(b, curLocation, title, author))
//					&& (curLocation < b.size() - 1)) {
//
//				curLocation++; // move to next box
//			}
//
//			if (isFound == false) {
//				return -1;
//			}
//		}
//
//		return curLocation; // return one specific result
//
//	}
//
//	/**
//	 * method to search for a reader with exact field values(reader Id, first name and last name) entered by user
//	 * @param List<Readers> r
//	 * @param keyword
//	 * @return index number found
//	 */
//	public int linearSerchForReader(List<Readers> r, String keyword) {
//
//		int curLocation = 0;
//		boolean isFound = false;
//
//		// keyword is title + author
//		if (keyword.startsWith("+")) {
//			String temp[] = null, id = "", f = "", l = "";
//			temp = keyword.substring(1).split("&");
//			id = temp[0];
//			f = temp[1];
//			l = temp[2];
//
//			// execute linear search
//			while (!(isFound = findReaderByIdAndFnameAndLname(r, curLocation, id, f, l))
//					&& (curLocation < r.size() - 1)) {
//
//				curLocation++; // move to next box
//			}
//
//			if (isFound == false) {
//				return -1;
//			}
//
//		}
//
//		return curLocation;
//	}

	/**
	 * method to compare strings with another strings.
	 * 
	 * @param r
	 * @param curLocation
	 * @param id
	 * @param f
	 * @param l
	 * @return true if readerId, first name and last name in a specific index of
	 *         List<Readers> r are equal to String id, String f and String l
	 *         ignoring case
	 */
	public boolean findReaderByIdAndFnameAndLname(List<Readers> r, int curLocation, String id, String f, String l) {

		if ((r.get(curLocation).getId().equalsIgnoreCase(id)) && (r.get(curLocation).getFname().equalsIgnoreCase(f))
				&& (r.get(curLocation).getLname().equalsIgnoreCase(l))) {

			// return the exact match
			System.out.println(IO.printUnderLine());
			System.out.println("[The exact match]");
			return true;
		}
		return false;
	}

	/**
	 * method to compare strings with another strings.
	 * 
	 * @param b
	 * @param curLocation
	 * @param title
	 * @param author
	 * @return true if title and author in a specific index of List<Books> b are
	 *         equal to String title and String author ignoring case
	 */
	public boolean findBooksByTitleAndAuthor(List<Books> b, int curLocation, String title, String author) {

		if ((b.get(curLocation).getTitle().equalsIgnoreCase(title))
				&& (b.get(curLocation).getAuthor().equalsIgnoreCase(author))) {

			// return the exact match
			System.out.println(IO.printUnderLine());
			System.out.println("[The exact match]");
			return true;
		}
		return false;
	}

	/**
	 * method to create a temporary reader list sorted in alphabetical order among reader ID, first name and last name,
	 * using @param arr.
	 * This will be used for binary search
	 * 
	 * @param arr that holds reader Id, first name and last name in alphabetical order
	 * @param r that is a ref of the original Reader list
	 * @return a temporary reader list sorted in alphabetical order among reader ID, first name and last name
	 */
	public List<Readers> createTempReadersList(String[] arr, List<Readers> r) {
		
		List<Readers> temp = new ArrayList<>(); // create a temp list
		
		// create a temporary Readers list
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < r.size(); j++) {

				if (arr[i].equalsIgnoreCase(r.get(j).getId()) || arr[i].equalsIgnoreCase(r.get(j).getFname())
						|| arr[i].equalsIgnoreCase(r.get(j).getLname())) {
					
					temp.add(new Readers(r.get(j), i));	// now, add reader obj to temp Reader list
				}

			}
		}

		for (int i = 0; i < temp.size(); i++) {
			/** 
			 * The list 'temp' now has OBJs basing its order on the values of indexes in String[] arr
			 * Set a temporary name tag that identifies an OBJ itself,
			 * so that it can be used when executing a binary search */
			temp.get(i).setNameTag(arr[i]);

			/* if necessary, uncomment the code below out to check what's set by the code above */
			//System.out.print(temp.get(i).sortedAllInAcendingToString());
		}
		return temp;
	}
	
	/**
	 * method to create a temporary reader list sorted in alphabetical order among reader ID, first name and last name,
	 * using @param arr.
	 * This will be used for binary search
	 * 
	 * @param arr that holds reader Id, first name and last name in alphabetical order
	 * @param r that is a ref of the original Reader list
	 * @param boolean isForAdvancedSearch that determines what values to be added into List<Books> temp
	 * 
	 * @return a temporary reader list sorted in alphabetical order among book ID, title and author if !isForAdvancedSearch
	 * @return a temporary reader list sorted in alphabetical order among book ID, title split up and author split up if isForAdvancedSearch
	 */
	public List<Books> createTempBooksList(String[] arr, List<Books> b, boolean isForAdvancedSearch) {
		
		List<Books> temp = new ArrayList<>(); // create a temp list
		
		if(!isForAdvancedSearch) {	
			// create a temporary Readers list
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < b.size(); j++) {

					if (arr[i].equals(b.get(j).getId()) || arr[i].equals(b.get(j).getTitle())
							|| arr[i].equals(b.get(j).getAuthor())) {
						
						temp.add(new Books(b.get(j), i));	// now, add reader obj to temp Books list
					}
				}
			}			
		}else {	//isForAdvancedSearch == true

			// create a temporary Readers list
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < b.size(); j++) {
					if (arr[i].equals(b.get(j).getId())) {
						// now, add reader obj to temp Books list
						temp.add(new Books(b.get(j), i));
					}	
					
					for (int k = 0; k < b.get(j).getAggregateTitleAndAuthorSplit().length; k++) {
						if (arr[i].equals(b.get(j).getAggregateTitleAndAuthorSplit()[k])) {
							// now, add reader obj to temp Books list
							temp.add(new Books(b.get(j), i));
						}	
					}
				}
			}
		}
		
		//System.out.println("temp list size: " + temp.size());		
		for (int i = 0; i < temp.size(); i++) {
			/** 
			 * The list 'temp' now has OBJs basing its order on the values of indexes in String[] arr
			 * Set a temporary name tag that identifies an OBJ itself,
			 * so that it can be used when executing a binary search */
			temp.get(i).setNameTag(arr[i]);

			/* if necessary, uncomment the code below out to check what's set by the code above */
			//System.out.print(temp.get(i).sortedAllInAcendingToString());
		}
		
		return temp;
	}
	
	/**
	 * method to execute a binary search for a book with a recursive approach
	 * This custom binary search implementation references/has modified/has improved the search algorithm course contents
	 * that was lectured by Professor Amilcar   
	 * 
	 * @param List<Books> temp that is ref of the temporary Books list sorted in alphabetical order based on fields among book ID, title and author
	 * @param String key that user enters as a string on a line
	 * @param String[] keys that stores the user's keyword split up by one word unit
	 * @param int keyIndex that indicates an index of keys
	 * @param int left
	 * @param int right
	 * @return index number found from temp
	 */
	 public /* static */ int binarySearch(List<Books> temp, String key, String[] keys, int keyIndex, int left, int right) {

		boolean isFound = false;
		int mid;
		int indexNum = 0;

		if (left > right) {		
			if (!isFound && keys == null) {
				return -1;	//an exit point; nothing is found with the String key
			}
			
			if (!isFound && keys != null && keyIndex < keys.length-1) {
				//recur the binary search method focusing on String[] keys. Change(increment) the pointer(int keyIndex)
				return binarySearch(temp, null, keys, keyIndex+1, 0, temp.size()-1);
				
			}else {
				return -1;	//an exit point; nothing is found with the String[] keys	
			}
		}else {
	
			if(keys == null) {	//String key-based process of binary search

				mid = 0;
				mid = (left + right) / 2; // set the middle index

				/* if necessary, uncommented the code below out to check the mid index at run time */
				//System.out.println("\ntemp.get(" + mid + ").getTempNameTag(): " + temp.get(mid).getNameTag());

				if ((key.compareToIgnoreCase(temp.get(mid).getNameTag()) == 0)) {

					isFound = true; // found. .compareToIgnoreCase() returns 0

					// take an actual index from the original Readers list
					indexNum = Integer.parseInt(temp.get(mid).getId().substring(1)) - 1;
					System.out.println(IO.printUnderLine());
					System.out.println("[The exact match]");
					System.out.println(key + " is found in the book index at " + indexNum);	// return the exact match

				} else {	

					if ((key.compareToIgnoreCase(temp.get(mid).getNameTag()) < 0)) {

						/* if necessary, uncomment the code below out to check the value that .compareToIgnoreCase() returns at run time */
						//System.out.println("key's value: " + key.compareToIgnoreCase(temp.get(mid).getNameTag()));

						return binarySearch(temp, key, null, keyIndex, left, mid-1);	//recur left of the middle 

					} else { 

						/* if necessary, uncomment the code below out to check the value that .compareToIgnoreCase() returns at run time */
						//System.out.println("key's value: " + key.compareToIgnoreCase(temp.get(mid).getNameTag()));
						
						return binarySearch(temp, key, null, keyIndex, mid+1, right);	//recur right of the middle  
					}
				}
				
			}else {	//String[] keys-based process of binary search
				
				mid = 0;
				mid = (left + right) / 2; // set the middle index

				/* if necessary, uncommented the code below out to check the mid index at run time */
				//System.out.println("\ntemp.get(" + mid + ").getTempNameTag(): " + temp.get(mid).getNameTag());

				if ((keys[keyIndex].compareToIgnoreCase(temp.get(mid).getNameTag()) == 0)) {

					isFound = true; // found. .compareToIgnoreCase() returns 0

					// take an actual index from the original Readers list
					indexNum = Integer.parseInt(temp.get(mid).getId().substring(1)) - 1;

					if( (temp.get(mid).getNameTag().equals(temp.get(mid).getId()))  
							|| (temp.get(mid).getNameTag().equals(temp.get(mid).getTitle()))
							|| (temp.get(mid).getNameTag().equals(temp.get(mid).getAuthor()))) {
						System.out.println(IO.printUnderLine());
						System.out.println("[The exact match]");	
						System.out.println(keys[keyIndex] + " is found in the book index at " + indexNum);
						
					}else {
						System.out.println(IO.printUnderLine());
						System.out.println("[Partial match]");	
						System.out.println(keys[keyIndex] + " is found in the book index at " + indexNum);
						System.out.println("\n\n* If you want have an exact match returned, please improve your search keyword"
								+ "\n* by checking the following field info of the book");
					}	

				} else {

					if ((keys[keyIndex].compareToIgnoreCase(temp.get(mid).getNameTag()) < 0)) {

						/* if necessary, uncomment the code below out to check the value that .compareToIgnoreCase() returns at run time */
						//System.out.println("key's value: " + keys[keyIndex].compareToIgnoreCase(temp.get(mid).getNameTag()));

						return binarySearch(temp, null, keys, keyIndex, left, mid-1);	//recur left of the middle 

					} else {

						/* if necessary, uncomment the code below out to check the value that .compareToIgnoreCase() returns at run time */
						//System.out.println("key's value: " + keys[keyIndex].compareToIgnoreCase(temp.get(mid).getNameTag()));

						return binarySearch(temp, null, keys, keyIndex, mid+1, right);	//recur right of the middle  
					}
					
				}
				
			}

		}

		return indexNum; //a successful exit point
	}
	
	/**
	 * method to execute a binary search with a WHILE-loop + a FOR-loop approach.
	 * it's in a simpler form than the binary search method for books.
	 * (The values of the fields of each of Reader OBJs, which are readerId, first name and last name, initially allows only one word format) 
	 * 
	 * This custom binary search implementation references/has modified/has improved the search algorithm course contents
	 * that was lectured by Professor Amilcar   
	 * 
	 * @param temp that is ref of the temporary Reader list sorted in alphabetical order based on fields among reader ID, first name and last name
	 * @param key that user enters as a string on a line
	 * @return index number found from temp
	 */
	public /* static */ int binarySearch(List<Readers> temp, String key) {

		boolean isFound = false;
		int mid, left, right;
		int indexNum = 0;

		String[] keySplit = null;
		keySplit = key.trim().split(" ");	// split up String key
		
		for (int i = 0; i < keySplit.length; i++) {

			mid = 0;
			left = 0;
			indexNum = 0;
			right = temp.size() - 1;

			while (right >= left) {
				
				mid = (right + left) / 2; // set the middle index

				/* if necessary, uncommented the code below out to check the mid index at run time */
				//System.out.println("\ntemp.get(" + mid + ").getTempNameTag(): " + temp.get(mid).getNameTag());
				
				if ((keySplit[i].compareToIgnoreCase(temp.get(mid).getNameTag()) == 0)) {

					isFound = true; // found. .compareToIgnoreCase() returns 0

					// take an actual index from the original Readers list
					indexNum = Integer.parseInt(temp.get(mid).getId().substring(1)) - 1;

					// return the exact match
					System.out.println(IO.printUnderLine());
					System.out.println("[The exact match]");
					System.out.println(keySplit[i] + " is found in the reader index at " + indexNum);
					break;

				} else {

					if ((keySplit[i].compareToIgnoreCase(temp.get(mid).getNameTag()) < 0)) {

						/* if necessary, uncomment the code below out to check the value that .compareToIgnoreCase() returns at run time */
						//System.out.println("key's value: " + keySplit[i].compareToIgnoreCase(temp.get(mid).getNameTag()));

						right = mid - 1; // right = mid -1 so that the mid index can move out to left side

					} else {
						
						/* if necessary, uncomment the code below out to check the value that .compareToIgnoreCase() returns at run time */
						//System.out.println("key's value: " + keySplit[i].compareToIgnoreCase(temp.get(mid).getNameTag()));

						left = mid + 1; // left = mid + 1 so that the mid index can move out to right side
					}
				}

			} // end of while-loop

			if (isFound == true) {
				break;	// terminate for-loop
			}

		} // end of for-loop

		if (isFound == false) {
			return -1;
		}

		return indexNum; // return indexNum
	}

}
