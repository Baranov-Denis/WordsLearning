package Viewer;

import Controller.AppController;
import Model.WordCard;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MainPage extends Viewer {

    public MainPage(AppController appController) {
        super(appController);
        name = "MainPage";
    }


    @Override
    public void runView(ArrayList<WordCard> wordsList, String message, int size) {

    }


    public void runView() {
        appController.loadTheme();
        //MyColors.changeTheme(themeDark);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(340, 605);
        frame.setTitle("Learning");
        frame.setLocation(1300, 300);
        frame.setLayout(new FlowLayout());
        frame.setAlwaysOnTop(true);
        frame.add(panel);
        panel.setPreferredSize(new Dimension(340, 585));
        panel.setBackground(MyColors.BACKGROUND);
        panel.removeAll();


        MyButton dictionaryEditor = new MyButton("Edit");
        dictionaryEditor.setPreferredSize(new Dimension(320, 180));
        dictionaryEditor.setFont(new Font("sans-serif", Font.BOLD, 30));
        dictionaryEditor.addActionListener(e -> appController.runEditorPage());

        MyButton learning = new MyButton("Learn");
        learning.setFont(new Font("sans-serif", Font.BOLD, 30));
        learning.setPreferredSize(new Dimension(320, 180));
        learning.addActionListener(e -> appController.runLearningPage());

        MyButton settings = new MyButton("Settings");
        settings.setFont(new Font("sans-serif", Font.BOLD, 30));
        settings.setPreferredSize(new Dimension(320, 180));
        settings.addActionListener(e -> appController.runSettingPage());

        panel.add(dictionaryEditor);
        panel.add(learning);
        panel.add(settings);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    @Override
    public void runView(String dictionaryFileNamePath) {

    }

    @Override
    public void runView(WordCard oneRandomWordForLearn, ArrayList<WordCard> wordsForButtons, boolean b) {

    }


//    /**
//     * Run Editor Page
//     */
//    public void runEditorPage(ArrayList<WordCard> wordsList, String message, int wordsCountNumber) {
//        panel.removeAll();
//        MyColors.changeTheme(themeDark);
//
//
//        //words list window
//        DefaultListModel<String> listModel = new DefaultListModel<>();
//        JList<String> wordsJList = new JList<>(listModel);
//        wordsJList.setFont(new Font("sans-serif", Font.PLAIN, 14));
//        wordsJList.setBorder(new BevelBorder(BevelBorder.LOWERED));
//        wordsJList.setBackground(MyColors.BUTTON_COLOR);
//        wordsJList.setForeground(MyColors.FONT);
//        JScrollPane scrollPaneForList = new JScrollPane(wordsJList);
//        scrollPaneForList.setPreferredSize(new Dimension(320, 285));
//        for (WordCard wordCard : wordsList) {
//            listModel.addElement(wordCard.getEnglishWord() + " - " + wordCard.getRussianWord() + " - " + wordCard.getCount() + " - " + wordCard.isLearning());
//        }
//
//
//        //Count words
//        JLabel wordsCount = new JLabel();
//        wordsCount.setPreferredSize(new Dimension(300, 20));
//        wordsCount.setText("<html> <div style=\"text-align: right;  width: " +
//                "300\"> In the dictionary <font size = 5>" + wordsCountNumber + "</font> words.</div></html>");
//
//
//        //ResultMessage
//        JLabel resultMessage = new JLabel();
//        resultMessage.setPreferredSize(new Dimension(300, 20));
//        resultMessage.setText("<html> <div style=\"text-align: right;  width: " +
//                "300\"><font size = 5>" + message + "</font></div></html>");
//
//
//        //deleteButton
//        MyButton deleteWord = new MyButton("Delete word.");
//        deleteWord.addActionListener(e -> {
//            int inp = JOptionPane.showConfirmDialog(panel,"Do yo really want delete this word?");
//            if(inp == 0) {
//                appController.deleteOneWord(wordsJList.getSelectedIndex());
//            }
//        });
//
//        //ADD WORDS PLACE
//
//
//        //English word
//        JLabel englishLabel = new JLabel("Enter English word: ");
//        englishLabel.setPreferredSize(new Dimension(150, 35));
//        englishLabel.setFont(new Font("sans-serif", Font.BOLD, 14));
//
//        JTextField textFieldForEnglishWord = new JTextField(10);
//        textFieldForEnglishWord.setFont(new Font("sans-serif", Font.PLAIN, 20));
//        textFieldForEnglishWord.setBorder(new BevelBorder(BevelBorder.LOWERED));
//        textFieldForEnglishWord.setCaretColor(MyColors.FONT);
//        textFieldForEnglishWord.setBackground(MyColors.BUTTON_COLOR);
//        textFieldForEnglishWord.setForeground(MyColors.FONT);
//
//
//        //Russian word
//        JLabel russianLabel = new JLabel("Enter Russian word: ");
//        russianLabel.setPreferredSize(new Dimension(150, 35));
//        russianLabel.setFont(new Font("sans-serif", Font.BOLD, 14));
//
//        JTextField textFieldForRussianWord = new JTextField(10);
//        textFieldForRussianWord.setFont(new Font("sans-serif", Font.PLAIN, 20));
//        textFieldForRussianWord.setBorder(new BevelBorder(BevelBorder.LOWERED));
//        textFieldForRussianWord.setCaretColor(MyColors.FONT);
//        textFieldForRussianWord.setBackground(MyColors.BUTTON_COLOR);
//        textFieldForRussianWord.setForeground(MyColors.FONT);
//
//
//        //SAVE Button
//        MyButton saveButton = new MyButton("Save word");
//        saveButton.addActionListener(e -> appController.saveOneWord(textFieldForEnglishWord.getText(),
//                textFieldForRussianWord.getText()));
//
//        MyButton back = new MyButton("<---Back");
//        back.addActionListener(e -> appController.runMainPage());
//
//
//        panel.add(scrollPaneForList);
//        panel.add(wordsCount);
//        panel.add(deleteWord);
//        panel.add(resultMessage);
//        panel.add(englishLabel);
//        panel.add(textFieldForEnglishWord);
//        panel.add(russianLabel);
//        panel.add(textFieldForRussianWord);
//        panel.add(saveButton);
//        panel.add(back);
//        SwingUtilities.updateComponentTreeUI(frame);
//    }



}
