package ch05.rc;

import java.net.InetSocketAddress;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.security.SecureRandom;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import javax.xml.ws.http.HTTPException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpsExchange;
import com.sun.net.httpserver.HttpsParameters;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FedExSecurePublisher {
    private Map<Integer, Integer> fibs;

    public FedExSecurePublisher() { 
	fibs = new HashMap<Integer, Integer>(); 
    }

    public static void main(String[ ] args) {
	new FedExSecurePublisher().publish();
    }

    public Map<Integer, Integer> getMap() { return fibs; }

    private void publish() {
	int port = 9876;
	String ip = "https://localhost:";
	String path = "/fib";
	String url_string = ip + port + path;

	HttpsServer server = get_https_server(ip, port, path);
	HttpContext http_ctx = server.createContext(path);

	System.out.println("Publishing RabbitCounter at " + url_string);
	if (server != null) server.start();
	else System.err.println("Failed to start server. Exiting.");
    }

    private HttpsServer get_https_server(String ip, int port, String path) {
	HttpsServer server = null;
	try {
	    InetSocketAddress inet = new InetSocketAddress(port);

	    // 2nd arg = max number of client requests to queue
	    server = HttpsServer.create(inet, 5); 
	    SSLContext ssl_ctx = SSLContext.getInstance("TLS");

	    // password for keystore
	    char[ ] password = "sweng465".toCharArray();
	    KeyStore ks = KeyStore.getInstance("JKS");
	    FileInputStream fis = new FileInputStream("key/rc.keystore");
	    ks.load(fis, password);

	    KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
	    kmf.init(ks, password);
	    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
	    tmf.init(ks);
	    ssl_ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

	    // Create SSL engine and configure HTTPS to use it.
	    final SSLEngine eng = ssl_ctx.createSSLEngine();
	    server.setHttpsConfigurator(new HttpsConfigurator(ssl_ctx) {
		    public void configure(HttpsParameters parms) {
			parms.setCipherSuites(eng.getEnabledCipherSuites());
			parms.setProtocols(eng.getEnabledProtocols());
		    }
		});
	    server.setExecutor(null); // use default
           
            HttpContext http_context = 
		server.createContext(path, new MyHttpHandler(this));
	}
	catch(Exception e) { System.err.println(e); }
	return server;
    }

    private TrustManager[ ] get_trust_mgr() {
	// No exceptions thrown in any of the overridden methods.
	TrustManager[ ] certs = new TrustManager[ ] {
	    new X509TrustManager() {
		public X509Certificate[ ] getAcceptedIssuers() { return null; }
		public void checkClientTrusted(X509Certificate[ ] certs, String t) { } 
		public void checkServerTrusted(X509Certificate[ ] certs, String t) { }
	    }
	};
	return certs;
    }
}

// The handle method is called on a particular request context,
// in this case on any request to the server that ends with /fib.
class MyHttpHandler implements HttpHandler {
    private final String xml_start = "<fib:response xmlns:fib = 'urn:fib'>";
    private final String xml_stop = "</fib:response>";
    private final String uri = "urn:fib";

    private FedExSecurePublisher pub;

    public MyHttpHandler(FedExSecurePublisher pub) { this.pub = pub; }

    public void handle(HttpExchange ex) {
        String verb = ex.getRequestMethod().toUpperCase();
	if (verb.equals("GET"))         doGet(ex);
	else if (verb.equals("POST"))   doPost(ex);
	else if (verb.equals("DELETE")) doDelete(ex);
	else throw new HTTPException(405);
    }

    private void respond_to_client(HttpExchange ex, String res) {
	try {
	    ex.sendResponseHeaders(200, 0); // 0 means: arbitrarily many bytes
      	    OutputStream out = ex.getResponseBody();
	    out.write(res.getBytes());
	    out.flush();
	    ex.close(); // closes all streams
	}
	catch(IOException e) { }
    }

    private void doGet(HttpExchange ex) {
	Map<Integer, Integer> fibs = pub.getMap();
	Collection<Integer> list = fibs.values();
	respond_to_client(ex, list.toString());
    }
    
    private void doPost(HttpExchange ex) {
        try {
            Map<Integer, Integer> fibs = pub.getMap();
            fibs.clear(); // clear to create a new map
            String trackingNum=null;
            
            InputStream in = ex.getRequestBody();
            byte[ ] raw_bytes = new byte[40];
            //comment out the next line if run into errors
            in.read();
            in.read(raw_bytes);
            trackingNum = new String(raw_bytes);
            trackingNum = trackingNum.replaceAll("[^\\x20-\\x7e]", "");
//            String newTrackNum=new String();
            for (char c : trackingNum.toCharArray()) 
                System.out.printf("U+%04x ", (int) c);
//}           String newTrackNumber=  trackingNum.replaceAll("[\uFEFF-\uFFFF]", ""); 
//            trackingNum=trackingNum.substring(0, trackingNum.length()-1);
//            System.out.println(nums);
//            System.out.println(trackingNum);
                FedExUtil feu = new FedExUtil();
                String response= feu.getInfo(trackingNum);
                
                respond_to_client(ex, response);
                System.out.println(response);
                
            
            
        } catch (IOException ex1) {
            Logger.getLogger(MyHttpHandler.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    private void doDelete(HttpExchange ex) {
	Map<Integer, Integer> fibs = pub.getMap();
	fibs.clear();
	respond_to_client(ex, "All entries deleted.");
    }

    
}
