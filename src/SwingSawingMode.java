import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingSawingMode implements ActionListener {

    public static SwingSawingMode swingSawingWords;

    JFrame frame;
    JPanel panel;

    JTextField textFieldForEnglishWord;
    JTextField textFieldForRussianWord;


    JLabel dictionaryCount;
    JLabel englishLabel;
    JLabel russianLabel;
    JLabel systemMessage;
    JLabel empty;

    JButton saveButton;
    JButton backButton;

    public SwingSawingMode() {

        swingSawingWords = this;
        frame = MainClass.mainClass.frame;
        panel = SwingMainPage.swingMainPage.panel;
        panel.removeAll();


        dictionaryCount = new JLabel();
        dictionaryCount.setText(String.format("The dictionary has : %s words.",
                MainClass.mainClass.dictionary.getAllWordsList().size()));
        dictionaryCount.setPreferredSize(new Dimension(230, 70));
        dictionaryCount.setFont(new Font("sans-serif", Font.BOLD, 14));

        englishLabel = new JLabel("Enter English word: ");
        englishLabel.setPreferredSize(new Dimension(150, 70));
        englishLabel.setFont(new Font("sans-serif", Font.PLAIN, 16));

        russianLabel = new JLabel("Enter Russian word: ");
        russianLabel.setPreferredSize(new Dimension(150, 70));
        russianLabel.setFont(new Font("sans-serif", Font.PLAIN, 16));

        textFieldForEnglishWord = new JTextField(9);
        textFieldForEnglishWord.setFont(new Font("sans-serif", Font.PLAIN, 20));
        textFieldForEnglishWord.setBackground(SwingMainPage.swingMainPage.buttonColor);
        textFieldForEnglishWord.setForeground(Color.WHITE);

        textFieldForRussianWord = new JTextField(9);
        textFieldForRussianWord.setFont(new Font("sans-serif", Font.PLAIN, 20));
        textFieldForRussianWord.setBackground(SwingMainPage.swingMainPage.buttonColor);
        textFieldForRussianWord.setForeground(Color.WHITE);

        systemMessage = new JLabel();
        systemMessage.setPreferredSize(new Dimension(335, 70));
        systemMessage.setFont(new Font("sans-serif", Font.BOLD, 14));


        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setBorder(new BevelBorder(5));
        saveButton.setBackground(SwingMainPage.swingMainPage.buttonColor);
        saveButton.setForeground(SwingMainPage.swingMainPage.buttonFontColor);
        saveButton.setPreferredSize(new Dimension(100, 27));

        empty = new JLabel();
        empty.setPreferredSize(new Dimension(100, 20));


        backButton = new JButton("<---Back");
        backButton.addActionListener(this);
        backButton.setBorder(new BevelBorder(5));
        backButton.setBackground(SwingMainPage.swingMainPage.buttonColor);
        backButton.setForeground(SwingMainPage.swingMainPage.buttonFontColor);
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


    @Override
    public void actionPerformed(ActionEvent e) {

        String eng = textFieldForEnglishWord.getText();
        String rus = textFieldForRussianWord.getText();

        if (e.getActionCommand().equals("Save")) {

            MainClass.mainClass.dictionary.addNewWord(eng, rus);
            textFieldForEnglishWord.setText("");
            textFieldForRussianWord.setText("");


        } else if (e.getActionCommand().equals("<---Back")) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    panel.removeAll();
                    new SwingMainPage();
                }
            });

        }
    }


}
