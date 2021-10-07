package game;

import javax.swing.*;
import java.awt.*;

public class Window {

    public Window(Dimension dimension, Game game) {
        JFrame frame = new JFrame("Chess");

        frame.setPreferredSize(dimension);
        frame.setMaximumSize(dimension);
        frame.setMinimumSize(dimension);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        frame.requestFocus();
    }
}
