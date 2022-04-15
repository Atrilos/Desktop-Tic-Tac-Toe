package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

public class TicTacToe extends JFrame {

    static String nextMove = "X";
    static int turnNumber;
    private static final JButton[] grid = new JButton[9];
    private static String letter = "A";
    private static int number = 3;
    private static JLabel statusLabel;
    private JButton buttonStartReset;
    private JButton buttonPlayer1;
    private JButton buttonPlayer2;

    public TicTacToe() {
        init();
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Tic Tac Toe");
        setLayout(new BorderLayout());

        JPanel startPanel = new JPanel(new GridLayout());
        startPanel.setPreferredSize(new Dimension(400, 30));
        startPanel.setSize(400, 30);

        buttonPlayer1 = new JButton("Human");
        buttonPlayer1.setName("ButtonPlayer1");
        buttonPlayer1.addActionListener(e -> playerButtonLogic(buttonPlayer1));

        buttonPlayer2 = new JButton("Human");
        buttonPlayer2.setName("ButtonPlayer2");
        buttonPlayer2.addActionListener(e -> playerButtonLogic(buttonPlayer2));

        buttonStartReset = new JButton("Start");
        buttonStartReset.addActionListener(e -> buttonStartResetLogic(buttonPlayer1, buttonPlayer2, buttonStartReset));
        buttonStartReset.setName("ButtonStartReset");

        startPanel.add(buttonPlayer1);
        startPanel.add(buttonStartReset);
        startPanel.add(buttonPlayer2);
        add(startPanel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(400, 370));
        gamePanel.setSize(400, 370);
        JPanel statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(400, 30));
        statusPanel.setSize(400, 30);
        add(gamePanel);
        add(statusPanel, BorderLayout.SOUTH);

        statusPanel.setLayout(new BorderLayout(2, 2));
        statusLabel = new JLabel("Game is not started");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setName("LabelStatus");
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusLabel.setVisible(true);

        gamePanel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton(" ");
            button.setFocusPainted(false);
            button.setEnabled(false);
            grid[i] = button;
            button.setName(getButtonName());
            button.setFont(new Font("Arial", Font.BOLD, 40));
            button.addActionListener(e -> gridButtonLogic(button));
            gamePanel.add(button);
        }
        JMenuBar menuBar = new Menu(this);
        setJMenuBar(menuBar);
        setVisible(true);
    }

    protected void buttonStartResetLogic(JButton buttonPlayer1, JButton buttonPlayer2, JButton buttonStartReset) {
        if (buttonStartReset.getText().equals("Start")) {
            buttonPlayer1.setEnabled(false);
            buttonPlayer2.setEnabled(false);
            switchGrid(true);
            buttonStartReset.setText("Reset");
            turnNumber = 0;
            makeMove();
        } else {
            buttonPlayer1.setEnabled(true);
            buttonPlayer1.setText("Human");
            buttonPlayer2.setEnabled(true);
            buttonPlayer2.setText("Human");
            for (var button : grid) {
                button.setText(" ");
            }
            switchGrid(false);
            statusLabel.setText("Game is not started");
            nextMove = "X";
            buttonStartReset.setText("Start");
        }
    }

    private void gridButtonLogic(JButton button) {
        if (button.getText().equals(" ")) {
            button.setText(nextMove);
            if (!checkForTerminal(getNextMove()))
                makeMove();
        }
    }
    private void playerButtonLogic(JButton buttonPlayer) {
        if (buttonPlayer.getText().equals("Human")) buttonPlayer.setText("Robot");
        else buttonPlayer.setText("Human");
    }

    private void makeMove() {
        statusLabel.setText(statusLabelMessage(false));
        turnNumber++;
        if (!((nextMove.equals("X") && buttonPlayer1.getText().equals("Human"))
                || (nextMove.equals("O") && buttonPlayer2.getText().equals("Human"))) && grid[0].isEnabled()) {
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    grid[Minimax.bestMove(getBoard(), nextMove.charAt(0), true)].doClick();
                    return null;
                }
            };
            worker.execute();
        }
    }

    private char[] getBoard() {
        char[] result = new char[9];
        for (int i = 0; i < 9; i++) {
            result[i] = grid[i].getText().charAt(0);
        }
        return result;
    }

    private String getButtonName() {
        var temp = "Button";
        if (!letter.equals("C")) {
            temp += letter + number;
            letter = "" + (char) (letter.charAt(0) + 1);
        } else {
            temp += letter + number;
            letter = "A";
            number--;
        }
        return temp;
    }

    private String getNextMove() {
        if (nextMove.equals("X")) {
            nextMove = "O";
            return "X";
        } else {
            nextMove = "X";
            return "O";
        }
    }

    private boolean checkForTerminal(String move) {
        for (int i = 0; i < 3; i++) {
            if ((grid[i].getText().equals(move)
                    && grid[i + 3].getText().equals(move)
                    && grid[i + 6].getText().equals(move))) {
                switchGrid(false);
                statusLabel.setText(statusLabelMessage(true));
                return true;
            }
        }
        for (int i = 0; i < 9; i += 3) {
            if (grid[i].getText().equals(move)
                    && grid[i + 1].getText().equals(move)
                    && grid[i + 2].getText().equals(move)) {
                switchGrid(false);
                statusLabel.setText(statusLabelMessage(true));
                return true;
            }
        }
        if (grid[0].getText().equals(move)
                && grid[4].getText().equals(move)
                && grid[8].getText().equals(move)) {
            switchGrid(false);
            statusLabel.setText(statusLabelMessage(true));
            return true;
        }
        if (grid[2].getText().equals(move)
                && grid[4].getText().equals(move)
                && grid[6].getText().equals(move)) {
            switchGrid(false);
            statusLabel.setText(statusLabelMessage(true));
            return true;
        }
        if (!grid[0].getText().equals(" ") && !grid[1].getText().equals(" ") && !grid[2].getText().equals(" ")
        && !grid[3].getText().equals(" ") && !grid[4].getText().equals(" ") && !grid[5].getText().equals(" ")
        && !grid[6].getText().equals(" ") && !grid[7].getText().equals(" ") && !grid[8].getText().equals(" ")) {
            switchGrid(false);
            statusLabel.setText("Draw");
            return true;
        }
        return false;
    }

    private void switchGrid(boolean isOn) {
        for (var button : grid) button.setEnabled(isOn);
    }

    public void setButtonPlayer1(String string) {
        this.buttonPlayer1.setText(string);
    }

    public void setButtonPlayer2(String string) {
        this.buttonPlayer2.setText(string);
    }

    public void setButtonStartReset(String string) {
        this.buttonStartReset.setText(string);
    }

    public JButton getButtonStartReset() {
        return buttonStartReset;
    }

    private String statusLabelMessage(boolean isTerminal) {
        if (!isTerminal)
            return MessageFormat.format("The turn of {0} Player ({1})", currentPlayer(), nextMove);
        else {
            turnNumber++;
            if (nextMove.equals("X"))
                nextMove = "O";
            else
                nextMove = "X";
            return MessageFormat.format("The {0} Player ({1}) wins", currentPlayer(), nextMove);
        }
    }

    private String currentPlayer() {
        if ((turnNumber) % 2 == 0)
            return buttonPlayer1.getText();
        else
            return buttonPlayer2.getText();
    }
}