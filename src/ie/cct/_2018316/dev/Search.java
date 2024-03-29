package ie.cct._2018316.dev;

import java.util.ArrayList;
import java.util.List;

import ie.cct._2018316.cunstructs.IO;

/**
 * Class that searches for a book/reader
 * 
 * The custom linear search and binary search are implemented in the following methods
 * 
 * public <T> int linearSerch(List<T> t, String keyword) 
 * public int binarySearch(List<Books> temp, String key, String[] keys, int keyIndex, int left, int right)
 * public int binarySearch(List<Readers> temp, String key)
 * 
 * They reference/have modified/have improved the search algorithm course contents that were lectured by Professor Amilcar
 *     
 * @author Kyu
 *
 */
public class Search {

	private List<Books> b;
	private List<Readers> r;

	//testing purpose.
	//method for printing an array and that is used for testing some of the result
	public void printArray(String[] arr) {
		
		String toReturn = "[ ";
		for (int i = 0; i < arr.length; i++) {
			toReturn += arr[i] + ", ";
			
		}
		toReturn += "]";
		System.out.println(toReturn);
		
	}
		
	/** 
	 * method to search for a book or a reader with exact field values entered by user.
	 * 
	 * [Note]
	 * Even though the type of the parameter t is surely determined (at human-understandable-level) when this method is called,
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
				while (!(isFound = findBooksByTitleAndAuthor(this.b, curLocation, title, author))
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
				while (!(isFound = findReaderByIdAndFnameAndLname(this.r, curLocation, id, f, l))
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
		
		//if necessary, uncomment the code below out to check the sorted values in String[]arr
		/***************************************************  //ctrl + /
		for (int i = 0; i < arr.length; i++) {
			System.out.println("arr[" + i + "]: " + arr[i]);
		} 
		****************************************************/	//ctrl + /
		
		// create a temporary Readers list
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < r.size(); j++) {

				if (arr[i].equals(r.get(j).getId())) {
					
					// now, add obj to temp Readers list
					temp.add(new Readers(r.get(j), i, arr[i]));
					j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
					break;	//exit from nested FOR-LOOP at current index
				}	
				
				if (arr[i].equals(r.get(j).getFname())) {
					
					if(temp.size() == 0) {
						
						// now, add obj to temp Readers list
						temp.add(new Readers(r.get(j), i, arr[i]));
						j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
						break;	//exit from nested FOR-LOOP at current index
						
					}else {
						
						if(filterDuplicateReaderEntry(r.get(j).getId(), r.get(j).getFname(), temp)) {
							// now, add obj to temp Readers list
							temp.add(new Readers(r.get(j), i, arr[i]));
							j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}
						
					}
					
				}
				
				if (arr[i].equals(r.get(j).getLname())) {
					
					if(temp.size() == 0) {
						
						// now, add obj to temp Readers list
						temp.add(new Readers(r.get(j), i, arr[i]));
						j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
						break;	//exit from nested FOR-LOOP at current index
						
					}else {
						
						if(filterDuplicateReaderEntry(r.get(j).getId(), r.get(j).getLname(), temp)) {
							// now, add obj to temp Readers list
							temp.add(new Readers(r.get(j), i, arr[i]));
							j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}
						
					}
					
				}
				
			}
		}
		
		//if necessary, uncomment the code below out 
		//to check the temp list created and sorted based on the values in String[]arr
		/***************************************************************************************	//ctrl + /
		System.out.println("arr.length: " + arr.length);
		System.out.println("temp.size(): " + temp.size());
		for (int i = 0; i < temp.size(); i++) {
			//if result is big, it may not print out all.
			System.out.print("In debugging.. temp index[" + i + "]: " + temp.get(i).tempIdentifierToString() + "\n");
		}
		****************************************************************************************/	//ctrl + /
	
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
		
		//if necessary, uncomment the code below out to check the sorted values in String[]arr
		/***************************************************  //ctrl + /
		for (int i = 0; i < arr.length; i++) {
			System.out.println("arr[" + i + "]: " + arr[i]);
		} 
		****************************************************/	//ctrl + /
		
		if(!isForAdvancedSearch) {	
			// create a temporary Books list
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < b.size(); j++) {

					if (arr[i].equals(b.get(j).getId())) {
						
						// now, add obj to temp Books list
						temp.add(new Books(b.get(j), i, arr[i]));
						j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
						break;	//exit from nested FOR-LOOP at current index
					}	
					
					if (arr[i].equals(b.get(j).getTitle())) {
						
						if(temp.size() == 0) {
							
							// now, add obj to temp Books list
							temp.add(new Books(b.get(j), i, arr[i]));
							j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateBookEntry(b.get(j).getId(), b.get(j).getTitle(), temp)) {
								// now, add obj to temp Books list
								temp.add(new Books(b.get(j), i, arr[i]));
								j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
								
							}
							
						}
						
					}	
					
					if (arr[i].equals(b.get(j).getAuthor())) {
						
						if(temp.size() == 0) {
							
							// now, add obj to temp Books list
							temp.add(new Books(b.get(j), i, arr[i]));
							j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateBookEntry(b.get(j).getId(), b.get(j).getAuthor(), temp)) {
								// now, add obj to temp Books list
								temp.add(new Books(b.get(j), i, arr[i]));
								j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
								
							}
							
						}
						
					}
				
				}
			}			
		}else {	//isForAdvancedSearch == true

			// create a temporary Books list
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < b.size(); j++) {
					if (arr[i].equals(b.get(j).getId())) {
						
						// now, add obj to temp Books list
						temp.add(new Books(b.get(j), i, arr[i]));
						j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
						break;	//exit from nested FOR-LOOP at current index
					}	
					
					for (int k = 0; k < b.get(j).getAggregateTitleAndAuthorSplit().length; k++) {
					
						if (arr[i].equals(b.get(j).getAggregateTitleAndAuthorSplit()[k])) {
							
							// now, add reader obj to temp Books list
							if(temp.size() == 0) {
								
								temp.add(new Books(b.get(j), i, arr[i]));
								
								//point k to be at the end of inner nested FOR-LOOP
								k = b.get(j).getAggregateTitleAndAuthorSplit().length-1;	
								j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
								
							}else {
							
								if(filterDuplicateBookEntry(b.get(j).getId(), b.get(j).getAggregateTitleAndAuthorSplit()[k], temp)) {
									
									//non-duplicate entry
									temp.add(new Books(b.get(j), i, arr[i]));
									
									//point k to be at the end of inner nested FOR-LOOP
									k = b.get(j).getAggregateTitleAndAuthorSplit().length-1;
									j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
									
								}else {

									//nested FOR-LOOP is not looked up
									if(j < b.size()-1) {
										break;	//exit from inner nested FOR-LOOP at current index
									}
								}
							}
							
						}
						
					}
					
				}
			}
		}
		
		//if necessary, uncomment the code below out 
		//to check the temp list created and sorted based on the values in String[]arr
		/***************************************************************************************	//ctrl + /
		System.out.println("arr.length: " + arr.length);
		System.out.println("temp.size(): " + temp.size());
		for (int i = 0; i < temp.size(); i++) {
			//if result is big, it may not print out all.
			System.out.print("In debugging.. temp index[" + i + "]: " + temp.get(i).tempIdentifierToString() + "\n");
		}
		****************************************************************************************/	//ctrl + /
		
		return temp;
	}
	
	/**
	 *  method to filter duplicate entry to the temporary Readers list
	 * @param rID
	 * @param tempKey
	 * @param temp
	 * @return true if not duplicate entry
	 */
	public boolean filterDuplicateReaderEntry(String rID, String tempKey, List<Readers> temp) {
		
		/* Once temp list has its OBJ based on a sorted input but cloned from the original Reader list,
		 * each of OBJs in the temp list has an unique ID(bookID) and an unique key ID(a specific sorted value) 
		 * 
		 * By regarding those two as a compound key,
		 * a duplicate entry to the temp list can be filtered
		 */
		
		for (int i = 0; i < temp.size(); i++) {

			if(temp.get(i).getId().equals(rID) && temp.get(i).getNameTag().equals(tempKey)) {
				
				return false;		
			}
		}
		
		return true;
	}
	
	/**
	 * method to filter duplicate entry to the temporary Books list
	 * @param bID
	 * @param tempKey
	 * @param temp
	 * @return true if not duplicate entry
	 */
	public boolean filterDuplicateBookEntry(String bID, String tempKey, List<Books> temp) {
		
		/* Once temp list has its OBJ based on a sorted input but cloned from the original Books list,
		 * each of OBJs in the temp list has an unique ID(bookID) and an unique key ID(a specific sorted value) 
		 * 
		 * By regarding those two as a compound key,
		 * a duplicate entry to the temp list can be filtered
		 */
		
		for (int i = 0; i < temp.size(); i++) {

			if(temp.get(i).getId().equals(bID) && temp.get(i).getNameTag().equals(tempKey)) {
				
				return false;		
			}
		}
		
		return true;
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
						//System.out.println("\ntemp.get(" + mid + ").getTempNameTag(): " + temp.get(mid).getNameTag());
						//System.out.println("temp.get(" + mid + ").getTempIdentifier(): " + temp.get(mid).getTempIdentifier());
						
						System.out.println(IO.printUnderLine());
						System.out.println("[Partial match]");	
						System.out.println(keys[keyIndex] + " is found in the book index at " + indexNum);
						System.out.println("\n\n* If you want to have an exact match returned, please improve your search keyword."
								+ "\n\n* If book DB is huge, a wrong or some different result might sometimes be returned."
								+ "\n  In that case, please re-run the program and try it again. "
								+ "\n  Otherwise, it's safe to search for a book by ID or use the search option \"2\" within the book search menu.");
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
					System.out.println("[Exact match]");
					System.out.println(keySplit[i] + " is found in the reader index at " + indexNum);
					System.out.println("\n* If result isn't what you look for, please improve search keyword."
							+ "\n\n* Searching for a reader by ID gives the best result by default. "
							+ "\n  Otherwise, use the search option \"2\" within the reader search menu.  ");
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
