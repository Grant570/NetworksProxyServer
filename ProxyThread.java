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
                //PrintWriter
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                //Open Socket's inputstream to a BufferedReader
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        ) {
            //create a string variable to input clients sent request
            String clientInput =null;
            //While Client is still sending requests, print Information to Command Line
            while((clientInput = in.readLine()) != null){
                clientInput = in.readLine();
                System.out.println(" Received from Client: " + clientInput);

                out.println("404 Error: Page not Found");
                out.flush();
                out.close();

            }

            //After Client stops sending request, close socket
            socket.close();



        }catch (IOException e){
            e.printStackTrace();
        }
    }
    }






