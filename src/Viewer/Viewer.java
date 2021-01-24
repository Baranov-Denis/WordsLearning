package Viewer;

import Controller.AppController;
import Model.WordCard;

import javax.swing.*;
import java.util.ArrayList;


public abstract class Viewer {
    String  name = "j";

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
        //System.out.println(themeDark);
    }


    public abstract void runView(ArrayList<WordCard> wordsList, String message, int size);
    public abstract void runView();

    public abstract void runView(String dictionaryFileNamePath);

    public abstract void runView(WordCard oneRandomWordForLearn, ArrayList<WordCard> wordsForButtons, boolean b);
}
