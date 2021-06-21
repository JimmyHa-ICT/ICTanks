import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Obstacle extends Sprite2D{

    private Image obstacleImg;

    public Obstacle(int x, int y) {
        posiX=x;
        posiY=y;
        obstacleImg = new ImageIcon("Images/obstacle.png").getImage();

        iWidth = obstacleImg.getWidth(null);
        iHeight = obstacleImg.getHeight(null);
        ImageBuff = new BufferedImage(iWidth, iHeight,BufferedImage.TYPE_INT_RGB);
        ImageBuff.createGraphics().drawImage(obstacleImg,0,0,null);
    }
}
