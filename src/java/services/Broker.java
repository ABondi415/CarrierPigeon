/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import Data.TrackingInformation;

/**
 *
 * @author A9712
 */
public class Broker implements BrokerIF {
    
    /**
     * Make web service calls here.
     * @param information 
     *  Basic tracking information for our site.
     */
    @Override
    public void route(TrackingInformation information) {
        switch (information.getCarrier()){
            case FedEx:
                
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
        }
    }
    
    
}
