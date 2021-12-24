package game.of.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
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
    String[] easyWordsArray;
    String[] mediumWordsArray;
    String[] hardWordsArray;

    int EASY_WORD = 1;
    int MEDIUM_WORD = 2;
    int HARD_WORD = 3;

    int testNumber;

    @Override
    public void onLetterSelected(int letterChosen) {
        testNumber++;
        boardCanvas.testProgress(testNumber);
        Log.i("testpos", "selected is " + letterChosen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlphabetKeyAdapter alphabetKeyAdapter = new AlphabetKeyAdapter(this);
        alphabetKeyAdapter.selectLetter(MainActivity.this);

        GridView alphabetGridView = findViewById(R.id.alphabet_listView);
        alphabetGridView.setNumColumns(9);
        alphabetGridView.setAdapter(alphabetKeyAdapter);

        boardCanvas = findViewById(R.id.board_canvas);
        boardCanvas.numberOfLetters("Blah");
        boardCanvas.invalidate();

        String easyWordsString = getString(R.string.easy_words_string);
        String mediumWordsString = getString(R.string.medium_words_string);
        String hardWordsString = getString(R.string.hard_words_string);

        easyWordsArray = easyWordsString.split(" ");
        mediumWordsArray = mediumWordsString.split(" ");
        hardWordsArray = hardWordsString.split(" ");

    }

    public String selectWord(int difficulty) {
        random = new Random();
        int positionSelected;
        String wordChosen = "";

        if (difficulty==1) {
            int easySize = easyWordsArray.length;
            positionSelected = random.nextInt((easySize));
            wordChosen = easyWordsArray[positionSelected];
        }
        if (difficulty==2) {
            int mediumSize = mediumWordsArray.length;
            positionSelected = random.nextInt(mediumSize);
            wordChosen = mediumWordsArray[positionSelected];
        }
        if (difficulty==3) {
            int hardSize = hardWordsArray.length;
            positionSelected = random.nextInt(hardSize);
            wordChosen = hardWordsArray[positionSelected];
        }
        return wordChosen;
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