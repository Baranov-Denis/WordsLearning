import Controller.AppController;
import Model.App;
import Viewer.MainPage;
import Viewer.EditorPage;
import Viewer.SettingPage;
import Viewer.LearningPage;
import Viewer.Viewer;

import javax.swing.*;

public class StartApp {


    public static void main(String[] args) {

        App app = new App();


        AppController appController = new AppController(app);

        Viewer viewer = new MainPage(appController);
        Viewer editorPage = new EditorPage(appController);
        Viewer settingPage = new SettingPage(appController);
        Viewer learningPage = new LearningPage(appController);

        //appController.addViewer(viewer, editorPage, settingPage);
        appController.addViewer(viewer);
        appController.addViewer(editorPage);
        appController.addViewer(settingPage);
        appController.addViewer(learningPage);

        SwingUtilities.invokeLater(appController::runMainPage);

    }
}
