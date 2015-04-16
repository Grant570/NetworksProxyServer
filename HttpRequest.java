import java.io.*;
import java.net.*;
import java.nio.Buffer;


/**
 * Created by Grant on 4/15/2015.
 */
public class HttpRequest {
    private Socket client = null;
    private String buffer = "";

    HttpRequest(Socket client) {
        this.client = client;
        try (
        //Open Socket's inputstream to a BufferedReader
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //Create a Data OutputStream that will be used to send to a browser
        DataOutputStream out = new DataOutputStream(client.getOutputStream());

        ) {
        //create a string variable to input clients sent request
        String clientInput;
        //While Client request are greater than 5, print Information to Command Line
        while((clientInput = in.readLine()).length() >= 5) {
            //System.out.println(clientInput);
            concat(clientInput);

        }

            //write message in bytes to the Data Output Stream created
            //out.writeBytes("404 Error: Page not Found\r\n");
            //close data output stream
            out.close();
            //After Client stops sending request, close socket
            client.close();

            }catch (IOException e){
              e.printStackTrace();
            }
    }

    //public String URL(String input) {

   //}

    public int Port(){
        return client.getPort();
    }

    public String getFullClientInput(){
        return buffer;
    }
    public String getUpdatedClientInput(){
        return replaceProtocols(buffer);
    }

    private void concat(String input){
         buffer+= input + "\r\n";
    }

    private String replaceProtocols(String input){
        if(input.contains("GET")){
            input = input.replace("1.1","1.0");
        }
        if(input.contains("Connection:")){
            input = input.replace("Keep-Alive","Close");
        }
        return input;
    }

    private String parseUrl(String input){
        String[] parts = input.split("GET");
        String[] parts2 = parts[1].split("HTTP/");
        return parts2[0];
    }

    public String URL(){
        return parseUrl(buffer);
    }
}
