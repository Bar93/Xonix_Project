import javax.swing.*;
import java.awt.*;

public class Enemy {
    private int x;
    private int y;
    private ImageIcon shurikan;
    public static final int WIDTH_ENEMY = 10;
    public static final int HEIGHT_ENEMY = 10;



    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void paint (Graphics g){
        g.fillOval(this.x,this.y,WIDTH_ENEMY, HEIGHT_ENEMY);
        g.setColor(Color.yellow);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ImageIcon getShurikan() {
        return shurikan;
    }

    public void setShurikan(ImageIcon shurikan) {
        this.shurikan = shurikan;
    }
}
