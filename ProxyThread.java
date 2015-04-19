/**
 * Created by Jorge and Grant Swenson  on 4/6/2015.
 *
 *
 *
 * Creates a Thread to be used in Multi-Threaded Proxy Server
 */
//import Java Classes to  be used in Server
import com.sun.org.apache.xpath.internal.SourceTree;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.net.*;
import java.io.*;

public class ProxyThread extends Thread{
    //Initialize a Socket
    private Socket client = null;
    private Socket server = null;
    HttpRequest request;
    HttpResponse response;


    //Class Constructor that Will Take in a Socket for reading and writing
    public ProxyThread(Socket client){
        super("SocketThread");
        //Initialize Socket
        this.client = client;

    }
    //Initiates Thread to be started
    public void run() {
        try{
            //create a new request from client
            request = new HttpRequest(this.client);
            //create a new socket to server based on client
            this.server = new Socket(this.request.getURL(), this.request.Port());
            //get the response with server socket
            response = new HttpResponse(this.server);
            //write clients request to server
            request.writeToOutput(server);
            //pass results to client stream
            response.writeToClientOutputStream(client);
            //close both connections
            this.server.close();
            this.client.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    }






