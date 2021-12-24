package game.of.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AlphabetKeyAdapter.LetterSelected {

    Random random;
    BoardCanvas boardCanvas;

    Button easyGame;
    Button mediumGame;
    Button hardGame;

    String[] easyWordsArray;
    String[] mediumWordsArray;
    String[] hardWordsArray;

    int EASY_WORD = 1;
    int MEDIUM_WORD = 2;
    int HARD_WORD = 3;

    @Override
    public void onLetterSelected(int letterChosen) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easyGame = findViewById(R.id.easy_game);
        mediumGame = findViewById(R.id.medium_game);
        hardGame = findViewById(R.id.hard_game);
        boardCanvas = findViewById(R.id.board_canvas);

        AlphabetKeyAdapter alphabetKeyAdapter = new AlphabetKeyAdapter(this);
        alphabetKeyAdapter.selectLetter(MainActivity.this);

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
            boardCanvas.numberOfLetters(selectWord(EASY_WORD));
            boardCanvas.invalidate();
//            switchViewsForActiveOrInactivePuzzle(true);
        });

        mediumGame.setOnClickListener(v-> {
            boardCanvas.numberOfLetters(selectWord(MEDIUM_WORD));
            boardCanvas.invalidate();
//            switchViewsForActiveOrInactivePuzzle(true);
        });

        hardGame.setOnClickListener(v-> {
            boardCanvas.numberOfLetters(selectWord(HARD_WORD));
            boardCanvas.invalidate();
//            switchViewsForActiveOrInactivePuzzle(true);
        });

    }

    public String selectWord(int difficulty) {
        random = new Random();
        int positionSelected;
        String wordChosen = "";

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
        } else {
            easyGame.setVisibility(View.VISIBLE);
            mediumGame.setVisibility(View.VISIBLE);
            hardGame.setVisibility(View.VISIBLE);

            boardCanvas.numberOfLetters("");
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