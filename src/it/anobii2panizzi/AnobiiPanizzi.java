package it.anobii2panizzi;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.Tag;

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
import org.xml.sax.XMLReader;

public class AnobiiPanizzi {


	public static void main(String args[]) {
		try {
			
//			while (Element el : tagList) {
//				
//			}
//			
//			tagList.get(0).getParentElement();
//			
//			System.out.println("tagList.size(): " + (tagList != null ? tagList.size() : "null"));
			
		} catch (Exception exc) {
			exc.printStackTrace(System.err);

		}

	}

}
