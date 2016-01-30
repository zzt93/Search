package inputMethod.lm;

import inputMethod.lexicon.Dictionary;
import inputMethod.lexicon.HashDictionary;
import mis.Config;
import mis.MyIn;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by zzt on 1/30/16.
 * <p>
 * Usage:
 */
public class HashLM implements LanguageModel {
    private static HashLM ourInstance = new HashLM();
    private int stage = 0;
    public static final String UNKNOWN = "<unknown>";
    private Dictionary lexicon;

    public static HashLM getInstance() {
        return ourInstance;
    }

    private HashMap<String, HashMap<String, Double>> bigram = new HashMap<>();
    private HashMap<String, Double> unigram = new HashMap<>();
    private MyIn in;

    private HashLM() {
        try {
            in = Config.getLanguageModelStatistic();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        lexicon = HashDictionary.getInstance();
        construct();
    }

    private void construct() {
        while (in.hasNextLine()) {
            if (parseLine(in.nextLine())) {
                break;
            }
        }
    }

    private boolean parseLine(String line) {
        if (stage == 0) {
            if (line.substring(0, 8).equals("\\1-gram\\")) {
                stage++;
            }
        } else if (stage == 1) {
            if (line.substring(0, 8).equals("\\2-gram\\")) {
                stage++;
            } else {
                //1-gram
                int splitIndex = line.indexOf(' ');
                String phrase = line.substring(0, splitIndex);
                double probability = Double.parseDouble(line.substring(splitIndex + 3, line.indexOf(' ', splitIndex + 3) - (splitIndex + 3)));
                if (!this.lexicon.containsKey(phrase)) {
                    return false;
                }

                unigram.put(phrase, probability);
            }
        } else if (stage == 2) {
            if (line.substring(0, 8).equals("\\3-gram\\")) {
                return true;
            } else {
                //2-gram
                int splitIndex1 = line.indexOf(' ');
                String phrase1 = line.substring(0, splitIndex1);
                int splitIndex2 = line.indexOf(' ', splitIndex1 + 1);
                String phrase2 = line.substring(splitIndex1 + 1, splitIndex2 - splitIndex1 - 1);
                double probability = Double.parseDouble(line.substring(splitIndex2 + 3, line.indexOf(' ', splitIndex2 + 3) - (splitIndex2 + 3)));

                if (!Objects.equals(phrase1, UNKNOWN) && !this.lexicon.containsKey(phrase1)) {
                    return false;
                }
                if (!Objects.equals(phrase2, UNKNOWN) && !this.lexicon.containsKey(phrase2)) {
                    return false;
                }

                HashMap<String, Double> dict;
                if (!bigram.containsKey(phrase1)) {
                    dict = new HashMap<>();
                    bigram.put(phrase1, dict);
                } else {
                    dict = bigram.get(phrase1);
                }
                dict.put(phrase2, probability);
            }
        }
        return false;
    }

    @Override
    public double getUnigram(String syllable) {
        if (unigram.containsKey(syllable)) {
            return unigram.get(syllable);
        }
        // TODO: 1/30/16 may be change here for unknown phrase
        // return very small value for unknown syllable
        return Double.MIN_NORMAL;
    }

    @Override
    public double getBigram(String phrase1, String phrase2) {
        double delta = 0;
        HashMap<String, Double> dict;
        if (bigram.containsKey(phrase1)) {
            dict = bigram.get(phrase1);
            if (dict.containsKey(phrase2)) {
                return dict.get(phrase2);
            } else if (dict.containsKey(UNKNOWN)) {
                delta = dict.get(UNKNOWN);
            }
        } else if (bigram.containsKey(UNKNOWN)) {
            dict = bigram.get(UNKNOWN);
            if (dict.containsKey(phrase2)) {
                delta = dict.get(phrase2);
            }
        }
        // TODO: 1/30/16 test here
        return getUnigram(phrase1) * getUnigram(phrase2) * (Math.E + delta);
    }
}
