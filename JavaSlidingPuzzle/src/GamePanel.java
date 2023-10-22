import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
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
    boolean buttonsInitialized = false;
    boolean winScreenCreated = false;
    boolean restarted = false;
    Timer timer;
    Random random;
    JButton buttonEasy;
    JButton buttonMedium;
    JButton buttonHard;
    JButton[][] buttons;
    JButton restartButton;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.setLayout(null);
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
            if (restarted) {
                removeRestartButton();
            }
            initializeButtons();
            preGame(g);
            buttonsInitialized = true;
        }
        if ( stage == 2 ) {
            removeDifficultyButtons();
            createPuzzle();
            puzzleCreated = true;
        }
        if ( stage == 3 ) {
            removeGameButtons();
            gameWin(g);
            winScreenCreated = true;
            restarted = true;
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

        if (!buttonsInitialized) {
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
            int maxNumber = gameWidth*gameHeight - 1;
            ArrayList<Integer> numbersAdded = new ArrayList<Integer>(maxNumber);
            for (int x = 0; x < gameWidth; x++) {
                for (int y = 0; y < gameHeight; y++) {
                    int numberToBeAdded = random.nextInt(maxNumber) + 1;
                    if ( numbersAdded.size() != maxNumber) {
                        while (numbersAdded.contains(numberToBeAdded)) {
                            numberToBeAdded = random.nextInt(maxNumber) + 1;
                        }
                        numbersAdded.add(numberToBeAdded);
                        buttons[x][y] = new JButton(String.valueOf(numberToBeAdded));
                        int finalX = x;
                        int finalY = y;
                        buttons[x][y].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                checkButtons(finalX, finalY);
                                boolean won = checkIfWon();
                                if (won) {
                                    stage = 3;
                                }
                            }
                        });
                        this.add(buttons[x][y], gameWidth * x + y);
                    }
                    else {
                        buttons[x][y] = new JButton("");
                        int finalX1 = x;
                        int finalY1 = y;
                        buttons[x][y].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                checkButtons(finalX1, finalY1);
                                boolean won = checkIfWon();
                                if (won) {
                                    stage = 3;
                                }
                            }
                        });
                        this.add(buttons[x][y], gameWidth * x + y);
                    }

                }
            }

        }
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void removeDifficultyButtons() {
        this.remove(buttonEasy);
        this.remove(buttonMedium);
        this.remove(buttonHard);
        this.revalidate();
        this.repaint();
    }

    public void checkButtons(int finalX, int finalY) {

        if (!Objects.equals(buttons[finalX][finalY].getText(), "")) {

            JButton clickedButton = buttons[finalX][finalY];

            // clickedButton is in the top left corner
            if (finalX == 0 && finalY == 0) {
                // button to the right of clickedButton
                if (Objects.equals(buttons[finalX + 1][finalY].getText(), "")) {
                    buttons[finalX + 1][finalY].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button below clickedButton
                if (Objects.equals(buttons[finalX][finalY + 1].getText(), "")) {
                    buttons[finalX][finalY + 1].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
            }
            // clickedButton is in the top right corner
            else if (finalX == gameWidth - 1 && finalY == 0) {
                // button to the left of clickedButton
                if (Objects.equals(buttons[finalX - 1][finalY].getText(), "")) {
                    buttons[finalX - 1][finalY].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button below clickedButton
                if (Objects.equals(buttons[finalX][finalY + 1].getText(), "")) {
                    buttons[finalX][finalY + 1].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
            }
            // clickedButton is in the bottom left corner
            else if (finalX == 0 && finalY == gameHeight - 1) {
                // button to the right of clickedButton
                if (Objects.equals(buttons[finalX + 1][finalY].getText(), "")) {
                    buttons[finalX + 1][finalY].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button above clickedButton
                if (Objects.equals(buttons[finalX][finalY - 1].getText(), "")) {
                    buttons[finalX][finalY - 1].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
            }
            // clickedButton is in the bottom right corner
            else if (finalX == gameWidth-1 && finalY == gameHeight-1) {
                // button to the left of clickedButton
                if (Objects.equals(buttons[finalX-1][finalY].getText(), "")) {
                    buttons[finalX-1][finalY].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button above clickedButton
                if (Objects.equals(buttons[finalX][finalY - 1].getText(), "")) {
                    buttons[finalX][finalY - 1].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
            }
            // clickedButton is on the top row but not in the corners
            else if (finalY == 0) {
                // button to the left of clickedButton
                if (Objects.equals(buttons[finalX-1][finalY].getText(), "")) {
                    buttons[finalX-1][finalY].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button to the right of clickedButton
                if (Objects.equals(buttons[finalX + 1][finalY].getText(), "")) {
                    buttons[finalX + 1][finalY].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button below clickedButton
                if (Objects.equals(buttons[finalX][finalY + 1].getText(), "")) {
                    buttons[finalX][finalY + 1].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
            }
            // clickedButton is on the left column but not in the corners
            else if (finalX == 0) {
                // button above clickedButton
                if (Objects.equals(buttons[finalX][finalY - 1].getText(), "")) {
                    buttons[finalX][finalY - 1].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button to the right of clickedButton
                if (Objects.equals(buttons[finalX + 1][finalY].getText(), "")) {
                    buttons[finalX + 1][finalY].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button below clickedButton
                if (Objects.equals(buttons[finalX][finalY + 1].getText(), "")) {
                    buttons[finalX][finalY + 1].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
            }
            // clickedButton is on the bottom row but not in the corners
            else if (finalY == gameHeight-1) {
                // button above clickedButton
                if (Objects.equals(buttons[finalX][finalY - 1].getText(), "")) {
                    buttons[finalX][finalY - 1].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button to the right of clickedButton
                if (Objects.equals(buttons[finalX + 1][finalY].getText(), "")) {
                    buttons[finalX + 1][finalY].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button to the left of clickedButton
                if (Objects.equals(buttons[finalX-1][finalY].getText(), "")) {
                    buttons[finalX-1][finalY].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
            }
            // clickedButton is on the right column but not in the corners
            else if (finalX == gameHeight-1) {
                // button above clickedButton
                if (Objects.equals(buttons[finalX][finalY - 1].getText(), "")) {
                    buttons[finalX][finalY - 1].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button below clickedButton
                if (Objects.equals(buttons[finalX][finalY + 1].getText(), "")) {
                    buttons[finalX][finalY + 1].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button to the left of clickedButton
                if (Objects.equals(buttons[finalX-1][finalY].getText(), "")) {
                    buttons[finalX-1][finalY].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
            }
            // clickedButton is somewhere in the middle
            else {
                // button above clickedButton
                if (Objects.equals(buttons[finalX][finalY - 1].getText(), "")) {
                    buttons[finalX][finalY - 1].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button below clickedButton
                if (Objects.equals(buttons[finalX][finalY + 1].getText(), "")) {
                    buttons[finalX][finalY + 1].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button to the left of clickedButton
                if (Objects.equals(buttons[finalX-1][finalY].getText(), "")) {
                    buttons[finalX-1][finalY].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
                // button to the right of clickedButton
                if (Objects.equals(buttons[finalX + 1][finalY].getText(), "")) {
                    buttons[finalX + 1][finalY].setText(clickedButton.getText());
                    buttons[finalX][finalY].setText("");
                }
            }
        }
    }

    public boolean checkIfWon() {
        boolean won = true;
        int number = 1;
        for (int x = 0; x < gameWidth; x++) {
            for (int y = 0; y < gameHeight; y++) {
                if (!Objects.equals(buttons[x][y].getText(), String.valueOf(number)) && number != 9) {
                    won = false;
                }
                number++;
            }
        }
        return won;
    }

    public void gameWin(Graphics g) {

        g.setColor(new Color(30, 200, 100));
        g.setFont((new Font("Ink Free", Font.BOLD, 75)));
        FontMetrics fm1 = getFontMetrics(g.getFont());
        g.drawString("You Won!", (SCREEN_WIDTH - fm1.stringWidth("Difficulty"))/2, g.getFont().getSize() + (SCREEN_HEIGHT/7)*2);

        if (!winScreenCreated) {

            restartButton = new JButton("Restart");
            restartButton.setBounds(SCREEN_WIDTH/2 - (BUTTON_WIDTH/2), (SCREEN_HEIGHT/4)*2, BUTTON_WIDTH, BUTTON_HEIGHT);
            restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stage = 1;
                }
            });
            this.add(restartButton);
        }
    }

    public void removeGameButtons() {

        for (int x = 0; x < gameWidth; x++) {
            for (int y = 0; y < gameHeight; y++) {
                this.remove(buttons[x][y]);
            }
        }
        this.revalidate();
        this.repaint();
    }

    public void removeRestartButton() {
        this.remove(restartButton);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
