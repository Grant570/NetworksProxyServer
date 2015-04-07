/**
 * Created by Jorge and Grant Swenson  on 4/6/2015.
 *
 *
 *
 * Creates a Thread to be used in Multi-Threaded Proxy Server
 */
//import Java Classes to  be used in Server
import com.sun.org.apache.xpath.internal.SourceTree;

import java.net.*;
import java.io.*;

public class ProxyThread extends Thread{
    //Initialize a Socket
    private Socket socket = null;
    //Class Constructor that Will Take in a Socket for reading and writing
    public ProxyThread(Socket socket){
        super("ProxyThread");
        //Initialize Socket
        this.socket = socket;
    }
    //Initiates Thread to be started
    public void run() {
        try (
                //Open Socket's inputstream to a BufferedReader
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //Create a Data OutputStream that will be used to send to a browser
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        ) {
            //create a string variable to input clients sent request
            String clientInput =null;
            //While Client request are greater than 5, print Information to Command Line
            while((clientInput = in.readLine()).length() >= 5){
                System.out.println(" Received from Client: " + clientInput);

            }
            //write message in bytes to the Data Output Stream created
            out.writeBytes("404 Error: Page not Found\r\n");
            //close data output stream
            out.close();
            //After Client stops sending request, close socket
            socket.close();
        
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    }






