package inputMethod.lexicon;

import java.util.ArrayList;

/**
 * Created by zzt on 1/29/16.
 * <p>
 * Usage:
 */
public interface Dictionary {

    ArrayList<String> find(String syllables);

    boolean containsKey(String key);
}
