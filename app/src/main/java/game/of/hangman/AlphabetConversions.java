package game.of.hangman;

public class AlphabetConversions {

    public String[] alphabetStringArray() {
        String alphabet = "A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z";
        return alphabet.split(", ");
    }

    public String convertPositionToLetter(int position) {
        String letter = "";
        if (position==0) letter = "A";
        if (position==1) letter = "B";
        if (position==2) letter = "C";
        if (position==3) letter = "D";
        if (position==4) letter = "E";
        if (position==5) letter = "F";
        if (position==6) letter = "G";
        if (position==7) letter = "H";
        if (position==8) letter = "I";
        if (position==9) letter = "J";
        if (position==10) letter = "K";
        if (position==11) letter = "L";
        if (position==12) letter = "M";
        if (position==13) letter = "N";
        if (position==14) letter = "O";
        if (position==15) letter = "P";
        if (position==16) letter = "Q";
        if (position==17) letter = "R";
        if (position==18) letter = "S";
        if (position==19) letter = "T";
        if (position==20) letter = "U";
        if (position==21) letter = "V";
        if (position==22) letter = "W";
        if (position==23) letter = "X";
        if (position==24) letter = "Y";
        if (position==25) letter = "Z";
        return letter;
    }
}
