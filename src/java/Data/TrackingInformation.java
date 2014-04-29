package Data;

import enums.CarrierType;
import java.util.ArrayList;
import java.util.Date;

/**
 * Basic class for tracking package information.  
 * @author A9712
 */
public class TrackingInformation {
    private int _id;
    
    private String _trackingNumber;
    
    private CarrierType _carrier;
    
    private String _destZipCode;
    
    private Date _mailingDate;
    
    private int _userId;
    
    public TrackingInformation() {
        
    }
    
    public TrackingInformation(String trackingNumber, CarrierType carrier){
        _trackingNumber = trackingNumber;
        _carrier = carrier;
    }
    
    public String getTrackingNumber(){
        return _trackingNumber;
    }
    
    public CarrierType getCarrier(){
        return _carrier;
    }

    /**
     * @return the _id
     */
    public int getId() {
        return _id;
    }

    /**
     * @param _id the _id to set
     */
    public void setId(int _id) {
        this._id = _id;
    }

    /**
     * @param _trackingNumber the _trackingNumber to set
     */
    public void setTrackingNumber(String _trackingNumber) {
        this._trackingNumber = _trackingNumber;
    }

    /**
     * @param _carrier the _carrier to set
     */
    public void setCarrier(CarrierType _carrier) {
        this._carrier = _carrier;
    }

    /**
     * @return the _destZipCode
     */
    public String getDestZipCode() {
        return _destZipCode;
    }

    /**
     * @param _destZipCode the _destZipCode to set
     */
    public void setDestZipCode(String _destZipCode) {
        this._destZipCode = _destZipCode;
    }

    /**
     * @return the _mailingDate
     */
    public Date getMailingDate() {
        return _mailingDate;
    }

    /**
     * @param _mailingDate the _mailingDate to set
     */
    public void setMailingDate(Date _mailingDate) {
        this._mailingDate = _mailingDate;
    }

    /**
     * @return the _userId
     */
    public int getUserId() {
        return _userId;
    }

    /**
     * @param _userId the _userId to set
     */
    public void setUserId(int _userId) {
        this._userId = _userId;
    }
}
