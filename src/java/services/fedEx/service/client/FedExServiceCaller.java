/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services.fedEx.service.client;

import Data.TrackingStatus;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import resolver.NSResolver;


/**
 *
 * @author Geiger
 */
public class FedExServiceCaller {
    String trkNum;
    ArrayList<TrackingStatus> al_ts;
    private static final String url_s = "https://localhost:9876/fib";

    public FedExServiceCaller(String trkNum){
        this.trkNum=trkNum;
        this.al_ts=new ArrayList<>();
       
       
    }
    
//    public void setup_and_test() {
//        // Create identifying names for service and port.
//        URI ns_URI = null;
//        try {
//            ns_URI = new URI("urn:fib");
//        }
//        catch(URISyntaxException e) { System.err.println(e); }
//
//        QName service_name = new QName("rcService", ns_URI.toString());
//        QName port = new QName("rcPort", ns_URI.toString());
//        String endpoint = "http://localhost:8888/rc";
//
//        // Now create a service proxy or dispatcher.
//        Service service = Service.create(service_name);
//        service.addPort(port, HTTPBinding.HTTP_BINDING, endpoint);
//        Dispatch<Source> dispatch =
//            service.createDispatch(port, Source.class, Service.Mode.PAYLOAD);
//
//        // Send some requests.
//        String xml_start = "<fib:request xmlns:fib = 'urn:fib'>";
//        String xml_end = "</fib:request>";
//
//        String xml = xml_start+this.trkNum+xml_end;
//        // GET request to test whether the POST worked.
//        invoke(dispatch, "POST", ns_URI.toString(), xml);
//
//    }
    public void getTrackingInformation(){
    try {
            // Create a context that doesn't check certificates.
            SSLContext ssl_ctx = SSLContext.getInstance("TLS");
            TrustManager[] trust_mgr = get_trust_mgr();
            ssl_ctx.init(null, // key manager
                    trust_mgr, // trust manager
                    new SecureRandom()); // random number generator
            HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());

            URL url = new URL(url_s);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            // Guard against "bad hostname" errors during handshake.
            conn.setHostnameVerifier(new HostnameVerifier() {

                public boolean verify(String host, SSLSession session) {
                    return true;
                }
            });

            //test request
//            List<Integer> nums = new ArrayList<Integer>();
//            nums.add(3); nums.add(5); nums.add(7);


            conn.setDoInput(true);
            conn.setDoOutput(true);

//            conn.setRequestMethod("GET");
            conn.setRequestMethod("POST");
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(new String(" "+this.trkNum).getBytes());
            out.flush();
            out.close();
            byte[] buffer = new byte[4096];
            InputStream in = conn.getInputStream();
            in.read(buffer);
            
            String response = new String(buffer);
            this.generateTrackingInformation(response);
//            dump_features(conn);
            conn.disconnect();

        } catch (MalformedURLException e) {
            System.err.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(e);
        }
    }


    private void invoke(Dispatch<Source> dispatch,
                        String verb,
                        String uri,
                        Object data) {
        Map<String, Object> request_context = dispatch.getRequestContext();
        request_context.put(MessageContext.HTTP_REQUEST_METHOD, verb);

        //System.out.println("Request: " + data);

        // Invoke
        StreamSource source = null;
        if (data != null) source = make_stream_source(data.toString());
        Source result = dispatch.invoke(source);
        display_result(result, uri);
    }

   private void display_result(Source result, String uri) {
        DOMResult dom_result = new DOMResult();
        try {
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(result, dom_result);

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xp = xpf.newXPath();
            xp.setNamespaceContext(new NSResolver("fib", uri));
            String result_string = 
               xp.evaluate("/fib:response", dom_result.getNode());
             generateTrackingInformation(result_string);
        }
        catch(TransformerConfigurationException e) { System.err.println(e); }
        catch(TransformerException e) { System.err.println(e); }
        catch(XPathExpressionException e) { System.err.println(e); }
    }

    private StreamSource make_stream_source(String msg) {
        ByteArrayInputStream stream = new ByteArrayInputStream(msg.getBytes());
        return new StreamSource(stream);
    }
    
    private void generateTrackingInformation(String res)
    {
        boolean first = true;
        String[] events = res.split("~");
        for(String s: events){
            if(first){
                first=false;
                continue;
            }
            String[] eventInfo = s.split(",");
            String[] dateInfo = eventInfo[0].split("-");
            Date date = new Date(Integer.parseInt(dateInfo[0])-1900,Integer.parseInt(dateInfo[1]),Integer.parseInt(dateInfo[2]));
            TrackingStatus ts = new TrackingStatus(date,eventInfo[1],eventInfo[2],null);
            al_ts.add(ts);
        }
       
    }
    
    public ArrayList<TrackingStatus> getTrackingStatus(){
        return this.al_ts;
    }
    
    
    private TrustManager[] get_trust_mgr() {
        // No exceptions thrown in any of the overridden methods.
        TrustManager[] certs = new TrustManager[]{
            new X509TrustManager() {

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs,
                        String type) {
                }

                public void checkServerTrusted(X509Certificate[] certs,
                        String type) {
                }
            }
        };
        return certs;
    }

    private void dump_features(HttpsURLConnection conn) {
        try {
            print("Status code:  " + conn.getResponseCode());
            print("Cipher suite: " + conn.getCipherSuite());
            Certificate[] certs = conn.getServerCertificates();
            for (Certificate cert : certs) {
                print("\tCert. type: " + cert.getType());
                print("\tHash code:  " + cert.hashCode());
                print("\tAlgorithm:  " + cert.getPublicKey().getAlgorithm());
                print("\tFormat:     " + cert.getPublicKey().getFormat());
                print("");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void print(String s) {
        System.out.println(s);
    }
}
