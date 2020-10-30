import java.io.* ;
import java.net.* ;
import java.util.* ;

public final class WebServer {
    public static void main(String argv[]) throws Exception {
    	
    	try {
    		// Could get the port number from the command line.
    		// int port = (new Integer(argv[0])).intValue();    		
    		
    		// Establish the listen socket.
    		ServerSocket serversocket = new ServerSocket(1068);		
    		
    		// Process HTTP service requests in an infinite loop.
    		while (true) {
    		    // Listen for a TCP connection request.
    			Socket socket = serversocket.accept();
    		    
    		    // Construct an object to process the HTTP request message.
    		    HttpRequest_upgrade request = new HttpRequest_upgrade(socket);
    		    
    		    // Create a new thread to process the request.
    		    Thread thread = new Thread(request);    		    
    		    // Start the thread.
    		    thread.start();
    		}
    		
    		
    	}catch(IOException e) {
    		System.out.print(e.getMessage());
    	}catch(Exception e) {
    		System.out.print(e.getMessage());
    	}    	
		
    }
}