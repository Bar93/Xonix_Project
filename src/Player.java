import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class Player extends Brick implements KeyListener {



    boolean inMotion;
    ArrayList<Brick> tail;
    private ImageIcon ninja;
    public static final int PLAYER_HEIGHT = 10;


    public Player(int kind, int x, int y) {
        super(kind, x, y);
        inMotion = false;
        tail = new ArrayList<>();
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (this.x < GameScene.GAME_SCENE_WIDTH - 10) {
                this.x += 10;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (this.x > 0) {
                this.x -= 10;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (this.y > 0) {
                this.y -= 10;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (this.y < GameScene.GAME_SCENE_HEIGHT - 10) {
                this.y += 10;

            }
        }
    }


    public void keyReleased(KeyEvent e) {

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
}

