/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author A9712
 */
public class TrackingStatus {
    
    private int _id;
    
    private Date _statusDate;
    
    private String _statusCity;
    
    private String _statusState;
    
    private int _trackingInformationId;
   
    public TrackingStatus() {
        
    }
        
    public TrackingStatus(Date statusDate, String city, String state, Time time){
        _statusDate = statusDate;
        _statusCity = city;
        _statusState = state;
    }
    
    public Date getStatusDate(){
        return _statusDate;
    }
    
    public String getStatusCity(){
        return _statusCity;
    }
    
    public String getStatusState(){
        return _statusState;
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
     * @param _statusDate the _statusDate to set
     */
    public void setStatusDate(Date _statusDate) {
        this._statusDate = _statusDate;
    }

    /**
     * @param _statusCity the _statusCity to set
     */
    public void setStatusCity(String _statusCity) {
        this._statusCity = _statusCity;
    }

    /**
     * @param _statusState the _statusState to set
     */
    public void setStatusState(String _statusState) {
        this._statusState = _statusState;
    }

    /**
     * @return the _trackingInformationId
     */
    public int getTrackingInformationId() {
        return _trackingInformationId;
    }

    /**
     * @param _trackingInformationId the _trackingInformationId to set
     */
    public void setTrackingInformationId(int _trackingInformationId) {
        this._trackingInformationId = _trackingInformationId;
    }
}
