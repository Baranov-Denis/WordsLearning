package Controller;

import Model.App;
import Model.WordCard;
import java.util.ArrayList;

public class AppController {

    private final App app;

    public AppController(App app) {
        this.app = app;
    }

    /**
     *
     * @return Theme
     * true - dark
     * false - light
     */
    public boolean loadTheme() {
        return app.isThemeDark();
    }

    /**
     *
     * @return size of Words List
     */
    public ArrayList<WordCard> getWordsList(){
        return app.getWordsList();
    }




    /**
     * deleteOne word
     */
    public void deleteOneWord(int index) {
        app.deleteOneWord(app.getWordsList().get(index));
    }

    /**
     * saveOne word
     */

    public boolean saveOneWord(String englishWord,String russianWord){
        return app.saveOneWord(englishWord,russianWord);
    }
}
