import javax.swing.*;
import javax.swing.border.BevelBorder;
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

  //  Color buttonColor = new Color(50,50,50);
  //  Color buttonFontColor = new Color(250,250,250);
   // Color redColor = new Color(255,50,10);

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


                }else if(e.getActionCommand().equals("Settings")){

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new SwingSettingsMode();
                        }
                    });
                }

    }
}
