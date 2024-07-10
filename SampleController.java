import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SampleController implements Initializable {

    // Declare FXML elements of the UI
    @FXML
    private Text hangmanTextArea;

    @FXML
    private TextField guess;

    @FXML
    private Text textForWord;

    @FXML
    private Text endOfGameText;

    // Declare private instance variables 
    private String word;

    private StringBuilder secretWord = new StringBuilder();

    private int livesPos = 0;

    ArrayList<String> hangManLives = new ArrayList<>(Arrays.asList(
            """
            +---+
            |   |
                |
                |
                |
                |
          =========""",
            """
            +---+
            |   |
            O   |
                |
                |
                |
          =========""",
            """
            +---+
            |   |
            O   |
            |   |
                |
                |
          =========""",
            """
            +---+
            |   |
            O   |
           /|   |
                |
                |
          =========""",
            """
            +---+
            |   |
            O   |
           /|\\  |
                |
                |
          =========""",
            """
            +---+
            |   |
            O   |
           /|\\  |
           /    |
                |
          =========""",
            """
            +---+
            |   |
            O   |
           /|\\  |
           / \\  |
                |
          ========="""
    ));

    private Media media;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hangmanTextArea.setText(hangManLives.get(livesPos));
        // Set up audio player
        media = new Media(new File("src/music.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.pause();
    }

    @FXML
    void getTextInput(ActionEvent event) {
        try {
            if(word == null){
                String input = guess.getText();
                if (input.isEmpty()) {
                    throw new Exception("Guess field is empty");
                }
                word = input;
                setupWord();
            } else{
                playTurn();
            }
            guess.clear();
        } catch (Exception e) {
            // Display error message to the user
            textForWord.setText(e.getMessage());
        }
    }

    public void setupWord(){
        int wordLength = word.length();
        secretWord.append("*".repeat(wordLength));
        textForWord.setText(String.valueOf(secretWord));
    }
//Define the playTurn method
    public void playTurn(){
        //Get the guess from the UI
        String guess = this.guess.getText();
        ArrayList<Integer> positions = new ArrayList<>();
        char[] wordChars = word.toCharArray();
        char letterGuess = guess.charAt(0);

        if(word.equals(guess)){
            endOfGameText.setText("You won!!");
            return;
        }
//If the guess appears in the chosen word,update the secret word with the guessed letter
        if(word.contains(guess)){
            for (int i = 0; i < word.length(); i++) {
                if(wordChars[i] == letterGuess){
                    positions.add(i);
                }
            }
            positions.forEach(pos ->{
                secretWord.setCharAt(pos,letterGuess);
            });

            textForWord.setText(String.valueOf(secretWord));
        } else {
            hangmanTextArea.setText(hangManLives.get(++livesPos));
            if(livesPos == 6){
                endOfGameText.setText("You LOST!!");
            }
        }
    }

    @FXML
    void hint(ActionEvent event) {
        hangmanTextArea.setText(hangManLives.get(++livesPos));
        if (secretWord.toString().equals(word)) {
            return;
        }
        int wordLength = word.length();
        if (secretWord.toString().equals("*".repeat(wordLength))) { // if this is the first hint, reveal the first letter
            secretWord.setCharAt(0, word.charAt(0));
            textForWord.setText(String.valueOf(secretWord));
        } else { // otherwise, reveal the next letter
            int nextLetterPos = secretWord.indexOf("*");
            if (nextLetterPos >= 0) {
                secretWord.setCharAt(nextLetterPos, word.charAt(nextLetterPos));
                textForWord.setText(String.valueOf(secretWord));
            }
        }
    }

    @FXML
    void reset(ActionEvent event) {
        word = null;
        secretWord.setLength(0);
        livesPos = 0;
        hangmanTextArea.setText(hangManLives.get(0));
        endOfGameText.setText("");
        guess.clear();
        textForWord.setText("");
    }

    @FXML
    private void toggleSound(MouseEvent event) {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        } else {
            mediaPlayer.play();
        }
    }
}
