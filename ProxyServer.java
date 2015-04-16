/*
 *
 * Created by Jorge Felix and Grant Swenson on 4/5/2015.
 *
 * Program Description:
 Step 2 -  Developing a primitive web server
 Write a primitive Java web server that would just get back to a client's
 (i.e. web browser's) request with a 404 response, i.e. resource not found.
 Your server, however, needs to be able to capture the client's request in
 full and output it on the command line.
 *
*/
import java.net.*;
import java.io.*;
//Class Initializes a ProxyThread
public class ProxyServer {
    //Main Class
    public static void main(String[] args) throws IOException {


        //create variable that initializes the port number to 80
        final int portNumber = 9998;
        //Create a Proxy Server while a Client is Trying to Connect to Port 80
        try (ServerSocket serverSocket =
                     new ServerSocket(portNumber)) {

            while(true){
                //Accept Socket and Start a new Thread
                new ProxyThread(serverSocket.accept()).start();
            }
        }//if client connection is not established then send error message to client
        catch (IOException e) {
            System.err.println("Couldn't listen on port " + portNumber);
            System.exit(-1);
        }
    }
}

