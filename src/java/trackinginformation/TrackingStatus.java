/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trackinginformation;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author A9712
 */
public class TrackingStatus {
    
    private Date _statusDate;
    
    private String _statusCity;
    
    private String _statusState;
    
    private Time _statusTime;
    
    public TrackingStatus(Date statusDate, String city, String state, Time time){
        _statusDate = statusDate;
        _statusCity = city;
        _statusState = state;
        _statusTime = time;
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
    
    public Time getStatusTime(){
        return _statusTime;
    }
}
