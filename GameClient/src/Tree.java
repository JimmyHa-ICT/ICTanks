import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Tree extends Sprite2D{

    private Image treeImg;

    public Tree(int x, int y) {
        posiX=x;
        posiY=y;
        treeImg = new ImageIcon("Images/tree.png").getImage();

        iWidth = treeImg.getWidth(null);
        iHeight = treeImg.getHeight(null);
        ImageBuff = new BufferedImage(iWidth, iHeight,BufferedImage.TYPE_INT_RGB);
        ImageBuff.createGraphics().drawImage(treeImg,0,0,null);
    }
}