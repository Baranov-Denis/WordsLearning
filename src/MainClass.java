import javax.swing.*;
import java.awt.*;

public class MainClass {
    public static MainClass mainClass;
    public Dictionary dictionary;
    public SwingMainPage m;
    JFrame frame;
    JPanel panel;


    public static void main(String[] args) {
        mainClass = new MainClass();
        mainClass.run();
       // mainClass.dictionary.setCountToKnow(10);
       // mainClass.dictionary.resetAllProgress();



    }


    private void run() {
        dictionary = new Dictionary();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                frame = new JFrame("Learning");
                frame.setLayout(new FlowLayout());
                frame.setSize(350, 420);
                frame.setLocation(1300, 300);
                frame.setAlwaysOnTop(true);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                panel = new JPanel();
                panel.setPreferredSize(new Dimension(340, 420));
                panel.setBackground(Color.GRAY);
                frame.add(panel);
                frame.setVisible(true);

                new SwingMainPage();
            }
        });




       // dictionary.addNewWord();



        //  dictionary.addNewWord();

    }
}
