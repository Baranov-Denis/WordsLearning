package Viewer;

import Controller.AppController;
import Model.WordCard;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class SettingPage extends Viewer{

    public SettingPage(AppController appController) {
        super(appController);
        name = "SettingPage";
    }





    public void runView(String dictionaryPath){
        System.out.println(themeDark);

        panel.removeAll();
        appController.loadTheme();

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





        SwingUtilities.updateComponentTreeUI(frame);

    }



    @Override
    public void runView() {

    }
}
