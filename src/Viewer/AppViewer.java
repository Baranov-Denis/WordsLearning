package Viewer;

import Controller.AppController;
import Model.WordCard;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class AppViewer extends JFrame {


    private final AppController appController;
    private final JPanel panel;
    private boolean themeDark;



    public AppViewer(AppController appController) {
        this.appController = appController;
        panel = new JPanel();
    }

    public void setThemeDark(boolean themeDark) {
        this.themeDark = themeDark;
    }



    public void runMainPage() {
        appController.loadTheme();
        setVisible(true);
        MyColors.changeTheme(themeDark);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(340, 605);
        setTitle("Learning");
        setLocation(1300, 300);
        setLayout(new FlowLayout());
        setAlwaysOnTop(true);
        add(panel);
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
        SwingUtilities.updateComponentTreeUI(this);
    }


    /**
     * Run Editor Page
     */
    public void runEditorPage(ArrayList<WordCard> wordsList, String message, int wordsCountNumber) {
        panel.removeAll();
        MyColors.changeTheme(themeDark);


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


        //deleteButton
        MyButton deleteWord = new MyButton("Delete word.");
        deleteWord.addActionListener(e -> {
            int inp = JOptionPane.showConfirmDialog(panel,"Do yo really want delete this word?");
            if(inp == 0) {
                appController.deleteOneWord(wordsJList.getSelectedIndex());
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
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void runLearningPage(WordCard learningWord, ArrayList<WordCard> words, boolean miss){
        panel.removeAll();
        MyColors.changeTheme(themeDark);

        JLabel learningWordLabel = new JLabel(learningWord.getEnglishWord(), SwingConstants.CENTER);
        learningWordLabel.setPreferredSize(new Dimension(285, 50));
        learningWordLabel.setFont(new Font("sans-serif", Font.BOLD, 35));
        panel.add(learningWordLabel);



       if(miss) {
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
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void runSettingPage(String dictionaryPath){
        panel.removeAll();
        MyColors.changeTheme(themeDark);
        panel.setBackground(MyColors.BACKGROUND);

        MyButton buttonReset = new MyButton("Reset learned progress");
        buttonReset.addActionListener(e -> appController.resetAllProgress());



        MyButton buttonBack = new MyButton("<---Back");
        buttonBack.addActionListener(e -> appController.runMainPage());


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


        panel.add(dark);
        panel.add(light);

        panel.add(buttonReset);

        panel.add(changeDirectory);
        panel.add(buttonBack);





        SwingUtilities.updateComponentTreeUI(this);

    }
}
