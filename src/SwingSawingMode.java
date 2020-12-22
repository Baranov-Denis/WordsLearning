import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingSawingMode implements ActionListener {

    public static SwingSawingMode swingSawingWords;
    private final MyButton saveButton;
    private final MyButton backButton;
    JLabel systemMessage;
    private JFrame frame;
    private JPanel panel;
    private JTextField textFieldForEnglishWord;
    private JTextField textFieldForRussianWord;
    private JLabel dictionaryCount;
    private JLabel englishLabel;
    private JLabel russianLabel;
    private JLabel empty;


    public SwingSawingMode() {

        swingSawingWords = this;
        frame = MainClass.mainClass.frame;
        panel = SwingMainPage.swingMainPage.panel;
        panel.removeAll();


        dictionaryCount = new JLabel();
        dictionaryCount.setText(wordsCountUpdate());
        dictionaryCount.setPreferredSize(new Dimension(335, 220));
        dictionaryCount.setFont(new Font("sans-serif", Font.BOLD, 14));

        englishLabel = new JLabel("Enter English word: ");
        englishLabel.setPreferredSize(new Dimension(150, 70));
        englishLabel.setFont(new Font("sans-serif", Font.PLAIN, 16));

        russianLabel = new JLabel("Enter Russian word: ");
        russianLabel.setPreferredSize(new Dimension(150, 70));
        russianLabel.setFont(new Font("sans-serif", Font.PLAIN, 16));

        textFieldForEnglishWord = new JTextField(9);
        textFieldForEnglishWord.setFont(new Font("sans-serif", Font.PLAIN, 20));
        textFieldForEnglishWord.setBackground(MyColors.BUTTON_COLOR);
        textFieldForEnglishWord.setForeground(MyColors.FONT);

        textFieldForRussianWord = new JTextField(9);
        textFieldForRussianWord.setFont(new Font("sans-serif", Font.PLAIN, 20));
        textFieldForRussianWord.setBackground(MyColors.BUTTON_COLOR);
        textFieldForRussianWord.setForeground(MyColors.FONT);

        systemMessage = new JLabel();
        systemMessage.setPreferredSize(new Dimension(335, 160));
        systemMessage.setFont(new Font("sans-serif", Font.BOLD, 14));


        saveButton = new MyButton("Save");
        saveButton.addActionListener(e -> {
            addNewWord();
        });
        saveButton.setPreferredSize(new Dimension(100, 27));

        empty = new JLabel();
        empty.setPreferredSize(new Dimension(100, 20));


        backButton = new MyButton("<---Back");
        backButton.addActionListener(e -> {
            new SwingMainPage();
        });
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
                        "dictionary has :<br><font size = 22> %s </font><br>" +
                        "words.</div></html>",
                MainClass.mainClass.dictionary.getAllWordsList().size());
    }


    /**
     *
     *
     * Метод возвращает системное сообщение об успешном сохранении или ошибке
     * @param eng английское слово
     * @param rus русское слово
     * @return
     */
    private String systemMessageUpdate(String eng, String rus) {
        if (!eng.equals("") && !rus.equals("")) {
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
     */
    private void addNewWord() {
        String eng = textFieldForEnglishWord.getText();
        String rus = textFieldForRussianWord.getText();
        MainClass.mainClass.dictionary.addNewWord(eng, rus);
        textFieldForEnglishWord.setText("");
        textFieldForRussianWord.setText("");
        dictionaryCount.setText(wordsCountUpdate());
        systemMessage.setText(systemMessageUpdate(eng, rus));
    }


}
