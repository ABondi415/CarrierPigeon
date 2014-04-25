/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Simple class to assist in validating the given tracking number.
 * @author A9712
 */
public class TrackingHelper {
    private static final String UPS_TRACKING_REGEX = "/\\b(1Z ?[0-9A-Z]{3} ?[0-9A-Z]{3} ?[0-9A-Z]{2} ?[0-9A-Z]{4} ?[0-9A-Z]{3} ?[0-9A-Z]|[\\dT]\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d)\\b/i";
    private static final String FEDEX_TRACKING_REGEX = "/\\b((96\\d\\d\\d\\d\\d ?\\d\\d\\d\\d|96\\d\\d) ?\\d\\d\\d\\d ?d\\d\\d\\d( ?\\d\\d\\d)?)\\b/i";
    private static final String USPS_TRACKING_REGEX = "/\\b(91\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d|91\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d)\\b/i";
    
    private static final Pattern UPS_Pattern = Pattern.compile(UPS_TRACKING_REGEX);
    private static final Pattern FEDEX_Pattern = Pattern.compile(FEDEX_TRACKING_REGEX);
    private static final Pattern USPS_Pattern = Pattern.compile(USPS_TRACKING_REGEX);
    
    private static Matcher matcher;
    
    public static boolean IsUPSTrackingNumber(String trackingNumber){
        matcher = UPS_Pattern.matcher(trackingNumber);
        return matcher.matches();
    }
    
    public static boolean IsFEDEXTrackingNumber(String trackingNumber){
        matcher = FEDEX_Pattern.matcher(trackingNumber);
        return matcher.matches();
    }
    
    public static boolean IsUSPSTrackingNumber(String trackingNumber){
        matcher = USPS_Pattern.matcher(trackingNumber);
        return matcher.matches();
    }
}
