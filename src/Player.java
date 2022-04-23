import javax.swing.*;
import java.util.ArrayList;

public class Player extends Brick  {



    boolean inMotion;
    ArrayList<Brick> tail;
    private ImageIcon ninja;
    int direction;
    public static final int PLAYER_HEIGHT = 10;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;




    public Player(int kind, int x, int y) {
        super(kind, x, y);
        inMotion = false;
        tail = new ArrayList<>();

    }

    public void moveRight (){
        if (this.x < GameScene.GAME_SCENE_WIDTH - 10) {
            this.x += 10;
        }
    }
      public void moveLeft (){
        if (this.x > 0) {
            this.x -= 10;

        }

    }
    public void moveUp () {
        if (this.y > 0) {
            this.y -= 10;
        }
    }
    public void moveDown()
        {
            if (this.y < GameScene.GAME_SCENE_HEIGHT - 10) {
                        this.y += 10;

                }
            }

    public boolean isInMotion() {
        return inMotion;
    }

    public void setInMotion(boolean inMotion) {
        this.inMotion = inMotion;
    }

    public ArrayList<Brick> getTail() {
        return tail;
    }

    public void setTail(ArrayList<Brick> tail) {
        this.tail = tail;
    }

    public ImageIcon getNinja() {
        return ninja;
    }

    public void setNinja(ImageIcon ninja) {
        this.ninja = ninja;
    }

    public boolean checkTail() {
        boolean ans = false;
        if (this.tail.size() > 1 && this.inMotion == true) {
            for (int i = 0; i < this.tail.size() - 2; i++) {
                for (int j = 0; j < this.tail.size(); j++) {
                    {
                        if (j - i == 1) {
                            if (this.tail.get(i).getX() == this.tail.get(j).getX()) {
                                if (this.tail.get(i).getY() - this.tail.get(j).getY() == 2) {
                                    System.out.println("ok");
                                    ans = true;
                                }
                            }
                            if (this.tail.get(i).getX() == this.tail.get(j).getX()) {
                                if (this.tail.get(j).getY() - this.tail.get(i).getY() == 2) {
                                    System.out.println("ok");
                                    ans = true;
                                }
                            }
                            if (this.tail.get(i).getY() == this.tail.get(j).getY()) {
                                if (this.tail.get(i).getX() - this.tail.get(j).getX() == 2) {
                                    System.out.println("ok");
                                    ans = true;
                                }
                            }
                            if (this.tail.get(i).getY() == this.tail.get(j).getY()) {
                                if (this.tail.get(j).getX() - this.tail.get(i).getX() == 2) {
                                    System.out.println("ok");
                                    ans = true;
                                }
                            }

                        }
                    }
                }
            }

        }
        return ans;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getMaxTailX (){
        int maxX=0;
        for (int i=0;i<this.tail.size();i++){
            if (this.tail.get(i).getX()>maxX){
                maxX=this.tail.get(i).getX();
            }
        }
        return maxX;
    }
    public int getMaxTailY (){
        int maxY=0;
        for (int i=0;i<this.tail.size();i++){
            if (this.tail.get(i).getY()>maxY){
                maxY=this.tail.get(i).getY();
            }
        }
        return maxY;
    }
    public int getMinTailY (){
        int minY =GameScene.COL;
        for (int i=0;i<this.tail.size();i++){
            if (this.tail.get(i).getX()< minY){
                minY =this.tail.get(i).getY();
            }
        }
        return minY;
    }
    public int getMinTailX (){
        int minX =GameScene.ROW;
        for (int i=0;i<this.tail.size();i++){
            if (this.tail.get(i).getX()< minX){
                minX =this.tail.get(i).getX();
            }
        }
        return minX;
    }
}

