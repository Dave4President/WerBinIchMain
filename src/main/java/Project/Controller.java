package Project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public Label output;

    @FXML
    public Button btnJa, btnNein, btnStartGame;

    public boolean isGameOn = true;
    public boolean answer;
    int probability;

    public void btnStartGameClicked() {

        startGame();

    }

    public void btnCloseClicked() {

    }

    public void btnReadMeClicked() {
        try {
            //constructor of file class having file as argument
            File file = new File("readme.txt");
            if (!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not
            {

                System.out.println("not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if (file.exists())         //checks file exists or not
                desktop.open(file);              //opens the specified file
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startGame() {

        isGameOn = true;
        btnStartGame.setDisable(true);
        btnJa.setDisable(false);
        btnNein.setDisable(false);

        Politician.createPoliticians();
        // Politiker werden aus dem File gelesen und als Objekte in der Arraylist gespeichert.
        Question.createQuestions();
        // Fragen werden aus dem File gelesen und als Objekte in der Arraylist gespeichert.

        //output.setText("vorFehler");

        play();

    }

    public void play() {
        if (isGameOn) {

            output.setText(Question.getNewQuestion(Question.stringToIndex(Politician.mergeProbability())));

            btnJa.setOnAction(event -> {
                answer = true;
                Question.removeCurrentQuestion();

                Politician.setPoliticianList(Question.giveCategory(), answer, Question.giveAnswer());

                isGameOn = endOfGame();

                play();

            });


            btnNein.setOnAction(event -> {
                answer = false;

                Politician.setPoliticianList(Question.giveCategory(), answer, Question.giveAnswer());

                isGameOn = endOfGame();

                play();

            });

        } else if (isGameOn == false) {
            btnStartGame.setDisable(false);
            btnStartGame.setText("Neues Spiel");
            btnJa.setDisable(true);
            btnNein.setDisable(true);
            output.setText("Deine Wahl ist auf " + lastPolitician + " gefallen.");
            //waitForInput();


            btnStartGame.setOnAction(event -> {
                startGame();
            });
        }
    }

    public void waitForInput() {


    }

    ;

    public static String lastPolitician;

    public boolean endOfGame() {

        // Wird am Ende vom Mainloop aufgerufen.
        // Wenn die Liste der Politiker nur 1 oder 0 Einträge hat,
        // ist das Spiel zu Ende und es wird der Name der letzten Person ausgegeben.

        int x = Politician.getPoliticiansLeft();

        if (x == 1) {
            lastPolitician = Politician.getLastPolitician();
            return false;
        } else if (x == 0) {
            lastPolitician = "niemanden";
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //startGame();
    }
}


