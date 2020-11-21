package ie.cct._2018316.dev;

import java.util.ArrayList;
import java.util.List;

import ie.cct._2018316.cunstructs.IO;

public class Sort {

	public Sort() {}

	/**
	 * method that returns field value of OBJs from the Readers list
	 * 
	 * @param List<Readers> r
	 * @param sortOp; 1,2, 3 or 4
	 * @return a string array collected
	 */
	public String[] collectFieldsOfReaders(List<Readers> r, int sortOp) {

		String[] arr = null; // an array to store temp

		if (sortOp == 1) {	//id
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
			//reader ID + names
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
	 * 
	 * @param book    ref
	 * @param sortOp; 1,2 or 3
	 * @return a string array collected
	 */
	public String[] collectFieldsOfBooks(List<Books> b, int sortOp) {

		String[] arr = null; // an array to store temp

		if (sortOp == 1) {
			arr = new String[b.size()];
			for (int i = 0; i < arr.length; i++) {
				// init arr with titles
				arr[i] = b.get(i).getTitle();
			}
		} else if (sortOp == 2) {
			// init arr with author
			arr = new String[b.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = b.get(i).getAuthor();
			}
		} else {
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

		} else {	//title and author
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
		}

	}

}
