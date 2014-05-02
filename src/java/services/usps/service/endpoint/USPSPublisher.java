/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services.usps.service.endpoint;

import javax.xml.ws.Endpoint;

/**
 *
 * @author will
 */
public class USPSPublisher {
    public static void main(String[ ] args) {
      // 1st argument is the publication URL
      // 2nd argument is an SIB instance
      Endpoint.publish("http://127.0.0.1:9876/uspsTracking", new USPSTrackingServiceSOAP());
    }
}
