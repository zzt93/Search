package inputMethod.lexicon;

import java.util.ArrayList;

/**
 * Created by zzt on 1/29/16.
 * <p>
 * Usage:
 * Mapping from pinyin to chinese word
 */
public interface Dictionary {

    ArrayList<String> find(String syllables);

    boolean containsWord(String word);
}
