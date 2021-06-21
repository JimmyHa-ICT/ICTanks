import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class GameBoardPanel extends JPanel {

    private Tank tank;
    private int width=1080;
    private int height=640;
    private static ArrayList<Obstacle> obstacles;
    private static ArrayList<Tree> trees;
    private static ArrayList<Tank> tanks;
    private boolean gameStatus;

    public GameBoardPanel(Tank tank,Client client, boolean gameStatus)
    {
        this.tank=tank;
        this.gameStatus=gameStatus;
        setSize(width,height);
        setBounds(180,20,width,height);
        addKeyListener(new InputManager(tank));
        setFocusable(true);
        
        tanks=new ArrayList<Tank>(100);

        obstacles = new ArrayList<Obstacle>(50);
        obstacles.add(new Obstacle(220, 100));
        obstacles.add(new Obstacle(280, 100));
        obstacles.add(new Obstacle(220, 160));
        obstacles.add(new Obstacle(280, 160));

        obstacles.add(new Obstacle(160, 420));
        obstacles.add(new Obstacle(220, 420));
        obstacles.add(new Obstacle(280, 420));
        obstacles.add(new Obstacle(280, 480));


        obstacles.add(new Obstacle(740, 100));
        obstacles.add(new Obstacle(740, 160));
        obstacles.add(new Obstacle(800, 100));
        obstacles.add(new Obstacle(740, 420));
        obstacles.add(new Obstacle(800, 420));
        obstacles.add(new Obstacle(740, 480));
        obstacles.add(new Obstacle(800, 480));

        obstacles.add(new Obstacle(480, 260));
        obstacles.add(new Obstacle(480, 320));
        obstacles.add(new Obstacle(540, 260));
        obstacles.add(new Obstacle(540, 320));

        trees = new ArrayList<Tree>(50);
        trees.add(new Tree(0,0));
        trees.add(new Tree(1000, 560));
        trees.add(new Tree(0,560));
        trees.add(new Tree(1000,0));

        for(int i=0;i<100;i++)
        {
            tanks.add(null);
        }
   
    }
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(),getHeight());
        g.drawImage(new ImageIcon("Images/bg.jpg").getImage(),50,50,this);

        if(gameStatus) 
        {
            tank.Draw(g, this);

            for(int i=1;i<tanks.size();i++) 
            {
                if(tanks.get(i)!=null)
                    tanks.get(i).Draw(g, this);
            }

            for(int i=0; i<obstacles.size();i++)
            {
                if(obstacles.get(i)!=null)
                    obstacles.get(i).Draw(g, this);
            }

            for(int i=0; i<trees.size();i++)
            {
                if(trees.get(i)!=null)
                    trees.get(i).Draw(g, this);
            }

        }
        
        repaint();
    }

    public void registerNewTank(Tank newTank)
    {
        tanks.set(newTank.getTankID(),newTank);
    }
    public void removeTank(int tankID)
    {
        tanks.set(tankID,null);
    }
    public Tank getTank(int id)
    {
        return tanks.get(id);
    }
    public void setGameStatus(boolean status)
    {
        gameStatus=status;
    }
  
    public static ArrayList<Tank> getClients()
    {
        return tanks;
    }
    public static ArrayList<Obstacle> getObstacles() { return obstacles; }
}
