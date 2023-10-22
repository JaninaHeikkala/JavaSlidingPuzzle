import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    GamePanel gamePanel;
    //JPanel buttonPanel;

    GameFrame() {

        gamePanel = new GamePanel();
        //buttonPanel = createButtonPanel();
        add(gamePanel);
        //add(buttonPanel);

        //setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.setTitle("Sliding Puzzle");
        //setSize(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
