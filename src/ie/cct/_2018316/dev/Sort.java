package ie.cct._2018316.dev;

import java.util.ArrayList;
import java.util.List;

import ie.cct._2018316.cunstructs.IO;

public class Sort {
	
	public Sort() {
		
	}

	/**
	 * method that returns a sorted string array
	 * @param book ref
	 * @param sortOp; 1,2 or 3
	 * @return a string array with the sorted strings
	 */
	public String[] BubbleSort(List<Books> b, int sortOp) {

		String[] arr= null;	//an array to store temp
		String temp = "";	//temp string 
		
		if(sortOp == 1) {
			arr = new String[b.size()];
			for(int i=0; i<arr.length; i++) {
				//init arr with titles
				arr[i] = b.get(i).getTitle();
			}	
		}else if(sortOp == 2) {
			//init arr with author
			arr = new String[b.size()];
			for(int i=0; i<arr.length; i++) {
				arr[i] = b.get(i).getAuthor();
			}	
		}else {
			int len = 0;
			len = b.size();
			len = len * 2;
			arr = new String[len];	//init arr size
			System.out.println("myArray.length; " + arr.length);
			
			for(int i=0; i<b.size(); i++) {
				//init arr with titles
				arr[i] = b.get(i).getTitle();
			}
			for(int i=0; i<b.size(); i++) {
				//init arr with author
				arr[b.size() + i] = b.get(i).getAuthor();
			}
			
			System.out.println(IO.printUnderLine() + "\nbefore sorting\n");
			for(int i=0; i<arr.length; i++) {
				System.out.println(arr[i]);
			}
		}
		
		for (int i=0; i<arr.length-1; i++) {	//stop at 2nd last item since the last one must be sorted
			for (int j=0; j<arr.length-1; j++) { //stop on getting to the one that is already sorted
				
				//.compareTo() needs to be implemented manually
				if ( arr[j].compareTo(arr[j+1]) > 0) {	//can change the > to < for DESCENDING order
					
					//the value at arr[j] > arr[j+1]  
					temp = arr[j];	    //temp assigned with the value at current index
					arr[j] = arr[j+1];	//current index assigned with the value at the next index of current index
					arr[j+1] = temp;	//the next index of current index assigned with temp
				}
			}
		}
		
		System.out.println(IO.printUnderLine() + "\nafter sorting\n");
		for(int i=0; i<arr.length; i++) {
			System.out.println(arr[i]);
		}

		return arr;
	} 
	
	/**
	 * method to print the sorted result
	 * @param result
	 * @param books ref
	 * @param sortOp; 1,2 or 3
	 */
	public void titleSorted(String[] result, List<Books> b, int sortOp) {
		
		List<Books> l = new ArrayList<>();

		if(sortOp == 1) {
			for(int i=0; i<result.length; i++) {
				for(int j=0; j<b.size(); j++) {	
					
					if(result[i].equals(b.get(j).getTitle())) {
						l.add(b.get(j));	//the sorted string values are added to l; the temp Books list
					}
				}
			}
			for(int i=0; i<l.size(); i++) {	
				System.out.print(l.get(i).titleSortedToString());	//print
			}	
			
		}else if(sortOp == 2) {
			for(int i=0; i<result.length; i++) {
				for(int j=0; j<b.size(); j++) {	
					
					if(result[i].equals(b.get(j).getAuthor())) {
						l.add(b.get(j));	//the sorted string values are added to l; the temp Books list
					}
				}
			}
			
			for(int i=0; i<l.size(); i++) {	
				System.out.print(l.get(i).authorSortedToString());	//print
			}	
			
		}else {
			for(int i=0; i<result.length; i++) {
				for(int j=0; j<b.size(); j++) {	
					
					//the array result has the sorted strings from the index 0 in alphabetical order.
					//if the value at current index of the array result is equals to either one value between title and author at current index of the original book list ref,
					if(result[i].equals(b.get(j).getTitle()) || result[i].equals(b.get(j).getAuthor())) {
						
						l.add(b.get(j));	//the sorted string values are added to l; the temp Books list
					}
					
//					if(result[i].equals(b.get(j).getAuthor())) {
//						l.add(b.get(j));
//					}
				}
			}
			
			for(int i=0; i<l.size(); i++) {	
				
				//title and author should be mixed and sorted in alphabetical order. 
				//In this case, pass the values in the array result to the toString method in current element(obj) in the temp book list
				//so that those work as each of sorted tag in alphabetical order. 
				System.out.print(l.get(i).titleAndAuthorSortedToString(result[i]));	
			}
		}
		
	}
	
	
}