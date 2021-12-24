package game.of.hangman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class BoardCanvas extends View {

    Context mContext;
    Canvas mCanvas;
    Paint mPaint;

    int numberOfSpaces;

    public BoardCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUpPaint();
    }

    private void setUpPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
    }

    public void numberOfLetters(String puzzle) {
        numberOfSpaces = puzzle.length();
    }

    public int dpConv(float pixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pixels, getResources().getDisplayMetrics());
    }

    @Override
    public void onDraw(Canvas canvas) {
        mCanvas = canvas;

        drawLetterBoard();
        drawHangMan();
    }

    public void drawLetterBoard() {
        int xPos = dpConv(33);

        for (int i=0; i<numberOfSpaces; i++) {
            mCanvas.drawLine(dpConv(xPos), 0, dpConv(xPos+30), 0, mPaint);
            dpConv(xPos+=42);
        }
    }

    public void drawHangMan() {
        int topXStart = 125;
        int topXEnd = 230;
        int topY = 60;

        mCanvas.drawLine(dpConv(topXStart), dpConv(topY), dpConv(topXEnd), dpConv(topY), mPaint);

        mCanvas.drawLine(dpConv(topXStart), dpConv(topY), dpConv(topXStart), dpConv(topY+30), mPaint);

        mCanvas.drawLine(dpConv(topXEnd-30), dpConv(topY), dpConv(topXEnd), dpConv(topY + 30), mPaint);

        mCanvas.drawLine(dpConv(topXEnd), dpConv(topY), dpConv(topXEnd), dpConv(topY + 150), mPaint);

        mCanvas.drawLine(dpConv(topXEnd-50), dpConv(topY+150), dpConv(topXEnd+50), dpConv(topY + 150), mPaint);

        mCanvas.drawLine(dpConv(topXEnd), dpConv(topY+120), dpConv(topXEnd-30), dpConv(topY + 150), mPaint);

        mCanvas.drawLine(dpConv(topXEnd), dpConv(topY+120), dpConv(topXEnd+30), dpConv(topY + 150), mPaint);

    }
}
