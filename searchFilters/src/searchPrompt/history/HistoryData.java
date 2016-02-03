package searchPrompt.history;

/**
 * Created by zzt on 2/3/16.
 * <p>
 * Usage:
 */
public class HistoryData {
    private static HistoryData ourInstance = new HistoryData();

    public static HistoryData getInstance() {
        return ourInstance;
    }

    private HistoryData() {
    }
}
