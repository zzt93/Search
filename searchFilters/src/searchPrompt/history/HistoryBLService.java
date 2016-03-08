package searchPrompt.history;

import java.util.ArrayList;

/**
 * Created by zzt on 2/3/16.
 * <p>
 * Usage:
 */
public interface HistoryBLService {

    /**
     * Related String:
     *  - synonyms
     *  - contain this string
     *  - (optional) contain synonyms
     *
     * @param s User input string
     * @return list of related string
     */
    ArrayList<String> related(String s);

    /**
     * Add this to history
     * @param s User input string
     */
    void count(String s);
}
