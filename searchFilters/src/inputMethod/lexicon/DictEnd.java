package inputMethod.lexicon;

import java.util.ArrayList;

/**
 * Created by zzt on 1/29/16.
 * <p>
 * Usage:
 */
public class DictEnd extends DictChar {

    private ArrayList<Lexicon> words;

    public DictEnd(Character character) {
        super(character);
    }

    public DictEnd(char endSymbol, String s) {
        super(endSymbol);
    }
}
