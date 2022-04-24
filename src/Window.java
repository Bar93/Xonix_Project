import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public static final int WINDOW_WIDTH = 700;
    public static final int WINDOW_HEIGHT = 700;

    public Window() {
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        GameScene game = new GameScene();
        FramePanel frame = new FramePanel();
        this.add(frame);
        this.add(game);
        JLabel score = new JLabel();
        score.setBounds(0, 0, FramePanel.PANEL_WIDTH, FramePanel.PANEl_HEIGHT);
        Font font = new Font("Arial", Font.BOLD, FramePanel.PANEl_HEIGHT/2);
        score.setForeground(Color.RED);
        score.setFont(font);
        frame.add(score);
        Thread t1 = new Thread(() -> {
            int areaPercent;
            while (true) {
                areaPercent= (int) game.calculateArea();
                score.setText("score:"+areaPercent+"% /"+GameScene.LEVEL_ONE+"%");
                frame.add(score);
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        this.setVisible(true);
    }
}


