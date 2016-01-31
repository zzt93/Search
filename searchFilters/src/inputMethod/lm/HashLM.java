package inputMethod.lm;

import inputMethod.lexicon.Dictionary;
import inputMethod.lexicon.HashDictionary;
import mis.Config;
import mis.MyIn;
import mis.Util;

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

    private HashMap<String, HashMap<String, Double>> bigram;
    private HashMap<String, Double> unigram;
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
        System.out.println("parsing languange model");
        while (in.hasNextLine()) {
            if (parseLine(in.nextLine())) {
                break;
            }
        }
        System.out.println("parsing lm done");
    }

    private boolean parseLine(String line) {
        if (stage == 0) {
            if (line.substring(0, 8).equals("\\1-gram\\")) {
                unigram = new HashMap<>(Util.getHashCapacity(line.substring(8)));
                stage++;
            }
        } else if (stage == 1) {
            if (line.substring(0, 8).equals("\\2-gram\\")) {
                bigram = new HashMap<>(Util.getHashCapacity(line.substring(8)));
                stage++;
            } else {
                //1-gram
                int splitIndex = line.indexOf(' ');
                String phrase = line.substring(0, splitIndex);
                double probability = Double.parseDouble(line.substring(splitIndex + 3, line.indexOf(' ', splitIndex + 3)));
                if (!this.lexicon.containsWord(phrase)) {
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
                String phrase2 = line.substring(splitIndex1 + 1, splitIndex2);
                double probability = Double.parseDouble(line.substring(splitIndex2 + 3, line.indexOf(' ', splitIndex2 + 3)));

                if (!Objects.equals(phrase1, UNKNOWN) && !this.lexicon.containsWord(phrase1)) {
                    return false;
                }
                if (!Objects.equals(phrase2, UNKNOWN) && !this.lexicon.containsWord(phrase2)) {
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
    public double getUnigram(String word) {
        if (unigram.containsKey(word)) {
            return unigram.get(word);
        }
        // TODO: 1/30/16 may be change here for unknown phrase
        // return very small value for unknown word
        return Double.MIN_NORMAL;
    }

    @Override
    public double getBigram(String phrase1, String phrase2) {
        double delta = 0;
        HashMap<String, Double> dict;
        if (bigram.containsKey(phrase1)) {
            dict = bigram.get(phrase1);
            if (dict.containsKey(phrase2)) {
                return dict.get(phrase2) * getUnigram(phrase1);
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
        return getUnigram(phrase1) * getUnigram(phrase2) * (delta);
    }

    public static void main(String[] args) {
        HashLM instance = HashLM.getInstance();
        MyIn in = new MyIn(System.in);
        while (in.hasNextLine()) {
            System.out.println(instance.getUnigram(in.nextLine()));
//            System.out.println(instance.getBigram(in.nextLine(), in.nextLine()));
        }
    }
}
