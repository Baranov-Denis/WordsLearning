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




    int random;

    public SwingLearningMode() {

        MainClass.mainClass.dictionary = new Dictionary();
        swingLearningMode = this;

        frame = SwingMainPage.swingMainPage.frame;
        panel = SwingMainPage.swingMainPage.panel;
        panel.removeAll();





        backButton = new MyButton("<---Back");
        backButton.setPreferredSize(new Dimension(70, 41));
        backButton.addActionListener(e -> {
            new SwingMainPage();
        });
        backButton.setMargin(new Insets(0, -3, 0, -3));
        backButton.setBorder(new BevelBorder(5));

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(340, 467));
        buttonPanel.setBackground(MyColors.BACKGROUND);

        frame.setLayout(new FlowLayout());


        wordForLearnLabel = new JLabel("", SwingConstants.CENTER);
        wordForLearnLabel.setPreferredSize(new Dimension(250, 50));
        wordForLearnLabel.setFont(new Font("sans-serif", Font.BOLD, 35));



            learningWordCard = MainClass.mainClass.dictionary.getRandomWordFromStudyingWordsList();
            if(learningWordCard!=null) {
                wordForLearnLabel.setText(learningWordCard.getEnglishWord());
            }else return;



        panel.add(wordForLearnLabel);
        panel.add(buttonPanel);
        panel.add(backButton);



        randomCards = MainClass.mainClass.dictionary.getRandomList(learningWordCard);

        countLearn = new JLabel(String.format(" %s / %s", learningWordCard.getCount(), MainClass.mainClass.dictionary.getCountToKnow()), SwingConstants.CENTER);
        countLearn.setPreferredSize(new Dimension(200, 40));

        panel.add(countLearn);

        createButtons();

        SwingUtilities.updateComponentTreeUI(frame);
    }


    private void createButtons() {
        buttonPanel.removeAll();

        for (int i = 0; i < 10; i++) {
            button = new MyButton(randomCards.get(i).getRussianWord());
            button.addActionListener(this);
            if (learningWordCard.getCount() == 0 && button.getText().equals(learningWordCard.getRussianWord())) {
                button.setForeground(MyColors.MY_GREEN);
            }
            button.setFont(new Font("sans-serif", Font.BOLD, 25));
            buttonPanel.add(button);
            SwingUtilities.updateComponentTreeUI(frame);
        }
    }

    /**
     * btnMyButton.addActionListener(e->{
     * JOptionPane.showMessageDialog(null,"Hi Manuel ");
     * });
     */

    private void updateButtons() {

        buttonPanel.removeAll();

        for (int i = 0; i < 10; i++) {
            button = new MyButton(randomCards.get(i).getRussianWord());
            button.addActionListener(this);
            if (button.getText().equals(learningWordCard.getRussianWord())) {
                button.setForeground(MyColors.MY_GREEN);
            } else {
                button.setForeground(MyColors.MY_RED);
            }
            button.setFont(new Font("sans-serif", Font.BOLD, 25));
            buttonPanel.add(button);

            SwingUtilities.updateComponentTreeUI(frame);
        }
    }

    public void nextWord() {
        wordForLearnLabel.setText("");//Очищаем поле с изучаемым словом
        panel.removeAll();//Удаляем кнопки


        learningWordCard = MainClass.mainClass.dictionary.getRandomWordFromStudyingWordsList();//Получаем случайное
        // слово из списка изучаемых слов
        randomCards = MainClass.mainClass.dictionary.getRandomList(learningWordCard);//Получаем 10 случайных карточек
        // в которые включена карточка с изучаемым словом для КНОПОК.
        if(learningWordCard != null) {
            wordForLearnLabel.setText(learningWordCard.getEnglishWord());//Устанавливаем английское слово в поле
            // изучаеиого слова
        }


        panel.add(wordForLearnLabel);//Добавляем на панель изучаеиое слово
        createButtons();//Создаём кнопки ипомещаем их на buttonPanel
        panel.add(buttonPanel);
        panel.add(backButton);//Добавляем кноапку назад т.к. очищаем всю пнел removeAll
        countLearn = new JLabel(String.format(" %s / %s", learningWordCard.getCount(),
                MainClass.mainClass.dictionary.getCountToKnow()), SwingConstants.CENTER);// Подписываем количество
        // попыток
        countLearn.setPreferredSize(new Dimension(200, 40));
        panel.add(countLearn);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(learningWordCard.getRussianWord())) {
            learningWordCard.setCount(learningWordCard.getCount() + 1);
            MainClass.mainClass.dictionary.wordTestLearn(learningWordCard);
            MainClass.mainClass.dictionary.saveDictionaryToFile();
            System.out.println(MainClass.mainClass.dictionary.getStudyingWordsList().size());
            if(MainClass.mainClass.dictionary.getStudyingWordsList().size()!=0) {
                nextWord();
            }else{
                new SwingMainPage();
                JOptionPane.showMessageDialog(SwingMainPage.swingMainPage.panel, "All words were learned!");
            }

        } else {
            MainClass.mainClass.dictionary.resetOneWordProgress(learningWordCard);
            updateButtons();
        }
    }

/*
    public void back(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                panel.removeAll();
                new SwingMainPage();
            }
        });

    }*/


}
