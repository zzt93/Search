package synonym.data;

import java.util.ArrayList;

/**
 * Created by zzt on 3/8/16.
 * <p>
 * Usage:
 */
public class CompositeChar implements SentenceUnit{

    ArrayList<SentenceUnit> units;

    @Override
    public PartOfSpeech getType() {
        return null;
    }
}
