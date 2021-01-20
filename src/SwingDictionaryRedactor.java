import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;

public class SwingDictionaryRedactor {

    private ArrayList<WordCard> wordCards;
    private JTextField textFieldForEnglishWord;
    private JTextField textFieldForRussianWord;
    private DefaultListModel<String> listModel;
    private JLabel systemMessage;
    private MyButton delete;
    private JLabel dictionaryCount;

    public SwingDictionaryRedactor() {
        JFrame frame = MainClass.mainClass.frame;
        JPanel panel = SwingMainPage.swingMainPage.panel;
        panel.removeAll();

        wordCards = MainClass.dictionary.getAllWordsList();


        listModel = new DefaultListModel<>();
        JList<String> wordsList = new JList<>(listModel);

        wordsList.setFont(new Font("sans-serif", Font.PLAIN, 14));
        wordsList.setBorder(new BevelBorder(BevelBorder.LOWERED));
        wordsList.setBackground(MyColors.BUTTON_COLOR);
        wordsList.setForeground(MyColors.FONT);
        JScrollPane scrollPaneForList = new JScrollPane(wordsList);
        scrollPaneForList.setPreferredSize(new Dimension(320, 280));
        showWord();//  for (WordCard word : wordCards) listModel.addElement(word.getEnglishWord() + " - " + word.getRussianWord() +
        //" - " + word.getCount() + " - " + word.isLearning() );

        dictionaryCount = new JLabel();
        dictionaryCount.setText(wordsCountUpdate());

/***
 *
 *
 *
 * Delete - i have to do it in method
 */
        delete = new MyButton("Delete");
        delete.addActionListener(e -> {
                int index = wordsList.getSelectedIndex();
                if(index >= 0) {

                    int inp = JOptionPane.showConfirmDialog(panel,
                            "\"" + wordCards.get(index).getEnglishWord() + " - " + wordCards.get(index).getRussianWord() +
                                    "\" will be removed!!!");

                    if (inp == 0) {


                        systemMessage.setText(systemMessageUpdate(wordCards.get(index).getEnglishWord(), wordCards.get(index).getRussianWord(), false));
                        MainClass.dictionary.deleteOneWord(wordCards.get(index));
                        MainClass.dictionary.readAllWordsFromFile();
                        wordCards = MainClass.dictionary.getAllWordsList();
                        listModel.clear();
                        showWord();
                        dictionaryCount.setText(wordsCountUpdate());
                    }
                }
        });


        JLabel englishLabel = new JLabel("Enter English word: ");
        englishLabel.setPreferredSize(new Dimension(150, 35));
        englishLabel.setFont(new Font("sans-serif", Font.BOLD, 14));

        JLabel russianLabel = new JLabel("Enter Russian word: ");
        russianLabel.setPreferredSize(new Dimension(150, 35));
        russianLabel.setFont(new Font("sans-serif", Font.BOLD, 14));

        textFieldForEnglishWord = new JTextField(10);
        textFieldForEnglishWord.setFont(new Font("sans-serif", Font.PLAIN, 20));
        textFieldForEnglishWord.setBorder(new BevelBorder(BevelBorder.LOWERED));
        textFieldForEnglishWord.setCaretColor(MyColors.FONT);
        textFieldForEnglishWord.setBackground(MyColors.BUTTON_COLOR);
        textFieldForEnglishWord.setForeground(MyColors.FONT);

        textFieldForRussianWord = new JTextField(10);
        textFieldForRussianWord.setFont(new Font("sans-serif", Font.PLAIN, 20));
        textFieldForRussianWord.setBorder(new BevelBorder(BevelBorder.LOWERED));
        textFieldForRussianWord.setCaretColor(MyColors.FONT);
        textFieldForRussianWord.setBackground(MyColors.BUTTON_COLOR);
        textFieldForRussianWord.setForeground(MyColors.FONT);

        MyButton saveButton = new MyButton("Save");
        saveButton.addActionListener(e -> addNewWord());
        //    saveButton.setPreferredSize(new Dimension(320, 27));


        systemMessage = new JLabel();
        systemMessage.setPreferredSize(new Dimension(320, 40));
        systemMessage.setFont(new Font("sans-serif", Font.BOLD, 14));


        MyButton backButton = new MyButton("<---Back");
        backButton.addActionListener(e -> new SwingMainPage());
        //  backButton.setPreferredSize(new Dimension(100, 27));


        frame.add(panel);
        panel.add(scrollPaneForList);
        panel.add(dictionaryCount);
        panel.add(delete);
        panel.add(systemMessage);
        panel.add(englishLabel);
        panel.add(textFieldForEnglishWord);
        panel.add(russianLabel);
        panel.add(textFieldForRussianWord);
        panel.add(saveButton);
        panel.add(backButton);

        SwingUtilities.updateComponentTreeUI(frame);
    }


    /**
     * Метод для работы кнопки SAVE
     * считывает текст из полей
     * запускает сохранение карточки в файл
     * очищает поля ввода
     * обновляет поле с количеством слов в словаре и сообщение об успешном сохранении или ошибке
     * In this method all the words make to lower case and trim space
     */
    private void addNewWord() {
        String eng = textFieldForEnglishWord.getText().toLowerCase().trim();
        String rus = textFieldForRussianWord.getText().toLowerCase().trim();
        systemMessage.setText(systemMessageUpdate(eng, rus, true));
        MainClass.dictionary.writeOneNewWordToFile(eng, rus);
        textFieldForEnglishWord.setText("");
        textFieldForRussianWord.setText("");
        dictionaryCount.setText(wordsCountUpdate());
        listModel.clear();
        showWord();

    }

    /**
     * Метод возвращает системное сообщение об успешном сохранении или ошибке
     *
     * @param eng английское слово
     * @param rus русское слово
     * @return system message
     */
    private String systemMessageUpdate(String eng, String rus, boolean added) {
        if (added) {
            if (!eng.equals("") && !rus.equals("") && !MainClass.dictionary.containedThisWord(new WordCard(eng,
                    rus, 0))) {

                return String.format("<html> <div style=\"text-align:" +
                                " center; margin : auto; width: 335;color : rgb(0, 155, 0)\"> <font size = 4>  " +
                                "%s <br> %s  </font></div></html>"
                        , eng,
                        rus);
            } else
                return "<html> <div style=\"text-align: center; margin : auto; width: 335; color : rgb(155, 0, 0)\">  <font size = 4>This" +
                        " " +
                        "word has " +
                        "already exist " +
                        "<br> or incorrect " +
                        "input.</font></div></html>";
        } else

            return "<html> <div style=\"text-align: center; margin : auto; width: 335; color : rgb(155, 0, 0)\">  " +
                    "<font size = 4>" + eng + " - " + rus + " was removed </font></div></html>";
    }


    private String wordsCountUpdate() {
        return String.format("<html> <div style=\"text-align: right; margin : auto; width: 320\"><font size = 5> %s " +
                        "</font>" +
                        "words of <font size = 5> %s </font> were learned.</div></html>",
                MainClass.dictionary.learnedWordsCounter(), MainClass.dictionary.getAllWordsList().size());
    }


    private void showWord() {
        for (WordCard word : wordCards)
            listModel.addElement(word.getEnglishWord() + " - " + word.getRussianWord() +
                    " - " + word.getCount());
    }


}
