/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import Data.TrackingInformation;
import Data.TrackingInformationController;
import Data.TrackingStatus;
import Data.TrackingStatusController;
import UPS.UPSClient;
import com.ups.xmlschema.xoltws.track.v2.ActivityLocationType;
import com.ups.xmlschema.xoltws.track.v2.ActivityType;
import com.ups.xmlschema.xoltws.track.v2.AddressType;
import com.ups.xmlschema.xoltws.track.v2.PackageType;
import com.ups.xmlschema.xoltws.track.v2.ShipmentType;
import com.ups.xmlschema.xoltws.track.v2.TrackResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.fedEx.service.client.FedExServiceCaller;
import services.usps.service.client.USPSServiceCaller;

/**
 *
 * @author A9712
 */
public class Broker implements BrokerIF {
    TrackingStatusController tsc = new TrackingStatusController();
    TrackingInformationController tic = new TrackingInformationController();
    /**
     * Make web service calls here.
     * @param information 
     *  Basic tracking information for our site.
     */
    @Override
    public void route(TrackingInformation information) {
        try {
            tic.insertTrackingInfo(information);
        } catch (SQLException ex) {
            System.out.println("tracking information failed");
        }
        ArrayList<TrackingStatus> al_ts= new ArrayList<TrackingStatus>();
        switch (information.getCarrier()){
            case FedEx:
                
                //make sure the class "TrackWebPublisher" in the "src.services.fedex.service.endpoint" 
                //package is running to communicate with the FedEx web service
                
                FedExServiceCaller fsc = new FedExServiceCaller(information.getTrackingNumber());
                fsc.setup_and_test();
                //call the FedEx tracking web service and fill an ArrayList of the tracking statuses
                
                al_ts = fsc.getTrackingStatus();
                //get all the tracking statuses
                
                for(TrackingStatus ts : al_ts){
                    try {
                        ts.setTrackingInformationId(information.getId());
                        tsc.insertTrackingStatus(ts);
                    } catch (SQLException ex) {
                        System.out.println("tracking status failed");
                    }
                }
                        
                // Call FedEx service
                break;
                
            case UPS:
                // Call UPS service
                  TrackResponse myResponse = UPSClient.getUPSTrackResponse(information.getTrackingNumber());
                  List<ShipmentType> shipList = myResponse.getShipment();
                  List<PackageType> packages = shipList.get(0).getPackage();
                  List<ActivityType> activityLocations = packages.get(0).getActivity();
                  ActivityLocationType lastAddress = activityLocations.get(0).getActivityLocation();
                  AddressType at = lastAddress.getAddress();
                  
                  //we are going to only store the most recent value into the
                  // database for now
                  TrackingStatus ts_ups = new TrackingStatus();
                  ts_ups.setTrackingInformationId(information.getId());
                  ts_ups.setStatusCity(at.getCity());
                  ts_ups.setStatusState(at.getStateProvinceCode());

        try {
            tsc.insertTrackingStatus(ts_ups);
        } catch (SQLException ex) {
            Logger.getLogger(Broker.class.getName()).log(Level.SEVERE, null, ex);
        }
                  
                  
                  
                break;
                
            case USPS:
                
                // Call USPS service
                USPSServiceCaller us_ser_call=
                        new USPSServiceCaller(information.getTrackingNumber(),
                                        information.getDestZipCode(),
                                        information.getMailingDate());
                
                al_ts=us_ser_call.getTrackingInformation();
                for(TrackingStatus ts : al_ts){
                    try {
                        ts.setTrackingInformationId(information.getId());
                        tsc.insertTrackingStatus(ts);
                    } catch (SQLException ex) {
                        System.out.println("tracking status failed");
                    }
                }
                
                break;
            
            default:
                // Do something here -- Should never happen.
                break;
           
        }
        
    }
    
    
}
