package ie.cct._2018316.dev;

import java.util.ArrayList;
import java.util.List;


public class Sort {
	
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
	 * method to perform a merge sort
	 * This custom merge implementation references/has modified/has improved the sort algorithm course content
	 * that was lectured by Professor Amilcar  
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
				
				System.out.println(s[count_s]);
				
				count_s1++;	//move the pointer == remove the string value in s1's index pointed 			
			}
			else {
				//the string value in s2's index pointed < the string value in s1's index pointed
				s[count_s] = s2[count_s2];	//add the string value in s2's index pointed into the sequence array s
				
				System.out.println(s[count_s]);
				
				count_s2++;	//move the pointer == remove the string value in s2's index pointed
			}
			
			//the sequence array s at the index pointed now has something in it throughout the if-else statement above. 
			count_s++;	//move the pointer == look at the next index of the sequence array
		}
		
		//keep adding the elements that have been left or that have not been moved in s1 into the sequence array s
		while (count_s1 < s1.length) {
			s[count_s] = s1[count_s1];	//add the string value in s1's index pointed into the sequence array
			
			System.out.println(s[count_s]);
			
			count_s1++;	//move the pointer == remove the string value in s1's index pointed 
			count_s++;
		}
		
		//keep adding the elements that have been left or that have not been moved in s2 into the sequence array s
		while (count_s2 < s2.length) {
			s[count_s] = s2[count_s2];	//add the string value in s2's index pointed into the sequence array 
			
			System.out.println(s[count_s]);
			
			count_s2++;	//move the pointer == remove the string value in s2's index pointed
			count_s++;	//move the pointer == look at the next index of the sequence array
		}
		
		
	}

	/**
	 * method that returns field value of OBJs from the Readers list
	 * 
	 * @param List<Readers> r
	 * @param sortOp; 1,2, 3 or 4
	 * @return a string array collected
	 */
	public String[] collectFieldsOfReaders(List<Readers> r, int sortOp) {

		String[] arr = null; // an array to store temp

		if (sortOp == 1) {	//reader id
			arr = new String[r.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = r.get(i).getId();
			}
		} else if (sortOp == 2) {	//fname
			arr = new String[r.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = r.get(i).getFname();
			}
		} else if (sortOp == 3) {	//lname
			arr = new String[r.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = r.get(i).getLname();
			}
		} else {
			//reader ID + fname + lname
			arr = new String[r.size()*3]; // init arr size

			for (int i = 0; i < r.size(); i++) {
				arr[i] = r.get(i).getId();
			}
			for (int i = 0; i < r.size(); i++) {
				arr[r.size() + i] = r.get(i).getFname();
			}
			for (int i = 0; i < r.size(); i++) {
				arr[r.size()*2+i] = r.get(i).getLname();
			}

		}
		
		return arr;
		
	}

	/**
	 * method to sort the values in the array arr
	 * .compareTo() used since no user input is involved for this bubble sort
	 * 
	 * @param arr
	 * @return sorted arr in ascending(alphabetical) order
	 */
	public String[] bubbleSort(String[] arr) {
		
		String temp = ""; // temp string
		
		for (int i = 0; i < arr.length - 1; i++) { // stop at 2nd last item since the last one must be sorted
			for (int j = 0; j < arr.length - 1; j++) { // stop on getting to the one that is already sorted

				if (arr[j].compareTo(arr[j + 1]) > 0) { // can change the > to < for DESCENDING order

					// the value at arr[j] > arr[j+1]
					temp = arr[j]; // temp assigned with the value at current index
					arr[j] = arr[j + 1]; // current index assigned with the value at the next index of current index
					arr[j + 1] = temp; // the next index of current index assigned with temp
				}
			}
		}
		return arr;
	}

	
	/**
	 * method that returns field value of OBJs from the Books list
	 * This is an advanced option for searching a book
	 * 
	 * @param List<Books> b
	 * @return a string array collected
	 */
	public String[] collectFieldsOfBooks(List<Books> b) {
		
		String[] arr = null; // an array to store temp
		
		int titleSplitSize = 0;
		int authorSplitSize = 0;
		
		for (int i = 0; i < b.size(); i++) {
			
			titleSplitSize += b.get(i).getTitleSplit().length;
			authorSplitSize += b.get(i).getAuthorSplit().length;
		}
		
		System.out.println("titleSplitSize: " + titleSplitSize);
		System.out.println("authorSplitSize: " + authorSplitSize);
		
		int len = 0;
		len = b.size() + titleSplitSize + authorSplitSize;
		arr = new String[len]; // init arr size

		//store bookId, title and author
		for (int i = 0; i < b.size(); i++) {
			arr[i] = b.get(i).getId();	//store book IDs
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
	 * method that returns field value of OBJs from the Books list
	 * 
	 * @param book    ref
	 * @param sortOp; 1,2 or 3
	 * @return a string array collected
	 */
	public String[] collectFieldsOfBooks(List<Books> b, int sortOp) {

		String[] arr = null; // an array to store temp

		if (sortOp == 1) {	//title
			arr = new String[b.size()];
			for (int i = 0; i < arr.length; i++) {
				// init arr with titles
				arr[i] = b.get(i).getTitle();
			}
		} else if (sortOp == 2) {	//author
			// init arr with author
			arr = new String[b.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = b.get(i).getAuthor();
			}
		} else if (sortOp == 3) {	//title and author
			
			int len = 0;
			len = b.size();
			len = len * 2;
			arr = new String[len]; // init arr size

			for (int i = 0; i < b.size(); i++) {
				// init arr with titles
				arr[i] = b.get(i).getTitle();
			}
			for (int i = 0; i < b.size(); i++) {
				// init arr with author
				arr[b.size() + i] = b.get(i).getAuthor();
			}
		}else {
			//bookId, title and author
			int len = 0;
			len = b.size();
			len = len * 3;
			arr = new String[len]; // init arr size

			for (int i = 0; i < b.size(); i++) {
				// init arr with titles
				arr[i] = b.get(i).getId();
			}
			for (int i = 0; i < b.size(); i++) {
				// init arr with titles
				arr[b.size() + i] = b.get(i).getTitle();
			}
			for (int i = 0; i < b.size(); i++) {
				// init arr with author
				arr[b.size() * 2 + i] = b.get(i).getAuthor();
			}
		}

		return arr;
	}
	
	/**
	 * method to print the sorted result depending on the sort option selected
	 * 
	 * @param String[] result
	 * @param List<Readers> r
	 * @param sortOp; 1,2,3 or 4
	 */
	public void printSortedResultOfReaders(String[] result, List<Readers> r, int sortOp) {

		List<Readers> l = new ArrayList<>();	//create a temp list

		/* The parameter result should now have its sorted string values in ascending order.
		 * 
		 * Now, the following steps should be executed;
		 * 1. re-order the original Readers list(List<Readers> r) based on the values in the parameter result
		 * 2. store it into a temp list l
		 * 3. print it 
		 */
		
		if (sortOp == 1) {	//reader ID
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < r.size(); j++) {

					if (result[i].equals(r.get(j).getId())) {
						l.add(r.get(j)); // the sorted string values are added to l; the temp Books list
					}
				}
			}
			for (int i = 0; i < l.size(); i++) {
				System.out.print(l.get(i).nameTagToString(result[i])); // print
			}

		} else if (sortOp == 2) {	//reader's first name
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < r.size(); j++) {

					if (result[i].equals(r.get(j).getFname())) {
						l.add(r.get(j)); // the sorted string values are added to l; the temp Books list
					}
				}
			}

			for (int i = 0; i < l.size(); i++) {
				System.out.print(l.get(i).nameTagToString(result[i])); // print
			}

		} else if (sortOp == 3) {	//reader's last name
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < r.size(); j++) {

					if (result[i].equals(r.get(j).getLname())) {
						l.add(r.get(j)); // the sorted string values are added to l; the temp Books list
					}
				}
			}

			for (int i = 0; i < l.size(); i++) {
				System.out.print(l.get(i).nameTagToString(result[i])); // print
			}

		} else {	//ascending(alphabetical) order throughout reader Id, fname and lname
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < r.size(); j++) {

					
					if (result[i].equals(r.get(j).getId()) 
							|| result[i].equals(r.get(j).getFname())
							|| result[i].equals(r.get(j).getLname())) {

						l.add(r.get(j)); // the sorted string values are added to l; the temp Books list
					}

				}
			}

			for (int i = 0; i < l.size(); i++) {
				System.out.print(l.get(i).nameTagToString(result[i])); // print
			}
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

		/* The parameter result should now have its sorted string values in ascending order.
		 * 
		 * Now, the following steps should be executed;
		 * 1. re-order the original Readers list(List<Books> b) based on the values in the parameter result
		 * 2. store it into a temp list l
		 * 3. print it 
		 */
		
		if (sortOp == 1) {	//title
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < b.size(); j++) {

					if (result[i].equals(b.get(j).getTitle())) {
						l.add(b.get(j)); // the sorted string values are added to l; the temp Books list
					}
				}
			}
			for (int i = 0; i < l.size(); i++) {
				System.out.print(l.get(i).titleSortedToString()); // print
			}

		} else if (sortOp == 2) {	//author
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < b.size(); j++) {

					if (result[i].equals(b.get(j).getAuthor())) {
						l.add(b.get(j)); // the sorted string values are added to l; the temp Books list
					}
				}
			}

			for (int i = 0; i < l.size(); i++) {
				System.out.print(l.get(i).authorSortedToString()); // print
			}

		} else if (sortOp == 3) {	//title and author
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < b.size(); j++) {

					if (result[i].equals(b.get(j).getTitle()) || result[i].equals(b.get(j).getAuthor())) {

						l.add(b.get(j)); // the sorted string values are added to l; the temp Books list
					}

				}
			}

			for (int i = 0; i < l.size(); i++) {

				System.out.print(l.get(i).titleAndAuthorSortedToString(result[i]));	//print
			}
			
		} else {	//sortOp == 4; book Id, title and author
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < b.size(); j++) {

					if (result[i].equals(b.get(j).getId()) || result[i].equals(b.get(j).getTitle()) || result[i].equals(b.get(j).getAuthor())) {

						l.add(b.get(j)); // the sorted string values are added to l; the temp Books list
					}

				}
			}

			for (int i = 0; i < l.size(); i++) {

				System.out.print(l.get(i).titleAndAuthorSortedToString(result[i]));	//print
			}
		}
		

	}

}
