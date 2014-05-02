
package clientImported;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "USPSTrackingServiceSOAP", targetNamespace = "http://endpoint.service.usps.services/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface USPSTrackingServiceSOAP {


    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<clientImported.TrackingStatus>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTrackingInfo", targetNamespace = "http://endpoint.service.usps.services/", className = "clientImported.GetTrackingInfo")
    @ResponseWrapper(localName = "getTrackingInfoResponse", targetNamespace = "http://endpoint.service.usps.services/", className = "clientImported.GetTrackingInfoResponse")
    @Action(input = "http://endpoint.service.usps.services/USPSTrackingServiceSOAP/getTrackingInfoRequest", output = "http://endpoint.service.usps.services/USPSTrackingServiceSOAP/getTrackingInfoResponse")
    public List<TrackingStatus> getTrackingInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "storeInformation", targetNamespace = "http://endpoint.service.usps.services/", className = "clientImported.StoreInformation")
    @ResponseWrapper(localName = "storeInformationResponse", targetNamespace = "http://endpoint.service.usps.services/", className = "clientImported.StoreInformationResponse")
    @Action(input = "http://endpoint.service.usps.services/USPSTrackingServiceSOAP/storeInformationRequest", output = "http://endpoint.service.usps.services/USPSTrackingServiceSOAP/storeInformationResponse")
    public void storeInformation(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}