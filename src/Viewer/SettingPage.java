package Viewer;

import Controller.AppController;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.File;

public class SettingPage extends Viewer{

    private String dictionaryPath;
    private int intNumOfRepeatOfASingleWord;
    private int intNumOfWords;

    public void setIntNumOfWords(int intNumOfWords) {
        this.intNumOfWords = intNumOfWords;
    }

    public void setIntNumOfRepeatOfASingleWord(int intNumOfRepeatOfASingleWord) {
        this.intNumOfRepeatOfASingleWord = intNumOfRepeatOfASingleWord;
    }

    public void setDictionaryPath(String dictionaryPath) {
        this.dictionaryPath = dictionaryPath;
    }

    public SettingPage(AppController appController) {
        super(appController);
        name = "SettingPage";
    }





    public void runView(){


        panel.removeAll();
       themeDark = appController.loadTheme();

        panel.setBackground(MyColors.BACKGROUND);

        MyButton buttonReset = new MyButton("Reset learned progress");
        buttonReset.addActionListener(e -> {
            int inp = JOptionPane.showConfirmDialog(panel, "Do yo really want reset all progress?");
            if (inp == 0) {
                appController.resetAllProgress();
            }
        });





        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton dark = new JRadioButton("Dark");
        dark.setBackground(MyColors.BUTTON_COLOR);
        dark.setForeground(MyColors.FONT);
        dark.setPreferredSize(new Dimension(157, 41));

        JRadioButton light = new JRadioButton("Light");
        light.setBackground(MyColors.BUTTON_COLOR);
        light.setForeground(MyColors.FONT);
        light.setPreferredSize(new Dimension(158, 41));


        if (themeDark) {
            dark.setSelected(true);
        } else {
            light.setSelected(true);
        }

        buttonGroup.add(dark);
        buttonGroup.add(light);

        light.addActionListener(e -> appController.changeTheme(false));
        dark.addActionListener(e -> appController.changeTheme(true));



        JLabel numberOfRepeatOfASingleWord = new JLabel();
        numberOfRepeatOfASingleWord.setText("How many times repeat one word :");
        numberOfRepeatOfASingleWord.setPreferredSize(new Dimension(250,40));

        JTextField numberField = new JTextField(3);
        numberField.setText(""+intNumOfRepeatOfASingleWord);
        numberField.setPreferredSize(new Dimension(50,40));
        numberField.setFont(new Font("sans-serif", Font.PLAIN, 20));
        numberField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        numberField.setCaretColor(MyColors.FONT);
        numberField.setBackground(MyColors.BUTTON_COLOR);
        numberField.setForeground(MyColors.FONT);


        JLabel numberOfWords = new JLabel();
        numberOfWords.setText("How many words to learn at the same time :");
        numberOfWords.setPreferredSize(new Dimension(250,40));

        JTextField wordsField = new JTextField(3);
        wordsField.setText(""+intNumOfWords);
        wordsField.setPreferredSize(new Dimension(50,40));
        wordsField.setFont(new Font("sans-serif", Font.PLAIN, 20));
        wordsField.setBorder(new BevelBorder(BevelBorder.LOWERED));
        wordsField.setCaretColor(MyColors.FONT);
        wordsField.setBackground(MyColors.BUTTON_COLOR);
        wordsField.setForeground(MyColors.FONT);



        MyButton changeDirectory = new MyButton(dictionaryPath);
        changeDirectory.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            int temp = chooser.showDialog(panel, "Открыть файл");
            if (temp == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                String fileName = file.getAbsolutePath();
                appController.setDictionaryPath(fileName);
            }
        });


        MyButton buttonBack = new MyButton("<---Back");
        buttonBack.addActionListener(e -> appController.saveAndExit(Integer.parseInt(numberField.getText()),Integer.parseInt(wordsField.getText())));


        panel.add(dark);
        panel.add(light);

        panel.add(numberOfRepeatOfASingleWord);
        panel.add(numberField);

        panel.add(numberOfWords);
        panel.add(wordsField);

        panel.add(buttonReset);

        panel.add(changeDirectory);
        panel.add(buttonBack);





        SwingUtilities.updateComponentTreeUI(frame);

    }




}
