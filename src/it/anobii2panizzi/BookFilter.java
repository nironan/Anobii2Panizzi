package it.anobii2panizzi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class BookFilter {

	/**
	 * Filtra i libri passati in input
	 * 
	 * @return
	 */
	public static BookInfo[] filterBookByName(BookInfo[] results, Book request) {
		// oggetto di output a cui aggiungere i candidati
		List<BookInfo> bookList = new ArrayList<BookInfo>();
		
		// ciclo tutti i candidati
		for (BookInfo aBook : results) {
			if (StringUtils.equalsIgnoreCase(aBook.getName(), request.getName())) {
				bookList.add(aBook);
			}
		}
		
		// se ho trovato più di un risultato provo a fare ulteriori filtri..
		if (bookList.size() > 0) {
			List<BookInfo> bookListCleaned = new ArrayList<BookInfo>();
			for (BookInfo aBook : bookList) {
				if (StringUtils.equalsIgnoreCase(aBook.getAuthor(), request.getAuthor())) {
					bookListCleaned.add(aBook);
				}
			}
			if (bookListCleaned.size() > 0 && bookListCleaned.size() != bookList.size()) {
				bookList = bookListCleaned;
			}
		}
		
		return bookList.toArray(new BookInfo[bookList.size()]);
	}
	
	private static String cleanAuthorName(String author) {
		String out = author;
		if (out != null) {
			if (out.indexOf("; ") > 0) {
				out = out.substring(0, out.indexOf("; "));
			}
			out = StringUtils.replace(out, " ", "");
			out = StringUtils.replace(out, "	", "");
		}
		
		return out;
	}
	
	/**
	 * Filtra i libri passati in input
	 * 
	 * @return
	 */
	public static BookInfo[] filterBookByAuthor(BookInfo[] results, Book request) {
		// oggetto di output a cui aggiungere i candidati
		List<BookInfo> bookList = new ArrayList<BookInfo>();
		
		// ciclo tutti i candidati
		String reqAuthor = cleanAuthorName(request.getAuthor());
		
		for (BookInfo aBook : results) {
			
			if (StringUtils.equalsIgnoreCase(cleanAuthorName(aBook.getAuthor()), reqAuthor)) {
				bookList.add(aBook);
			}
		}
		
		if (bookList.isEmpty() && request.getAuthor().contains(" ")) {
			String lastAuthorNameReq = request.getAuthor().substring(request.getAuthor().lastIndexOf(" ") + 1);
			
			// riciclo di nuovo tutti i candidati
			for (BookInfo aBook : results) {
				
				String lastAuthorNameCurrentBook = aBook.getAuthor().substring(request.getAuthor().lastIndexOf(" ") + 1);
				
				if (StringUtils.equalsIgnoreCase(lastAuthorNameCurrentBook, lastAuthorNameReq)) {
					bookList.add(aBook);
				}
			}
			
		}
		
		// TODO da buttare via, e da aggiungere un filtro successivo a seconda della disponibilità...
		
		// se ho trovato più di un risultato provo a fare ulteriori filtri..
		if (bookList.size() > 0) {
			List<BookInfo> bookListCleaned = new ArrayList<BookInfo>();
			for (BookInfo aBook : bookList) {
				if (StringUtils.equalsIgnoreCase(aBook.getAuthor(), request.getAuthor())) {
					bookListCleaned.add(aBook);
				}
			}
			if (bookListCleaned.size() > 0 && bookListCleaned.size() != bookList.size()) {
				bookList = bookListCleaned;
			}
		}
		
		return bookList.toArray(new BookInfo[bookList.size()]);
	}
}
