package services.fedEx.service.endpoint;


import com.fedex.track.stub.Address;
import com.fedex.track.stub.ClientDetail;
import com.fedex.track.stub.CompletedTrackDetail;
import com.fedex.track.stub.NotificationSeverityType;
import com.fedex.track.stub.TrackDetail;
import com.fedex.track.stub.TrackEvent;
import com.fedex.track.stub.TrackIdentifierType;
import com.fedex.track.stub.TrackPackageIdentifier;
import com.fedex.track.stub.TrackPortType;
import com.fedex.track.stub.TrackReply;
import com.fedex.track.stub.TrackRequest;
import com.fedex.track.stub.TrackRequestProcessingOptionType;
import com.fedex.track.stub.TrackSelectionDetail;
import com.fedex.track.stub.TrackServiceLocator;
import com.fedex.track.stub.TransactionDetail;
import com.fedex.track.stub.VersionId;
import com.fedex.track.stub.WebAuthenticationCredential;
import com.fedex.track.stub.WebAuthenticationDetail;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import resolver.NSResolver;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Geiger
 */

@WebServiceProvider
@BindingType(value = HTTPBinding.HTTP_BINDING)
public class TrackWebServiceREST implements Provider<Source> {
    
     private final String uri = "urn:fib";
     private static String accountNumber = "546238466";
    private static String meterNumber = "106440305";
    private static String key = "YfIuzhkW37le4oR0";
   private static String password ="f6oeTgQxe2WPOHQAXvjaKWwCa";
   private final String xml_start = "<fib:response xmlns:fib = 'urn:fib'>";
    private final String xml_stop = "</fib:response>";
    
    @Resource
    protected WebServiceContext ws_ctx;
    
     public Source invoke(Source request) {
        // Filter on the HTTP request verb
        if (ws_ctx == null) throw new RuntimeException("DI failed on ws_ctx.");

        // Grab the message context and extract the request verb.
        MessageContext msg_ctx = ws_ctx.getMessageContext();
        String http_verb = (String) msg_ctx.get(MessageContext.HTTP_REQUEST_METHOD);
        http_verb = http_verb.trim().toUpperCase();

        // Act on the verb.
        if      (http_verb.equals("POST"))    return doPost(request);
        else throw new HTTPException(405);   // bad verb exception
    }
    
    private Source doPost(Source req) {
        
        //Sooooo use the verb "POST" to actually get work done...
        String trackingNumber= extract_request(req);
        System.out.println("here");
        System.out.println("Tracking number: "+trackingNumber );
        
        TrackRequest request = new TrackRequest();

        //origninal code: --skip--
        //request.setClientDetail(createClientDetail());
        ClientDetail cd = new ClientDetail();
        //set the login information to get access to FedEx's web service
        cd.setAccountNumber(TrackWebServiceREST.accountNumber);
        cd.setMeterNumber(TrackWebServiceREST.meterNumber);
        
        //Original Code: --skip--
//        request.setWebAuthenticationDetail(createWebAuthenticationDetail());
        WebAuthenticationCredential wac = new WebAuthenticationCredential();
        wac.setKey(TrackWebServiceREST.key);
        wac.setPassword(TrackWebServiceREST.password);
        WebAuthenticationDetail wad = new WebAuthenticationDetail(wac);
        
        request.setClientDetail(cd);
        request.setWebAuthenticationDetail(wad);
        //
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setCustomerTransactionId("java sample - Tracking Request"); //This is a reference field for the customer.  Any value can be used and will be provided in the response.
        request.setTransactionDetail(transactionDetail);
 
        //
        VersionId versionId = new VersionId("trck",8, 0, 0);
        request.setVersion(versionId);
        //
        TrackSelectionDetail selectionDetail=new TrackSelectionDetail();
        TrackPackageIdentifier packageIdentifier=new TrackPackageIdentifier();
        packageIdentifier.setType(TrackIdentifierType.TRACKING_NUMBER_OR_DOORTAG);
        
        packageIdentifier.setValue(trackingNumber);
        
        
        selectionDetail.setPackageIdentifier(packageIdentifier);
        request.setSelectionDetails(new TrackSelectionDetail[] {selectionDetail});
        TrackRequestProcessingOptionType processingOption=TrackRequestProcessingOptionType.INCLUDE_DETAILED_SCANS;
        request.setProcessingOptions(new TrackRequestProcessingOptionType[]{processingOption});
    String s = new String();
	    //
		try {
			// Initializing the service
			TrackServiceLocator service;
			TrackPortType port;
                        
			//
			service = new TrackServiceLocator();
			//updateEndPoint(service);
			port = service.getTrackServicePort();
		    //
			TrackReply reply = port.track(request); // This is the call to the web service passing in a request object and returning a reply object
			//
			if (isResponseOk(reply.getHighestSeverity())) // check if the call was successful
			{
				s = formTrackingString(reply.getCompletedTrackDetails());
			}
                        System.out.println(s);
//                        else
//                        {
//                            System.out.println("FAIL");
//                        }
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	
        
        String xml = xml_start + s+ xml_stop;
        return make_stream_source(xml);
    }
    
    private static boolean isResponseOk(NotificationSeverityType notificationSeverityType) {
		if (notificationSeverityType == null) {
			return false;
		}
		if (notificationSeverityType.equals(NotificationSeverityType.WARNING) ||
			notificationSeverityType.equals(NotificationSeverityType.NOTE)    ||
			notificationSeverityType.equals(NotificationSeverityType.SUCCESS)) {
			return true;
		}
 		return false;
	}
    
    private String formTrackingString(CompletedTrackDetail[] ctd){
        String trackingData= new String();
        
        for (int i=0; i< ctd.length; i++){
            TrackDetail[] td = ctd[i].getTrackDetails();
            TrackEvent[] events=td[i].getEvents();
            for(int j=0;j<events.length;j++){
                TrackEvent event = events[j];
                //get the time of the last event...
                Calendar c = event.getTimestamp();
                int month = c.get(Calendar.MONTH)+1;
                int day = c.get(Calendar.DAY_OF_MONTH);
		int year = c.get(Calendar.YEAR);
		String date = new String(year + "-" + month + "-" + day);
                
                //get the addess of the last event
                Address a = event.getAddress();
                String city = a.getCity();
                String zip = a.getPostalCode();
                String state = a.getStateOrProvinceCode();
                
                trackingData= trackingData+"~"+date+","+city+","+state+","+zip;
            }
        }
        
        return trackingData;
        
    }
    
      private StreamSource make_stream_source(String msg) {
        System.out.println(msg);
        ByteArrayInputStream stream = new ByteArrayInputStream(msg.getBytes());
        return new StreamSource(stream);
    }
      
      private String extract_request(Source request) {
        String request_string = null;
        try {
            DOMResult dom_result = new DOMResult();
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(request, dom_result);

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xp = xpf.newXPath();
            xp.setNamespaceContext(new NSResolver("fib", uri));
            request_string = xp.evaluate("/fib:request", dom_result.getNode());
        }
        catch(TransformerConfigurationException e) { System.err.println(e); }
        catch(TransformerException e) { System.err.println(e); }
        catch(XPathExpressionException e) { System.err.println(e); }

        return request_string;
      }
      
    
}
