

import java.io.*;
import java.net.*;




/**
 * Created by Grant and Jorge on 4/15/2015.
 */
public class HttpRequest {
    private Socket client = null;
    private byte[] buffer = new byte[8000];
    private int len;

    //constructor
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //writes modified client input to server
    public void writeToOutput(Socket server){
        try{
            //create output stream to server
            OutputStream outputStream = server.getOutputStream();
            //write the buffer we just read to the output stream
            outputStream.write(buffer, 0, len);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    //gets output stream of client
    public OutputStream getClientOutputStream(){
        if(len > 0) {
            try {
                return this.client.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //gets the buffer from the client
    public byte[] getClientBuffer(){
        return buffer;
    }

//returns 80 by default otherwise choose between ftp and https
    public int Port(){
        if( this.getURL().toLowerCase().contains("https:")){
            return 443;
        }
        else if(this.getURL().toLowerCase().contains("http:")){
            return 80;
        }
        else if(this.getURL().toLowerCase().contains("ftp:")){
            return 20;
        }
         return 80;
    }
    //replace the protocols to old school
    private String replaceProtocols(String input){
        //downgrade to 1.0
        if(input.contains("GET")){
            input = input.replace("HTTP/1.1","HTTP/1.0");
        }
        //check for keep alive upper-case, replace with close
        if(input.contains("Connection: Keep-Alive")){
            input = input.replace("Connection: Keep-Alive","Connection: close");
        }
        //check for keep alive lowercase, replace with close
       else  if(input.contains("Connection: keep-alive")){
            input = input.replace("Connection: keep-alive","Connection: close");
        }
        return input;
    }
//return the url to be used
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
        //url could not be found
       return "";
    }
//function to get the url
    public String getURL(){
        return parseUrl(new String(buffer, 0 , buffer.length) );
    }
    //helper function, replaces protocols from byte buffer
    private byte[] replace(byte[] buf){
       String replacedStr =  replaceProtocols(new String(buf, 0, buf.length));
        byte rtnByte[] = replacedStr.getBytes();
        return rtnByte;
    }
}
