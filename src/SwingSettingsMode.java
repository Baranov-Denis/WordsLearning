import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class SwingSettingsMode {

    public static SwingSettingsMode swingSettingsMode;

    private final JFrame frame;
    private final JPanel panel;
    private final MyButton buttonReset, buttonBack, buttonSelectDictionary, buttonChangeCountWordRepeats;
    private final JTextField fieldChangeCountWordRepeats;
    private final ButtonGroup buttonGroup;
    private final JRadioButton dark, light;

    private ArrayList<String> tempSettings;


    public SwingSettingsMode() {
        swingSettingsMode = this;
        tempSettings = MainClass.mainClass.getSettings();

        frame = MainClass.mainClass.frame;
        panel = SwingMainPage.swingMainPage.panel;
        panel.removeAll();
        panel.setBackground(MyColors.BACKGROUND);

        buttonReset = new MyButton("Reset learned progress");
        buttonReset.addActionListener(e -> {
            MainClass.mainClass.dictionary.resetAllProgress();
        });


        buttonBack = new MyButton("<---Back");
        buttonBack.addActionListener(e -> {
            new SwingMainPage();
        });


        buttonGroup = new ButtonGroup();
        dark = new JRadioButton("Dark");
        dark.setBackground(MyColors.BUTTON_COLOR);
        dark.setForeground(MyColors.FONT);
        dark.setPreferredSize(new Dimension(157, 41));
        light = new JRadioButton("Light");
        light.setBackground(MyColors.BUTTON_COLOR);
        light.setForeground(MyColors.FONT);
        light.setPreferredSize(new Dimension(158, 41));

        if (MainClass.mainClass.getSettings().get(1).equals("dark")) {
            dark.setSelected(true);
        } else {
            light.setSelected(true);
        }


        buttonGroup.add(dark);
        buttonGroup.add(light);

        light.addActionListener(e -> {
            tempSettings.set(1, "light");
            saveSettings();
            MainClass.mainClass.myColors = new MyColors(false);
            new SwingSettingsMode();
            SwingUtilities.updateComponentTreeUI(frame);
        });

        dark.addActionListener(e -> {
            tempSettings.set(1, "dark");
            saveSettings();
            MainClass.mainClass.myColors = new MyColors(true);
            new SwingSettingsMode();
            SwingUtilities.updateComponentTreeUI(frame);
        });

        buttonChangeCountWordRepeats = new MyButton("How repeat ");
        buttonChangeCountWordRepeats.setPreferredSize(new Dimension(233, 41));
        fieldChangeCountWordRepeats = new JTextField(3);
        fieldChangeCountWordRepeats.setFont(new Font("sans-serif", Font.BOLD, 28));
        fieldChangeCountWordRepeats.setForeground(MyColors.FONT);
        fieldChangeCountWordRepeats.setBackground(MyColors.BUTTON_COLOR);
        fieldChangeCountWordRepeats.setText("" + MainClass.mainClass.dictionary.getCountToKnow());
        buttonChangeCountWordRepeats.addActionListener(e -> {
            int temp = MainClass.mainClass.dictionary.getCountToKnow();
            try {
                temp = Integer.parseInt(fieldChangeCountWordRepeats.getText());
            } catch (NumberFormatException r) {
                System.out.println("Err");
            }
            if (temp > 1 && temp < 100) {
                MainClass.mainClass.dictionary.setCountToKnow(temp);
                tempSettings.set(2, temp + "");
                saveSettings();
            }
        });


        buttonSelectDictionary = new MyButton("Change Dictionary Path");
        buttonSelectDictionary.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int temp = chooser.showDialog(null, "Открыть файл");
            if (temp == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                String fileName = file.getAbsolutePath();
                tempSettings.set(0,fileName);
                saveSettings();
                MainClass.mainClass.dictionary.getAllWordsList().clear();
                MainClass.mainClass.setFileName(fileName);
                MainClass.mainClass.dictionary = new Dictionary();
            }
        });


        panel.add(dark);
        panel.add(light);
        panel.add(buttonChangeCountWordRepeats);
        panel.add(fieldChangeCountWordRepeats);
        panel.add(buttonSelectDictionary);
        panel.add(buttonReset);
        panel.add(buttonBack);


        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void changeTheme(String theme) {
        ArrayList<String> settings = MainClass.mainClass.getSettings();
        MainClass.mainClass.getSettings().set(1, theme);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("settings.txt", false))) {
            writer.write(settings.get(0) + "\r");
            writer.write(theme);
            writer.flush();
        } catch (Exception r) {

        }
    }

    private void changeDictionary(String path) {
        ArrayList<String> settings = MainClass.mainClass.getSettings();
        MainClass.mainClass.getSettings().set(0, path);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("settings.txt", false))) {
            writer.write(path + "\r");
            writer.write(settings.get(1) + "\r");
            writer.flush();
        } catch (Exception r) {

        }
    }

    private void saveSettings() {


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("settings.txt", false))) {
            writer.write(tempSettings.get(0) + "\r");
            writer.write(tempSettings.get(1) + "\r");
            writer.write(tempSettings.get(2) + "\r");
            writer.flush();
        } catch (Exception r) {

        }
    }


}
