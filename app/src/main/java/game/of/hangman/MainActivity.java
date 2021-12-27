package game.of.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AlphabetKeyAdapter.LetterSelected, BoardCanvas.GameOver {

    //Fira code retina
    AlphabetConversions alphabetConversions;
    AlphabetKeyAdapter alphabetKeyAdapter;
    ArrayList<Integer> arrayListOfPreviouslySelectedLetters;
    Random random;
    BoardCanvas boardCanvas;

    Button easyGame;
    Button mediumGame;
    Button hardGame;
    Button resetGame;
    TextView gameOverTextView;
    boolean gameIsActive;

    String[] easyWordsArray;
    String[] mediumWordsArray;
    String[] hardWordsArray;

    int EASY_WORD = 1;
    int MEDIUM_WORD = 2;
    int HARD_WORD = 3;
    String wordChosen;

    @Override
    public void onGameEnded(boolean isWon) {
        setEndOfGameViewsAndVariables(isWon);
    }

    @Override
    public void onLetterSelected(int alphabetLetter) {
        if (gameIsActive) {
            if (!arrayListOfPreviouslySelectedLetters.contains(alphabetLetter)) {
                boardCanvas.addLetterSelectedToTotalLetterArrayList(alphabetLetter);
                boardCanvas.addLetterSelectedToPuzzleArrayList(returnArrayOfChosenLetterInPuzzleIfItExists(alphabetLetter));
            }

            alphabetKeyAdapter.greyOutSelectedLetter(alphabetLetter);
            arrayListOfPreviouslySelectedLetters.set(alphabetLetter, alphabetLetter);
        }
    }

    public ArrayList<Integer> returnArrayOfChosenLetterInPuzzleIfItExists(int keyboardLetterPosition) {
        String letterSelected = alphabetConversions.convertPositionToLetter(keyboardLetterPosition);
        ArrayList<String> chosenWordArray = splitPuzzleWord(wordChosen);

        ArrayList<Integer> positionListOfChosenLetterInPuzzle = new ArrayList<>();

        for (int i=0; i<chosenWordArray.size(); i++) {
            positionListOfChosenLetterInPuzzle.add(-1);
            if (chosenWordArray.get(i).equalsIgnoreCase(letterSelected)) {
                positionListOfChosenLetterInPuzzle.set(i, i);
            }
        }

        return positionListOfChosenLetterInPuzzle;
    }

    public ArrayList<String> splitPuzzleWord(String word) {
        ArrayList<String> wordArray = new ArrayList<>();
        for (int i=0; i<word.length(); i++) {
            wordArray.add(word.substring(i, i+1));
        }
        return wordArray;
    }

    public void setEndOfGameViewsAndVariables(boolean gameIsWon) {
        gameIsActive = false;
        gameOverTextView.setVisibility(View.VISIBLE);
        resetGame.setVisibility(View.VISIBLE);
        if (gameIsWon) {
            gameOverTextView.setText("YOU WIN!");
        } else {
            gameOverTextView.setText("YOU LOSE!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alphabetConversions = new AlphabetConversions();

        alphabetKeyAdapter = new AlphabetKeyAdapter(getApplicationContext(), alphabetConversions.alphabetStringArray());
        alphabetKeyAdapter.selectLetter(MainActivity.this);
        boardCanvas = findViewById(R.id.board_canvas);
        boardCanvas.gameHasEnded(MainActivity.this);

        easyGame = findViewById(R.id.easy_game);
        mediumGame = findViewById(R.id.medium_game);
        hardGame = findViewById(R.id.hard_game);

        gameOverTextView = findViewById(R.id.game_over_textView);
        gameOverTextView.setVisibility(View.GONE);
        resetGame = findViewById(R.id.reset_game);
        resetGame.setVisibility(View.GONE);

        GridView alphabetGridView = findViewById(R.id.alphabet_listView);
        alphabetGridView.setNumColumns(9);
        alphabetGridView.setAdapter(alphabetKeyAdapter);

        String easyWordsString = getString(R.string.easy_words_string);
        String mediumWordsString = getString(R.string.medium_words_string);
        String hardWordsString = getString(R.string.hard_words_string);

        easyWordsArray = easyWordsString.split(" ");
        mediumWordsArray = mediumWordsString.split(" ");
        hardWordsArray = hardWordsString.split(" ");

        easyGame.setOnClickListener(v-> {
            boardCanvas.numberOfLettersInPuzzle(selectWord(EASY_WORD));
            boardCanvas.invalidate();
            setViewsAndVariablesForActiveAndInactiveGames(true);
        });

        mediumGame.setOnClickListener(v-> {
            boardCanvas.numberOfLettersInPuzzle(selectWord(MEDIUM_WORD));
            boardCanvas.invalidate();
            setViewsAndVariablesForActiveAndInactiveGames(true);
        });

        hardGame.setOnClickListener(v-> {
            boardCanvas.numberOfLettersInPuzzle(selectWord(HARD_WORD));
            boardCanvas.invalidate();
            setViewsAndVariablesForActiveAndInactiveGames(true);
        });

        resetGame.setOnClickListener(v-> {
            setViewsAndVariablesForActiveAndInactiveGames(gameIsActive);
        });

    }

    public String selectWord(int difficulty) {
        random = new Random();
        int positionSelected;

        if (difficulty==EASY_WORD) {
            int easySize = easyWordsArray.length;
            positionSelected = random.nextInt((easySize));
            wordChosen = easyWordsArray[positionSelected];

//            //Todo: Testing.
//            wordChosen = "believe";
        }
        if (difficulty==MEDIUM_WORD) {
            int mediumSize = mediumWordsArray.length;
            positionSelected = random.nextInt(mediumSize);
            wordChosen = mediumWordsArray[positionSelected];
        }
        if (difficulty==HARD_WORD) {
            int hardSize = hardWordsArray.length;
            positionSelected = random.nextInt(hardSize);
            wordChosen = hardWordsArray[positionSelected];
        }

        boardCanvas.populatePuzzleArrayListWithBlanks(wordChosen.length());
        gameIsActive = true;
        return wordChosen;
    }

    public void setViewsAndVariablesForActiveAndInactiveGames(boolean activeGame) {
        if (activeGame) {
            easyGame.setVisibility(View.GONE);
            mediumGame.setVisibility(View.GONE);
            hardGame.setVisibility(View.GONE);

            arrayListOfPreviouslySelectedLetters = new ArrayList<>();
            for (int i=0; i<26; i++) {
                arrayListOfPreviouslySelectedLetters.add(-1);
            }
        } else {
            easyGame.setVisibility(View.VISIBLE);
            mediumGame.setVisibility(View.VISIBLE);
            hardGame.setVisibility(View.VISIBLE);
            gameOverTextView.setVisibility(View.GONE);
            resetGame.setVisibility(View.GONE);

            boardCanvas.resetGallows();
            boardCanvas.numberOfLettersInPuzzle("");
            boardCanvas.drawPuzzleLetterBoard();
            boardCanvas.invalidate();

            alphabetKeyAdapter.greyOutSelectedLetter(-1);
        }
    }


    public void logs() {
        Log.i("testArray", getString(R.string.hard_words_string));
        Log.i("testArray", getString(R.string.medium_words_string));
        Log.i("testArray", getString(R.string.easy_words_string));

        String easy = selectWord(1);
        String medium = selectWord(2);
        String hard = selectWord(3);
        Log.i("testWord", "easy is " + easy);
        Log.i("testWord", "medium is " + medium);
        Log.i("testWord", "hard is " + hard);
    }
}