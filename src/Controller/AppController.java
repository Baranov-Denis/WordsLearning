package Controller;

import Model.App;
import Model.WordCard;
import Viewer.AppViewer;

public class AppController {
    private final App app;
    private String message = "";
    private AppViewer appViewer;


    public AppController(App app) {
        this.app = app;
    }

    public void addViewer(AppViewer appViewer) {
        this.appViewer = appViewer;
    }

    /**
     * true - dark
     * false - light
     */
    public void loadTheme() {
        appViewer.setThemeDark(app.isThemeDark());
    }


    /**
     * deleteOne word
     */
    public void deleteOneWord(int index) {
        if (index >= 0) {
            app.deleteOneWord(app.getWordsList().get(index));
            message = "Word Deleted.";
        }
        appViewer.runEditorPage(app.getWordsList(), message, app.getWordsList().size());
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
        appViewer.runEditorPage(app.getWordsList(), message, app.getWordsList().size());
        message = "";
    }


    public void runMainPage() {
        appViewer.runMainPage();
    }

    public void runEditorPage() {
        appViewer.runEditorPage(app.getWordsList(), message, app.getWordsList().size());
        message = "";
    }

    public void runLearningPage() {
        app.createStudyingListFromAllWordsList();
        app.createOneRandomWordForLearn();
        app.createRandomList(app.getOneRandomWordForLearn());
        appViewer.runLearningPage(app.getOneRandomWordForLearn(), app.getWordsForButtons(),
                true);
    }

    public void buttonsAction(String word) {

        if (word.equals(app.getOneRandomWordForLearn().getRussianWord())) {
            app.missed(app.getOneRandomWordForLearn());
            appViewer.runLearningPage(app.getOneRandomWordForLearn(),
                    app.getWordsForButtons(), true);
            for(WordCard f : app.getWordsListForLearning()) System.out.println(f.getRussianWord());
        } else {
            app.loose(app.getOneRandomWordForLearn());
            appViewer.runLearningPage(app.getOneRandomWordForLearn(),
                    app.getWordsForButtons(), false);
        }
        app.saveDictionaryToFile();
    }

    public void runSettingPage() {
        appViewer.runSettingPage(app.getDictionaryFileNamePath());
    }

    public void resetAllProgress() {
        app.resetAllProgress();
        appViewer.runSettingPage(app.getDictionaryFileNamePath());
    }

    public void changeTheme(boolean theme) {
        app.setThemeDark(theme);
        app.saveSettingToFile();
        appViewer.setThemeDark(app.isThemeDark());
        appViewer.runSettingPage(app.getDictionaryFileNamePath());
    }


    public void setDictionaryPath(String fileName) {
        app.setDictionaryFileNamePath(fileName);
        app.saveSettingToFile();
        app.readAllWordsFromFile();
        appViewer.runSettingPage(app.getDictionaryFileNamePath());
    }
}
