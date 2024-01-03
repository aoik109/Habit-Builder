package ui;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

//The main method that is run; instantiates a new DailyMissionApp
// CITATION: JsonSerializationDemo by Paul Carter provided by the CPSC 210 Instructors
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Daily Missions App");
                frame.setLayout(new BorderLayout(10, 5));
                frame.setPreferredSize(new Dimension(700, 400));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                try {
                    frame.add(new DailyMissionAppGui());
                } catch (FileNotFoundException e) {
                    System.out.println("Unable to run application: file not found");
                }
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
