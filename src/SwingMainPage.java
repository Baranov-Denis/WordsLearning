import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingMainPage extends JButton implements ActionListener {

    public static SwingMainPage swingMainPage;

    SwingSawingMode swingSawingMode;

    JFrame frame;
    JButton buttonForSaveNewWords;
    JButton buttonForLearnWords;
    JPanel panel;

    Color buttonColor = new Color(50,50,50);
    Color buttonFontColor = new Color(250,250,250);
    Color redColor = new Color(255,50,10);

    Font buttonFont = new Font("serif", Font.BOLD, 30);


    public SwingMainPage() {

        swingMainPage = this;

        frame = MainClass.mainClass.frame;

        panel = MainClass.mainClass.panel;



        buttonForSaveNewWords = new JButton("Add new words");
        buttonForSaveNewWords.setPreferredSize(new Dimension(320, 180));
        buttonForSaveNewWords.setFont(buttonFont);
        buttonForSaveNewWords.setBackground(buttonColor);
        buttonForSaveNewWords.setForeground(buttonFontColor);
        buttonForSaveNewWords.addActionListener(this);
        buttonForSaveNewWords.setBorderPainted(false);


        buttonForLearnWords = new JButton("Learning");
        buttonForLearnWords.setPreferredSize(new Dimension(320, 180));
        buttonForLearnWords.setFont(buttonFont);
        buttonForLearnWords.setBorder(new BevelBorder(5));
        buttonForLearnWords.setBackground(buttonColor);
        buttonForLearnWords.setForeground(buttonFontColor);
        buttonForLearnWords.addActionListener(this);


        panel.add(buttonForSaveNewWords);
        panel.add(buttonForLearnWords);


        SwingUtilities.updateComponentTreeUI(frame);

    }



    @Override
    public void actionPerformed(ActionEvent e) {

                if (e.getActionCommand().equals("Add new words")) {

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new SwingSawingMode();
                        }
                    });

                }else if(e.getActionCommand().equals("Learning")){

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new SwingLearningMode();
                        }
                    });


                }

    }
}
