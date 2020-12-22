import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SwingLearningMode implements ActionListener {

    public static SwingLearningMode swingLearningMode;

    JFrame frame;

    JPanel panel;
    JPanel buttonPanel;

    JLabel wordForLearnLabel;
    JLabel countLearn;

    Card learningWordCard;
    ArrayList<Card> randomCards;
    ArrayList<JButton> buttons;

    MyButton button;

    MyButton backButton;

 //   private int buttonHeight = 41;


    int random;

    public SwingLearningMode() {
     //   buttons = new ArrayList<>();

        swingLearningMode = this;
        frame = SwingMainPage.swingMainPage.frame;
        panel = SwingMainPage.swingMainPage.panel;
        panel.removeAll();


        backButton = new MyButton("<---Back");
        backButton.setPreferredSize(new Dimension(70, 41));
        backButton.addActionListener(e->{
            new SwingMainPage();
        });
        backButton.setMargin(new Insets(0, -3, 0, -3));
        backButton.setBorder(new BevelBorder(5));
     //   backButton.setBackground(SwingMainPage.swingMainPage.buttonColor);
     //   backButton.setForeground(SwingMainPage.swingMainPage.buttonFontColor);


        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(340, 467));
        buttonPanel.setBackground(MyColors.BACKGROUND);
        // buttonPanel.setBackground(Color.GRAY);

        frame.setLayout(new FlowLayout());




        wordForLearnLabel = new JLabel("", SwingConstants.CENTER);
        wordForLearnLabel.setPreferredSize(new Dimension(250, 50));
        wordForLearnLabel.setFont(new Font("sans-serif", Font.BOLD, 35));

        learningWordCard = MainClass.mainClass.dictionary.getRandomWordFromStudyingWordsList();
        wordForLearnLabel.setText(learningWordCard.getEnglishWord());





        panel.add(wordForLearnLabel);
        panel.add(buttonPanel);
        panel.add(backButton);

        //  random = (int) (Math.random() * 10);


        randomCards = MainClass.mainClass.dictionary.getRandomList(learningWordCard);

        countLearn = new JLabel(String.format(" %s / %s",learningWordCard.getCount() ,  MainClass.mainClass.dictionary.getCountToKnow()), SwingConstants.CENTER);
        countLearn.setPreferredSize(new Dimension(200,40));

        panel.add(countLearn);

        createButtons();


        SwingUtilities.updateComponentTreeUI(frame);
    }


    private void createButtons() {
        buttonPanel.removeAll();
       // buttonPanel.setBackground(Color.GRAY);
        for (int i = 0; i < 10; i++) {
            button = new MyButton(randomCards.get(i).getRussianWord());
         //   button.setPreferredSize(new Dimension(320, buttonHeight));
            button.addActionListener(this);
            button.setBorder(new BevelBorder(5));
           // button.setBackground(SwingMainPage.swingMainPage.buttonColor);
           // button.setForeground(SwingMainPage.swingMainPage.buttonFontColor);
            if (learningWordCard.getCount() == 0 && button.getText().equals(learningWordCard.getRussianWord())) {
                button.setForeground(MyColors.MY_GREEN);
            }
          //  buttons.add(button);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setFont(new Font("sans-serif", Font.BOLD, 25) );
            buttonPanel.add(button);
            SwingUtilities.updateComponentTreeUI(frame);
        }
    }

    /**
     *
     * btnMyButton.addActionListener(e->{
     *         JOptionPane.showMessageDialog(null,"Hi Manuel ");
     *     });
     */

    private void updateButtons() {

        buttonPanel.removeAll();
      //  buttonPanel.setBackground(SwingMainPage.swingMainPage.redColor);
        for (int i = 0; i < 10; i++) {
            button = new MyButton(randomCards.get(i).getRussianWord());
       //     button.setPreferredSize(new Dimension(320, buttonHeight));
            button.addActionListener(this);
            // System.out.println(button.getText());
            button.setBorder(new BevelBorder(5));
           // button.setBackground(SwingMainPage.swingMainPage.buttonColor);
         //   button.setForeground(SwingMainPage.swingMainPage.buttonFontColor);
            if (button.getText().equals(learningWordCard.getRussianWord())) {
                button.setForeground(MyColors.MY_GREEN);
            }else {
                button.setForeground(MyColors.MY_RED);
            }
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setFont(new Font("sans-serif", Font.BOLD, 25) );
            buttonPanel.add(button);

            SwingUtilities.updateComponentTreeUI(frame);
        }
    }

    public void nextWord() {


        wordForLearnLabel.setText("");
        panel.removeAll();

        panel.setBackground(new Color(150, 150, 150));


        learningWordCard = MainClass.mainClass.dictionary.getRandomWordFromStudyingWordsList();
        randomCards = MainClass.mainClass.dictionary.getRandomList(learningWordCard);
        wordForLearnLabel.setText(learningWordCard.getEnglishWord());


        panel.add(wordForLearnLabel);
        createButtons();
        panel.add(buttonPanel);
        panel.add(backButton);
        countLearn = new JLabel(String.format(" %s / %s",learningWordCard.getCount() ,  MainClass.mainClass.dictionary.getCountToKnow()), SwingConstants.CENTER);
        countLearn.setPreferredSize(new Dimension(200,40));

        panel.add(countLearn);
        random = (int) (Math.random() * 10);


    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(learningWordCard.getRussianWord())) {

            learningWordCard.setCount(learningWordCard.getCount() + 1);
            MainClass.mainClass.dictionary.wordTestLearn(learningWordCard);
            MainClass.mainClass.dictionary.saveDictionaryToFile();
            nextWord();


        } else {
            // panel.setBackground(new Color(222, 25, 0));
            MainClass.mainClass.dictionary.resetOneWordProgress(learningWordCard);

            updateButtons();


        }


    }


    public void back(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                panel.removeAll();
                new SwingMainPage();
            }
        });

    }


}
