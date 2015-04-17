import jdk.internal.util.xml.impl.Input;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.net.*;
import java.nio.Buffer;


/**
 * Created by Grant and Jorge on 4/15/2015.
 */
public class HttpRequest {
    private Socket client = null;
    private byte[] buffer = new byte[8000];
    private int len;


    HttpRequest(Socket client) throws IOException {
        this.client = client;

        try {
            //Open Socket's inputstream to a BufferedReader
            InputStream in = client.getInputStream();
            //Create a Data OutputStream that will be used to send to a browser
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            len = in.read(buffer);
            //replace 1.1 to 1.0, keep-alive to close
            buffer = replace(buffer);

            if(len > 0) {
                //writing the request, 80 by default for now
                Socket socket = new Socket(this.getURL(), 80);
                OutputStream outputStream = socket.getOutputStream();
                //write the buffer we just read to the output stream
                outputStream.write(buffer, 0, len);


                //copy response from server request
                OutputStream incomingStream = this.client.getOutputStream();
                InputStream outgoingStream = socket.getInputStream();
                //continue to read until the end
                for (int length; (length = outgoingStream.read(buffer)) != -1; ) {
                    //write these bytes to the client
                    incomingStream.write(buffer, 0, length);
                }
                //close all connections
                incomingStream.close();
                outputStream.close();
                outgoingStream.close();
                in.close();

                socket.close();
            }
            //nothing being sent, close socket
            else{
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//need to parse for this
    public int Port(){
         return 80;
    }

    //replace the protocols to old school
    private String replaceProtocols(String input){
        if(input.contains("GET")){
            input = input.replace("1.1","1.0");
        }
        if(input.contains("Connection:")){
            input = input.replace("Keep-Alive","Close");
        }
        return input;
    }
//gets the url to be used
    private String parseUrl(String input){
        if(input.contains("GET"))
        {
            String[] parts = input.split("GET");
            String[] parts2 = parts[1].split("HTTP/");
            String str = parts2[0];
            try {

                URL url = new URL(str);
                return url.getHost();
            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
        }
       return "";
    }
//funciton to get the url
    public String getURL(){
        return parseUrl(new String(buffer, 0 , len) );
    }

    //helper function
    private byte[] replace(byte[] buf){
       String replacedStr =  replaceProtocols(new String(buf, 0, len));
        byte rtnByte[] = replacedStr.getBytes();
        return rtnByte;
    }
}
