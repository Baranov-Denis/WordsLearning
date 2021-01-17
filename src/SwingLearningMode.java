import javax.swing.*;
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

    WordCard learningWordWordCard;
    ArrayList<WordCard> randomWordCards;

    MyButton button;

    MyButton backButton;





    public SwingLearningMode() {

        MainClass.dictionary = new Dictionary();
        swingLearningMode = this;

        frame = SwingMainPage.swingMainPage.frame;
        panel = SwingMainPage.swingMainPage.panel;
        panel.removeAll();





        backButton = new MyButton("<---Back");
        backButton.setPreferredSize(new Dimension(70, 41));
        backButton.addActionListener(e -> new SwingMainPage());
        backButton.setMargin(new Insets(0, -3, 0, -3));
        //backButton.setBorder(new BevelBorder(0));

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(340, 467));
        buttonPanel.setBackground(MyColors.BACKGROUND);

        frame.setLayout(new FlowLayout());


        wordForLearnLabel = new JLabel("", SwingConstants.CENTER);
        wordForLearnLabel.setPreferredSize(new Dimension(250, 50));
        wordForLearnLabel.setFont(new Font("sans-serif", Font.BOLD, 35));



            learningWordWordCard = MainClass.dictionary.getRandomWordFromStudyingWordsList();
            if(learningWordWordCard !=null) {
                wordForLearnLabel.setText(learningWordWordCard.getEnglishWord());
            }else return;



        panel.add(wordForLearnLabel);
        panel.add(buttonPanel);
        panel.add(backButton);



        randomWordCards = MainClass.dictionary.getRandomList(learningWordWordCard);

        countLearn = new JLabel(String.format(" %s / %s", learningWordWordCard.getCount(),
                MainClass.dictionary.getCountToKnow()), SwingConstants.CENTER);
        countLearn.setPreferredSize(new Dimension(200, 40));

        panel.add(countLearn);

        createButtons();

        SwingUtilities.updateComponentTreeUI(frame);
    }


    private void createButtons() {
        buttonPanel.removeAll();

        for (int i = 0; i < 10; i++) {
            button = new MyButton(randomWordCards.get(i).getRussianWord());
            button.addActionListener(this);
            if (learningWordWordCard.getCount() == 0 && button.getText().equals(learningWordWordCard.getRussianWord())) {
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
            button = new MyButton(randomWordCards.get(i).getRussianWord());
            button.addActionListener(this);
            if (button.getText().equals(learningWordWordCard.getRussianWord())) {
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


        learningWordWordCard = MainClass.dictionary.getRandomWordFromStudyingWordsList();//Получаем случайное
        // слово из списка изучаемых слов
        randomWordCards = MainClass.dictionary.getRandomList(learningWordWordCard);//Получаем 10 случайных карточек
        // в которые включена карточка с изучаемым словом для КНОПОК.
        if(learningWordWordCard != null) {
            wordForLearnLabel.setText(learningWordWordCard.getEnglishWord());//Устанавливаем английское слово в поле
            // изучаеиого слова
        }


        panel.add(wordForLearnLabel);//Добавляем на панель изучаеиое слово
        createButtons();//Создаём кнопки ипомещаем их на buttonPanel
        panel.add(buttonPanel);
        panel.add(backButton);//Добавляем кноапку назад т.к. очищаем всю пнел removeAll
        countLearn = new JLabel(String.format(" %s / %s", learningWordWordCard.getCount(),
                MainClass.dictionary.getCountToKnow()), SwingConstants.CENTER);// Подписываем количество
        // попыток
        countLearn.setPreferredSize(new Dimension(200, 40));
        panel.add(countLearn);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(learningWordWordCard.getRussianWord())) {
            learningWordWordCard.setCount(learningWordWordCard.getCount() + 1);
            MainClass.dictionary.wordTestLearn(learningWordWordCard);
            MainClass.dictionary.saveDictionaryToFile();
            if(MainClass.dictionary.getStudyingWordsList().size()!=0) {
                nextWord();
            }else{
                new SwingMainPage();
                JOptionPane.showMessageDialog(SwingMainPage.swingMainPage.panel, "All words were learned!");
            }

        } else {
            MainClass.dictionary.resetOneWordProgress(learningWordWordCard);
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
