package Viewer;

import Controller.AppController;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class AppViewer extends JFrame {


    private final AppController appController;
    private boolean themeDark;
    private final JPanel panel;
    private final MyButton back;

    public AppViewer(AppController appController) {
        this.appController = appController;
        panel = new JPanel();
        themeDark = appController.loadTheme();
        MyColors.changeTheme(themeDark);
        back = new MyButton("<---Back");
        back.addActionListener(e -> runMainPage());
    }

    public void runMainPage() {
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
        dictionaryEditor.addActionListener(e -> runEditorPage());

        MyButton learning = new MyButton("Learn");
        learning.setFont(new Font("sans-serif", Font.BOLD, 30));
        learning.setPreferredSize(new Dimension(320, 180));

        MyButton settings = new MyButton("Settings");
        settings.setFont(new Font("sans-serif", Font.BOLD, 30));
        settings.setPreferredSize(new Dimension(320, 180));

        panel.add(dictionaryEditor);
        panel.add(learning);
        panel.add(settings);
        SwingUtilities.updateComponentTreeUI(this);
    }


    /**
     * Run Editor Page
     */
    private void runEditorPage() {
        panel.removeAll();
        MyColors.changeTheme(themeDark);


        //words list window
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> wordsList = new JList<>(listModel);
        wordsList.setFont(new Font("sans-serif", Font.PLAIN, 14));
        wordsList.setBorder(new BevelBorder(BevelBorder.LOWERED));
        wordsList.setBackground(MyColors.BUTTON_COLOR);
        wordsList.setForeground(MyColors.FONT);
        JScrollPane scrollPaneForList = new JScrollPane(wordsList);
        scrollPaneForList.setPreferredSize(new Dimension(320, 285));
        for (int i = 0; i < appController.getWordsList().size(); i++) {
            listModel.addElement(appController.getWordsList().get(i).getEnglishWord() + " - " + appController.getWordsList().get(i).getRussianWord());
        }


        //Count words
        JLabel wordsCount = new JLabel();
        wordsCount.setPreferredSize(new Dimension(300, 20));
        wordsCount.setText(String.format("<html> <div style=\"text-align: right;  width: " +
                        "300\"> In the dictionary <font size = 5>%s</font> words.</div></html>",
                appController.getWordsList().size()));


        //ResultMessage
        JLabel resultMessage = new JLabel();
        resultMessage.setPreferredSize(new Dimension(300,20));


        //deleteButton
        MyButton deleteWord = new MyButton("Delete word");
        deleteWord.addActionListener(e -> {
            if (wordsList.getSelectedIndex() >= 0) {
                appController.deleteOneWord(wordsList.getSelectedIndex());
                listModel.clear();
                for (int i = 0; i < appController.getWordsList().size(); i++) {
                    listModel.addElement(appController.getWordsList().get(i).getEnglishWord() + " - " + appController.getWordsList().get(i).getRussianWord());
                }
                resultMessage.setText("Word deleted.");
            }
            wordsCount.setText(String.format("<html> <div style=\"text-align: right;  width: " +
                            "300\"> In the dictionary <font size = 5>%s </font> words.</div></html>",
                    appController.getWordsList().size()));
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
        saveButton.addActionListener(e -> {
            if((appController.saveOneWord(textFieldForEnglishWord.getText(), textFieldForRussianWord.getText())) && (!textFieldForEnglishWord.getText().equals("")) && (!textFieldForRussianWord.getText().equals(""))) {
                listModel.clear();
                for (int i = 0; i < appController.getWordsList().size(); i++) {
                    listModel.addElement(appController.getWordsList().get(i).getEnglishWord() + " - " + appController.getWordsList().get(i).getRussianWord());
                }
                resultMessage.setText("Word saved.");
            }else {
                resultMessage.setText("Error word did not saved.");
            }
            textFieldForEnglishWord.setText("");
            textFieldForRussianWord.setText("");
        });


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
}
