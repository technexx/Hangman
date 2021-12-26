package game.of.hangman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BoardCanvas extends View {

    AlphabetConversions alphabetConversions = new AlphabetConversions();
    Canvas mCanvas;
    Paint mPaint;
    Paint mPaintText;

    int numberOfSpaces;
    int mGallowsProgress;

    String letterReceived;
    ArrayList<String> totalLettersSelectedArrayList = new ArrayList<>();
    ArrayList<String> lettersInPuzzleArrayList = new ArrayList<>();

    public void populatePuzzleArrayListWithBlanks(int numberOfBlanks) {
        for (int i=0; i<numberOfBlanks; i++) {
            lettersInPuzzleArrayList.add(" ");
        }
    }

    public void addLetterSelectedToTotalLetterArrayList(int alphabetPosition) {
        letterReceived = alphabetConversions.convertPositionToLetter(alphabetPosition);
        if (!totalLettersSelectedArrayList.contains(letterReceived)) {
            totalLettersSelectedArrayList.add(letterReceived);
        }
        Log.i("testWord", "total list is " + totalLettersSelectedArrayList);
    }

    public void addLetterSelectedToPuzzleArrayList(int letterPositionInPuzzle) {
        if (!lettersInPuzzleArrayList.contains(letterReceived)) {
            lettersInPuzzleArrayList.set(letterPositionInPuzzle, letterReceived);
        }
        invalidate();
    }

    public BoardCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUpPaint();
    }

    private void setUpPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);

        mPaintText = new Paint();
        mPaintText.setTextSize(70f);
        mPaintText.setColor(Color.BLACK);
    }

    public void drawPuzzleLetterBoard() {
        int xPos = (setSpacingOfPuzzleLetterBoard(numberOfSpaces));
        int yPos = dpConv(60);

        for (int i=0; i<numberOfSpaces; i++) {
            mCanvas.drawLine(xPos, yPos, xPos+dpConv(20), yPos, mPaint);
            xPos += dpConv(30);
        }

        //Todo: Need to adjust for multiple letters in word.
        Log.i("testWord", "selected list is " + lettersInPuzzleArrayList);
        for (int k=0; k<lettersInPuzzleArrayList.size(); k++) {
            String puzzleLetter = lettersInPuzzleArrayList.get(k);
            int indexPlaces = lettersInPuzzleArrayList.size() - 1;
            if (!puzzleLetter.equals(" ")) {
                int letterPosition = setSpacingOfLetterPopulatingBoard(indexPlaces - k);

//                int letterPosition = setSpacingOfLetterPopulatingBoard(indexPlaces - k);
                mCanvas.drawText(puzzleLetter, letterPosition, dpConv(25) , mPaintText);
            }
        }
    }

    public int setSpacingOfLetterPopulatingBoard(int positionInPuzzle) {
        if (positionInPuzzle!=11) {
            return dpConv( (220) - (30*positionInPuzzle));
        } else {
            return dpConv(4);
        }
    }

    public int setSpacingOfPuzzleLetterBoard(int numberOfLetters) {
        int spacing = dpConv(192);
        for (int i=0; i<numberOfLetters; i++) {
            spacing -= dpConv(16);
        }
        if (numberOfLetters==12) {
            spacing = dpConv(4);
        }
        return spacing;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void numberOfLettersInPuzzle(String puzzle) {
        numberOfSpaces = puzzle.length();
    }

    public void addToGallows() {
        mGallowsProgress++;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        mCanvas = canvas;

        drawPuzzleLetterBoard();
        drawGallows();
        drawHangMan(mGallowsProgress);
    }

    public void drawGallows() {
        int xPosStart = 125;
        int xPosEnd = 230;
        int topY = 110;
        int bottomY = 290;

        mCanvas.drawLine(dpConv(xPosStart), dpConv(topY), dpConv(xPosEnd), dpConv(topY), mPaint);
        mCanvas.drawLine(dpConv(xPosStart), dpConv(topY), dpConv(xPosStart), dpConv(topY+30), mPaint);
        mCanvas.drawLine(dpConv(xPosEnd-30), dpConv(topY), dpConv(xPosEnd), dpConv(topY + 30), mPaint);
        mCanvas.drawLine(dpConv(xPosEnd), dpConv(topY), dpConv(xPosEnd), dpConv(bottomY), mPaint);
        mCanvas.drawLine(dpConv(xPosEnd-50), dpConv(bottomY), dpConv(xPosEnd+50), dpConv(bottomY), mPaint);
        mCanvas.drawLine(dpConv(xPosEnd), dpConv(bottomY-30), dpConv(xPosEnd-30), dpConv(bottomY), mPaint);
        mCanvas.drawLine(dpConv(xPosEnd), dpConv(bottomY-30), dpConv(xPosEnd+30), dpConv(bottomY), mPaint);
    }

    public void drawHangMan(int progress) {
        int xPosStart = 125;
        int topY = 165;
        int bottomY = 265;

        if (progress>0) {
            mCanvas.drawCircle(dpConv(xPosStart), dpConv(topY), dpConv(25), mPaint);
            mCanvas.drawCircle(dpConv(xPosStart-10), dpConv(topY-10), dpConv(3), mPaint);
            mCanvas.drawCircle(dpConv(xPosStart+10), dpConv(topY-10), dpConv(3), mPaint);
            mPaint.setStyle(Paint.Style.FILL);
            mCanvas.drawCircle(dpConv(xPosStart), dpConv(topY), dpConv(3), mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
            mCanvas.drawLine(dpConv(xPosStart), dpConv(topY+9), dpConv(xPosStart+5), dpConv(topY+18), mPaint);
            mCanvas.drawLine(dpConv(xPosStart), dpConv(topY+9), dpConv(xPosStart-5), dpConv(topY+18), mPaint);
        }
        if (progress>1) {
            mCanvas.drawLine(dpConv(xPosStart), dpConv(topY+25), dpConv(xPosStart), dpConv(bottomY), mPaint);
        }
        if (progress>2) {
            mCanvas.drawLine(dpConv(xPosStart), dpConv(topY+60), dpConv(xPosStart-30), dpConv(topY+30), mPaint);
        }
        if (progress>3) {
            mCanvas.drawLine(dpConv(xPosStart), dpConv(topY+60), dpConv(xPosStart+30), dpConv(topY+30), mPaint);
        }
        if (progress>4) {
            mCanvas.drawLine(dpConv(xPosStart), dpConv(bottomY), dpConv(xPosStart-30), dpConv(bottomY+30), mPaint);
        }
        if (progress>5) {
            mCanvas.drawLine(dpConv(xPosStart), dpConv(bottomY), dpConv(xPosStart+30), dpConv(bottomY+30), mPaint);

        }
    }

    public int dpConv(float pixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pixels, getResources().getDisplayMetrics());
    }
}
