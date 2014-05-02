/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.usps.service.endpoint;

import Data.TrackingStatus;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.restlet.Client;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author will
 */
@WebService
public class USPSTrackingServiceSOAP {

    private String endpoint = "http://production.shippingapis.com/ShippingAPITest.dll?API=TrackV2&XML=";
   
    private HashMap<String,Integer> months=new HashMap() {};
    private String[] month_names={"January","February","March","April","May","June","July","August","September","November","December"};
    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        USPSTesting ut = new USPSTesting();
//        ut.getTrackingInfo("9114901159818909415173", "77009", "2014-02-07");
//
//    }

    @WebMethod
    public ArrayList<TrackingStatus> getTrackingInfo(String trackingNum, String zipCode, String mailingDate) {
        //before doing anything, fill the Map object with month information
        ArrayList<TrackingStatus> al_ts = new ArrayList();
        int month_counter = 1;
        for(String month: month_names)
        {
            months.put(month, month_counter);
            month_counter++;
        }
        
        //get the information for the package
        
        HttpURLConnection conn;

        
        String url_string = endpoint + "<TrackFieldRequest USERID=\"515PENNS3107\">"
                + "<TrackID ID=\"" + trackingNum + "\">"
                + "<DestinationZipCode>" + zipCode + "</DestinationZipCode>"
                + "<MailingDate>" + mailingDate + "</MailingDate>"
                + "</TrackID></TrackFieldRequest>";

       
//            URL url = new URL(url_string);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.connect();
//            print_and_parse(conn, true);
            System.out.println(url_string);
            this.storeInformation(url_string, al_ts);
            
            return al_ts;

    }

    
//    public static Document getDom4jDocument(org.w3c.dom.Document w3cDocument) {
//        //System.out.println("XMLUtility : Inside getDom4jDocument()");
//        org.dom4j.Document dom4jDocument = null;
//        DOMReader xmlReader = null;
//        try {
//            //System.out.println("Before conversion of w3cdoc to dom4jdoc");
//            xmlReader = new DOMReader();
//            dom4jDocument = xmlReader.read(w3cDocument);
//            //System.out.println("Conversion complete");
//        } catch (Exception e) {
//            System.out.println("General Exception :- " + e.getMessage());
//        }
//        //System.out.println("XMLUtility : getDom4jDocument() Finished");
//        return dom4jDocument;
//    }

    public void storeInformation(String url, ArrayList<TrackingStatus> al_ts) {
        Request request = new Request();
        request.setResourceRef(url);

        Client c = new Client(Protocol.HTTP);

        Response response;

        request.setMethod(Method.GET);
        request.setEntity(null);
        
        response = get_response(c, request);
        
        try {
            dump(response, al_ts);
        } catch (IOException ex) {
            System.out.println("OH NO SHIT HAPPENED");
        }
    }

    private Response get_response(Client client, Request request) {
        return client.handle(request);
    }

    private void dump(Response response, ArrayList<TrackingStatus> al_ts) throws IOException {
        try {
            if (response.getStatus().isSuccess()) {
                //response.getEntity().write(System.out);
                Document doc = getDom4jDocument(response.getEntityAsDom().getDocument());

                Element root = doc.getRootElement();

                for (Iterator t = root.elementIterator(); t.hasNext();) {
                    Element trackInfo = (Element) t.next();

                    for (Iterator i = trackInfo.elementIterator("TrackDetail"); i.hasNext();) {
                        TrackingStatus ts = new TrackingStatus();
                        Element element = (Element) i.next();
                        String date = ((Element) element.elementIterator("EventDate").next()).getText();
                        String[] date_a = date.split(",");
                        String[] monthDay = date_a[0].split(" ");
                        Date d = new Date(Integer.parseInt(date_a[1].replaceAll("\\s+","")),
                                        months.get(monthDay[0]),
                                        Integer.parseInt(monthDay[1]));
                        ts.setStatusDate(d);
                        ts.setStatusCity(((Element) element.elementIterator("EventCity").next()).getText());
                        ts.setStatusState(((Element) element.elementIterator("EventState").next()).getText());
                        
                        al_ts.add(ts);
                    }
                }

            } else {
                System.err.println(response.getStatus().getDescription());
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static org.dom4j.Document getDom4jDocument(org.w3c.dom.Document w3c) {
        //System.out.println("XMLUtility : Inside getDom4jDocument()");
        org.dom4j.Document dom4jDocument = null;
        DOMReader xmlReader = null;
        try {
            //System.out.println("Before conversion of w3cdoc to dom4jdoc");
            xmlReader = new DOMReader();
            dom4jDocument = xmlReader.read((org.w3c.dom.Document) w3c);
            //System.out.println("Conversion complete");
        } catch (Exception e) {
            System.out.println("General Exception :- " + e.getMessage());
        }
        //System.out.println("XMLUtility : getDom4jDocument() Finished");
        return dom4jDocument;
    }

}
