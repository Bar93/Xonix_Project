import javax.swing.*;
import java.awt.*;

public class Brick extends Component {

    protected int kind;
    protected int x;
    protected int y;
    protected ImageIcon imageBrick;
    public static final int WIDTH=10;
    public static final int HEIGHT =10;
    public static final int EMPTY_BRICK=1;
    public static final int FULL_BRICK=2;
    public static final int TEMP_BRICK=3;
    public static final int PLAYER_BRICK=4;




    public Brick(int kind, int x, int y ) {
        this.kind = kind;
        this.x = x;
        this.y = y;



    }

    public void paint (Graphics g,int x,int y){
        if (this.kind == FULL_BRICK){
            g.setColor(Color.BLACK);

        }
        if (this.kind == EMPTY_BRICK){
            g.setColor(Color.CYAN);

        }
        if (this.kind == TEMP_BRICK)
        {
            g.setColor(Color.RED);
        }
        if (this.kind == PLAYER_BRICK)
        {
            g.setColor(Color.green);
        }
            g.fillRect(x, y, WIDTH, HEIGHT);


    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ImageIcon getImageBrick() {
        return imageBrick;
    }

    public void setImageBrick(ImageIcon imageBrick) {
        this.imageBrick = imageBrick;
    }


}
