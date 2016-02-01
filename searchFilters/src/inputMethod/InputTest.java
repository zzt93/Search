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
public class InputTest {

    public static void main(String[] args) throws FileNotFoundException {
        MyIn in = new MyIn("Input.test");
        initialSystem();
        while (in.hasNextLine()) {
            try {
                ArrayList<Lexicon> lexicons = getLexicons(in.nextLine());
                lexicons.forEach(System.out::println);
            } catch (NotPinyinException e) {
                e.printStackTrace();
            }
        }
    }

    private static ArrayList<Lexicon> getLexicons(String line) throws NotPinyinException {
        SyllableGraph syllableGraph = new SyllableGraph(line);
        System.out.println(syllableGraph);
        LexiconGraph lexiconGraph = syllableGraph.toLexicon();
        return lexiconGraph.shortestPath(4);
    }

    private static void initialSystem() {
        // in order to load file
        int i = PinyinTree.END_SYMBOL;
        HashDictionary.getInstance();
        HashLM.getInstance();
        System.out.println("------------------------");
        System.out.println("initialization done");
        System.out.println("------------------------");
    }
}
