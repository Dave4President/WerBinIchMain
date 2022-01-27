package Project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GuiLoader extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/WerBinIch.fxml"));
        primaryStage.setTitle("Loco - Wer Bin Ich?");
        primaryStage.getIcons().add(new Image("file:loco.png"));
        primaryStage.setScene(new Scene(root, 800, 200));
        primaryStage.show();

    }



}


