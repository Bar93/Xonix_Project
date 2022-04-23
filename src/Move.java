import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Move implements KeyListener {
    private Player player;

    public Move (Player player) {
        this.player = player;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_DOWN){
            if (this.player.inMotion){
                if (this.player.getDirection()!=Player.UP){
                    this.player.moveDown();
                    this.player.setDirection(Player.DOWN);
                }
            }
            else {
                this.player.moveDown();
                this.player.setDirection(Player.DOWN);
            }

        }
        if (e.getKeyCode()==KeyEvent.VK_UP){
            if (this.player.inMotion){
                if (this.player.getDirection()!=Player.DOWN){
                    this.player.moveUp();
                    this.player.setDirection(Player.UP);
                }
            }
            else {
                this.player.moveUp();
                this.player.setDirection(Player.UP);
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT){
            if (this.player.inMotion){
                if (this.player.getDirection()!=Player.RIGHT){
                    this.player.moveLeft();
                    this.player.setDirection(Player.LEFT);
                }
            }
            else {
                this.player.moveLeft();
                this.player.setDirection(Player.LEFT);
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT){
            if (this.player.inMotion){
                if (this.player.getDirection()!=Player.LEFT){
                    this.player.moveRight();
                    this.player.setDirection(Player.RIGHT);
                }
            }
            else {
                this.player.moveRight();
                this.player.setDirection(Player.RIGHT);
            }
        }

    }

    public void keyReleased(KeyEvent e) {

    }
}
