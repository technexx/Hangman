package game.of.hangman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlphabetKeyAdapter extends ArrayAdapter<AlphabetKeyAdapter> {
    Context mContext;
    String[] mAlphabetList;

    LetterSelected mLetterSelected;
    int positionToGreyOut = -1;

    public interface LetterSelected {
        void onLetterSelected(int currentLetterSelected);
    }

    public void selectLetter(LetterSelected xLetterSelected) {
        mLetterSelected = xLetterSelected;
    }

    public AlphabetKeyAdapter(Context context, String[] alphabetList) {
        super(context, 0);
        this.mContext = context;
        this.mAlphabetList = alphabetList;
    }

    public void greyOutSelectedLetter(int position) {
        positionToGreyOut = position;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alphabet_layout, parent, false);
        }

        TextView letter = convertView.findViewById(R.id.letter_in_adapter);
        letter.setText(mAlphabetList[position]);

        convertView.setOnClickListener(v-> {
            mLetterSelected.onLetterSelected(position);
            if (positionToGreyOut==position) {
                letter.setAlpha(0.3f);
            }
        });

        if (positionToGreyOut==-1) {
            letter.setAlpha(1);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return mAlphabetList.length;
    }
}
