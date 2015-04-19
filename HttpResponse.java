import jdk.internal.util.xml.impl.Input;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.net.*;
import java.nio.Buffer;



/**
 * Created by Grant and Jorge on 4/15/2015.
 */
public class HttpResponse {
    private Socket server = null;
    private byte[] buffer = new byte[8000];
    private InputStream in;

    HttpResponse(Socket server) throws IOException {
        this.server = server;
        try {
            //Open Socket's inputstream to a BufferedReader
             in = this.server.getInputStream();
            }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //writes the response from server to client
    public void writeToClientOutputStream (Socket client){
        try{
            OutputStream clientOutputStream = client.getOutputStream();
            for (int length; (length =  in.read(buffer)) != -1; ) {
                //write these bytes to the client
                clientOutputStream.write(buffer, 0, length);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
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
}
