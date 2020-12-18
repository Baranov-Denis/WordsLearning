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

    Card learningWordCard;
    ArrayList<Card> randomCards;
    ArrayList<JButton> buttons;

    JButton button;
    JButton back;


    int random;

    public SwingLearningMode() {
        buttons = new ArrayList<>();

        swingLearningMode = this;

        frame = SwingMainPage.swingMainPage.frame;
        panel = SwingMainPage.swingMainPage.panel;

        panel.remove(SwingMainPage.swingMainPage.buttonForLearnWords);
        panel.remove(SwingMainPage.swingMainPage.buttonForSaveNewWords);


        //panel.setBackground(new Color(100, 100, 100));


        back = new JButton("<---Back");
        back.setPreferredSize(new Dimension(70, 40));
        back.addActionListener(this::back);
        back.setMargin(new Insets(0, -3, 0, -3));
        back.setBorder(new BevelBorder(5));
        back.setBackground(SwingMainPage.swingMainPage.buttonColor);
        back.setForeground(SwingMainPage.swingMainPage.buttonFontColor);


        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(340, 420));
        // buttonPanel.setBackground(Color.GRAY);

        frame.setLayout(new FlowLayout());


        wordForLearnLabel = new JLabel("", SwingConstants.CENTER);
        wordForLearnLabel.setPreferredSize(new Dimension(250, 40));
        wordForLearnLabel.setFont(new Font("sans-serif", Font.BOLD, 30));

        learningWordCard = MainClass.mainClass.dictionary.getRandomWordFromStudyingWordsList();
        wordForLearnLabel.setText(learningWordCard.getEnglishWord());


        panel.add(back);
        panel.add(wordForLearnLabel);
        panel.add(buttonPanel);

        //  random = (int) (Math.random() * 10);


        randomCards = MainClass.mainClass.dictionary.getRandomList(learningWordCard);

        createButtons();


        SwingUtilities.updateComponentTreeUI(frame);
    }


    private void createButtons() {
        buttonPanel.removeAll();
        buttonPanel.setBackground(Color.GRAY);
        for (int i = 0; i < 10; i++) {
            button = new JButton(randomCards.get(i).getRussianWord());
            button.setPreferredSize(new Dimension(320, 27));
            button.addActionListener(this);
            button.setBorder(new BevelBorder(5));
            button.setBackground(SwingMainPage.swingMainPage.buttonColor);
            button.setForeground(SwingMainPage.swingMainPage.buttonFontColor);
            if (learningWordCard.getCount() == 0 && button.getText().equals(learningWordCard.getRussianWord())) {
                button.setForeground(Color.GREEN);
            }
            buttons.add(button);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            buttonPanel.add(button);
            SwingUtilities.updateComponentTreeUI(frame);
        }
    }

    private void updateButtons() {

        buttonPanel.removeAll();
        buttonPanel.setBackground(SwingMainPage.swingMainPage.redColor);

        //SwingUtilities.updateComponentTreeUI(frame);
        for (int i = 0; i < 10; i++) {
            button = new JButton(randomCards.get(i).getRussianWord());
            button.setPreferredSize(new Dimension(320, 27));
            button.addActionListener(this);
            // System.out.println(button.getText());
            button.setBorder(new BevelBorder(5));
            button.setBackground(SwingMainPage.swingMainPage.buttonColor);
            button.setForeground(SwingMainPage.swingMainPage.buttonFontColor);
            if (learningWordCard.getCount() < 222220 && button.getText().equals(learningWordCard.getRussianWord())) {
                System.out.println(button.getText() + "      " + button.getText().equals(learningWordCard.getRussianWord()));

                button.setForeground(Color.GREEN);


            }

            //   button.setBorder(new BevelBorder(9));
            buttons.add(button);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            buttonPanel.add(button);
            SwingUtilities.updateComponentTreeUI(frame);
        }
    }

    public void nextWord() {


        wordForLearnLabel.setText("");
        panel.removeAll();
        panel.add(back);
        panel.setBackground(new Color(150, 150, 150));


        learningWordCard = MainClass.mainClass.dictionary.getRandomWordFromStudyingWordsList();
        randomCards = MainClass.mainClass.dictionary.getRandomList(learningWordCard);
        wordForLearnLabel.setText(learningWordCard.getEnglishWord());


        panel.add(wordForLearnLabel);
        createButtons();
        panel.add(buttonPanel);

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
