package it.anobii2panizzi;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import it.anobii2library.request.PanizziSearchMgr;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
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
			Workbook wb = WorkbookFactory.create(new File(Config.INPUT_FILE_LOCATION));
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
				
				if (books == null || ArrayUtils.isEmpty(books)) {
					System.out.println("Non trovato LIBRO " + request);
					continue;
				}
				
				for (BookInfo myBook : books) {
					myBook.setAuthor(row.getCell(4).getStringCellValue().trim());
					// step 2: open the link associated with the title and filter by author  
					BookInfo[] bookLocations = PanizziSearchMgr.searchAvailableEditions(request, myBook);
					
					if (bookLocations == null || ArrayUtils.isEmpty(bookLocations)) {
						System.out.println("Non trovato AUTORE " + request);
						continue;
					}
				
					for (BookInfo aBook : bookLocations) {
						List<BookInfo> booksComplete = PanizziSearchMgr.getEditionCompleteInfo(aBook);
						
						booksComplete = PanizziSearchMgr.getAvailability(booksComplete);
						
						if (booksComplete != null && booksComplete.size() > 0) {
							// TODO if one of the instances is available, take that one!
							row.createCell(12).setCellValue(booksComplete.get(0).getLibrary());
							row.createCell(13).setCellValue(booksComplete.get(0).getAvailability());;
							System.out.println("informazioni complete: " + booksComplete.get(0));
							break;
						}
					}
				}
			}
			FileOutputStream out = new FileOutputStream(Config.OUTPUT_FILE_LOCATION);
		    wb.write(out);
		    out.close();
			
		} catch (Exception exc) {
			exc.printStackTrace(System.err);
		}

	}

}
