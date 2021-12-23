package game.of.hangman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlphabetKeyAdapter extends ArrayAdapter<AlphabetKeyAdapter> {
    Context mContext;
    LetterSelected mLetterSelected;

    public interface LetterSelected {
        void onLetterSelected(int letterChosen);
    }

    public void selectLetter(LetterSelected xLetterSelected) {
        mLetterSelected = xLetterSelected;
    }

    public AlphabetKeyAdapter(Context context) {
        super(context, 0);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alphabet_layout, parent, false);
        }

        convertView.setOnClickListener(v-> {
            mLetterSelected.onLetterSelected(position);
        });

        TextView letter = convertView.findViewById(R.id.letter_in_adapter);
        letter.setText(alphabetStringArray()[position]);
        return convertView;
    }

    @Override
    public int getCount() {
        return alphabetStringArray().length;
    }

    public String[] alphabetStringArray() {
        String alphabet = "A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z";
        return alphabet.split(", ");
    }
}
