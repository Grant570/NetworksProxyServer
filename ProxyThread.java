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
    private Socket socket = null;
    private Socket server = null;
    HttpRequest request;


    //Class Constructor that Will Take in a Socket for reading and writing
    public ProxyThread(Socket socket){
        super("SocketThread");
        //Initialize Socket
        this.socket = socket;
    }
    //Initiates Thread to be started
    public void run() {
        try{
            request = new HttpRequest(this.socket);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //attempt to conenct to webserver
        //try{
            //request.contectToServer();
        //}
       // catch (IOException e){
            //e.printStackTrace();
        //}

        //System.out.println(request.getFullClientInput());
        //System.out.println(request.getUpdatedClientInput());
        //System.out.println(request.URL());
        //System.out.println("PORT: "+ request.Port());

       // try (
                //Open Socket's inputstream to a BufferedReader
               //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //Create a Data OutputStream that will be used to send to a browser
                //DataOutputStream out = new DataOutputStream(socket.getOutputStream());

      // ) {
            //create a string variable to input clients sent request
            //String clientInput =null;
            //While Client request are greater than 5, print Information to Command Line
           // while((clientInput = in.readLine()).length() >= 5){
                //System.out.println(" Received from Client: " + clientInput);

            //}
            //write message in bytes to the Data Output Stream created
            //close data output stream
           // out.close();
            //After Client stops sending request, close socket
            //socket.close();
        
        //}catch (IOException e){
         //  e.printStackTrace();
        //}

    }


    }






