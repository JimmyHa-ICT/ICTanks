import java.io.IOException;
import java.net.SocketException;
import java.net.*;
import java.io.*;

public class Main {
    public Main() {
    }
    
    public static void main(String args[]) throws IOException
    {
        String clientSentence;
        String capitalizedSentence = new String();
        ServerSocket welcomeSocket = new ServerSocket(6789);
        while(true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();

            if (clientSentence.contains("create"))
            {
                try {

                    Server server = new Server();
                    server.start();
                    System.out.println("Server is running ..." + Server.serverCount);
                    capitalizedSentence = server.serverPort + "\n";
                } catch (SocketException ex) {
                    ex.printStackTrace();
                }
            }

            //capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
        }



    }
    
}
