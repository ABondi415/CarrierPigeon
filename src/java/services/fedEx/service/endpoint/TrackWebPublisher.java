package services.fedEx.service.endpoint;


import javax.xml.ws.Endpoint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Geiger
 */
public class TrackWebPublisher{
    public static void main(String[ ] args) {
	int port = 8888;
	String url = "http://localhost:" + port + "/rc";
	System.out.println("Restfully publishing: " + url);
	Endpoint.publish(url, new TrackWebServiceREST());
    }
}
