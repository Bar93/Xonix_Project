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
            this.player.moveDown();
            this.player.setDirection(Player.DOWN);
        }
        if (e.getKeyCode()==KeyEvent.VK_UP){
            this.player.moveUp();
            this.player.setDirection(Player.UP);
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT){
            this.player.moveLeft();
            this.player.setDirection(Player.LEFT);
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT){
            this.player.moveRight();
            this.player.setDirection(Player.RIGHT);
        }

    }

    public void keyReleased(KeyEvent e) {

    }
}
