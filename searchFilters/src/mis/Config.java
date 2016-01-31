package mis;

import java.io.FileNotFoundException;

/**
 * Created by zzt on 1/29/16.
 * <p>
 * Usage:
 */
public class Config {
    public static final String ALL_PINYIN_PATH = "allPinyin.data";
    public static final String DICT_PATH = "dict.data";
    public static final int DICT_WORD_SIZE = (int) (137760 * 1.3);
    public static final String DICT_SPLIT = "\t";
    private static final String LM_PATH = "lm.data0";

    public static MyIn getDictionary() throws FileNotFoundException {
        return new MyIn(DICT_PATH);
    }

    public static MyIn getPinyinStream() throws FileNotFoundException {
        return new MyIn(ALL_PINYIN_PATH);
    }

    public static MyIn getLanguageModelStatistic() throws FileNotFoundException {
        return new MyIn(LM_PATH);
    }
}
