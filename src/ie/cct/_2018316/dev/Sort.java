package ie.cct._2018316.dev;

import java.util.ArrayList;
import java.util.List;

import ie.cct._2018316.cunstructs.IO;

/**
 * Class for sorting books/readers
 *  
 * The custom merge sort and bubble sort are implemented in the following methods
 * 
 * public String[] mergeSort(String[] arr)
 * public String[] bubbleSort(String[] arr)
 * 
 * They reference/have modified/have improved the search algorithm course contents that were lectured by Professor Amilcar
 * 
 * @author Kyu
 *
 */
public class Sort {
	
	private List<Books> b;
	private List<Readers> r;
	
	public Sort() {}
	
	//method for printing an array and that is used for testing some of the sorting result
	public void printArray(String[] arr) {
		
		String toReturn = "[ ";
		for (int i = 0; i < arr.length; i++) {
			toReturn += arr[i] + ", ";		
		}
		toReturn += "]";
		System.out.println(toReturn);
		
	}
	
	//method for printing an array and that is used for testing some of the sorting result
	public void printArrayInColumn(String[] arr) {
		
		for (int i = 0; i < arr.length; i++) {
			System.out.println(i + "\t" + arr[i]);			
		}	
	}
	

	/**
	 * method to swap temp-assigned values at specific indexes with its original alphanumeric IDs
	 * On calling this method, arr is sorted.
	 * Just put the original value back to itself.
	 * Now, String[] arr is ready to interact with .compareTo() method
	 * 
	 * @param arr
	 * @return arr
	 */
	public String[] removeTempPrefixAndSuffix(String[] arr) {
		
		for (int i = 0; i < arr.length; i++) {
		
			if(arr[i].startsWith("BA") && arr[i].endsWith("!")) {
				
				arr[i] = arr[i].substring(2, arr[i].length()-1);
				arr[i] = "B" + arr[i]; 
			}
			
			if(arr[i].startsWith("BB") && arr[i].endsWith("!")) {
				
				arr[i] = arr[i].substring(2, arr[i].length()-1);
				arr[i] = "B" + arr[i];
			}
			
			if(arr[i].startsWith("RA") && arr[i].endsWith("!")) {
				
				arr[i] = arr[i].substring(2, arr[i].length()-1);
				arr[i] = "R" + arr[i]; 
			}
			
			if(arr[i].startsWith("RB") && arr[i].endsWith("!")) {
				
				arr[i] = arr[i].substring(2, arr[i].length()-1);
				arr[i] = "R" + arr[i];
			}
		}
		return arr;
	}
	
	
	
	/**
	 * method to return String[] arr with a temporary update before executing a sort/search.
	 * main function is to keep looking for a specific index that has alphanumeric ID.
	 * 
	 * On finding it, set a temporary UNICODE/ASCII based value to it,
	 * in order for .compareTo() method to return correct result when it's used for a sort or a search. 
	 * 
	 * [e.g.]
	 * When calling .compareTo() method during searching and sorting in particular,
	 * 'B100' will be returned before 'B99' since 'B100' lower value than 'B99' based on UNICODE/ASCII values, 
	 * 'R100' will be returned before 'R99' since 'R100' lower value than 'R99' based on UNICODE/ASCII values, 
	 * which will give an unexpected result at a human-understandable level.  
	 *  
	 * Thus, add an appropriate prefix and suffix in temporary.
	 * 
	 * @param <T> a referenced list  
	 * @param arr
	 * @param b
	 * @param isHundreadUnit
	 * @return arr
	 */
	@SuppressWarnings("unchecked")
	public <T> String[] setTempPrefixAndSuffix(String[] arr, List<T> t, boolean isHundreadUnit) {
		
		if (t.get(0).getClass().getSimpleName().equals("Books")) {
			
			this.b = (List<Books>) t;
			
			if(isHundreadUnit) {
				
				for (int i = 99; i < this.b.size(); i++) {
					
					for (int j = 0; j < arr.length; j++) {
						
						if(this.b.get(i).getId().equals(arr[j])) {
							
							if(i < 999) {
								
								arr[j] = "BA" + arr[j].substring(1) + "!";	//e.g. BA100!, BA101!...
							}
						}
					}
				}
				
			}else { //Thousands
				
				for (int i = 999; i < this.b.size(); i++) {
					
					for (int j = 0; j < arr.length; j++) {
						
						
						if(this.b.get(i).getId().equals(arr[j])) {
							
							arr[j] = "BB" + arr[j].substring(1) + "!";	//e.g. BB1000!, BB1001!...
						}
						
					}
					
				}
			}
		
		}
		
		if (t.get(0).getClass().getSimpleName().equals("Readers")) {
			
			this.r = (List<Readers>) t;
			
			if(isHundreadUnit) {
				
				for (int i = 99; i < this.r.size(); i++) {
					
					for (int j = 0; j < arr.length; j++) {
						
						if(this.r.get(i).getId().equals(arr[j])) {
							
							if(i < 999) {
								arr[j] = "RA" + arr[j].substring(1) + "!";	//e.g. RA100!, RA101!...
							}

						}
						
					}
					
				}
			}else { //Thousands
				
				for (int i = 999; i < this.r.size(); i++) {
					
					for (int j = 0; j < arr.length; j++) {
						
						
						if(this.r.get(i).getId().equals(arr[j])) {
							
							arr[j] = "RB" + arr[j].substring(1) + "!";	//e.g. RB1000!, RB1001!...
						}
						
					}
					
				}
			}
			
		}
		

		return arr;
	}
		
	/**
	 * method to perform a merge sort 
	 * 
	 * @param arr that has been populated with string values
	 */
	public String[] mergeSort(String[] arr) {
		
		//divide should keep going only if arr.length > 1 
		if(arr.length > 1) {
			
			//divide; first half 
			int fHalfSize = arr.length / 2;
			String[] fHalf = new String[fHalfSize];
			
			for(int i=0; i<fHalf.length; i++) {
				fHalf[i] = arr[i];
			}
			
			/* if necessary, uncomment the code below out to check the values of String[] fHalf at run time */
			//System.out.println("\n+++++++ first half ++++++ ");
			//printArray(fHalf);
			
			mergeSort(fHalf);
			
			//divide; second half
			int sHalfSize = arr.length - fHalfSize;
			String[] sHalf = new String[sHalfSize];
			
			for(int i=0; i<sHalf.length; i++) {
				sHalf[i] = arr[fHalf.length + i];
			}
			
			/* if necessary, uncomment the code below out to check the values of String[] sHalf at run time */
			//System.out.println("\n+++++++ second half ++++++ ");
			//printArray(sHalf);
			
			mergeSort(sHalf);
			
			//conquer 
			merge(fHalf, sHalf, arr);
			
		}

		return arr;
	}
	
	/**
	 * method to perform "conquer"
	 * This custom merge implementation references/has modified/has improved the sort algorithm course content
	 * that was lectured by Professor Amilcar   
	 * 
	 * @param s1 == sequence1; the array fHalf passed from the method mergeSort()
	 * @param s2 == sequence2; the array sHalf passed from the method mergeSort()
	 * @param s  == sequence; the array arr(the input array to be sorted) passed from the method mergeSort()
	 */
	public void merge(String[] s1, String[] s2, String[] s) {
		
		int count_s1 = 0;
		int count_s2 = 0;
		int count_s = 0;
		
		//keep merging until either s1 or s2 is empty
		while(count_s1 < s1.length && count_s2 < s2.length) {
			
			if(s1[count_s1].compareTo(s2[count_s2]) < 0) {
				//the string value in s1's index pointed < the string value in s2's index pointed
				s[count_s] = s1[count_s1];	//add the string value in s1's index pointed into the sequence array s
				
				//System.out.println(s[count_s]);
				
				count_s1++;	//move the pointer == remove the string value in s1's index pointed 			
			}
			else {
				//the string value in s2's index pointed < the string value in s1's index pointed
				s[count_s] = s2[count_s2];	//add the string value in s2's index pointed into the sequence array s
				
				//System.out.println(s[count_s]);
				
				count_s2++;	//move the pointer == remove the string value in s2's index pointed
			}
			
			//the sequence array s at the index pointed now has something in it throughout the if-else statement above. 
			count_s++;	//move the pointer == look at the next index of the sequence array
		}
		
		//keep adding the elements that have been left or that have not been moved in s1 into the sequence array s
		while (count_s1 < s1.length) {
			
			s[count_s] = s1[count_s1];	//add the string value in s1's index pointed into the sequence array
			
			//System.out.println(s[count_s]);
			
			count_s1++;	//move the pointer == remove the string value in s1's index pointed 
			count_s++;
		}
		
		//keep adding the elements that have been left or that have not been moved in s2 into the sequence array s
		while (count_s2 < s2.length) {
			
			s[count_s] = s2[count_s2];	//add the string value in s2's index pointed into the sequence array 
			
			//System.out.println(s[count_s]);
			
			count_s2++;	//move the pointer == remove the string value in s2's index pointed
			count_s++;	//move the pointer == look at the next index of the sequence array
		}
		
		
	}
	
	/**
	 * method to sort the values in the array arr
	 * .compareTo() used since no user input is involved for this bubble sort.
	 * 
	 * @param arr
	 * @return sorted arr in ascending(alphabetical) order
	 */
	public String[] bubbleSort(String[] arr) {
		
		String temp = ""; // temp string
		
		for (int i = 0; i < arr.length - 1; i++) { // stop at 2nd last item since the last one must be sorted
			for (int j = 0; j < arr.length - 1; j++) { // stop on getting to the one that is already sorted

				if (arr[j].compareTo(arr[j + 1]) > 0) { // can change the > to < for DESCENDING order

					temp = arr[j]; // temp assigned with the value at current index
					arr[j] = arr[j + 1]; // current index assigned with the value at the next index of current index
					arr[j + 1] = temp; // the next index of current index assigned with temp
				}
			}
		}
		return arr;
	}

	/**
	 * method that returns field value of OBJs from the Readers list
	 * 
	 * @param List<Readers> r
	 * @param sortOp; 1,2, 3 or 4
	 * @return a string array collected
	 */
	public String[] collectFieldsOfReaders(List<Readers> r, int sortOp) {

		String[] arr = null; 
			
		if (sortOp == 2) {	//fname
			
			arr = new String[r.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = r.get(i).getFname();
			}
			
		} else if (sortOp == 3) {	//lname
			
			arr = new String[r.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = r.get(i).getLname();
			}
			
		} else if (sortOp == 4) {	// fname + lname
			
			arr = new String[r.size() * 2]; 
			for (int i = 0; i < r.size(); i++) {
				arr[i] = r.get(i).getFname();
			}
			for (int i = 0; i < r.size(); i++) {
				arr[r.size() + i] = r.get(i).getLname();
			}
			
		} else {	//sortOp == 5; reader ID + fname + lname
	
			arr = new String[r.size() * 3]; 
			for (int i = 0; i < r.size(); i++) {
				arr[i] = r.get(i).getId();
			}
			for (int i = 0; i < r.size(); i++) {
				arr[r.size() + i] = r.get(i).getFname();
			}
			for (int i = 0; i < r.size(); i++) {
				arr[r.size() * 2 + i] = r.get(i).getLname();
			}

		}
		
		return arr;
		
	}
	
	/**
	 * Method that collects field value of OBJs from the Books list.
	 * titles and authors -except bookIDs- of Books OBJs can compose of either one word or multiple words.
	 * Thus, all of the values of the field title and author in each of Books OBJs are broken down into one word-based string value,
	 * which will include both of the cases when a value is one word and is more than one word respectively.    
	 * 
	 * This is an advanced option for searching a book.
	 * 
	 * @param List<Books> b
	 * @return a string array collected
	 */
	public String[] collectFieldsOfBooks(List<Books> b) {
		
		String[] arr = null; // an array to store temp
		
		int titleSplitSize = 0;
		int authorSplitSize = 0;
		
		for (int i = 0; i < b.size(); i++) {
			//init each of the int variables
			titleSplitSize += b.get(i).getTitleSplit().length;
			authorSplitSize += b.get(i).getAuthorSplit().length;
		}
		
		/* if necessary, uncomment the following out to check each of the variable sizes assigned*/
		//System.out.println("titleSplitSize: " + titleSplitSize);
		//System.out.println("authorSplitSize: " + authorSplitSize);
		
		int len = 0;
		len = b.size() + titleSplitSize + authorSplitSize;
		arr = new String[len]; // init arr size

		for (int i = 0; i < b.size(); i++) {
			arr[i] = b.get(i).getId();	//store book IDs into arr
		}
		
		int index = 0;
		for (int i = 0; i < b.size(); i++) {
			for (int j = 0; j < b.get(i).getTitleSplit().length; j++) {
				
				//store title by a word unit
				arr[b.size() + index] = b.get(i).getTitleSplit()[j];
				
				if(index < titleSplitSize) {
					index++;	
				}
			}
		}
		
		index = 0;
		for (int i = 0; i < b.size(); i++) {
			for (int j = 0; j < b.get(i).getAuthorSplit().length; j++) {
				
				//store author by a word unit
				arr[b.size() + titleSplitSize + index] = b.get(i).getAuthorSplit()[j];
				
				if(index < authorSplitSize) {
					index++;	
				}
			}		
		}		
		
		return arr;
	}
	
	/**
	 * method that collects field value of OBJs from the Books list
	 * 
	 * @param book    ref
	 * @param sortOp; 1,2 or 3
	 * @return a string array collected
	 */
	public String[] collectFieldsOfBooks(List<Books> b, int sortOp) {

		String[] arr = null; 

		if (sortOp == 2) {	//title
			
			arr = new String[b.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = b.get(i).getTitle();	//store titles into arr
			}
			
		} else if (sortOp == 3) {	//author

			arr = new String[b.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = b.get(i).getAuthor();	//store author into arr
			}
		} else if (sortOp == 4) {	//title and author
			
			int len = 0;
			len = b.size();
			len = len * 2;
			arr = new String[len]; 

			for (int i = 0; i < b.size(); i++) {
				arr[i] = b.get(i).getTitle();	//store titles into arr
			}
			
			for (int i = 0; i < b.size(); i++) {
				arr[b.size() + i] = b.get(i).getAuthor();	//store author into arr
			}
			
		}else {	//sortOp == 5; bookId, title and author
			
			int len = 0;
			len = b.size();
			len = len * 3;
			arr = new String[len];

			for (int i = 0; i < b.size(); i++) {
				arr[i] = b.get(i).getId();	//store book IDs into arr
			}
			
			for (int i = 0; i < b.size(); i++) {
				arr[b.size() + i] = b.get(i).getTitle();	//store titles into arr
			}
			for (int i = 0; i < b.size(); i++) {
				arr[b.size() * 2 + i] = b.get(i).getAuthor();	//store author into arr
			}
		}

		return arr;
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
	 * method to print the sorted result of Readers list depending on the sort option selected
	 * 
	 * @param String[] result
	 * @param List<Readers> r
	 * @param sortOp; 1,2,3 or 4
	 */
	public void printSortedResultOfReaders(String[] result, List<Readers> r, int sortOp) {

		List<Readers> l = new ArrayList<>();	//create a temp list

		/** The parameter String[] result passed should now have its sorted string values in ascending order.
		 * 
		 * Now, the following steps should be executed;
		 * 1. re-order the original Readers list(List<Readers> r) based on the values in the parameter String[] result
		 * 2. store the sorted Readers OBJs into the temp list l
		 * 3. the temp list l 
		 */
		
		if (sortOp == 1) {	//reader ID

			printSortedResult(r, true);
			
		} else if (sortOp == 2) {	//reader's first name
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < r.size(); j++) {
						
					if (result[i].equals(r.get(j).getFname())) {
					
						if(l.size() == 0) {
							
							// now, add obj to temp Readers list l
							l.add(new Readers(r.get(j), i, result[i]));
							j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateReaderEntry(r.get(j).getId(), r.get(j).getFname(), l)) {
								// now, add obj to temp Readers list l
								l.add(new Readers(r.get(j), i, result[i]));
								j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
								
							}
							
						}
					}

				}
			}
			
			printSortedResult(l, false);


		} else if (sortOp == 3) {	//reader's last name
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < r.size(); j++) {

					if (result[i].equals(r.get(j).getLname())) {
						
						if(l.size() == 0) {
							
							// now, add obj to temp Readers list l
							l.add(new Readers(r.get(j), i, result[i]));
							j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateReaderEntry(r.get(j).getId(), r.get(j).getLname(), l)) {
								// now, add obj to temp Readers list l
								l.add(new Readers(r.get(j), i, result[i]));
								j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
								
							}
							
						}

					}
				}
			}

			printSortedResult(l, false);

		} else if (sortOp == 4) {	//reader's first and last name
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < r.size(); j++) {

					if (result[i].equals(r.get(j).getFname())) {
						
						if(l.size() == 0) {
							
							// now, add obj to temp Readers list l
							l.add(new Readers(r.get(j), i, result[i]));
							j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateReaderEntry(r.get(j).getId(), r.get(j).getFname(), l)) {
								// now, add obj to temp Readers list l
								l.add(new Readers(r.get(j), i, result[i]));
								j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
								
							}
							
						}
					}
					
					if (result[i].equals(r.get(j).getLname())) {
						
						if(l.size() == 0) {
							
							// now, add obj to temp Readers list l
							l.add(new Readers(r.get(j), i, result[i]));
							j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateReaderEntry(r.get(j).getId(), r.get(j).getLname(), l)) {
								// now, add obj to temp Readers list l
								l.add(new Readers(r.get(j), i, result[i]));
								j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
								
							}
							
						}

					}
					
				}
			}
			
			printSortedResult(l, false);


		} else {	//ascending(alphabetical) order throughout reader Id, fname and lname
	
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < r.size(); j++) {

					if (result[i].equals(r.get(j).getId())) {
						// now, add obj to temp Readers list l
						l.add(new Readers(r.get(j), i, result[i]));
						j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
						break;	//exit from nested FOR-LOOP at current index
					}
							
					
					if (result[i].equals(r.get(j).getFname())) {
						
						if(l.size() == 0) {
							
							// now, add obj to temp Readers list l
							l.add(new Readers(r.get(j), i, result[i]));
							j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateReaderEntry(r.get(j).getId(), r.get(j).getFname(), l)) {
								// now, add obj to temp Readers list l
								l.add(new Readers(r.get(j), i, result[i]));
								j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
								
							}
							
						}
					}
					
					if (result[i].equals(r.get(j).getLname())) {
						
						if(l.size() == 0) {
							
							// now, add obj to temp Readers list l
							l.add(new Readers(r.get(j), i, result[i]));
							j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateReaderEntry(r.get(j).getId(), r.get(j).getLname(), l)) {
								// now, add obj to temp Readers list l
								l.add(new Readers(r.get(j), i, result[i]));
								j = r.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
								
							}
							
						}

					}
				}
			}
			printSortedResult(l, false);
			
		}
	}
	

	
	 
	/**
	 * method to print the sorted result depending on the sort option selected
	 * 
	 * @param String[] result
	 * @param List<Books> b
	 * @param sortOp; 1,2 or 3
	 */
	public void printSortedResultOfBooks(String[] result, List<Books> b, int sortOp) {

		List<Books> l = new ArrayList<>();

		/** The parameter String[] result passed should now have its sorted string values in ascending order.
		 * 
		 * Now, the following steps should be executed;
		 * 1. re-order the original Books list(List<Books> b) based on the values in the parameter String[] result
		 * 2. store the sorted Books OBJs into the temp list l
		 * 3. the temp list l 
		 */	
		if (sortOp == 1) {	//ID
	
			printSortedResult(b, true);

		} else if (sortOp == 2) {	//title
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < b.size(); j++) {

					if (result[i].equals(b.get(j).getTitle())) {
						
						if(l.size() == 0) {
							
							// now, add obj to temp Books list
							l.add(new Books(b.get(j), i, result[i]));
							j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateBookEntry(b.get(j).getId(), b.get(j).getTitle(), l)) {
								// now, add obj to temp Books list
								l.add(new Books(b.get(j), i, result[i]));
								j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
							}
						}
					}	
				}
			}
			
			printSortedResult(l, false);

		} else if (sortOp == 3) {	//author
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < b.size(); j++) {

					if (result[i].equals(b.get(j).getAuthor())) {
						
						if(l.size() == 0) {
							
							// now, add obj to temp Books list
							l.add(new Books(b.get(j), i, result[i]));
							j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateBookEntry(b.get(j).getId(), b.get(j).getAuthor(), l)) {
								// now, add obj to temp Books list
								l.add(new Books(b.get(j), i, result[i]));
								j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
							}
						}
					}
				}
			}

			printSortedResult(l, false);

		} else if (sortOp == 4) {	//title and author
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < b.size(); j++) {

					if (result[i].equals(b.get(j).getTitle())) {
						
						if(l.size() == 0) {
							
							// now, add obj to temp Books list
							l.add(new Books(b.get(j), i, result[i]));
							j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateBookEntry(b.get(j).getId(), b.get(j).getTitle(), l)) {
								// now, add obj to temp Books list
								l.add(new Books(b.get(j), i, result[i]));
								j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
							}
						}
					}
					
					if (result[i].equals(b.get(j).getAuthor())) {
						
						if(l.size() == 0) {
							
							// now, add obj to temp Books list
							l.add(new Books(b.get(j), i, result[i]));
							j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateBookEntry(b.get(j).getId(), b.get(j).getAuthor(), l)) {
								// now, add obj to temp Books list
								l.add(new Books(b.get(j), i, result[i]));
								j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
							}
						}
					}
				}
			}

			printSortedResult(l, false);
			
		} else {	//sortOp == 5; book Id, title and author
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < b.size(); j++) {

					if (result[i].equals(b.get(j).getId())) {

						// now, add obj to temp Books list
						l.add(new Books(b.get(j), i, result[i]));
						j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
						break;	//exit from nested FOR-LOOP at current index
					}
					
					if (result[i].equals(b.get(j).getTitle())) {
						
						if(l.size() == 0) {
							
							// now, add obj to temp Books list
							l.add(new Books(b.get(j), i, result[i]));
							j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateBookEntry(b.get(j).getId(), b.get(j).getTitle(), l)) {
								// now, add obj to temp Books list
								l.add(new Books(b.get(j), i, result[i]));
								j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
							}
						}
					}
					
					if (result[i].equals(b.get(j).getAuthor())) {
						
						if(l.size() == 0) {
							
							// now, add obj to temp Books list
							l.add(new Books(b.get(j), i, result[i]));
							j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
							break;	//exit from nested FOR-LOOP at current index
							
						}else {
							
							if(filterDuplicateBookEntry(b.get(j).getId(), b.get(j).getAuthor(), l)) {
								// now, add obj to temp Books list
								l.add(new Books(b.get(j), i, result[i]));
								j = b.size()-1;	//point j to be at the end of nested FOR-LOOP
								break;	//exit from nested FOR-LOOP at current index
							}
						}
					}

				}
			}

			printSortedResult(l, false);
		}
		
	}
	
	/**
	 * method to print a temporary list
	 * @param <T>
	 * @param l
	 * @param isDefaultToString
	 */
	public <T> void printSortedResult(List<T> l, boolean isDefaultToString) {
		
		String printOp = "";
		
		if (l.get(0).getClass().getSimpleName().equals("Rent")) {
			
			for (int i = 0; i < l.size(); i++) {
				
				if(l.size() > 500 && i >= 500) {
					if(printOp != null) {
						printOp = IO.menu(IO.askUserForPrintingLargeRecords(), "^[y|Y|n|N]$");	
					}
					if(printOp != null && !printOp.equalsIgnoreCase("y")) {
						System.out.println("going back to menu..."); break;
						
					}else {
						if(i < 1000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Rent) l.get(i)).toString().replace("\n", "")); // print
							}
							printOp = null;							
							
						}else if (i > 1000 && i < 1500) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Rent) l.get(i)).toString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 1500 && i < 2000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Rent) l.get(i)).toString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 2000 && i < 2500) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Rent) l.get(i)).toString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 2500 && i < 3000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Rent) l.get(i)).toString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 3000 && i < 3500) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Rent) l.get(i)).toString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 3500 && i < 4000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Rent) l.get(i)).toString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 4000 && i < 4500) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Rent) l.get(i)).toString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 4500 && i < 5000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Rent) l.get(i)).toString().replace("\n", "")); // print
							}
							printOp = null;			
							
						} else { printOp = ""; }
					}
				}else {
					if(isDefaultToString) {
						System.out.println(i+1 + "\t" + ((Rent) l.get(i)).toString().replace("\n", "")); // print
					}
				}
				
			}
		}
		
		if (l.get(0).getClass().getSimpleName().equals("Books")) {
	
			for (int i = 0; i < l.size(); i++) {
				
				if(l.size() > 500 && i >= 500) {
					if(printOp != null) {
						printOp = IO.menu(IO.askUserForPrintingLargeRecords(), "^[y|Y|n|N]$");	
					}
					if(printOp != null && !printOp.equalsIgnoreCase("y")) {
						System.out.println("going back to menu..."); break;
						
					}else {
						if(i < 1000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;							
							
						}else if (i > 1000 && i < 1500) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 1500 && i < 2000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 2000 && i < 2500) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 2500 && i < 3000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 3000 && i < 3500) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 3500 && i < 4000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 4000 && i < 4500) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;			
							
						}else if (i > 4500 && i < 5000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Books) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;			
							
						} else { printOp = ""; }
					}
				}else {
					if(isDefaultToString) {
						System.out.println(i+1 + "\t" + ((Books) l.get(i)).toString().replace("\n", "")); // print
					}else {
						System.out.println(i+1 + "\t" + ((Books) l.get(i)).nameTagToString().replace("\n", "")); // print
					}
				}
				
			}
		}

		if (l.get(0).getClass().getSimpleName().equals("Readers")) {
			
			for (int i = 0; i < l.size(); i++) {
				
				if(l.size() > 500 && i >= 500) {
					if(printOp != null) {
						printOp = IO.menu(IO.askUserForPrintingLargeRecords(), "^[y|Y|n|N]$");	
					}
					if(printOp != null && !printOp.equalsIgnoreCase("y")) {
						System.out.println("going back to menu..."); break;
						
					}else {
						if(i < 1000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;
							
						}else if (i > 1000 && i < 1500) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;
							
						}else if (i > 1500 && i < 2000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;
							
						}else if (i > 2000 && i < 2500) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;
							
						}else if (i > 2500 && i < 3000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;
							
						}else if (i > 3000 && i < 3500) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;
							
						}else if (i > 3500 && i < 4000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;
							
						}else if (i > 4000 && i < 4500) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;
							
						}else if (i > 4500 && i < 5000) {
							if(isDefaultToString) {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).toString().replace("\n", "")); // print
							}else {
								System.out.println(i+1 + "\t" + ((Readers) l.get(i)).nameTagToString().replace("\n", "")); // print
							}
							printOp = null;
							
						} else { printOp = ""; }
					}
				}else {
					if(isDefaultToString) {
						System.out.println(i+1 + "\t" + ((Readers) l.get(i)).toString().replace("\n", "")); // print
					}else {
						System.out.println(i+1 + "\t" + ((Readers) l.get(i)).nameTagToString().replace("\n", "")); // print
					}
				}
				
			}
		}
		
	}

}
