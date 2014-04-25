package trackinginformation;

import enums.CarrierType;
import java.util.ArrayList;
import java.util.Date;

/**
 * Basic class for tracking package information.  
 * @author A9712
 */
public class TrackingInformation {
    private String _trackingNumber;
    
    private CarrierType _carrier;
    
    private ArrayList<TrackingStatus> _trackingStatuses;
    
    public TrackingInformation(String trackingNumber, CarrierType carrier){
        _trackingNumber = trackingNumber;
        _carrier = carrier;
        _trackingStatuses = new ArrayList<>();
    }
    
    public String getTrackingNumber(){
        return _trackingNumber;
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
