
/**
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

public class ProxyServer {

    public static void main(String[] args) throws IOException {


        //create variable that initializes the port number
        int portNumber = Integer.parseInt(args[0]);

        try(
                //create new ServerSocket that takes in the port number
                ServerSocket serverSocket =
                        new ServerSocket(Integer.parseInt(args[0]));
                //Connects to the client socket
                Socket clientSocket = serverSocket.accept();
                //opens socket's outputstream to Printwriter
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                //opens socket's inputstream to Bufferedreader
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()))
        ){
            //create string variable for input of client
            String inputLine;
            //The while loop will retrieve the inputted information from the client and decide whether it is odd or even
            while((inputLine = in.readLine()) != null){
                //The Try and catch checks to see whether the input is an integer or string
                //if it is an integer it will go through the if statements of try
                try {
                    Integer.parseInt(inputLine);
                    //convert input from string to integer
                    int number = Integer.parseInt(inputLine);
                    //if remainder of input divided by 2 is 1, then the integer is odd
                    if (number % 2 == 1) {
                        //System.out.println( "Odd");
                        out.println("Odd");
                    }
                    //else the integer is even
                    else{
                        // System.out.println("Even");
                        out.println("Even");
                    }
                    //if inout is character, then go into catch
                } catch(NumberFormatException e){
                    //if input is bye end connection of server program
                    if(inputLine.equals("bye")){
                        out.println("bye");
                        System.exit(1);}
                    //else, send message to client requesting an integer
                    else{
                        out.println(inputLine + " is not an integer");
                    }
                }
            }

        }catch (IOException e){
            System.out.println("Exception caught when trying to listen on port" + portNumber + " or listening for a connection" );
            System.out.println(e.getMessage());
        }
    }
}

