
package clientImported;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the clientImported package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _StoreInformation_QNAME = new QName("http://endpoint.service.usps.services/", "storeInformation");
    private final static QName _GetTrackingInfo_QNAME = new QName("http://endpoint.service.usps.services/", "getTrackingInfo");
    private final static QName _StoreInformationResponse_QNAME = new QName("http://endpoint.service.usps.services/", "storeInformationResponse");
    private final static QName _GetTrackingInfoResponse_QNAME = new QName("http://endpoint.service.usps.services/", "getTrackingInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: clientImported
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTrackingInfoResponse }
     * 
     */
    public GetTrackingInfoResponse createGetTrackingInfoResponse() {
        return new GetTrackingInfoResponse();
    }

    /**
     * Create an instance of {@link GetTrackingInfo }
     * 
     */
    public GetTrackingInfo createGetTrackingInfo() {
        return new GetTrackingInfo();
    }

    /**
     * Create an instance of {@link StoreInformationResponse }
     * 
     */
    public StoreInformationResponse createStoreInformationResponse() {
        return new StoreInformationResponse();
    }

    /**
     * Create an instance of {@link StoreInformation }
     * 
     */
    public StoreInformation createStoreInformation() {
        return new StoreInformation();
    }

    /**
     * Create an instance of {@link TrackingStatus }
     * 
     */
    public TrackingStatus createTrackingStatus() {
        return new TrackingStatus();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.service.usps.services/", name = "storeInformation")
    public JAXBElement<StoreInformation> createStoreInformation(StoreInformation value) {
        return new JAXBElement<StoreInformation>(_StoreInformation_QNAME, StoreInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTrackingInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.service.usps.services/", name = "getTrackingInfo")
    public JAXBElement<GetTrackingInfo> createGetTrackingInfo(GetTrackingInfo value) {
        return new JAXBElement<GetTrackingInfo>(_GetTrackingInfo_QNAME, GetTrackingInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreInformationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.service.usps.services/", name = "storeInformationResponse")
    public JAXBElement<StoreInformationResponse> createStoreInformationResponse(StoreInformationResponse value) {
        return new JAXBElement<StoreInformationResponse>(_StoreInformationResponse_QNAME, StoreInformationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTrackingInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.service.usps.services/", name = "getTrackingInfoResponse")
    public JAXBElement<GetTrackingInfoResponse> createGetTrackingInfoResponse(GetTrackingInfoResponse value) {
        return new JAXBElement<GetTrackingInfoResponse>(_GetTrackingInfoResponse_QNAME, GetTrackingInfoResponse.class, null, value);
    }

}
