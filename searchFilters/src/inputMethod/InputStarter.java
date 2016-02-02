package inputMethod;

import inputMethod.lexicon.HashDictionary;
import inputMethod.lexicon.Lexicon;
import inputMethod.lexicon.LexiconGraph;
import inputMethod.lm.HashLM;
import inputMethod.pinyin.NotPinyinException;
import inputMethod.pinyin.PinyinTree;
import inputMethod.syllable.SyllableGraph;
import mis.MyIn;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by zzt on 1/27/16.
 * <p>
 * Usage:
 */
public class InputStarter {

    static {
        // in order to load large first, reduce the time of first request
        int i = PinyinTree.END_SYMBOL;
        HashDictionary.getInstance();
        HashLM.getInstance();
        System.out.println("-------------------------");
        System.out.println("---initialization done---");
        System.out.println("-------------------------");
    }


    public static void main(String[] args) throws FileNotFoundException {

        MyIn in = new MyIn(System.in);
        while (in.hasNextLine()) {
            try {
                ArrayList<Lexicon> lexicons = getLexicons(in.nextLine(), 4);
                lexicons.forEach(System.out::println);
            } catch (NotPinyinException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Lexicon> getLexicons(String line, int resultSize) throws NotPinyinException {
        SyllableGraph syllableGraph = new SyllableGraph(line);
        System.out.println(syllableGraph);
        LexiconGraph lexiconGraph = syllableGraph.toLexicon();
        return lexiconGraph.bestPath(resultSize);
    }

    /**
     *
     * @param line a line may contain pinyin and chinese word
     * @return the fist extracted pinyin
     */
    private static String extractPinyin(String line) {
        int i;
        for (i = 0; i < line.toCharArray().length; i++) {
            char c = line.charAt(i);
            /*
            for char is 16bits which can't represent all unicode characters in SMP
            The Java platform uses the UTF-16 representation in char arrays and in the String and StringBuffer classes.
            In this representation, supplementary characters are represented as a pair of char values
            A char value, therefore, represents Basic Multilingual Plane (BMP) code points
             */
            if (c <= 127) {
                break;
            }
        }
        return line.substring(i);
    }

}
