import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int DELAY = 75;
    int stage = 1;
    int difficulty = 0;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        startGame();
    }

    public void startGame() {
        stage = 1;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        //if before the game
        if ( stage == 1 ) {
            preGame(g);
        }
        if ( stage == 2 ) {
            g.drawString("djf", 2, 2);
        }

    }

    public void preGame(Graphics g) {

        //Draw difficulty text
        g.setColor(new Color(30, 200, 100));
        g.setFont((new Font("Ink Free", Font.BOLD, 75)));
        FontMetrics fm1 = getFontMetrics(g.getFont());
        g.drawString("Difficulty", (SCREEN_WIDTH - fm1.stringWidth("Difficulty"))/2, g.getFont().getSize() + (SCREEN_HEIGHT/5));

        JButton buttonEasy = new JButton("easy");
        buttonEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 1;
                stage = 2;
                return;
            }
        });

        System.out.println(difficulty);

        this.add(buttonEasy);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
