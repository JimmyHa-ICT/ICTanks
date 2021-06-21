import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

public class Tank extends Sprite2D {
    
    private Image[] tankImg;

    private Bullet bullet[]=new Bullet[1000];
    private int curBomb=0;
    private int tankID;
    private int direction=1;
    private float velocity = 6f;
    private int width = 1080,height=640;

    public int getDirection() 
    {
        return direction;
    }

    public Tank() 
    {  
        while(posiX<70 | posiY<50 | posiY>height-43 | posiX>width-43)
        {
            posiX=(int)(Math.random()*width);
            posiY=(int)(Math.random()*height);
        }
        loadAllImage(4);
        
    }
    public Tank(int x,int y,int dir,int id)
    {
        posiX=x;
        posiY=y;
        tankID=id;
        direction=dir;
        loadAllImage(0);
    }
    public void loadAllImage(int a)
    {
        tankImg=new Image[4];
        for(int i=a;i<tankImg.length+a;i++)
        {
            tankImg[i-a]=new ImageIcon("Images/"+i+".png").getImage();
        }
        iWidth = tankImg[direction-1].getWidth(null);
        iHeight = tankImg[direction-1].getHeight(null);
        ImageBuff=new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_RGB);
        ImageBuff.createGraphics().drawImage(tankImg[direction-1],0,0,null);
    }

    public BufferedImage getBuffImage()
    {
        return ImageBuff;
    }

    public void moveLeft()
    {
        if(direction==1|direction==3)
        {
           setDirection(4);
        }
        else
        {
            
            int temp;
            
            temp= (int) (posiX - velocity);
            if(checkCollision(temp,posiY)==false&&temp<0)
            {
                posiX=0;
            }
            else if(checkCollision(temp,posiY)==false)
            {
                posiX=temp;
            }
        }
        
    }
    public void moveRight()
    {
        if(direction==1|direction==3)
        {
           setDirection(2);
        }
        else
        {       
            int temp;
            temp=(int)(posiX + velocity);
            if(checkCollision(temp,posiY)==false&&temp>width-iWidth)
            {
            
                posiX=width-iWidth;
            }
            else if(checkCollision(temp,posiY)==false)
            {
                posiX=temp;
            }
        }
        
    }
    public void moveForward()
    {
        if(direction==2|direction==4)
        {
           setDirection(1);
        }
        else
        {
                int temp;
                temp=(int)(posiY - velocity);
                if(checkCollision(posiX,temp)==false&&temp<0)
                {
                    posiY=0;
                }
                else if(checkCollision(posiX,temp)==false)
                {
                    posiY=temp;
                }
        }
    }
    public void moveBackward()
    {
        if(direction==2|direction==4)
        {
           setDirection(3);
        }
        else
        {
            int temp;
            temp=(int)(posiY + velocity);
            if(checkCollision(posiX,temp)==false&&temp>height-iHeight)
            {
              posiY=height-iHeight;
            }
            else if(checkCollision(posiX,temp)==false)
            {
                posiY=temp;
            } 
        }
    }
    
    public void shot()
    {
        bullet[curBomb]=new Bullet(this.getXposition(),this.getYposition(),direction);
        bullet[curBomb].startBombThread(true);
        curBomb++;
    }
    public Bullet[] getBullet()
    {
        return bullet;
    }
    public void setTankID(int id)
    {
        tankID=id;
    }
    public int getTankID()
    {
        return tankID;
    }
    public void setDirection(int dir)
    {
        iWidth = tankImg[dir-1].getWidth(null);
        iHeight = tankImg[dir-1].getHeight(null);
        ImageBuff=new BufferedImage(tankImg[dir-1].getWidth(null),tankImg[dir-1].getHeight(null),BufferedImage.TYPE_INT_RGB);
        ImageBuff.createGraphics().drawImage(tankImg[dir-1],0,0,null);
        direction=dir;
    }

    public void Shot() 
    {
        bullet[curBomb]=new Bullet(this.getXposition(),this.getYposition(),direction);
        
        bullet[curBomb].startBombThread(false);
        curBomb++;
    
    }
    public boolean checkCollision(int xP,int yP)
    {
        ArrayList<Tank>clientTanks=GameBoardPanel.getClients();
        ArrayList<Obstacle> clientObstacles = GameBoardPanel.getObstacles();

        int x,y, w, h;
        for(int i=1;i<clientTanks.size();i++) {
            if(clientTanks.get(i)!=null) 
            {
                x = clientTanks.get(i).getXposition();
                y = clientTanks.get(i).getYposition();
                w = clientTanks.get(i).iWidth;
                h = clientTanks.get(i).iHeight;

                if(direction==1)
                {
                    if(((yP<=y+h)&&yP>=y)&&((xP <= x+w && xP >= x)||(xP+ iWidth >= x)&&(xP+iWidth<=x+w)))
                    {
                        return true;
                    }
                }
                else if(direction==2)
                {
                    if(((xP+iWidth >= x) && (xP+iWidth<=x+w)) && ((yP<=y+h && yP>=y)||(yP+iHeight>=y&&yP+iHeight<=y+h)))
                    {
                        return true;
                    }
                }
                else if(direction==3)
                {
                    if(((yP+ iHeight >= y) && (yP+iHeight<=y+h)) &&((xP<=x+w && xP>=x)||(xP+iWidth>=x && xP+iWidth<=x+w)))
                    {
                        return true;
                    }
                }
                else if(direction==4)
                {
                    if((xP<=x+w && xP>=x) && ((yP<=y+h && yP>=y) || (yP+iHeight >= y && yP+iHeight <= y+h)))
                    {
                        return true;
                    }
                }
            }
        }

        for(int i=0;i<clientObstacles.size();i++) {
            if(clientObstacles.get(i)!=null)
            {
                x = clientObstacles.get(i).getXposition();
                y = clientObstacles.get(i).getYposition();
                w = clientObstacles.get(i).iWidth;
                h = clientObstacles.get(i).iHeight;
                if(direction==1)
                {
                    if(((yP<=y+h)&&yP>=y)&&((xP <= x+w && xP >= x)||(xP+ iWidth >= x)&&(xP+iWidth<=x+w)))
                    {
                        return true;
                    }
                }
                else if(direction==2)
                {
                    if(((xP+iWidth >= x) && (xP+iWidth<=x+w)) && ((yP<=y+h && yP>=y)||(yP+iHeight>=y&&yP+iHeight<=y+h)))
                    {
                        return true;
                    }
                }
                else if(direction==3)
                {
                    if(((yP+ iHeight >= y) && (yP+iHeight<=y+h)) &&((xP<=x+w && xP>=x)||(xP+iWidth>=x && xP+iWidth<=x+w)))
                    {
                        return true;
                    }
                }
                else if(direction==4)
                {
                    if((xP<=x+w && xP>=x) && ((yP<=y+h && yP>=y) || (yP+iHeight >= y && yP+iHeight <= y+h)))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void Draw(Graphics2D g, JPanel j)
    {
        g.drawImage(this.getBuffImage(), getXposition(), getYposition(), j);

        for (Bullet b: this.bullet)
        {
            if (b != null)
                if (b.stop == false)
                    g.drawImage(b.getBuffImage(), b.getXposition(), b.getYposition(), j);
        }
    }
}
