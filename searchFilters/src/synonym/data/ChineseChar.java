package synonym.data;

import java.util.ArrayList;

/**
 * Created by zzt on 3/8/16.
 * <p>
 * Usage:
 *
 * Represent the minimum unit of chinese sentence:
 * a chinese char
 * a chinese word
 */
public class ChineseChar implements SentenceUnit {

    private String cChar;
    private ArrayList<PartOfSpeech> partOfSpeeches = new ArrayList<>();


    @Override
    public PartOfSpeech getType() {
        return null;
    }
}
