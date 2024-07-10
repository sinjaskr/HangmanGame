/*
  PROJECT HANGMAN 
  GROUP- 9
  GROUP MEMBERS: GURWINDER KAUR(991647985)
                 JASKARAN SINGH(991648751)
                 PAWANPREET KAUR(991668359)
                 LOVELEEN KAUR(991670050)
 */

 /*
  Hangman project is a word guessing game where the player tries 
  to guess a secret word by suggesting letters within a certain 
  number of attempts, with each incorrect guess resulting in the
  drawing of a "hangman" figure.  
  */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        
        //loading the FXML file "sample.fxml" and setting the root to it
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml")); 
        primaryStage.setTitle("Hangman Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args); // launching the javaFX application
    }
}
