import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int BUTTON_HEIGHT = SCREEN_HEIGHT/10;
    static final int BUTTON_WIDTH = SCREEN_HEIGHT/6;
    static final int DELAY = 75;
    int stage = 1;
    int difficulty = 0;
    int gameWidth;
    int gameHeight;
    boolean puzzleCreated = false;
    Timer timer;
    Random random;
    JButton buttonEasy;
    JButton buttonMedium;
    JButton buttonHard;
    //JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, b25;
    JButton[][] buttons;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.setLayout(null);
        startGame();
        initializeButtons();
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
            removeButtons();
            createPuzzle();
            puzzleCreated = true;
        }

    }

    public void preGame(Graphics g) {

        //Draw difficulty text
        g.setColor(new Color(30, 200, 100));
        g.setFont((new Font("Ink Free", Font.BOLD, 75)));
        FontMetrics fm1 = getFontMetrics(g.getFont());
        g.drawString("Difficulty", (SCREEN_WIDTH - fm1.stringWidth("Difficulty"))/2, g.getFont().getSize() + (SCREEN_HEIGHT/7)*2);

    }

    public void initializeButtons() {

        buttonEasy = new JButton("easy");
        buttonEasy.setBounds(SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) - SCREEN_WIDTH/5, (SCREEN_HEIGHT/4)*2, BUTTON_WIDTH, BUTTON_HEIGHT);
        buttonEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 1;
                stage = 2;
                return;
            }
        });

        buttonMedium = new JButton("medium");
        buttonMedium.setBounds(SCREEN_WIDTH/2 - (BUTTON_WIDTH/2), (SCREEN_HEIGHT/4)*2, BUTTON_WIDTH, BUTTON_HEIGHT);
        buttonMedium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 2;
                stage = 2;
                return;
            }
        });

        buttonHard = new JButton("hard");
        buttonHard.setBounds(SCREEN_WIDTH/2 - (BUTTON_WIDTH/2) + SCREEN_WIDTH/5, (SCREEN_HEIGHT/4)*2, BUTTON_WIDTH, BUTTON_HEIGHT);
        buttonHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 3;
                stage = 2;
                return;
            }
        });

        this.add(buttonEasy);
        this.add(buttonMedium);
        this.add(buttonHard);
    }

    public void createPuzzle() {

        if (!puzzleCreated) {

            if (difficulty == 1) {
                gameHeight = 3;
                gameWidth = 3;
            } else if (difficulty == 2) {
                gameHeight = 4;
                gameWidth = 4;
            } else {
                gameHeight = 5;
                gameWidth = 5;
            }

            buttons = new JButton[gameWidth][gameHeight];
            this.setLayout(new GridLayout(gameWidth, gameHeight));
            int maxNumber = gameWidth*gameHeight;
            ArrayList<Integer> numbersAdded = new ArrayList<Integer>(maxNumber);
            for (int i = 0; i < gameWidth; i++) {
                for (int j = 0; j < gameHeight; j++) {
                    int numberToBeAdded = random.nextInt(maxNumber) + 1;
                    while (numbersAdded.contains(numberToBeAdded)) {
                        numberToBeAdded = random.nextInt(maxNumber) + 1;
                        //System.out.println(numberToBeAdded);
                    }
                    numbersAdded.add(numberToBeAdded);
                    buttons[i][j] = new JButton(String.valueOf(numberToBeAdded));
                    this.add(buttons[i][j], gameWidth * i + j);
                }
            }
        }


    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }


    public void removeButtons() {
        this.remove(buttonEasy);
        this.remove(buttonMedium);
        this.remove(buttonHard);
        this.revalidate(); // Update the panel
        this.repaint(); // Repaint the panel without the buttons
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
