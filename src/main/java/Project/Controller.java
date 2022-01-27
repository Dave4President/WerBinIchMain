package Project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.File;

public class Controller{

    @FXML
    public Label output;

    @FXML
    public Button btnJa, btnNein, btnStartGame;

    public boolean isGameOn = true;
    public boolean answer;

    public void readFile(String filename){
        try {
            //constructor of file class having file as argument
            File file = new File(filename);
            if (!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not
            {
                System.out.println("not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if (file.exists())         //checks file exists or not
                desktop.open(file);    //opens the specified file
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void btnReadMeClicked() {

        readFile("readme.txt");
    }

    public void btnPolitikerClicked() {

        readFile("jsonData.json");
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

        } else if (!isGameOn) {
            btnStartGame.setDisable(false);
            btnStartGame.setText("Neues Spiel");
            btnJa.setDisable(true);
            btnNein.setDisable(true);
            output.setText("Deine Wahl ist auf " + lastPolitician + " gefallen.");

            btnStartGame.setOnAction(event -> startGame());
        }
    }


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

}


