/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UPS;

import com.ups.wsdl.xoltws.track.v2.TrackErrorMessage;
import com.ups.wsdl.xoltws.track.v2.TrackPortType;
import com.ups.wsdl.xoltws.track.v2.TrackService;
import com.ups.xmlschema.xoltws.common.v1.RequestType;
import com.ups.xmlschema.xoltws.track.v2.ShipmentType;
import com.ups.xmlschema.xoltws.track.v2.TrackRequest;
import com.ups.xmlschema.xoltws.track.v2.TrackResponse;
import com.ups.xmlschema.xoltws.upss.v1.UPSSecurity;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Eric Komorek
 */
public class UPSClient {
   
   
   
   
     static TrackService service;
    
    private static final String LICENSE_NUMBER = "4CCFB7526E0494D6";
	private static final String USER_NAME = "erickomorek";
	private static final String PASSWORD = "Web Services";
	private static final String OUT_FILE_LOCATION = "./src/java/ups/XOLTResult.xml";
	private static final String TOOL_OR_WEB_SERVICE_NAME = "Carrier Pigeon";
	private static final String ENDPOINT_URL = "https://wwwcie.ups.com/webservices/Track";
    
    private static String statusCode = null;
	private static String description = null;
   /* private static Properties props = null;
        static {
    	props = new Properties();
    	try{
    		props.load(new FileInputStream("./build.properties"));
    	}catch (Exception e) {
			statusCode = e.getMessage();
			description = e.toString();
			updateResultsToFile(statusCode, description);
    		e.printStackTrace();
		}	
    }*/
    
    /**
     * @param args the command line arguments
     */
        
   public static final TrackResponse getUPSTrackResponse(String trackingNumber){
       
       return getRequest(trackingNumber);
   }
        
        
    private static TrackResponse getRequest(String trackingNumber) {
    	try {
        	service = new TrackService();
        	TrackPortType trackPortType = service.getTrackPort();
        	
        	BindingProvider bp = (BindingProvider)trackPortType;
         	bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ENDPOINT_URL);
      
            
         	TrackResponse trackResponse = trackPortType.processTrack(populateTrackRequest(trackingNumber), populateUPSSecurity());            
            statusCode = trackResponse.getResponse().getResponseStatus().getCode();
            description = trackResponse.getResponse().getResponseStatus().getDescription();
            updateResultsToFile(statusCode, description);
           
            return trackResponse;
            
        } catch(Exception e) {
        	if(e  instanceof TrackErrorMessage){
        		TrackErrorMessage trkErrorMessage = (TrackErrorMessage)e;
        		statusCode = trkErrorMessage.getFaultInfo().getErrorDetail().get(0).getPrimaryErrorCode().getCode();
        		description = trkErrorMessage.getFaultInfo().getErrorDetail().get(0).getPrimaryErrorCode().getDescription();
        		updateResultsToFile(statusCode, description);
        	} else {
        		statusCode = e.getMessage();
        		description = e.toString();
        		updateResultsToFile(statusCode, description);
        	}    		
            e.printStackTrace();
        }
        return null;
    }

    private static TrackRequest populateTrackRequest(String trackingNumber){
    	
    	TrackRequest trackRequest = new TrackRequest();
		RequestType request = new RequestType();
		
		List<String> requestOption = request.getRequestOption();
		requestOption.add("15");
		trackRequest.setRequest(request);			
		trackRequest.setInquiryNumber(trackingNumber);
		trackRequest.setTrackingOption("1");		
    	return trackRequest;
    }
    
    private static UPSSecurity populateUPSSecurity(){
    	UPSSecurity upss = new UPSSecurity();
		UPSSecurity.ServiceAccessToken upsSvcToken = new UPSSecurity.ServiceAccessToken();
		upsSvcToken.setAccessLicenseNumber(LICENSE_NUMBER);
		upss.setServiceAccessToken(upsSvcToken);
		UPSSecurity.UsernameToken upsSecUsrnameToken = new UPSSecurity.UsernameToken();
		upsSecUsrnameToken.setUsername(USER_NAME);
		upsSecUsrnameToken.setPassword(PASSWORD);
		upss.setUsernameToken(upsSecUsrnameToken);
		
		return upss;
    }
    
    /**
     * This method updates the XOLTResult.xml file with the received status and description
     * @param statusCode
     * @param description
     */
	   private static void updateResultsToFile(String statusCode, String description){
	    	BufferedWriter bw = null;
	    	try{    		
	    		
	    		File outFile = new File(OUT_FILE_LOCATION);
	    		System.out.println("Output file deletion status: " + outFile.delete());
	    		outFile.createNewFile();
	    		System.out.println("Output file location: " + outFile.getCanonicalPath());
	    		bw = new BufferedWriter(new FileWriter(outFile));
	    		StringBuffer strBuf = new StringBuffer();
	    		strBuf.append("<ExecutionAt>");
	    		strBuf.append(Calendar.getInstance().getTime());
	    		strBuf.append("</ExecutionAt>\n");
	    		strBuf.append("<ToolOrWebServiceName>");
	    		strBuf.append(TOOL_OR_WEB_SERVICE_NAME);
	    		strBuf.append("</ToolOrWebServiceName>\n");
	    		strBuf.append("\n");
	    		strBuf.append("<ResponseStatus>\n");
	    		strBuf.append("\t<Code>");
	    		strBuf.append(statusCode);
	    		strBuf.append("</Code>\n");
	    		strBuf.append("\t<Description>");
	    		strBuf.append(description);
	    		strBuf.append("</Description>\n");
	    		strBuf.append("</ResponseStatus>");
	    		bw.write(strBuf.toString());
	    		bw.close();    		    		
	    	}catch (Exception e) {
				e.printStackTrace();
			}finally{
				try{
					if (bw != null){
						bw.close();
						bw = null;
					}
				}catch (Exception e) {
					e.printStackTrace();
				}			
			}		
	    }
}

