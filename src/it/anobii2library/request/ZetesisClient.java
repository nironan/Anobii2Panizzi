package it.anobii2library.request;

import it.anobii2panizzi.BookInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.apache.poi.poifs.property.NPropertyTable;

public class ZetesisClient {
	/**
	 * Prima chiamata: esegue la ricerca di un libro a partire dal nome. Equivale a ricercare per titolo dalla home;
	 * restituirà un array di Libri 
	 * 
	 * @param bookName
	 * @return
	 * @throws Exception
	 */
	protected static byte[] searchBookByTitle(String bookName) throws Exception {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("WCI", "Browse"));
		formparams.add(new BasicNameValuePair("WCE", "MENU"));
		formparams.add(new BasicNameValuePair("HTZMNP",
				"HTMLMOVE=html/catalogo_i.htm,;BROWSE=TITOLI,0,1,1,10"));
		formparams.add(new BasicNameValuePair("HTZLKP", ""));
		formparams
				.add(new BasicNameValuePair("HTZEXT",
						"LNG=ITA;HLP=WWW-BROWSE-HLP,1;WDB=DB1;UID=111227_15150D33"));
		formparams.add(new BasicNameValuePair("HTZFNF", bookName));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
				"UTF-8");
		HttpPost httpPost = new HttpPost(
				"http://cataloghi.comune.re.it/Cataloghi/Zetesis.ASP?WCI=Browse&WCE=MENU");
		httpPost.setEntity(entity);

		return new DefaultHttpClient().execute(httpPost, createResponseHandler());
	}
	
	/**
	 * Seconda chiamata: a partire da un titolo e da una href, simula il click sul collegamento per andare nel dettaglio del
	 * libro ed avere tutte le locations possedute dal sistema bibliotecario
	 * 
	 * @param bookName
	 * @return
	 * @throws Exception
	 */
	protected static byte[] searchAvailableEditions(BookInfo bookInfo) throws Exception {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("HTZLKP", bookInfo.getHref()));
		formparams.add(new BasicNameValuePair("HTZEXT", "LNG=ITA;HLP=WWW-BROWSE-HLP,1;WDB=DB1;UID=111227_15150D33"));
		
		String hrefSubposition = bookInfo.getHrefSubPosition();
		
		formparams.add(new BasicNameValuePair("HTZMNP",
				"HTMLMOVE=html/catalogo_i.htm,;BROWSE=TITOLI,0," + hrefSubposition + "," + hrefSubposition + ",14"));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
				"UTF-8");
		HttpPost httpPost = new HttpPost(
				"http://cataloghi.comune.re.it/Cataloghi/Zetesis.ASP?WCI=Browse&WCE=MENU");
		httpPost.setEntity(entity);

		return new DefaultHttpClient().execute(httpPost, createResponseHandler());
	}
	
	/**
	 * Terza chiamata: a partire da un titolo, da una href e da una posizione, simula il click sulla posizione per caricare  
	 * la posizione Dewey all'interno delle singole biblioteche
	 * 
	 * @param bookName
	 * @return
	 * @throws Exception
	 */
	protected static byte[] getBookCompleteInfo(BookInfo bookInfo) throws Exception {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		
		formparams.add(new BasicNameValuePair("HTZLKP", bookInfo.getHref()));
		formparams.add(new BasicNameValuePair("HTZEXT", "LNG=ITA;HLP=WWW-BROWSE-HLP,1;WDB=DB1;UID=111227_15150D33"));
		
		String hrefSubposition = bookInfo.getHrefSubPosition();
		
		formparams.add(new BasicNameValuePair("HTZMNP",
				"HTMLMOVE=html/catalogo_i.htm,;BROWSE=TITOLI,0," + hrefSubposition + "," + hrefSubposition + ",14"
				+";BROWSE=MAIN,0," + hrefSubposition + "," + hrefSubposition + ",14"));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
				"UTF-8");
		HttpPost httpPost = new HttpPost(
				"http://cataloghi.comune.re.it/Cataloghi/Zetesis.ASP?WCI=Browse&WCE=MENU");
		httpPost.setEntity(entity);
		
		return new DefaultHttpClient().execute(httpPost, createResponseHandler());
	}
	/**
	 * Quarta chiamata: a partire da un titolo, da una href e da una posizione, simula il click sul link "Disponibilità"
	 * per verificare la giacenza del libro
	 * 
	 * @param bookName
	 * @return
	 * @throws Exception
	 */
	protected static byte[] getBookAvailability(BookInfo bookInfo) throws Exception {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		
		formparams.add(new BasicNameValuePair("HTZEXT", "LNG=ITA;HLP=WWW-BROWSE-HLP,1;WDB=DB1;UID=111227_15150D33"));
		formparams.add(new BasicNameValuePair("HTZFMF", "2"));
		formparams.add(new BasicNameValuePair("HTZOWN.x", "9"));
		formparams.add(new BasicNameValuePair("HTZOWN.y", "16"));
		
		String hrefSubposition = bookInfo.getHrefSubPosition();
		
		formparams.add(new BasicNameValuePair("HTZMNP",
				"HTMLMOVE=html/catalogo_i.htm,;BROWSE=TITOLI,0," + hrefSubposition + "," + hrefSubposition + ",14"
				+";BROWSE=MAIN,0," + hrefSubposition + "," + hrefSubposition + ",14;RECORD=" + bookInfo.getRecordForAvailabilityHref()));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
				"UTF-8");
		HttpPost httpPost = new HttpPost(
				"http://cataloghi.comune.re.it/Cataloghi/Zetesis.ASP?WCI=Record&WCE=MENU");
		httpPost.setEntity(entity);
		
		return new DefaultHttpClient().execute(httpPost, createResponseHandler());
		
		
		/*List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("WCI", "Browse"));
		formparams.add(new BasicNameValuePair("WCE", "MENU"));
		formparams.add(new BasicNameValuePair("HTZEXT", "FMT=MAIN,2;LNG=ITA;HLP=WWW-BROWSE-HLP,1;WDB=DB1;UID=111227_15150D33"));
		formparams.add(new BasicNameValuePair("HTZFMF", "2"));
		
		String hrefSubposition = bookInfo.getHrefSubPosition();
		
		formparams.add(new BasicNameValuePair("HTZMNP",
				"HTMLMOVE=html/catalogo_i.htm,;BROWSE=TITOLI,0," + hrefSubposition + "," + hrefSubposition + ",14"
				+";BROWSE=MAIN,0," + hrefSubposition + "," + hrefSubposition + ",14"
				+";RECORD=MAIN,1," + hrefSubposition + ",1,13,0"));

//<input type="hidden" name="HTZMNP" value="BROWSE=TITOLI,0,177328,177328,14;BROWSE=MAIN,1,442981,442981,14;RECORD=MAIN,1,442981,1,13,0">

//<input type="image" name="HTZOWN" src="images/bt_loan.gif">

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
				"UTF-8");
		HttpPost httpPost = new HttpPost(
				"http://cataloghi.comune.re.it/Cataloghi/Zetesis.ASP?WCI=Record&WCE=MENU");
		httpPost.setEntity(entity);
		
		return new DefaultHttpClient().execute(httpPost, createResponseHandler());*/
	}
	
	private static ResponseHandler<byte[]> createResponseHandler() {
		return new ResponseHandler<byte[]>() {
			public byte[] handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toByteArray(entity);
				} else {
					return null;
				}
			}
		};
	}
	
}
