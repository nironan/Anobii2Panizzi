package it.anobii2panizzi;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import it.anobii2library.request.PanizziSearchMgr;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.Tag;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.xml.sax.XMLReader;

public class AnobiiPanizzi {


	public static void main(String args[]) {
		try {
			//TODO split the single requests as threads for performance tuning
			
			// 1. loads the wishlist and creates the appropriate requests
			Workbook wb = WorkbookFactory.create(new File(Config.FILE_LOCATION));
			Sheet sh = wb.getSheetAt(0);
			
			int counter = 0;
			
			for (Row row : sh) {
				
				counter++;
				if (counter == 1) {
					continue;
				} else if (counter == 11) {
					break;
				}
				
				Book request = new Book();
				request.setName(row.getCell(2).getStringCellValue().trim());
				BookInfo[] books = PanizziSearchMgr.searchBook(request);
				
				assertNotNull(books);
				assertFalse("Nessun libro trovato", books.length == 0);
				
				for (BookInfo myBook : books) {
					myBook.setAuthor(row.getCell(4).getStringCellValue().trim());
					// step 2: open the link associated with the title and filter by author  
					BookInfo[] bookLocations = PanizziSearchMgr.searchAvailableEditions(request, myBook);
					
					assertNotNull(bookLocations);
					assertFalse("Nessun libro trovato", ArrayUtils.isEmpty(bookLocations));
				
					for (BookInfo aBook : bookLocations) {
						List<BookInfo> booksComplete = PanizziSearchMgr.getEditionCompleteInfo(aBook);
						
//						assertNotNull(booksComplete);
//						assertFalse("Nessun libro trovato per la libreria selezionata", booksComplete.size() == 0);
						if (booksComplete != null && booksComplete.size() > 0) {
							row.getCell(9).setCellValue(booksComplete.get(0).getLibrary());;
							System.out.println("informazioni complete: " + booksComplete.get(0));
							break;
						}
					}
				}
			}
			  
			// 2. fills in the book informations
			
			// 3. writes the book details to the wishlist

			
		} catch (Exception exc) {
			exc.printStackTrace(System.err);
		}

	}

}
