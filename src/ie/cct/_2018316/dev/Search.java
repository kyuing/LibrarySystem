package ie.cct._2018316.dev;

import java.util.List;

import ie.cct._2018316.cunstructs.IO;

public class Search /* implements SearchInterface */ {

	public int LinearSerch(List<Books> b, String keyword) {		
		int curLocation = 0; 
		boolean isFound = false;	
		
		//keyword is title + author
		if(keyword.startsWith("+")) {
			String titileAndAutor[] = null, title="", author="";
			titileAndAutor = keyword.substring(1).split("&");
			title = titileAndAutor[0];
			author = titileAndAutor[1];

			//return true if title && author exactly matches with title && author in a specific book  
			while ( !(isFound = findBooksByTitleAndAuthor(b, curLocation, title, author)) && (curLocation < b.size() -1)) {

				curLocation++;	//move to next box
			}
			
			if(isFound == false) {
				return -1;
			}

		}else {
			
			//return true if a keyword exactly matches with one between Id, title or author 
			while ( !(isFound = findBooksByKeyword(b, curLocation, keyword)) && (curLocation < b.size() -1)) {

				curLocation++;	//move to next box
			}
			
			if(isFound == false) {
				//if still false, give one more go.
				
				
				curLocation = 0;	//re-init it
				//return true if any partial match is found 
				while ( !(isFound = findBooksByKeywordSplitUp(b, curLocation, keyword)) && (curLocation < b.size() -1)) {

					curLocation++;	//move to next box
				}
			}

			if(isFound == false) {
				return -1;
			}	
		}
	
		return curLocation;	//return one specific result

	}
	
	//return true the input title and author are equal to a specific book record
	public boolean findBooksByTitleAndAuthor(List<Books> b, int curLocation, String title, String author) {
	
		if((b.get(curLocation).getTitle().equalsIgnoreCase(title)) && (b.get(curLocation).getAuthor().equalsIgnoreCase(author))) {
			
			//return the exact match
			System.out.println(IO.printUnderLine());
			System.out.println("[The exact match]");
			return true;
		}
		return false;
	}
	

	public boolean findBooksByKeyword(List<Books> b, int curLocation, String keyword) {

		String k = keyword.trim();
		
		if( (b.get(curLocation).getId().equalsIgnoreCase(k))
				|| (b.get(curLocation).getTitle().equalsIgnoreCase(k))
				|| (b.get(curLocation).getAuthor().equalsIgnoreCase(k))) {
		
			//return the exact match
			System.out.println(IO.printUnderLine());
			System.out.println("[The exact match]");
			return true;
		}
		return false;
	}

	
	public boolean findBooksByKeywordSplitUp(List<Books> b, int curLocation, String keyword) {

		String key = keyword.trim();
		
		String[] temp = key.split(" ");	
		if(temp != null) {
			for(int i=0; i<temp.length; i++) {
				
				//split the input keyword up by a word unit to see if any partial or full match is.
				if( (b.get(curLocation).getId().equalsIgnoreCase(temp[i].toString()))
						|| (b.get(curLocation).getTitle().equalsIgnoreCase(temp[i].toString()))
						|| (b.get(curLocation).getAuthor().equalsIgnoreCase(temp[i].toString()))) {
					
					//return the best matching one. 
					System.out.println(IO.printUnderLine() + 
							"\n* QUICK NOTE" +
							"\n  If you want to get a more accurate result (the exact match), "
							+ "\n  please improve the search term based on the book info you want to get and try again."
							+ "\n  A single accurate input at a time gives the exact match much more clearly."
							+ "\n\n[The best match]");
					
					return true;
					
				}else {
					//title or author could be in a word form or in a multiple word form as well.
					String[] title = b.get(curLocation).getTitle().split(" ");
					String[] author = b.get(curLocation).getAuthor().split(" ");
					
					if(title != null) {
						for(int j=0; j<title.length; j++) {
							for(int k=0; k<temp.length; k++) {
								
								//check if there's any findings between pieces of title and pieces of the keyword
								if(temp[k].toString().equalsIgnoreCase(title[j].toString())) {	
									//return the partial matching one
									System.out.println(IO.printUnderLine() + 
											"\n* QUICK NOTE" +
											"\n  If you want to get a more accurate result (the exact match), "
											+ "\n  please improve the search term based on the book info you want to get and try again."
											+ "\n  A single accurate input at a time gives the exact match much more clearly."
											+ "\n  You are STRONGLY recommended to improve the search term for getting the exact match."
											+ "\n\n[Partial match]");
									
									//inform user the partial match
									System.out.println("The input '" + temp[k].toString() + "' is partially matching with '" + 
									title[j].toString() + "' in \"" + b.get(curLocation).getTitle() + "\". " + "Please check the follwoing record retrieved");
									return true;
								}	
							}	
						}	
					}
					
					if(author != null) {
						for(int j=0; j<author.length; j++) {
							for(int k=0; k<temp.length; k++) {
								
								//check if there's any findings between pieces of author and pieces of the keyword
								if(temp[k].toString().equalsIgnoreCase(author[j].toString())) {	
									//return the partial matching one
									System.out.println(IO.printUnderLine() + 
											"\n* QUICK NOTE" +
											"\n  If you want to get a more accurate result (the exact match), "
											+ "\n  please improve the search term based on the book info you want to get and try again."
											+ "\n  A single accurate input at a time gives the exact match much more clearly."
											+ "\n  You are STRONGLY recommended to improve the search term for getting the exact match."
											+ "\n\n[Partial match]");
									
									//inform user the partial match
									System.out.println("The input '" + temp[k].toString() + "' is partially matching with '" + 
									author[j].toString() + "' in \"" + b.get(curLocation).getAuthor() + "\". " + "Please check the follwoing record retrieved");
									
									return true;
								}	
							}	
						}	
					}
					
					
//					//check if there's any findings between pieces of title and the keyword
//					if(title != null) {
//						for(int j=0; j<title.length; j++) {
//							if(keyword.equalsIgnoreCase(title[j].toString())) {
//								//return the partial matching one
//								System.out.println(IO.printUnderLine() + 
//										"\n* QUICK NOTE" +
//										"\n  If you want to get a more accurate result (the exact match), "
//										+ "\n  please improve the search term based on the book info you want to get and try again."
//										+ "\n  A single accurate input at a time gives the exact match much more clearly."
//										+ "\n  You are STRONGLY recommended to improve the search term for getting the exact match."
//										+ "\n\n[3Partial match]");
//								
//								//inform user the partial match
//								System.out.println("The input '" + keyword + "' is partially matching with '" + 
//								title[j].toString() + "' in \"" + b.get(curLocation).getTitle() + "\". " + "Please check the follwoing record retrieved");
//								return true;
//							}	
//						}	
//					}
//					
//					//check if there's any findings between pieces of author and the keyword
//					if(author != null) {
//						for(int j=0; j<author.length; j++) {
//							if(keyword.equalsIgnoreCase(author[j].toString())) {
//								//return the partial matching one
//								System.out.println(IO.printUnderLine() + 
//										"\n* QUICK NOTE" +
//										"\n  If you want to get a more accurate result (the exact match), "
//										+ "\n  please improve the search term based on the book info you want to get and try again."
//										+ "\n  A single accurate input at a time gives the exact match much more clearly."
//										+ "\n  You are STRONGLY recommended to improve the search term for getting the exact match."
//										+ "\n\n[4Partial match]");
//								
//								//inform user the partial match
//								System.out.println("The input '" + keyword + "' is partially matching with '" + 
//										author[j].toString() + "' in \"" + b.get(curLocation).getAuthor() + "\". " + "Please check the follwoing record retrieved");
//								return true;
//							}	
//						}	
//					}
					
				}
			}		
		}
			
		return false;
	}

}
