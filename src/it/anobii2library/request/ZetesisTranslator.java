package it.anobii2library.request;

import it.anobii2panizzi.BookInfo;
import it.anobii2panizzi.Config;

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
					if (nameAuthorStrings.length > 1) { //TODO: handle also this type of string: [<b>Salinger, J. D.</b>, Il giovane Holden, 2002] 
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

//	private static final String INIZIO_TAG_NOME_BIBLIOTECA = "&nbsp;&nbsp;&nbsp;<font size="+1">";
	// single result even though many instances may be present - used to bypass Zetesis' crazy 
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
					
					String inventory = el.nextElementSibling().nextElementSibling().text();
					if (inventory.indexOf("Inventario: ") >= 0) {
						inventory = inventory.substring(12);
					}
					newBook.setInventory(inventory.trim());
				}
				out.add(newBook);				
			}
			
			elements = doc.select("input[name=HTZMNP]").iterator();
			if (elements != null && elements.hasNext()) {
				org.jsoup.nodes.Element el = elements.next();
				
				for (BookInfo aBook : out) {
					aBook.setAvailabilityHref(el.val());
				}
			}

		} finally {
			IOUtils.closeQuietly(bos);
		}
		
		return out;
	}
	

	protected static List<BookInfo> getBookAvailability(byte[] response, List<BookInfo> bookReqs)
			throws Exception {

		List<BookInfo> out = new ArrayList<BookInfo>();
		ByteArrayInputStream bos = null;
		try {
			bos = new ByteArrayInputStream(response);

			Document doc = Jsoup.parse(new String(response));
			Iterator<org.jsoup.nodes.Element> elements = doc.select("li").iterator();
			
			while (elements.hasNext()) {
				org.jsoup.nodes.Element el = elements.next();
				String elText = el.text();
				String availability = elText.substring(elText.indexOf("===>")+4);
				String libraryName = elText.substring(0, elText.indexOf("Coll")).trim();
				String inventory = elText.substring(elText.indexOf("Inv. ") + 5, elText.indexOf("===>")).trim();
				for (BookInfo aBook : bookReqs) { //TODO usare l'inventory!?
//					if (Config.areLibrariesEquivalent(libraryName, aBook.getLibrary())) {
					if (inventory.equalsIgnoreCase(aBook.getInventory())) {
						aBook.setAvailability(availability);
						out.add(aBook);
						break;
					}
				}
			}
			
		} finally {
			IOUtils.closeQuietly(bos);
		}
		return out;
	}
}
