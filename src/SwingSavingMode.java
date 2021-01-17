import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SwingSavingMode implements ActionListener {

    public static SwingSavingMode swingSawingMode;
    JLabel systemMessage;
    private final JTextField textFieldForEnglishWord;
    private final JTextField textFieldForRussianWord;
    private final JLabel dictionaryCount;


    public SwingSavingMode() {

        swingSawingMode = this;
        JFrame frame = MainClass.mainClass.frame;
        JPanel panel = SwingMainPage.swingMainPage.panel;
        panel.removeAll();


        dictionaryCount = new JLabel();
        dictionaryCount.setText(wordsCountUpdate());
        dictionaryCount.setPreferredSize(new Dimension(335, 220));
        dictionaryCount.setFont(new Font("sans-serif", Font.BOLD, 14));

        JLabel englishLabel = new JLabel("Enter English word: ");
        englishLabel.setPreferredSize(new Dimension(150, 70));
        englishLabel.setFont(new Font("sans-serif", Font.PLAIN, 16));

        JLabel russianLabel = new JLabel("Enter Russian word: ");
        russianLabel.setPreferredSize(new Dimension(150, 70));
        russianLabel.setFont(new Font("sans-serif", Font.PLAIN, 16));

        textFieldForEnglishWord = new JTextField(9);
        textFieldForEnglishWord.setFont(new Font("sans-serif", Font.PLAIN, 20));
        textFieldForEnglishWord.setBorder(new BevelBorder(BevelBorder.LOWERED));
        textFieldForEnglishWord.setCaretColor(MyColors.FONT);
        textFieldForEnglishWord.setBackground(MyColors.BUTTON_COLOR);
        textFieldForEnglishWord.setForeground(MyColors.FONT);

        textFieldForRussianWord = new JTextField(9);
        textFieldForRussianWord.setFont(new Font("sans-serif", Font.PLAIN, 20));
        textFieldForRussianWord.setBorder(new BevelBorder(BevelBorder.LOWERED));
        textFieldForRussianWord.setCaretColor(MyColors.FONT);
        textFieldForRussianWord.setBackground(MyColors.BUTTON_COLOR);
        textFieldForRussianWord.setForeground(MyColors.FONT);

        systemMessage = new JLabel();
        systemMessage.setPreferredSize(new Dimension(335, 160));
        systemMessage.setFont(new Font("sans-serif", Font.BOLD, 14));


        MyButton saveButton = new MyButton("Save");
        saveButton.addActionListener(e -> addNewWord());
        saveButton.setPreferredSize(new Dimension(100, 27));

        JLabel empty = new JLabel();
        empty.setPreferredSize(new Dimension(100, 20));


        MyButton backButton = new MyButton("<---Back");
        backButton.addActionListener(e -> new SwingMainPage());
        backButton.setPreferredSize(new Dimension(100, 27));

        panel.add(dictionaryCount);

        panel.add(englishLabel);
        panel.add(textFieldForEnglishWord);

        panel.add(russianLabel);
        panel.add(textFieldForRussianWord);

        panel.add(systemMessage);

        panel.add(backButton);
        panel.add(empty);
        panel.add(saveButton);


        SwingUtilities.updateComponentTreeUI(frame);

    }

    private String wordsCountUpdate() {
        return String.format("<html> <div style=\"text-align: center; margin : auto; width: 335\">The " +
                        "dictionary has <br><font size = 22> %s </font><br>" +
                        "words.<br><font size = 22> %s </font><br>of them were learned.</div></html>",
                MainClass.dictionary.getAllWordsList().size() , MainClass.dictionary.learnedWordsCounter()) ;
    }


    /**
     * Метод возвращает системное сообщение об успешном сохранении или ошибке
     *
     * @param eng английское слово
     * @param rus русское слово
     * @return system message
     */
    private String systemMessageUpdate(String eng, String rus) {
        if (!eng.equals("") && !rus.equals("") && !MainClass.dictionary.containedThisWord(new WordCard(eng,
                rus, 0 ))) {

            return String.format("<html> <div style=\"text-align:" +
                            " center; margin : auto; width: 335;color : rgb(0, 155, 0)\"> <font size = 9>  " +
                            "%s <br> %s </font><br>  was added. </div></html>"
                    , eng,
                    rus);
        } else
            return "<html> <div style=\"text-align: center; margin : auto; width: 335; color : rgb(155, 0, 0)\"> This" +
                    " " +
                    "word has " +
                    "already exist " +
                    "<br> or incorrect " +
                    "input.</div></html>";
    }


    @Override
    public void actionPerformed(ActionEvent e) {

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
        systemMessage.setText(systemMessageUpdate(eng, rus));
        MainClass.dictionary.writeOneNewWordToFile(eng,rus);
        textFieldForEnglishWord.setText("");
        textFieldForRussianWord.setText("");
        dictionaryCount.setText(wordsCountUpdate());

    }


}
