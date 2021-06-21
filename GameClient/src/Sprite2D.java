import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class Sprite2D {
    protected BufferedImage ImageBuff;
    protected Image image;

    protected int posiX=-1,posiY=-1;
    protected boolean isActive = true;
    protected int iWidth, iHeight;

    public int getXposition()
    {
        return posiX;
    }
    public int getYposition()
    {
        return posiY;
    }
    public void setXpoistion(int x)
    {
        posiX=x;
    }
    public void setYposition(int y)
    {
        posiY=y;
    }

    public BufferedImage getBuffImage()
    {
        return ImageBuff;
    }

    public boolean IsCollided(Sprite2D s)       //AABB collision detection
    {
        if (posiX + iWidth < s.posiX || posiX > s.posiX + s.iWidth)
            return false;
        if (posiY + iHeight < s.posiY || posiY > s.posiY + s.iHeight)
            return false;

        return true;
    }

    public void Draw(Graphics g, JPanel j)
    {
        g.drawImage(this.getBuffImage(), getXposition(), getYposition(), j);
    }

    public boolean isActived()
    {
        return isActive;
    }

    public void setActive(boolean b)
    {
        isActive = b;
    }
}
