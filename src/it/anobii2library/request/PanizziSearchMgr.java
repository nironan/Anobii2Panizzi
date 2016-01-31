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
			byte[] rawResponse = ZetesisClient.searchBookByTitle(bookReq.getName());
			
			out = ZetesisTranslator.getBooksFromResponse(rawResponse);
		}
		
		out = BookFilter.filterBookByName(out, bookReq);
		
		return out;
	}
	
	public static BookInfo[] searchAvailableEditions(Book originalReq, BookInfo bookReq) throws Exception {
		BookInfo[] out = null;
		
		// la classe book in ingresso deve già avere i riferimenti completi (URL / id / etc..)
		if (bookReq != null && bookReq.getName() != null) {
			byte[] rawResponse = ZetesisClient.searchAvailableEditions(bookReq);
			
			out = ZetesisTranslator.getBookEditions(rawResponse);
		}
		
		out = BookFilter.filterBookByAuthor(out, bookReq);
		
		return out;
	}
	
	public static List<BookInfo> getEditionCompleteInfo(BookInfo bookReq) throws Exception {
		// la classe book in ingresso deve già avere i riferimenti completi (URL / id / etc..)
		List<BookInfo> out = null;
		if (bookReq != null && bookReq.getName() != null) {
			byte[] rawResponse = ZetesisClient.getBookCompleteInfo(bookReq);
			
			out = ZetesisTranslator.getBookCompleteInfo(rawResponse, bookReq);
		}
		
		return out;
	}
	
	public static List<BookInfo> getAvailability(List<BookInfo> bookReq) throws Exception {
		// la classe book in ingresso deve già avere i riferimenti completi (URL / id / etc..)
		List<BookInfo> out = null;
		if (bookReq != null) {
			byte[] rawResponse = ZetesisClient.getBookAvailability(bookReq.get(0));
			out = ZetesisTranslator.getBookAvailability(rawResponse, bookReq);
		}
		
		return BookFilter.filterBookByLibrary(out);
	}
}
