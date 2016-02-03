package searchPrompt.history;

import java.util.ArrayList;

/**
 * Created by zzt on 2/3/16.
 * <p>
 * Usage:
 */
public interface HistoryBLService {

    ArrayList<String> related(String s);

    void count(String s);
}
