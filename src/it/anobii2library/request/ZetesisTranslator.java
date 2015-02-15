package it.anobii2library.request;

import it.anobii2panizzi.BookInfo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import net.htmlparser.jericho.TagType;
import net.htmlparser.jericho.TextExtractor;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ZetesisTranslator {

	protected static BookInfo[] getBooksFromResponse(byte[] response)
			throws Exception {
		BookInfo[] out = null;

		ByteArrayInputStream bos = null;
		try {
			bos = new ByteArrayInputStream(response);
			Source jerichoSource = new Source(bos);
			jerichoSource.fullSequentialParse();
			// cerco tutti i link con HTZBLK
			// jerichoSource.
			List<Element> tagList = jerichoSource.getAllElements("name",
					"HTZBLK", false);
			// jerichoSource.getAllElements();
			if (tagList != null) {
				out = new BookInfo[tagList.size()];

				int i = 0;
				for (Element tag : tagList) {
					out[i] = new BookInfo();
					out[i].setHref(tag.getAttributeValue("href"));
					out[i].setName(tag.getParentElement()
							.getFirstElement("font").getContent().toString()
							.trim());

					i++;
				}
			}

		} finally {
			IOUtils.closeQuietly(bos);
		}
		return out;
	}

	protected static BookInfo[] getBookEditions(byte[] response)
			throws Exception {
		BookInfo[] out = null;

		ByteArrayInputStream bos = null;
		try {
			bos = new ByteArrayInputStream(response);
			Source jerichoSource = new Source(bos);
			jerichoSource.fullSequentialParse();
			// cerco tutti i link con HTZBLK
			// jerichoSource.
			List<Element> tagList = jerichoSource.getAllElements("name",
					"HTZBLK", false);
			// jerichoSource.getAllElements();
			if (tagList != null) {
				out = new BookInfo[tagList.size()];

				int i = 0;
				for (Element tag : tagList) {
					out[i] = new BookInfo();
					out[i].setHref(tag.getAttributeValue("href"));

					String bookInfoStr = tag.getParentElement()
							.getFirstElement("font").getContent().toString()
							.trim();

					// out[i].setAuthor(bookInfoStr.substring(bookInfoStr.indexOf(TAG_B)
					// + TAG_B.length(), bookInfoStr.indexOf(TAG_B_END)));

					String[] bookStrings = bookInfoStr.split("<br>");
					String[] nameAuthorStrings = bookStrings[1].split("/");

					out[i].setName(StringUtils.trimToNull(nameAuthorStrings[0]));
					if (nameAuthorStrings.length > 1) {
						out[i].setAuthor(StringUtils
								.trimToNull(nameAuthorStrings[1]));
					}
					if (nameAuthorStrings.length > 2) {
						out[i].setYear(StringUtils.trimToNull(bookStrings[2]));
					}

					i++;
				}
			}

		} finally {
			IOUtils.closeQuietly(bos);
		}
		return out;
	}

	private static final String TAG_B = "<b>";
	private static final String TAG_B_END = "</b>";

//	private static final String INIZIO_TAG_NOME_BIBLIOTECA = "&nbsp;&nbsp;&nbsp;<font size="+1">";
	
	protected static List<BookInfo> getBookCompleteInfo(byte[] response, BookInfo bookReq)
			throws Exception {

		List<BookInfo> out = new ArrayList<BookInfo>();
		ByteArrayInputStream bos = null;
		try {
			bos = new ByteArrayInputStream(response);

			Document doc = Jsoup.parse(new String(response));
			Iterator<org.jsoup.nodes.Element> elements = doc.select("font[size=+1]").iterator();
			
			while (elements.hasNext()) {
				org.jsoup.nodes.Element el = elements.next();
				BookInfo newBook = (BookInfo) bookReq.clone();
				newBook.setLibrary(el.text());
				if (elements.hasNext()) {
					el = elements.next();
					newBook.setPosition(el.text());
				}
				out.add(newBook);				
			}
			
			
			
			//			Source jerichoSource = new Source(bos);
//			jerichoSource.fullSequentialParse();
			
			// cerco tutti i link con HTZBLK
			// jerichoSource.

//			TextExtractor te = new TextExtractor(jerichoSource) {
//				public boolean excludeElement(StartTag startTag) {
//					return false;
//				}
//			};
//			//TODO parsing secco
//			
//			System.out.println(te.setIncludeAttributes(true).toString());
//
//			String pageText = te.setIncludeAttributes(true).toString();
//			
//			int libraryStrIdx = pageText.indexOf("Biblioteca");
//			if (libraryStrIdx > -1) {
//				bookReq.setLibrary(pageText.substring(libraryStrIdx + "Biblioteca".length(), 
//						pageText.indexOf("Collocazione")));
//			}			
//			
//			int positionStrIdx = pageText.indexOf("Collocazione:");
//			if (positionStrIdx > -1) {
//				bookReq.setPosition(pageText.substring(positionStrIdx + "Collocazione:".length(), 
//						pageText.indexOf("Inventario")));
//			}			
//
//			int inventoryStrIdx = pageText.indexOf("Inventario:");
//			if (inventoryStrIdx > -1) {
//				bookReq.setInventory(pageText.substring(inventoryStrIdx + "Inventario:".length(), inventoryStrIdx + "Inventario:".length() + 12));
//			}
			
//			List<Element> tagList = jerichoSource.getAllElements("font");
//			if (tagList != null) {
//				out = new BookInfo();
//				String a = "";
//				for (Element aElem : tagList) {
//
//					List<Element> elemtni = aElem.getAllElements();
//					
////					List<Element> elem2 = aElem.getParentElement()
////							.getAllElements("b");
//
////					Element em = elem2.get(0);
//
//				}
//
//				// out.setHref(tag.getAttributeValue("href"));
//				// out.setName(tag.getParentElement().getFirstElement("font").getContent().toString().trim());
//			}

		} finally {
			IOUtils.closeQuietly(bos);
		}
		
		return out;
	}
}
