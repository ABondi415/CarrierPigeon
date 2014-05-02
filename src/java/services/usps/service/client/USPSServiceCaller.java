/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services.usps.service.client;

import Data.TrackingStatus;
import clientImported.USPSTrackingServiceSOAP;
import clientImported.USPSTrackingServiceSOAPService;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author will
 */
public class USPSServiceCaller {
//    public static void main(String[] args){
//    USPSTesting ut = new USPSTesting();
//    ArrayList<TrackingStatus> al_ts = ut.getTrackingInfo("9114901159818909415173", "77009", "2014-02-07");
//    System.out.println(al_ts.size());
//    }
    private String trackingNum;
    private String zip;
    private Date date;
    
    public USPSServiceCaller(String trackingNum, String zip,Date date){
        this.trackingNum=trackingNum;
        this.zip=zip;
        this.date=date;
    }
    
    public ArrayList<TrackingStatus> getTrackingInformation(){
        USPSTrackingServiceSOAPService service = new USPSTrackingServiceSOAPService();
        USPSTrackingServiceSOAP usTest = service.getUSPSTrackingServiceSOAPPort();
        ArrayList<TrackingStatus>al_ts= new ArrayList();
//        List<TrackingStatus> al_ts = usTest.getTrackingInfo("9114901159818909415173", "77009", "2014-02-07");
        
        List<clientImported.TrackingStatus> l_ts = usTest.getTrackingInfo(
                this.trackingNum, this.zip, this.dateToString(this.date));
        
//        TrackingStatus ts = l_ts.get(0);
        Iterator<clientImported.TrackingStatus> it = l_ts.iterator();
        while(it.hasNext())
        {
            clientImported.TrackingStatus ci_ts = it.next();
            Date d = new Date(ci_ts.getStatusDate().getYear()-3800,ci_ts.getStatusDate().getMonth(),ci_ts.getStatusDate().getDay());
            TrackingStatus ts = new TrackingStatus(d,ci_ts.getStatusCity(),ci_ts.getStatusState(),null);
            al_ts.add(ts);
        }
//        Date d = new Date(ts.getStatusDate().getYear(),ts.getStatusDate().getMonth(),ts.getStatusDate().getDay());
//        Data.TrackingStatus usts = new Data.TrackingStatus(d,ts.getStatusCity(),ts.getStatusState(),null);
        return al_ts;
    }
    
    private String dateToString(Date d)
    {
       // 2014-02-07
        
     return Integer.toString(1900 + d.getYear())+"-"+this.dateFormatter((d.getMonth()+1))+"-"
             +this.dateFormatter(d.getDate());
    }
    private String dateFormatter(int val){
        return val<10 ? new String("0"+Integer.toString(val)) 
                : new String(Integer.toString(val));
    }
}
