/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UPS;


import com.ups.xmlschema.xoltws.track.v2.ActivityType;
import com.ups.xmlschema.xoltws.track.v2.PackageType;
import com.ups.xmlschema.xoltws.track.v2.ShipmentActivityType;
import com.ups.xmlschema.xoltws.track.v2.ShipmentType;
import com.ups.xmlschema.xoltws.track.v2.TrackResponse;
import java.util.List;



/**
 *
 * @author Eric Komorek
 */
public class UPSClientTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
       
        TrackResponse myResponse = UPSClient.getUPSTrackResponse("1zr230f60311734465");
        List<ShipmentType> shipList = myResponse.getShipment();
        //Test Tracking Numbers
        //1ZX799330394877692 - Left with Man Erie PA
        //1Z2R4A440348279465 - Front Door Erie PA
        //1ZRX12661327807297
        //1zr230f60311734465
        
        
        
        
        for(ShipmentType shipment : shipList){
            
            List<PackageType> packages = shipment.getPackage();
            for (PackageType pack : packages){
               List<ActivityType> activityLocations = pack.getActivity();
               for(ActivityType act : activityLocations) {
                   System.out.println(act.getActivityLocation().getDescription() + " - " + act.getDate() );
                   System.out.println(act.getActivityLocation().getAddress().getCity() + ", " + act.getActivityLocation().getAddress().getStateProvinceCode() + "  " + act.getActivityLocation().getAddress().getPostalCode());
                           
               }
                
            }
        }
    }
    
}
