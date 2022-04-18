import javax.swing.*;

public class Window extends JFrame {
    public static final int WINDOW_WIDTH = 700;
    public static final int WINDOW_HEIGHT = 700;

    public Window(){
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        FramePanel frame = new FramePanel();
        GameScene game = new GameScene();
        this.add(frame);
        this.add(game);
        this.setVisible(true);
    }
}
