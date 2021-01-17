import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingMainPage extends JButton implements ActionListener {

    public static SwingMainPage swingMainPage;

    JFrame frame;
    MyButton buttonForSaveNewWords;
    MyButton buttonForLearnWords;
    MyButton buttonSettings;
    JPanel panel;

    Font buttonFont = new Font("sans-serif", Font.BOLD, 30);


    public SwingMainPage() {

        swingMainPage = this;

        frame = MainClass.mainClass.frame;
        panel = MainClass.mainClass.panel;
        panel.removeAll();
        panel.setBackground(MyColors.BACKGROUND);

        buttonForSaveNewWords = new MyButton("Add new words");
        buttonForSaveNewWords.setPreferredSize(new Dimension(320, 187));
        buttonForSaveNewWords.setFont(buttonFont);
        buttonForSaveNewWords.addActionListener(this);


        buttonForLearnWords = new MyButton("Learning");
        buttonForLearnWords.setPreferredSize(new Dimension(320, 187));
        buttonForLearnWords.setFont(buttonFont);
        buttonForLearnWords.addActionListener(this);

        buttonSettings = new MyButton("Settings");
        buttonSettings.setPreferredSize(new Dimension(320, 187));
        buttonSettings.setFont(buttonFont);
        buttonSettings.addActionListener(this);

        panel.add(buttonForSaveNewWords);
        panel.add(buttonForLearnWords);
        panel.add(buttonSettings);

        SwingUtilities.updateComponentTreeUI(frame);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case "Add new words":

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new SwingSavingMode();
                    }
                });
                break;

            case "Learning":

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new SwingLearningMode();
                    }
                });
                break;

            case "Settings":

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new SwingSettingsMode();
                    }
                });
                break;
        }
    }
}
