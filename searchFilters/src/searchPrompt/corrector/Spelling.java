package searchPrompt.corrector;

import mis.Config;
import mis.MyIn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by zzt on 2/3/16.
 * <p>
 * Usage:
 */
public class Spelling {
    private static Spelling ourInstance;

    static {
        try {
            ourInstance = new Spelling(Config.getSpellingCount());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Spelling getInstance() {
        return ourInstance;
    }

    private final HashMap<String, Double> nWords = new HashMap<>();

    public Spelling(MyIn in) throws IOException {
        while (in.hasNextLine()) {
            String[] split = in.nextLine().split("\t");
            nWords.put(split[0], Double.parseDouble(split[1]));
        }
    }

    private ArrayList<String> edits(String word) {
        ArrayList<String> result = new ArrayList<>();
        // delete one char
        for (int i = 0; i < word.length(); ++i) {
            result.add(word.substring(0, i) + word.substring(i + 1));
        }
        // swap two char
        for (int i = 0; i < word.length() - 1; ++i) {
            result.add(word.substring(0, i) + word.substring(i + 1, i + 2) + word.substring(i, i + 1) + word.substring(i + 2));
        }
        // replace one char
        for (int i = 0; i < word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i + 1));
            }
        }
        // insert onc char
        for (int i = 0; i <= word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
            }
        }
        return result;
    }

    public String correct(String word) {
        if (nWords.containsKey(word)) {
            return word;
        }
        ArrayList<String> list = edits(word);
        HashMap<Double, String> candidates = new HashMap<>();
        for (String s : list) {
            if (nWords.containsKey(s)) {
                candidates.put(nWords.get(s), s);
            }
        }
        if (candidates.size() > 0) {
            return candidates.get(Collections.max(candidates.keySet()));
        }
        // edit distance 2
        for (String s : list) {
            for (String w : edits(s)) {
                if (nWords.containsKey(w)) {
                    candidates.put(nWords.get(w), w);
                }
            }
        }
        return candidates.size() > 0 ? candidates.get(Collections.max(candidates.keySet())) : word;
    }

    public static void main(String args[]) throws IOException {
        MyIn in = new MyIn();
        Spelling spelling = Spelling.getInstance();
        while (in.hasNextLine()) {
            System.out.println(spelling.correct(in.nextLine()));
        }
    }


}
