import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ClientGUI extends JFrame implements ActionListener,WindowListener 
{

    private JLabel portLabel;
    private static JLabel scoreLabel;
    private static JLabel scoreText;

    private JTextField portText;
    
    private JButton registerButton;
    private JButton createRoomButton;
    
    
    private JPanel registerPanel;
    public static JPanel gameStatusPanel;
    private Client client;
    private Tank clientTank;

    
    private static int score;
    
    int width=1280,height=720;
    int createPort = 6789;
    boolean isRunning=true;
    private GameBoardPanel boardPanel;
    
    public ClientGUI() 
    {
        score=0;
        setTitle("ICTanks");
        setSize(width,height);
        setLocation(60,100);
        getContentPane().setBackground(Color.white);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        addWindowListener(this);
        registerPanel=new JPanel();
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setSize(200,140);
        registerPanel.setBounds(0,50,560,140);
        registerPanel.setLayout(null);
        
        gameStatusPanel=new JPanel();
        gameStatusPanel.setBackground(Color.WHITE);
        gameStatusPanel.setSize(200,300);
        gameStatusPanel.setBounds(0,210,200,311);
        gameStatusPanel.setLayout(null);
        
        portLabel=new JLabel("Room ID");
        portLabel.setBounds(65,15,50,25);


        scoreText = new JLabel("Score");
        scoreText.setBounds(50, 75, 100, 25);
        scoreText.setFont(new Font("Calibri", Font.BOLD, 30));

        scoreLabel = new JLabel("0");
        scoreLabel.setBounds(70,110,100,40);
        scoreLabel.setFont(new Font("Calibri", Font.BOLD, 48));
        
        portText=new JTextField("");
        portText.setBounds(40,40,100,25);
       
        registerButton=new JButton("Join");
        registerButton.setBounds(10,80,75,25);
        registerButton.addActionListener(this);
        registerButton.setFocusable(true);

        createRoomButton=new JButton("Create");
        createRoomButton.setBounds(90,80,75,25);
        createRoomButton.addActionListener(this);
        createRoomButton.setFocusable(true);

        registerPanel.add(portLabel);
        registerPanel.add(portText);
        registerPanel.add(registerButton);
        registerPanel.add(createRoomButton);

        gameStatusPanel.add(scoreText);
        gameStatusPanel.add(scoreLabel);
            
        client=Client.getGameClient();
         
        clientTank=new Tank();
        boardPanel=new GameBoardPanel(clientTank,client,false);
        
        getContentPane().add(registerPanel);        
        getContentPane().add(gameStatusPanel);
        getContentPane().add(boardPanel);        
        setVisible(true);

    }
    
    public static int getScore()
    {
        return score;
    }
    
    public static void setScore(int addScore)
    {
        score += addScore;
        scoreLabel.setText("" + score);
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        Object obj = e.getSource();
        
        if(obj == registerButton)
        {
            createRoomButton.setEnabled(false);
            portText.setEnabled(false);
            registerButton.setEnabled(false);
            registerButton.setEnabled(false);
            
            try 
            {
                 client.register(Integer.parseInt(portText.getText()),
                         clientTank.getXposition(),clientTank.getYposition());
                 boardPanel.setGameStatus(true);
                 boardPanel.repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                 new ClientRecivingThread(client.getSocket()).start();
                 registerButton.setFocusable(false);
                 boardPanel.setFocusable(true);
            } catch (Exception ex)
            {
                JOptionPane.showMessageDialog(this,"The Server is not running, try again later!","Tanks 2D Multiplayer Game",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("The Server is not running!");
                registerButton.setEnabled(true);
                createRoomButton.setEnabled(true);
                portText.setEnabled(true);
            }
        }

        else if (obj == createRoomButton)
        {
            createRoomButton.setEnabled(false);
            portText.setEnabled(false);
            registerButton.setEnabled(false);
            registerButton.setEnabled(false);
            try {
                client.createRoom(createPort,
                        clientTank.getXposition(),clientTank.getYposition());
                boardPanel.setGameStatus(true);
                boardPanel.repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                new ClientRecivingThread(client.getSocket()).start();
                portText.setText(String.valueOf(client.getPort()));
                boardPanel.setFocusable(true);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,"Error, try again later!","Tanks 2D Multiplayer Game",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Cannot get new room!");
                createRoomButton.setEnabled(true);
                registerButton.setEnabled(true);
                portText.setEnabled(true);
            }
        }
    }

    public void windowOpened(WindowEvent e) 
    {

    }

    public void windowClosing(WindowEvent e) 
    {
         Client.getGameClient().sendToServer(new Protocol().ExitMessagePacket(clientTank.getTankID()));
         isRunning = false;
    }

    public void windowClosed(WindowEvent e) {
        
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
    
    public class ClientRecivingThread extends Thread
    {
        Socket clientSocket;
        DataInputStream reader;
        public ClientRecivingThread(Socket clientSocket)
        {
            this.clientSocket=clientSocket;
            try {
                reader=new DataInputStream(clientSocket.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
        public void run()
        {
            while(isRunning) 
            {
                String sentence="";
                try {
                    sentence=reader.readUTF();                
                } catch (IOException ex) {
                    ex.printStackTrace();
                }                
               if(sentence.startsWith("ID"))
               {
                    int id=Integer.parseInt(sentence.substring(2));
                    clientTank.setTankID(id);
                    System.out.println("My ID= "+id);
                    
               }
               else if(sentence.startsWith("NewClient"))
               {
                    int pos1=sentence.indexOf(',');
                    int pos2=sentence.indexOf('-');
                    int pos3=sentence.indexOf('|');
                    int x=Integer.parseInt(sentence.substring(9,pos1));
                    int y=Integer.parseInt(sentence.substring(pos1+1,pos2));
                    int dir=Integer.parseInt(sentence.substring(pos2+1,pos3));
                    int id=Integer.parseInt(sentence.substring(pos3+1,sentence.length()));
                    if(id!=clientTank.getTankID())
                        boardPanel.registerNewTank(new Tank(x,y,dir,id));
               }   
               else if(sentence.startsWith("Update"))
               {
                    int pos1=sentence.indexOf(',');
                    int pos2=sentence.indexOf('-');
                    int pos3=sentence.indexOf('|');
                    int x=Integer.parseInt(sentence.substring(6,pos1));
                    int y=Integer.parseInt(sentence.substring(pos1+1,pos2));
                    int dir=Integer.parseInt(sentence.substring(pos2+1,pos3));
                    int id=Integer.parseInt(sentence.substring(pos3+1,sentence.length()));
                
                    if(id!=clientTank.getTankID())
                    {
                        boardPanel.getTank(id).setXpoistion(x);
                        boardPanel.getTank(id).setYposition(y);
                        boardPanel.getTank(id).setDirection(dir);
                        boardPanel.repaint();
                    }
                    
               }
               else if(sentence.startsWith("Shot"))
               {
                    int id=Integer.parseInt(sentence.substring(4));
                
                    if(id!=clientTank.getTankID())
                    {
                        boardPanel.getTank(id).Shot();
                    }
                    
               }
               else if(sentence.startsWith("Remove"))
               {
                  int id=Integer.parseInt(sentence.substring(6));
                  
                  if(id==clientTank.getTankID())
                  {
                        int response=JOptionPane.showConfirmDialog(null,"Sorry, You are loss. Do you want to try again ?","Tanks 2D Multiplayer Game",JOptionPane.OK_CANCEL_OPTION);
                        if(response==JOptionPane.OK_OPTION)
                        {
                            //client.closeAll();
                            setVisible(false);
                            dispose();
                            
                            new ClientGUI();
                        }
                        else
                        {
                            System.exit(0);
                        }
                  }
                  else
                  {
                      boardPanel.removeTank(id);
                  }
               }
               else if(sentence.startsWith("Exit"))
               {
                   int id=Integer.parseInt(sentence.substring(4));
                  
                  if(id!=clientTank.getTankID())
                  {
                      boardPanel.removeTank(id);
                  }
               }
                      
            }
           
            try {
                reader.close();
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
    }
}
