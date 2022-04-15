package tictactoe;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Menu extends JMenuBar {

    private JMenu menuGame;

    Menu(TicTacToe ticTacToe) {
        super();
        init(ticTacToe);
        add(menuGame);
    }

    private void init(TicTacToe ticTacToe) {
        menuGame = new JMenu("Game");
        menuGame.setName("MenuGame");
        menuGame.setMnemonic(KeyEvent.VK_G);

        JMenuItem menuHumanHuman = new JMenuItem("Human vs Human");
        menuHumanHuman.setName("MenuHumanHuman");
        menuHumanHuman.setMnemonic(KeyEvent.VK_H);
        JMenuItem menuHumanRobot = new JMenuItem("Human vs Robot");
        menuHumanRobot.setName("MenuHumanRobot");
        menuHumanRobot.setMnemonic(KeyEvent.VK_R);
        JMenuItem menuRobotHuman = new JMenuItem("Robot vs Human");
        menuRobotHuman.setName("MenuRobotHuman");
        menuRobotHuman.setMnemonic(KeyEvent.VK_U);
        JMenuItem menuRobotRobot = new JMenuItem("Robot vs Robot");
        menuRobotRobot.setName("MenuRobotRobot");
        menuRobotRobot.setMnemonic(KeyEvent.VK_O);
        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.setName("MenuExit");
        menuExit.setMnemonic(KeyEvent.VK_X);

        menuHumanHuman.addActionListener(e -> menuItemLogic(ticTacToe, "Human", "Human"));
        menuHumanRobot.addActionListener(e -> menuItemLogic(ticTacToe, "Human", "Robot"));
        menuRobotHuman.addActionListener(e -> menuItemLogic(ticTacToe, "Robot", "Human"));
        menuRobotRobot.addActionListener(e -> menuItemLogic(ticTacToe, "Robot", "Robot"));
        menuExit.addActionListener(e -> System.exit(0));

        menuGame.add(menuHumanHuman);
        menuGame.add(menuHumanRobot);
        menuGame.add(menuRobotHuman);
        menuGame.add(menuRobotRobot);
        menuGame.addSeparator();
        menuGame.add(menuExit);
    }

    private void menuItemLogic(TicTacToe ticTacToe, String playerOne, String playerTwo) {
        ticTacToe.setButtonStartReset("Reset");
        ticTacToe.getButtonStartReset().doClick();
        ticTacToe.setButtonPlayer1(playerOne);
        ticTacToe.setButtonPlayer2(playerTwo);
        ticTacToe.getButtonStartReset().doClick();
    }
}
