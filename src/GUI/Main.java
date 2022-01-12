package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("mainWindow.fxml"));
        Parent mainWindow = mainLoader.load();
        //MainWindowController mainWindowController = mainLoader.getController();
        primaryStage.setTitle("Toy Language Interpreter");
        primaryStage.setScene(new Scene(mainWindow, 620, 600));
        primaryStage.show();

//        FXMLLoader secondaryLoader = new FXMLLoader();
//        secondaryLoader.setLocation(getClass().getResource("secondaryWindow.fxml"));
//        Parent secondaryWindow = secondaryLoader.load();
//        SecondaryWindowController secondaryWindowController = secondaryLoader.getController();
//        secondaryWindowController.setMainWindowController(mainWindowController);
//        Stage secondaryStage = new Stage();
//        secondaryStage.setTitle("Example");
//        secondaryStage.setScene(new Scene(secondaryWindow, 500, 550));
//        secondaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
