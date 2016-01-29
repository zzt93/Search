package inputMethod.lexicon;

import mis.Config;
import mis.MyIn;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

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
    private MyIn in;

    private HashDictionary() {
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
            if (pinyinToWords.containsKey(split[0])) {
                words = pinyinToWords.get(split[0]);
            } else {
                words = new ArrayList<>();
            }
            words.add(split[1]);
            pinyinToWords.put(split[0], words);
        }
    }

    @Override
    public ArrayList<String> find(String syllables) {
        return pinyinToWords.get(syllables);
    }
}
