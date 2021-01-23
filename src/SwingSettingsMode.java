import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class SwingSettingsMode {

    public static SwingSettingsMode swingSettingsMode;

    private final JFrame frame;
    private final JPanel panel;
    private final MyButton buttonSelectDictionary;
    private JLabel statistic;
    private final JTextField fieldChangeCountWordRepeats/*, fieldCurrentDirectory*/;


    public SwingSettingsMode() {
        swingSettingsMode = this;

        frame = MainClass.mainClass.frame;
        panel = SwingMainPage.swingMainPage.panel;
        panel.removeAll();
        panel.setBackground(MyColors.BACKGROUND);

        MyButton buttonReset = new MyButton("Reset learned progress");
        buttonReset.addActionListener(e -> {

            int inp = JOptionPane.showConfirmDialog(panel, "All progress will be reset!!!");

            if (inp == 0) {
              MainClass.dictionary.resetAllProgress();
                statistic.setText(wordsCountUpdate());
            }
        });

        statistic = new JLabel();
        statistic.setText(wordsCountUpdate());
        statistic.setPreferredSize(new Dimension(320, 41));
        statistic.setBackground(MyColors.BUTTON_COLOR);


        MyButton buttonBack = new MyButton("<---Back");
        buttonBack.addActionListener(e -> {
            saveCountRepeat();
            new SwingMainPage();
                }
        );


        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton dark = new JRadioButton("Dark");
        dark.setBackground(MyColors.BUTTON_COLOR);
        dark.setForeground(MyColors.FONT);
        dark.setPreferredSize(new Dimension(157, 41));

        JRadioButton light = new JRadioButton("Light");
        light.setBackground(MyColors.BUTTON_COLOR);
        light.setForeground(MyColors.FONT);
        light.setPreferredSize(new Dimension(158, 41));

        if (MainClass.isThemeDark()) {
            dark.setSelected(true);
        } else {
            light.setSelected(true);
        }


        buttonGroup.add(dark);
        buttonGroup.add(light);

        light.addActionListener(e -> {
            MainClass.setThemeDark(false);
            MainClass.mainClass.saveSettingToFile(MainClass.mainClass.getSettingFilePath());
            MainClass.myColors.changeTheme(false);
            new SwingSettingsMode();
            SwingUtilities.updateComponentTreeUI(frame);
        });

        dark.addActionListener(e -> {
            MainClass.setThemeDark(true);
            MainClass.mainClass.saveSettingToFile(MainClass.mainClass.getSettingFilePath());
            MainClass.myColors.changeTheme(true);
            new SwingSettingsMode();
            SwingUtilities.updateComponentTreeUI(frame);
        });

        MyButton buttonChangeCountWordRepeats = new MyButton("Press for save word repeat ");
        buttonChangeCountWordRepeats.setPreferredSize(new Dimension(233, 41));
        fieldChangeCountWordRepeats = new JTextField(3);
        fieldChangeCountWordRepeats.setFont(new Font("sans-serif", Font.BOLD, 28));
        fieldChangeCountWordRepeats.setBorder(new BevelBorder(BevelBorder.LOWERED));
        fieldChangeCountWordRepeats.setCaretColor(MyColors.FONT);
        fieldChangeCountWordRepeats.setForeground(MyColors.FONT);
        fieldChangeCountWordRepeats.setBackground(MyColors.BUTTON_COLOR);
        fieldChangeCountWordRepeats.setText("" + MainClass.dictionary.getCountToKnow());
        buttonChangeCountWordRepeats.addActionListener(e -> {
           saveCountRepeat();
        });

        JLabel numberOfRepetitionsOfAWordToLearn = new JLabel("Number of repetitions of a word");
        numberOfRepetitionsOfAWordToLearn.setPreferredSize(new Dimension(233, 41));
        numberOfRepetitionsOfAWordToLearn.setFont(new Font("sans-serif", Font.BOLD, 14));





        buttonSelectDictionary = new MyButton("Dictionary : " + MainClass.getDictionaryFilePath());
        buttonSelectDictionary.addActionListener(this::actionForChangeDirectory);



        panel.add(dark);
        panel.add(light);
       // panel.add(buttonChangeCountWordRepeats);
        panel.add(numberOfRepetitionsOfAWordToLearn);
        panel.add(fieldChangeCountWordRepeats);
        panel.add(buttonSelectDictionary);
        panel.add(statistic);
        panel.add(buttonReset);
        panel.add(buttonBack);





        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void saveCountRepeat(){
        int temp = MainClass.dictionary.getCountToKnow();
        try {
            temp = Integer.parseInt(fieldChangeCountWordRepeats.getText());
        } catch (NumberFormatException r) {
            System.out.println("Err");
        }
        if (temp > 1 && temp < 100) {
            MainClass.dictionary.setCountToKnow(temp);
            //tempSettings.set(2, temp + "");
            MainClass.setNumberOfRepeatOfASingleWord(temp);
            //saveSettings();
            MainClass.mainClass.saveSettingToFile(MainClass.mainClass.getSettingFilePath());
        }
    }

    private String wordsCountUpdate() {
        return String.format("<html><div style=\"text-align: center; margin : auto; width: 335\">Learned <font size =" +
                        " 5> %s </font>" +
                        "from <font size = 5> %s </font>words.</div></html>",
                MainClass.dictionary.learnedWordsCounter(),
                MainClass.dictionary.getAllWordsList().size()  ) ;
    }




    public void actionForChangeDirectory(ActionEvent e){
       JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        int temp = chooser.showDialog(panel, "Открыть файл");
        if (temp == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String fileName = file.getAbsolutePath();
            MainClass.setDictionaryFilePath(fileName);
            MainClass.mainClass.saveSettingToFile(MainClass.mainClass.getSettingFilePath());
            MainClass.dictionary.getAllWordsList().clear();
            MainClass.setDictionaryFilePath(fileName);
            MainClass.dictionary = new Dictionary();
            buttonSelectDictionary.setText("Dictionary : " + MainClass.getDictionaryFilePath());
            statistic.setText(wordsCountUpdate());
        }

    }


}
