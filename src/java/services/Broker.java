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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.fedEx.service.client.FedExServiceCaller;

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
//        try {
//            tic.insertTrackingInfo(information);
//        } catch (SQLException ex) {
//            System.out.println("tracking information failed");
//        }
        switch (information.getCarrier()){
            case FedEx:
                //make sure the class "TrackWebPublisher" in the "src.services.fedex.service.endpoint" 
                //package is running to communicate with the FedEx web service
                
                FedExServiceCaller fsc = new FedExServiceCaller(information.getTrackingNumber());
                fsc.setup_and_test();
                //call the FedEx tracking web service and fill an ArrayList of the tracking statuses
                
                ArrayList<TrackingStatus> al_ts = fsc.getTrackingStatus();
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
                break;
                
            case USPS:
                // Call USPS service
                break;
            
            default:
                // Do something here -- Should never happen.
                break;
           
        }
        
    }
    
    
}
