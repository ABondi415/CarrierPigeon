/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trackinginformation;

import enums.CarrierType;
import java.util.ArrayList;
import java.util.Date;

/**
 * Basic class for tracking package information.  
 * @author A9712
 */
public abstract class TrackingInformation {
    private String _trackingNumber;
    
    private Date _trackingDate;
    
    private CarrierType _carrier;
    
    private ArrayList<TrackingStatus> _trackingStatuses;
    
    protected TrackingInformation(String trackingNumber, Date trackingDate, CarrierType carrier){
        _trackingNumber = trackingNumber;
        _trackingDate = trackingDate;
        _carrier = carrier;
        _trackingStatuses = new ArrayList<>();
    }
    
    public String getTrackingNumber(){
        return _trackingNumber;
    }
    
    public Date getTrackingDate(){
        return _trackingDate;
    }
    
    public CarrierType getCarrier(){
        return _carrier;
    }
    
    public ArrayList<TrackingStatus> getTrackingStatuses(){
        return _trackingStatuses;
    }
    
    public void addTrackingStatus(TrackingStatus status){
        _trackingStatuses.add(status);
    }
}
