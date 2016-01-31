package inputMethod.lexicon;

import mis.Config;
import mis.MyIn;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by zzt on 1/29/16.
 * <p>
 * Usage:
 */
public class HashDictionary implements Dictionary{
    private static HashDictionary ourInstance = new HashDictionary();

    public static HashDictionary getInstance() {
        return ourInstance;
    }

    private HashMap<String, ArrayList<String>> pinyinToWords = new HashMap<>();
    private HashSet<String> words;

    private MyIn in;

    private HashDictionary() {
        words = new HashSet<>(Config.DICT_WORD_SIZE);
        try {
            in = Config.getDictionary();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        construct();
    }

    private void construct() {
        while (in.hasNextLine()) {
            String[] split = in.nextLine().split(Config.DICT_SPLIT);
            assert split.length == 2;
            ArrayList<String> words;
            String key = split[0].replace("'", "");
            if (pinyinToWords.containsKey(key)) {
                words = pinyinToWords.get(key);
            } else {
                words = new ArrayList<>();
            }
            words.add(split[1]);
            pinyinToWords.put(key, words);
            this.words.add(split[1]);
        }
    }

    @Override
    public ArrayList<String> find(String syllables) {
        return pinyinToWords.get(syllables);
    }

    @Override
    public boolean containsWord(String word) {
        return words.contains(word);
    }
}
