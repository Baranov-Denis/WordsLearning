package Controller;

import Model.App;
import Viewer.*;

import java.util.ArrayList;

public class AppController {
    private final App app;
    private MainPage mainPage;
    private EditorPage editorPage;
    private LearningPage learningPage;
    private SettingPage settingPage;
    private ArrayList<Viewer> viewers;


    public AppController(App app) {
        this.app = app;
        viewers = new ArrayList<>();
    }

    public void addViewer(Viewer viewer) {
        viewers.add(viewer);
        switch (viewer.getName()) {
            case "MainPage":
                mainPage = (MainPage) viewer;
                break;
            case "EditorPage":
                editorPage = (EditorPage) viewer;
                break;
            case "LearningPage":
                learningPage = (LearningPage) viewer;
                break;
            case "SettingPage":
                settingPage = (SettingPage) viewer;
                break;
        }
    }

    private Viewer run(String name) {
        Viewer targetViewer = null;
        for (Viewer viewer : viewers) {
            if (viewer.getName().equals(name)) {
                targetViewer = viewer;
                break;
            }
        }
        return targetViewer;
    }

    /**
     * true - dark
     * false - light
     */
    public void loadTheme() {
        for (Viewer viewer : viewers) viewer.setThemeDark(app.isThemeDark());
    }


    /***
     ***************************************** RUN MAIN PAGE ****************************************
     */
    public void runMainPage() {
        mainPage.runView();
    }


    /**
     * ***************************************** RUN EDITOR PAGE *************************************
     */
    public void runEditorPage() {
        editorPage.setWordsList(app.getWordsList());
        editorPage.setMessage("");
        editorPage.setWordsCountNumber(app.getWordsList().size());
        editorPage.runView();
    }

    /**
     * delete One word
     */
    public void deleteOneWord(int index) {
        app.deleteOneWord(index);
        editorPage.setMessage("Word Deleted.");
        editorPage.setWordsList(app.getWordsList());
        editorPage.setWordsCountNumber(app.getWordsList().size());
        editorPage.runView();
    }


    /**
     * save One word
     */

    public void saveOneWord(String englishWord, String russianWord) {
        if (app.saveOneWord(englishWord, russianWord)) {
            editorPage.setMessage("Word saved.");
        } else {
            editorPage.setMessage("Error word did not saved.");
        }
        editorPage.setWordsList(app.getWordsList());
        editorPage.setWordsCountNumber(app.getWordsList().size());
        editorPage.runView();
    }


    /**
     * ********************************************************* RUN LEARNING PAGE **************************
     */


    public void runLearningPage() {
        app.runLearn();
        learningPage.setLearningWord(app.getOneRandomWordForLearn());
        learningPage.setWords(app.getWordsForButtons());
        learningPage.setHit(true);
        learningPage.runView();

    }

    public void buttonsAction(String word) {


        if (app.checkingWord(word)) {
            learningPage.setLearningWord(app.getOneRandomWordForLearn());
            learningPage.setWords(app.getWordsForButtons());
            learningPage.setHit(true);
            learningPage.runView();
        } else {
            learningPage.setLearningWord(app.getOneRandomWordForLearn());
            learningPage.setWords(app.getWordsForButtons());
            learningPage.setHit(false);
            learningPage.runView();
        }
    }


    /**
     ******************************************************* RUN SETTING PAGE ****************************************
     */
    public void runSettingPage() {
        run("SettingPage").runView(app.getDictionaryFileNamePath());
    }

    public void resetAllProgress() {
        app.resetAllProgress();
        run("SettingPage").runView(app.getDictionaryFileNamePath());
    }

    public void changeTheme(boolean theme) {

        app.setThemeDark(theme);
        app.saveSettingToFile();
        run("SettingPage").setThemeDark(app.isThemeDark());
        run("SettingPage").runView(app.getDictionaryFileNamePath());
    }


    public void setDictionaryPath(String fileName) {
        app.setDictionaryFileNamePath(fileName);
        app.saveSettingToFile();
        app.readAllWordsFromFile();
        run("SettingPage").runView(app.getDictionaryFileNamePath());
    }
}
