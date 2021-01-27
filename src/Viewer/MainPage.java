package Viewer;

import Controller.AppController;


import javax.swing.*;

import java.awt.*;


public class MainPage extends Viewer {

    public MainPage(AppController appController) {
        super(appController);
        name = "MainPage";
    }




    public void runView() {
       themeDark = appController.loadTheme();

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(340, 605);
        frame.setTitle("Learning");
        frame.setLocation(1300, 300);
        frame.setLayout(new FlowLayout());
        frame.setAlwaysOnTop(true);
        frame.add(panel);
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
        SwingUtilities.updateComponentTreeUI(frame);
    }





}
