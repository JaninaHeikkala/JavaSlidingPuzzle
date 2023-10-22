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

    /*private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        JLabel difficultyLabel = new JLabel("Difficulty");
        difficultyLabel.setFont(new Font("Ink Free", Font.BOLD, 75));
        difficultyLabel.setForeground(new Color(30, 200, 100));
        panel.add(difficultyLabel);

        // Create buttons for difficulty levels
        JButton buttonEasy = new JButton("Easy");
        JButton buttonMedium = new JButton("Medium");
        JButton buttonHard = new JButton("Hard");

        // Add action listeners to the buttons
        buttonEasy.addActionListener(e -> setDifficultyAndStart(1));
        buttonMedium.addActionListener(e -> setDifficultyAndStart(2));
        buttonHard.addActionListener(e -> setDifficultyAndStart(3));

        // Add buttons to the panel
        panel.add(buttonEasy);
        panel.add(buttonMedium);
        panel.add(buttonHard);

        return panel;
    }

    private void setDifficultyAndStart(int difficulty) {
        gamePanel.setDifficulty(difficulty);
        gamePanel.startGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame());
    }*/

}
