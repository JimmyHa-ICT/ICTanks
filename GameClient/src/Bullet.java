import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Bullet extends Sprite2D{

    private Image bombImg;

    private int direction;
    public boolean stop=false;
    private float velocity = 30f;
    
    public Bullet(int x, int y, int direction) {
        posiX=x;
        posiY=y;
        this.direction=direction;
        stop=false;
        bombImg = new ImageIcon("Images/bullet.png").getImage();

        iWidth = bombImg.getWidth(null);
        iHeight = bombImg.getHeight(null);
        ImageBuff=new BufferedImage(iWidth, iHeight,BufferedImage.TYPE_INT_RGB);
        ImageBuff.createGraphics().drawImage(bombImg,0,0,null);
    }

    public boolean checkCollision()
    {
        ArrayList<Tank> clientTanks=GameBoardPanel.getClients();
        int x,y, w, h;
        for(int i=1; i<clientTanks.size(); i++) {
            if(clientTanks.get(i)!=null) {
                x=clientTanks.get(i).getXposition();
                y=clientTanks.get(i).getYposition();
                w = clientTanks.get(i).iWidth;
                h = clientTanks.get(i).iHeight;

                if((posiY >= y&& posiY <= y+h) && (posiX >= x && posiX <= x+w))
                {
                    
                    ClientGUI.setScore(50);
                    
                    ClientGUI.gameStatusPanel.repaint();
                    
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    if(clientTanks.get(i)!=null)
                     Client.getGameClient().sendToServer(new Protocol().RemoveClientPacket(clientTanks.get(i).getTankID()));  
                    
                    return true;
                }
            }
        }

        ArrayList<Obstacle> clientObstacles = GameBoardPanel.getObstacles();
        for (int i=0; i < clientObstacles.size(); i++)
        {
            if(clientObstacles.get(i)!=null) {
                x = clientObstacles.get(i).getXposition();
                y = clientObstacles.get(i).getYposition();
                w = clientObstacles.get(i).iWidth;
                h = clientObstacles.get(i).iHeight;

                if ((posiY >= y && posiY <= y + h) && (posiX >= x && posiX <= x + w)) {
                    stop = true;
                    return true;
                }
            }
        }

        return false;
    }
    
    
    
    public void startBombThread(boolean chekCollision) {
        
            new BombShotThread(chekCollision).start();
            
    }
    
    private class BombShotThread extends Thread 
    {    
        boolean checkCollis;        // whether we wanna check the bullet collision or not
        public BombShotThread(boolean chCollision)
        {
            checkCollis=chCollision;
        }
        public void run() 
        {
            if(checkCollis) {
                
                if(direction==1) 
                {
                    posiX=17+posiX;
                    while(posiY>0)
                    {
                        posiY=(int)(posiY-velocity);
                        if(checkCollision()) 
                        {
                            break;
                        }
                        try {
                            
                            Thread.sleep(40);       //interval between two frames
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                    
                } 
                else if(direction==2) 
                {
                    posiY=17+posiY;
                    posiX+=30;
                    while(posiX<1080)
                    {
                        posiX=(int)(posiX+velocity);
                        if(checkCollision()) 
                        {
                            break;
                        }
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                else if(direction==3) 
                {
                    posiY+=30;
                    posiX+=20;
                    while(posiY<640)
                    {    
                        posiY=(int)(posiY+velocity);
                        if(checkCollision()) 
                        {
                            break;
                        }
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                else if(direction==4) 
                {
                    posiY=21+posiY;
                    
                    while(posiX>0)
                    {
                        posiX=(int)(posiX-velocity);
                        if(checkCollision()) 
                        {
                            break;
                        }
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                
                stop=true;
            } 
            else 
            {
                 if(direction==1) 
                {
                    posiX=17+posiX;
                    while(posiY>0)
                    {
                        posiY=(int)(posiY-velocity);
                        
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                    
                } 
                else if(direction==2) 
                {
                    posiY=17+posiY;
                    posiX+=30;
                    while(posiX<1080)
                    {
                        posiX=(int)(posiX+velocity);
                        
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                else if(direction==3) 
                {
                    posiY+=30;
                    posiX+=20;
                    while(posiY<640)
                    {    
                        posiY=(int)(posiY+velocity);
                        
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                else if(direction==4) 
                {
                    posiY=21+posiY;
                    
                    while(posiX>0)
                    {
                        posiX=(int)(posiX-velocity);
                        
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                stop=true;
            }
        }
    }
}
