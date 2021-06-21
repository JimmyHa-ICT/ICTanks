import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;


public class Client {

    private Socket clientSocket;
    private String hostName = "localhost";
    private int serverPort;
    private DataInputStream reader;
    private DataOutputStream writer;
    private Protocol protocol;

    private static Client client;
    private Client() throws IOException 
    {
        protocol=new Protocol();
    }

    public void createRoom(int port, int posX, int posY) throws Exception
    {
        String sentence;
        String modifiedSentence;
        Socket clientSocket = new Socket(InetAddress.getLocalHost(), 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        //sentence = inFromUser.readLine();
        outToServer.writeBytes( "create" + '\n');
        modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);
        //
        // use modified sentence to register
        this.register(Integer.parseInt(modifiedSentence), posX, posY);
        clientSocket.close();
    }

    public void register(int port,int posX,int posY) throws IOException
    {
        this.serverPort=port;
        //this.hostName=Ip;
        clientSocket=new Socket(this.hostName, port);
        writer=new DataOutputStream(clientSocket.getOutputStream());
      
        writer.writeUTF(protocol.RegisterPacket(posX,posY));
        

    }
  
    public void sendToServer(String message)
    {   
        if(message.equals("exit"))
            System.exit(0);
        else
        {
             try {
                 Socket s=new Socket(hostName,serverPort);
                 System.out.println(message);
                 writer=new DataOutputStream(s.getOutputStream());
                writer.writeUTF(message);
            } catch (IOException ex) {

            }
        }

    }
    
    public Socket getSocket()
    {
        return clientSocket;
    }
    public String getIP()
    {
        return hostName;
    }
    public int getPort() { return serverPort;    }
    public static Client getGameClient()
    {
        if(client==null)
            
            try {
                client=new Client();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        return client;
    }
    public void closeAll()
    {
        try {
            reader.close(); 
            writer.close();
            clientSocket.close();
        } catch (IOException ex) {
            
        }
    }
}
