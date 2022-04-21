import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy {
    private int x;
    private int y;
    private int direction;
    private ImageIcon shurikan;
    public static final int WIDTH_ENEMY = 10;
    public static final int HEIGHT_ENEMY = 10;
    public static final int CROSS_DOWN_LEFT = 1;
    public static final int CROSS_DOWN_RIGHT = 2;
    public static final int CROSS_UP_LEFT = 3;
    public static final int CROSS_UP_RIGHT = 4;


    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        Random rnd = new Random();
        this.direction = rnd.nextInt(4) + 1;
    }

    public void paint(Graphics g) {
        g.fillOval(this.x, this.y, WIDTH_ENEMY, HEIGHT_ENEMY);
        g.setColor(Color.BLACK);

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

    public void moveEnemyCross(Brick[][] bord) {
        switch (this.direction) {
            case CROSS_DOWN_RIGHT:
                this.x += 10;
                this.y += 10;
                if (bord[x / 10][y / 10 + 1].getKind() == Brick.FULL_BRICK) {
                    this.direction = CROSS_UP_LEFT;
                }
                if (bord[x / 10 + 1][y / 10].getKind() == Brick.FULL_BRICK) {
                    this.direction = CROSS_DOWN_LEFT;
                }
                break;
            case CROSS_DOWN_LEFT:
                this.x -= 10;
                this.y += 10;
                if (bord[x / 10][y / 10 + 1].getKind() == Brick.FULL_BRICK) {
                    this.direction = CROSS_UP_RIGHT;
                }
                if (bord[x / 10 - 1][y / 10].getKind() == Brick.FULL_BRICK) {
                    this.direction = CROSS_DOWN_RIGHT;
                }
                break;
            case CROSS_UP_LEFT:
                this.x += 10;
                this.y -= 10;
                if (bord[x / 10][y / 10 - 1].getKind() == Brick.FULL_BRICK) {
                    this.direction = CROSS_DOWN_RIGHT;
                }
                if (bord[x / 10 + 1][y / 10].getKind() == Brick.FULL_BRICK) {
                    this.direction = CROSS_DOWN_LEFT;
                }
                break;
            case CROSS_UP_RIGHT:
                this.x -= 10;
                this.y -= 10;
                if (bord[x / 10][y / 10 - 1].getKind() == Brick.FULL_BRICK) {
                    this.direction = CROSS_DOWN_RIGHT;
                }
                if (bord[x / 10 - 1][y / 10].getKind() == Brick.FULL_BRICK) {
                    this.direction = CROSS_UP_LEFT;
                }
                break;

        }
    }
}
