package it.anobii2library.request;

import static org.junit.Assert.*;

import java.util.List;

import it.anobii2panizzi.Book;
import it.anobii2panizzi.BookInfo;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

public class PanizziSearchMgrTest {

//	@Test
//	public void testSearchBook() {
//		try {
//			Book request = new Book();
//			request.setName("il GIOVANE holden");
//			Book[] books = PanizziSearchMgr.searchBook(request);
//			
//			assertNotNull(books);
//			assertFalse("Nessun libro trovato", books.length == 0);
//			
//			for (Book aBook : books) {
//				System.out.println(aBook);
//			}
//			
//			
//			
//		} catch (Exception exc) {
//			exc.printStackTrace(System.err);
//			fail("Errore nella ricerca! ");
//		}
//	}
 
	@Test
	public void testSearchBookComplete() {
		try {
			// step 1: search by title (case insensitive), filtering by the most relevant one (perfect hit) 
			Book request = new Book();
			request.setName("il GIOVANE holden");
			BookInfo[] books = PanizziSearchMgr.searchBook(request);
			
			assertNotNull(books);
			assertFalse("Nessun libro trovato", books.length == 0);
			
			for (BookInfo myBook : books) {
				myBook.setAuthor("J.D. Salinger");
				request.setAuthor("J.D. Salinger");
				// step 2: open the link associated with the title and filter by author  
				BookInfo[] bookLocations = PanizziSearchMgr.searchAvailableEditions(request, myBook);
				
				assertNotNull(bookLocations);
				assertFalse("Nessun libro trovato", ArrayUtils.isEmpty(bookLocations));
			
				for (BookInfo aBook : bookLocations) {
					List<BookInfo> booksComplete = PanizziSearchMgr.getEditionCompleteInfo(aBook);
					
					booksComplete = PanizziSearchMgr.getAvailability(booksComplete);
					
					assertNotNull(booksComplete);
					assertFalse("Nessun libro trovato per la libreria selezionata", booksComplete.size() == 0);
					
					for (BookInfo aBookComplete : booksComplete) {
						System.out.println("informazioni complete: " + aBookComplete);
					}
					break;
				}
			}
			
		} catch (Exception exc) {
			exc.printStackTrace(System.err);
			fail("Errore nella ricerca! ");
		}
	}

}
