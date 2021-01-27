package Controller;

import Model.App;
import Viewer.*;

public class AppController {
    private final App app;
    private MainPage mainPage;
    private EditorPage editorPage;
    private LearningPage learningPage;
    private SettingPage settingPage;


    public AppController(App app) {
        this.app = app;

    }

    public void addViewer(Viewer viewer) {

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


    /**
     * true - dark
     * false - light
     */
    public boolean loadTheme() {
        MyColors.changeTheme(app.isThemeDark());
        return app.isThemeDark();
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
     * ****************************************************** RUN SETTING PAGE ****************************************
     */
    public void runSettingPage() {
        settingPage.setIntNumOfRepeatOfASingleWord(app.getNumberOfRepeatOfASingleWord());
        settingPage.setDictionaryPath(app.getDictionaryFileNamePath());
        settingPage.setIntNumOfWords(app.getNumberOfLearningWords());
        settingPage.runView();
    }

    public void resetAllProgress() {
        app.resetAllProgress();
        settingPage.runView();
    }

    public void changeTheme(boolean theme) {
        app.setThemeDark(theme);
        app.saveSettingToFile();
        settingPage.setThemeDark(app.isThemeDark());
        settingPage.runView();
    }


    public void setDictionaryPath(String fileName) {
        app.setDictionaryFileNamePath(fileName);
        app.saveSettingToFile();
        app.readAllWordsFromFile();
        settingPage.setDictionaryPath(app.getDictionaryFileNamePath());
        settingPage.runView();
    }

    public void saveAndExit(int numOfRepeat, int numOfWords) {
        app.setNumberOfLearningWords(numOfWords);
        app.setNumberOfRepeatOfASingleWord(numOfRepeat);
        app.saveSettingToFile();
        mainPage.runView();
    }
}
