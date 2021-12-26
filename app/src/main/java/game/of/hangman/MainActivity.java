package game.of.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AlphabetKeyAdapter.LetterSelected {

    AlphabetConversions alphabetConversions;
    AlphabetKeyAdapter alphabetKeyAdapter;
    Random random;
    BoardCanvas boardCanvas;

    Button easyGame;
    Button mediumGame;
    Button hardGame;
    boolean hasGameStarted;

    String[] easyWordsArray;
    String[] mediumWordsArray;
    String[] hardWordsArray;

    int EASY_WORD = 1;
    int MEDIUM_WORD = 2;
    int HARD_WORD = 3;

    String wordChosen;
    String currentLetterSelected;
    int LETTER_DOES_NOT_EXIST = -1;

    @Override
    public void onLetterSelected(int letterClickedInAdapter) {
        if (hasGameStarted) {
            Log.i("testWord", "word is " + wordChosen);
            alphabetKeyAdapter.greyOutSelectedLetter(letterClickedInAdapter);
            //For list of letters already chosen.
            currentLetterSelected = alphabetConversions.convertPositionToLetter(letterClickedInAdapter);
            boardCanvas.letterSelectedFromKeyboard(letterClickedInAdapter);

            int positionOFLetterSelectedInPuzzle = checkAndReturnLettersInPuzzle(letterClickedInAdapter);
            fillInLetterOrGallows(positionOFLetterSelectedInPuzzle);
        }
    }

    public void fillInLetterOrGallows(int letterPosition) {
        if (letterPosition==LETTER_DOES_NOT_EXIST) {
            boardCanvas.addToGallows();
        } else {
            boardCanvas.replaceBlankSpaceWithLetter(letterPosition);
        }
    }

    public int checkAndReturnLettersInPuzzle(int keyboardLetterPosition) {
        String letterSelected = alphabetConversions.convertPositionToLetter(keyboardLetterPosition);
        ArrayList<String> chosenWordArray = splitPuzzleWord(wordChosen);
        int positionOfChosenLetterInPuzzle = -1;

        for (int i=0; i<chosenWordArray.size(); i++) {
            if (chosenWordArray.get(i).equalsIgnoreCase(letterSelected)) {
                positionOfChosenLetterInPuzzle = i;
            }
        }
        return positionOfChosenLetterInPuzzle;
    }

    public ArrayList<String> splitPuzzleWord(String word) {
        ArrayList<String> wordArray = new ArrayList<>();
        for (int i=0; i<word.length(); i++) {
            wordArray.add(word.substring(i, i+1));
        }
        return wordArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alphabetConversions = new AlphabetConversions();

        alphabetKeyAdapter = new AlphabetKeyAdapter(getApplicationContext(), alphabetConversions.alphabetStringArray());
        alphabetKeyAdapter.selectLetter(MainActivity.this);

        easyGame = findViewById(R.id.easy_game);
        mediumGame = findViewById(R.id.medium_game);
        hardGame = findViewById(R.id.hard_game);
        boardCanvas = findViewById(R.id.board_canvas);

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
            switchViewsForActiveOrInactivePuzzle(true);
            Log.i("testWord", "array is " + splitPuzzleWord(wordChosen));
        });

        mediumGame.setOnClickListener(v-> {
            boardCanvas.numberOfLettersInPuzzle(selectWord(MEDIUM_WORD));
            boardCanvas.invalidate();
            switchViewsForActiveOrInactivePuzzle(true);
        });

        hardGame.setOnClickListener(v-> {
            boardCanvas.numberOfLettersInPuzzle(selectWord(HARD_WORD));
            boardCanvas.invalidate();
            switchViewsForActiveOrInactivePuzzle(true);
        });

    }

    public String selectWord(int difficulty) {
        random = new Random();
        int positionSelected;
        String word = "";

        if (difficulty==EASY_WORD) {
            int easySize = easyWordsArray.length;
            positionSelected = random.nextInt((easySize));
            wordChosen = easyWordsArray[positionSelected];
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
        return wordChosen;
    }

    public void switchViewsForActiveOrInactivePuzzle(boolean activeGame) {
        if (activeGame) {
            easyGame.setVisibility(View.GONE);
            mediumGame.setVisibility(View.GONE);
            hardGame.setVisibility(View.GONE);
            hasGameStarted = true;
        } else {
            easyGame.setVisibility(View.VISIBLE);
            mediumGame.setVisibility(View.VISIBLE);
            hardGame.setVisibility(View.VISIBLE);
            hasGameStarted = false;

            boardCanvas.numberOfLettersInPuzzle("");
            boardCanvas.drawPuzzleLetterBoard();
            boardCanvas.invalidate();
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