package it.anobii2panizzi;

import it.anobii2library.request.PanizziSearchMgr;

public class SearchEngine {

	/**
	 * Ricerca un libro a partire da un nome
	 * 
	 * @param bookName
	 * @return
	 * @throws Exception
	 */
	public static Book[] searchBookByName(String bookName) throws Exception {
		
		Book filter = new Book();
		filter.setName(bookName.toUpperCase());
		BookInfo[] out = PanizziSearchMgr.searchBook(filter);
		
		out = BookFilter.filterBookByName(out, filter);
		
		return out;
	}
	
	
}
