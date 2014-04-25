/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helpers;

import enums.CarrierType;

/**
 *
 * @author A9712
 */
public class CarrierTypeHelper {
    public static CarrierType StringToCarrierType(String type){
        type = type.toUpperCase();
        switch (type){
            case "FEDEX":
                return CarrierType.FedEx;
            
            case "UPS":
                return CarrierType.UPS;
                
            case "USPS":
                return CarrierType.USPS;
                
            default:
                return CarrierType.Unknown;
        }
    }
    
    public static String CarrierTypeToString(CarrierType type) {
        switch (type){
            case FedEx:
                return "FedEx";
                
            case UPS:
                return "UPS";
                
            case USPS:
                return "USPS";
                
            default:
                return "Unknown";
        }
    }
}
