/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services.usps.service.client;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 *
 * @author ajb481
 */
public class USPSSoapHandler implements SOAPHandler<SOAPMessageContext>{

    public USPSSoapHandler(){}
    
    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
	// Is this an outbound message, i.e., a request?
	Boolean request_p = (Boolean)
            context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

	// Manipulate the SOAP only if it's a request
        if (request_p) {
	    // Generate a UUID and its associated timestamp
	    // to place in the message header.
	    UUID uuid = UUID.randomUUID();

	    try {
		SOAPMessage msg = context.getMessage();
		SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
		SOAPHeader hdr = env.getHeader();
		// Ensure that the SOAP message has a header.
		if (hdr == null) hdr = env.addHeader();

		QName qname = new QName("uuid");
		SOAPHeaderElement helem = hdr.addHeaderElement(qname);

		// In SOAP 1.2, setting the actor is equivalent to
		// setting the role.
		helem.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
		helem.addTextNode(uuid.toString());
		msg.saveChanges();

		// For tracking, write to standard output.
		msg.writeTo(System.out);
	    }
	    catch(SOAPException e) { System.err.println(e); }
	    catch(IOException e) { System.err.println(e); }
	}
        return true; // continue down the chain
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        try {
	    context.getMessage().writeTo(System.out);
	}
	catch(SOAPException e) { /*System.err.println(e);*/ }
	catch(IOException e) { /*System.err.println(e);*/ }
        return true; 
    }

    @Override
    public void close(MessageContext context) {
        // Do nothing.
    }
    
}
