package it.anobii2library.request;

import static org.junit.Assert.*;
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
			Book request = new Book();
			request.setName("il GIOVANE holden");
			BookInfo[] books = PanizziSearchMgr.searchBook(request);
			
			assertNotNull(books);
			assertFalse("Nessun libro trovato", books.length == 0);
			
			books[0].setAuthor("J.D. Salinger");
			
			BookInfo[] bookLocations = PanizziSearchMgr.searchAvailableEditions(request, books[0]);
			
			assertNotNull(bookLocations);
			assertFalse("Nessun libro trovato", ArrayUtils.isEmpty(bookLocations));
		
			PanizziSearchMgr.getEditionCompleteInfo(bookLocations[0]);
			
			System.out.println("informazioni complete: " + bookLocations[0]);
			
		} catch (Exception exc) {
			exc.printStackTrace(System.err);
			fail("Errore nella ricerca! ");
		}
	}

}
