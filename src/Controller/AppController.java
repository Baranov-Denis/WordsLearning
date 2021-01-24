package Controller;

import Model.App;
import Model.WordCard;
import Viewer.Viewer;

import java.util.ArrayList;

public class AppController {
    private final App app;
    private String message = "";
    private ArrayList<Viewer> viewers;


    public AppController(App app) {
        this.app = app;
        viewers = new ArrayList<>();
    }

    public void addViewer(Viewer viewer) {
        viewers.add(viewer);
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
     * ---------------------------------Run Main Page--------------------------------------------
     */
    public void runMainPage() {
        run("MainPage").runView();
    }


    /**
     * ---------------------------------Run Editor Page-------------------------------------------
     */
    public void runEditorPage() {
        run("EditorPage").runView(app.getWordsList(), message, app.getWordsList().size());
        message = "";
    }

    /**
     * deleteOne word
     */
    public void deleteOneWord(int index) {
        if (index >= 0) {
            app.deleteOneWord(app.getWordsList().get(index));
            message = "Word Deleted.";
        }

        run("EditorPage").runView(app.getWordsList(), message, app.getWordsList().size());
        message = "";
    }


    /**
     * saveOne word
     */

    public void saveOneWord(String englishWord, String russianWord) {
        if ((!app.containedThisWord(new WordCard(englishWord, russianWord))) && (!englishWord.equals("")) && (!russianWord.equals(""))) {
            app.saveOneWord(englishWord, russianWord);
            message = "Word saved.";
        } else {
            message = "Error word did not saved.";
        }
        run("EditorPage").runView(app.getWordsList(), message, app.getWordsList().size());
        message = "";
    }


    /**
     * ------------------------------Run Learning Page-------------------------------------------
     */


    public void runLearningPage() {
        app.runLearn();
        run("LearningPage").runView(app.getOneRandomWordForLearn(), app.getWordsForButtons(), true);
    }

    public void buttonsAction(String word) {

        if (word.equals(app.getOneRandomWordForLearn().getRussianWord())) {
            app.hit(app.getOneRandomWordForLearn());
            run("LearningPage").runView(app.getOneRandomWordForLearn(),app.getWordsForButtons(), true);
        } else {
            app.loose(app.getOneRandomWordForLearn());
            run("LearningPage").runView(app.getOneRandomWordForLearn(),app.getWordsForButtons(), false);
        }
        app.saveDictionaryToFile();
    }


    /**
     * ------------------------------Run Setting Page-------------------------------------------
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
