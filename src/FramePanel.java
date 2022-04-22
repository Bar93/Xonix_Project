import javax.swing.*;
import java.awt.*;

public class FramePanel extends JPanel {
    public static final int PANEL_WIDTH = 600;
    public static final int PANEl_HEIGHT = 40;


    public FramePanel() {
        this.setBounds(0, 0, PANEL_WIDTH, PANEl_HEIGHT);
        this.setBackground(Color.BLUE);
    }
}

