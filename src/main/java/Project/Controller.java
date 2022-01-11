package Project;


import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public  class Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        //label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
    }

    public Controller(){

    }



    public void buttonjaclicked(javafx.event.ActionEvent actionEvent) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Ja GEDRÜCKT");
        Button btn = new Button();
        btn.setText("Ja GEDRÜCKT");
        btn.setOnAction( (event) -> Platform.exit() );
        Pane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 200, 150));
        primaryStage.show();

    }

    public void buttonneinclicked(javafx.event.ActionEvent actionEvent) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Nein GEDRÜCKT");
        Button btn = new Button();
        btn.setText("Nein GEDRÜCKT");
        btn.setOnAction( (event) -> Platform.exit() );
        Pane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 200, 150));
        primaryStage.show();

    }
}
