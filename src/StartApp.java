import Controller.AppController;
import Model.App;
import Viewer.AppViewer;

import javax.swing.*;

public class StartApp {



    public static void main(String[] args) {

        App app = new App();
        app.loadSettingFromFile();
        app.readAllWordsFromFile();

        AppController appController = new AppController(app);

        AppViewer viewer = new AppViewer(appController);


        SwingUtilities.invokeLater(viewer::runMainPage);

    }
}
