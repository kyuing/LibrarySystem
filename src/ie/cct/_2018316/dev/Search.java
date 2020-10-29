package ie.cct._2018316.dev;

import java.util.ArrayList;
import java.util.List;

import ie.cct._2018316.cunstructs.IO;

public class Search {

	public int LinearSerch(List<Books> b, String keyword) {
		int curLocation = 0;
		boolean isFound = false;

		// keyword is title + author
		if (keyword.startsWith("+")) {
			String titileAndAutor[] = null, title = "", author = "";
			titileAndAutor = keyword.substring(1).split("&");
			title = titileAndAutor[0];
			author = titileAndAutor[1];

			// return true if title && author exactly matches with title && author in a
			// specific book
			while (!(isFound = findBooksByTitleAndAuthor(b, curLocation, title, author))
					&& (curLocation < b.size() - 1)) {

				curLocation++; // move to next box
			}

			if (isFound == false) {
				return -1;
			}

		} else {

			// return true if a keyword exactly matches with one between Id, title or author
			while (!(isFound = findBooksByKeyword(b, curLocation, keyword)) && (curLocation < b.size() - 1)) {

				curLocation++; // move to next box
			}

			if (isFound == false) {
				// if still false, give one more go.

				curLocation = 0; // re-init it
				// return true if any partial match is found
				while (!(isFound = findBooksByKeywordSplitUp(b, curLocation, keyword))
						&& (curLocation < b.size() - 1)) {

					curLocation++; // move to next box
				}
			}

			if (isFound == false) {
				return -1;
			}
		}

		return curLocation; // return one specific result

	}

	// return true the input title and author are equal to a specific book record
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

	public boolean findBooksByKeyword(List<Books> b, int curLocation, String keyword) {

		String k = keyword.trim();

		if ((b.get(curLocation).getId().equalsIgnoreCase(k)) || (b.get(curLocation).getTitle().equalsIgnoreCase(k))
				|| (b.get(curLocation).getAuthor().equalsIgnoreCase(k))) {

			// return the exact match
			System.out.println(IO.printUnderLine());
			System.out.println("[The exact match]");
			return true;
		}
		return false;
	}

	public boolean findBooksByKeywordSplitUp(List<Books> b, int curLocation, String keyword) {

		String key = keyword.trim();

		String[] temp = key.split(" ");
		if (temp != null) {
			for (int i = 0; i < temp.length; i++) {

				// split the input keyword up by a word unit to see if any partial or full match
				// is.
				if ((b.get(curLocation).getId().equalsIgnoreCase(temp[i].toString()))
						|| (b.get(curLocation).getTitle().equalsIgnoreCase(temp[i].toString()))
						|| (b.get(curLocation).getAuthor().equalsIgnoreCase(temp[i].toString()))) {

					// return the best matching one.
					System.out.println(IO.printUnderLine() + "\n* QUICK NOTE"
							+ "\n  If you want to get a more accurate result (the exact match), "
							+ "\n  please improve the search term based on the book info you want to get and try again."
							+ "\n  A single accurate input at a time gives the exact match much more clearly."
							+ "\n\n[The best match]");

					return true;

				} else {
					// title or author could be in a word form or in a multiple word form as well.
					String[] title = b.get(curLocation).getTitle().split(" ");
					String[] author = b.get(curLocation).getAuthor().split(" ");

					if (title != null) {
						for (int j = 0; j < title.length; j++) {
							for (int k = 0; k < temp.length; k++) {

								// check if there's any findings between pieces of title and pieces of the
								// keyword
								if (temp[k].toString().equalsIgnoreCase(title[j].toString())) {
									// return the partial matching one
									System.out.println(IO.printUnderLine() + "\n* QUICK NOTE"
											+ "\n  If you want to get a more accurate result (the exact match), "
											+ "\n  please improve the search term based on the book info you want to get and try again."
											+ "\n  A single accurate input at a time gives the exact match much more clearly."
											+ "\n  You are STRONGLY recommended to improve the search term for getting the exact match."
											+ "\n\n[Partial match]");

									// inform user the partial match
									System.out.println(
											"The input '" + temp[k].toString() + "' is partially matching with '"
													+ title[j].toString() + "' in \"" + b.get(curLocation).getTitle()
													+ "\". " + "Please check the follwoing record retrieved");
									return true;
								}
							}
						}
					}

					if (author != null) {
						for (int j = 0; j < author.length; j++) {
							for (int k = 0; k < temp.length; k++) {

								// check if there's any findings between pieces of author and pieces of the
								// keyword
								if (temp[k].toString().equalsIgnoreCase(author[j].toString())) {
									// return the partial matching one
									System.out.println(IO.printUnderLine() + "\n* QUICK NOTE"
											+ "\n  If you want to get a more accurate result (the exact match), "
											+ "\n  please improve the search term based on the book info you want to get and try again."
											+ "\n  A single accurate input at a time gives the exact match much more clearly."
											+ "\n  You are STRONGLY recommended to improve the search term for getting the exact match."
											+ "\n\n[Partial match]");

									// inform user the partial match
									System.out.println(
											"The input '" + temp[k].toString() + "' is partially matching with '"
													+ author[j].toString() + "' in \"" + b.get(curLocation).getAuthor()
													+ "\". " + "Please check the follwoing record retrieved");

									return true;
								}
							}
						}
					}
				}
			}
		}

		return false;
	}

	public /* static */ int BinarySearch(List<Readers> r, String key) {

		List<Readers> temp = new ArrayList<>();	//create a temp list
		String[] arr = null;	//arr for storing the values of reader ID, 1st name and 2nd name
		arr = new String[r.size() * 3];	//init size of arr
		
		Sort s = new Sort();
		arr = s.BubbleSort(r, arr);	//arr will store the sorted strings(reader ID, 1st name and 2nd name) in alphabetical order
		
//		System.out.println("the sorted array arr+++++++++++++++++++++++++++++");
//		for (int i = 0; i < arr.length; i++) {
//			System.out.println(arr[i]);
//		}
		
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < r.size(); j++) {

				if (arr[i].equalsIgnoreCase(r.get(j).getId()) || arr[i].equalsIgnoreCase(r.get(j).getFname())
						|| arr[i].equalsIgnoreCase(r.get(j).getLname())) {
					//now, add reader obj to temp Reader list if arr found the matching string value from the Reader list r.   
					temp.add(new Readers(r.get(j), i));
				}

			}
		}

		for (int i = 0; i < temp.size(); i++) {
			
			//set a temp name tag which will work for binary search to each of reader obj in temp 
			temp.get(i).setNameTag(arr[i]);	 
			System.out.print(temp.get(i).sortedAllInAcendingToString());
		}

		/* now, Everything is ready to do a binary search. 
		 * We've just got a temp reader SORTED in alphabetical order based on reader's ID, fname and lname.
		 * 
		 * Technically, index number in temp list is conceptually the same as the nameTag in each of the index
		 * since all the nameTags were initially sorted in alphabetical order. */
		boolean isFound = false;
		int mid = 0;
		int left = 0;
		int indexNum = 0;
		int right = temp.size() - 1;	//or arr.length - 1; 

		while (right >= left) {
			mid = (right + left) / 2;	//set the middle index

			System.out.println("\ntemp.get(" + mid + ").getTempNameTag(): " + temp.get(mid).getNameTag());

			//.compareToIgnoreCase() checks the length of string(char) and the hex value of string(char) between two strings based on UNICODE 
			//example
			/* String str = "abcd";
	        System.out.println( str.compareTo("abcd") );  // 0
	        System.out.println( str.compareTo("ab") );  //  2
	        System.out.println( str.compareTo("a") );  //  3
	        System.out.println( str.compareTo("c") );  //  -2       
	        System.out.println( "".compareTo(str) );  //  -4 */
			
	        //Thus, if the UNICODE value of a search keyword == the UNICODE value of nameTag in the mid index of temp list, 
	        if ((key.compareToIgnoreCase(temp.get(mid).getNameTag()) == 0)) {
				
				//found
				isFound = true;
				indexNum = Integer.parseInt(temp.get(mid).getId().substring(1)) - 1;	//take an actual index from the original reader obj
				
				// return the exact match
				System.out.println(IO.printUnderLine());
				System.out.println("[The exact match]");
				System.out.println(key + " is found in the reader index at " + indexNum);

				break;
			}

			if ((key.compareToIgnoreCase(temp.get(mid).getNameTag()) < 0)) {
				//the UNICODE value of a search keyword < the UNICODE value of nameTag in the mid index of temp list
				System.out.println("key's value: " + key.compareToIgnoreCase(temp.get(mid).getNameTag()));
				right = mid - 1;	//right = mid -1 so that the mid index can move out to left side
			} else {
				//the UNICODE value of a search keyword > the UNICODE value of nameTag in the mid index of temp list
				System.out.println("key's value: " + key.compareToIgnoreCase(temp.get(mid).getNameTag()));
				left = mid + 1;	//left = mid + 1 so that the mid index can move out to right side
			}
		}

		if (isFound == false) {
			return -1;
		}

		return indexNum;	//return indexNum
	}

}
