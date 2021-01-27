package Viewer;

import Controller.AppController;

import javax.swing.*;


public abstract class Viewer {
    String  name = "";

    public String getName() {
        return name;
    }

    static JFrame frame = new JFrame();
    static JPanel panel = new JPanel();
    final AppController appController;
    boolean themeDark;

    public Viewer(AppController appController) {
        this.appController = appController;
    }

    public void setThemeDark(boolean themeDark) {
        this.themeDark = themeDark;
        MyColors.changeTheme(themeDark);
    }



    public abstract void runView();




}
