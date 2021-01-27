package Viewer;

import Controller.AppController;
import Model.WordCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LearningPage extends Viewer {

    private WordCard learningWord;
    private ArrayList<WordCard> words;
    private boolean hit;

    public void setLearningWord(WordCard learningWord) {
        this.learningWord = learningWord;
    }

    public void setWords(ArrayList<WordCard> words) {
        this.words = words;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public LearningPage(AppController appController) {
        super(appController);
        name = "LearningPage";
    }


    public void runView(){
        panel.removeAll();
        MyColors.changeTheme(themeDark);

        JLabel learningWordLabel = new JLabel(learningWord.getEnglishWord(), SwingConstants.CENTER);
        learningWordLabel.setPreferredSize(new Dimension(285, 50));
        learningWordLabel.setFont(new Font("sans-serif", Font.BOLD, 35));
        panel.add(learningWordLabel);



        if(hit) {
            for (int i = 0; i < 10; i++) {
                MyButton button = new MyButton(words.get(i).getRussianWord());
                if (learningWord.getCount() == 0 && button.getText().equals(learningWord.getRussianWord())) {
                    button.setForeground(MyColors.MY_GREEN);
                }
                button.setFont(new Font("sans-serif", Font.BOLD, 25));
                button.addActionListener(e -> appController.buttonsAction(button.getText()));
                panel.add(button);
            }
        }else {
            for (int i = 0; i < 10; i++) {
                MyButton button = new MyButton(words.get(i).getRussianWord());
                if ( button.getText().equals(learningWord.getRussianWord())) {
                    button.setForeground(MyColors.MY_GREEN);
                }else {
                    button.setForeground(MyColors.MY_RED);
                }
                button.setFont(new Font("sans-serif", Font.BOLD, 25));
                button.addActionListener(e -> appController.buttonsAction(button.getText()));
                panel.add(button);
            }
        }


        MyButton back = new MyButton("<---Back");
        back.addActionListener(e -> appController.runMainPage());



        panel.add(back);
        SwingUtilities.updateComponentTreeUI(frame);
    }





    @Override
    public void runView(String dictionaryFileNamePath) {

    }
}
