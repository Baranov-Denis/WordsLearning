package Viewer;

import Controller.AppController;
import Model.WordCard;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;

public class EditorPage extends Viewer {

    private ArrayList<WordCard> wordsList;
    private String message;
    private int wordsCountNumber;

    public void setWordsList(ArrayList<WordCard> wordsList) {
        this.wordsList = wordsList;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setWordsCountNumber(int wordsCountNumber) {
        this.wordsCountNumber = wordsCountNumber;
    }

    public EditorPage(AppController appController) {
        super(appController);
        name = "EditorPage";
        wordsList = new ArrayList<>();
    }



    public void runView() {
        panel.removeAll();
       //themeDark = appController.loadTheme();

        //words list window
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> wordsJList = new JList<>(listModel);
        wordsJList.setFont(new Font("sans-serif", Font.PLAIN, 14));
        wordsJList.setBorder(new BevelBorder(BevelBorder.LOWERED));
        wordsJList.setBackground(MyColors.BUTTON_COLOR);
        wordsJList.setForeground(MyColors.FONT);
        JScrollPane scrollPaneForList = new JScrollPane(wordsJList);
        scrollPaneForList.setPreferredSize(new Dimension(320, 285));
        for (WordCard wordCard : wordsList) {
            listModel.addElement(wordCard.getEnglishWord() + " - " + wordCard.getRussianWord() + " - " + wordCard.getCount() + " - " + wordCard.isLearning());
        }


        //Count words
        JLabel wordsCount = new JLabel();
        wordsCount.setPreferredSize(new Dimension(300, 20));
        wordsCount.setText("<html> <div style=\"text-align: right;  width: " +
                "300\"> In the dictionary <font size = 5>" + wordsCountNumber + "</font> words.</div></html>");


        //ResultMessage
        JLabel resultMessage = new JLabel();
        resultMessage.setPreferredSize(new Dimension(300, 20));
        resultMessage.setText("<html> <div style=\"text-align: right;  width: " +
                "300\"><font size = 5>" + message + "</font></div></html>");


        //DELETE Button
        MyButton deleteWord = new MyButton("Delete word.");
        deleteWord.addActionListener(e -> {
            if(wordsJList.getSelectedIndex() >= 0) {
                int inp = JOptionPane.showConfirmDialog(panel, "Do yo really want delete this word?");
                if (inp == 0) {
                    appController.deleteOneWord(wordsJList.getSelectedIndex());
                }
            }else {
                appController.runEditorPage();
            }
        });

        //ADD WORDS PLACE


        //English word
        JLabel englishLabel = new JLabel("Enter English word: ");
        englishLabel.setPreferredSize(new Dimension(150, 35));
        englishLabel.setFont(new Font("sans-serif", Font.BOLD, 14));

        JTextField textFieldForEnglishWord = new JTextField(10);
        textFieldForEnglishWord.setFont(new Font("sans-serif", Font.PLAIN, 20));
        textFieldForEnglishWord.setBorder(new BevelBorder(BevelBorder.LOWERED));
        textFieldForEnglishWord.setCaretColor(MyColors.FONT);
        textFieldForEnglishWord.setBackground(MyColors.BUTTON_COLOR);
        textFieldForEnglishWord.setForeground(MyColors.FONT);


        //Russian word
        JLabel russianLabel = new JLabel("Enter Russian word: ");
        russianLabel.setPreferredSize(new Dimension(150, 35));
        russianLabel.setFont(new Font("sans-serif", Font.BOLD, 14));

        JTextField textFieldForRussianWord = new JTextField(10);
        textFieldForRussianWord.setFont(new Font("sans-serif", Font.PLAIN, 20));
        textFieldForRussianWord.setBorder(new BevelBorder(BevelBorder.LOWERED));
        textFieldForRussianWord.setCaretColor(MyColors.FONT);
        textFieldForRussianWord.setBackground(MyColors.BUTTON_COLOR);
        textFieldForRussianWord.setForeground(MyColors.FONT);


        //SAVE Button
        MyButton saveButton = new MyButton("Save word");
        saveButton.addActionListener(e -> appController.saveOneWord(textFieldForEnglishWord.getText(),
                textFieldForRussianWord.getText()));

        MyButton back = new MyButton("<---Back");
        back.addActionListener(e -> appController.runMainPage());


        panel.add(scrollPaneForList);
        panel.add(wordsCount);
        panel.add(deleteWord);
        panel.add(resultMessage);
        panel.add(englishLabel);
        panel.add(textFieldForEnglishWord);
        panel.add(russianLabel);
        panel.add(textFieldForRussianWord);
        panel.add(saveButton);
        panel.add(back);
        SwingUtilities.updateComponentTreeUI(frame);
    }







}
