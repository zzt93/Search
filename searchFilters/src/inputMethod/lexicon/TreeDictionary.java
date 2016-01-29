package inputMethod.lexicon;

import inputMethod.pinyin.PinyinTree;
import mis.Config;
import mis.MyIn;
import mis.TrieTree;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by zzt on 1/29/16.
 * <p>
 * Usage:
 * Using TrieTree to implement the mapping from pinyin to lexicon
 * // TODO: 1/29/16 not finish implementation
 */
@Deprecated
public class TreeDictionary implements Dictionary {
    private static TreeDictionary ourInstance = new TreeDictionary();

    public static TreeDictionary getInstance() {
        return ourInstance;
    }

    private MyIn in;
    public final DictChar DICT_END = new DictChar(PinyinTree.END_SYMBOL);
    private TrieTree<DictChar> all = new TrieTree<>(new DictChar(PinyinTree.NOT_LETTER),
            DICT_END);

    private TreeDictionary() {
        try {
            in = Config.getDictionary();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        construct(all);
    }

    private void construct(TrieTree<DictChar> all) {
        while (in.hasNextLine()) {
            String[] split = in.nextLine().split(Config.DICT_SPLIT);
            assert split.length == 2;
            char[] chars = split[0].toCharArray();
            ArrayList<DictChar> tmp = new ArrayList<>();
            for (char aChar : chars) {
                tmp.add(new DictChar(aChar));
            }
            tmp.add(new DictEnd(PinyinTree.END_SYMBOL, split[1]));
            // TODO: 1/29/16 should update the end of trie tree, not simply add to it
            all.add(tmp);
        }
    }

    @Override
    public ArrayList<String> find(String syllables) {
        ArrayList<Lexicon> words = new ArrayList<>();
        return words;
    }
}
