package it.anobii2library.request;

import java.util.List;

import it.anobii2panizzi.Book;
import it.anobii2panizzi.BookFilter;
import it.anobii2panizzi.BookInfo;

public class PanizziSearchMgr {
	public static BookInfo[] searchBook(Book bookReq) throws Exception {
		BookInfo[] out = null;

		// la classe book in ingresso va compilata con i filtri per la ricerca. Vado dal meno preciso al più preciso
		if (bookReq.getName() != null) {
			byte[] authorResponse = ZetesisClient.searchBookByTitle(bookReq.getName());
			
			out = ZetesisTranslator.getBooksFromResponse(authorResponse);
		}
		
		out = BookFilter.filterBookByName(out, bookReq);
		
		return out;
	}
	
	public static BookInfo[] searchAvailableEditions(Book originalReq, BookInfo bookReq) throws Exception {
		BookInfo[] out = null;
		
		// la classe book in ingresso deve già avere i riferimenti completi (URL / id / etc..)
		if (bookReq != null && bookReq.getName() != null) {
			byte[] authorResponse = ZetesisClient.searchAvailableEditions(bookReq);
			
			out = ZetesisTranslator.getBookEditions(authorResponse);
		}
		
		out = BookFilter.filterBookByAuthor(out, bookReq);
		
		return out;
	}
	
	public static List<BookInfo>  getEditionCompleteInfo(BookInfo bookReq) throws Exception {
		// la classe book in ingresso deve già avere i riferimenti completi (URL / id / etc..)
		List<BookInfo> out = null;
		if (bookReq != null && bookReq.getName() != null) {
			byte[] authorResponse = ZetesisClient.getBookCompleteInfo(bookReq);
			
			out = ZetesisTranslator.getBookCompleteInfo(authorResponse, bookReq);
		}
		
		out = BookFilter.filterBookByLibrary(out);
		
		return out;
	}
}
