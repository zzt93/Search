package searchPrompt.inputMethod.pinyin;

import mis.Config;
import mis.MyIn;
import mis.TreeIterator;
import mis.TrieTree;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by zzt on 1/26/16.
 * <p>
 * Usage: Using the TrieTree adt split pinyin to parts: e.g. split 'woshi' to 'wo shi' in the process of split, can tell
 * whether it is pinyin or english word
 */
public class PinyinTree {

    public static final char NOT_LETTER = '1';
    public static final char END_SYMBOL = '1';
    private static MyIn in;
    private static TrieTree<Character> all = new TrieTree<>(NOT_LETTER, END_SYMBOL);

    static {
        try {
            in = Config.getPinyinStream();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        construct(all);
    }

    private static void construct(TrieTree<Character> all) {
        while (in.hasNext()) {
            char[] chars = in.next().toCharArray();
            ArrayList<Character> tmp = new ArrayList<>();
            for (char aChar : chars) {
                tmp.add(aChar);
            }
            tmp.add(END_SYMBOL);
            all.add(tmp);
        }
    }

    public static TreeIterator<Character> iterator() {
        return all.iterator();
    }

    public static SinglePinyinType isValidSinglePinyin(String input) {
        TreeIterator<Character> iterator = all.iterator();
        for (char c : input.toCharArray()) {
            if (iterator.hasChild(c)) {
                iterator.move(c);
            } else {
                return SinglePinyinType.INVALID;
            }
        }
        return iterator.canExit() ? SinglePinyinType.VALID : SinglePinyinType.NEED_MORE;
    }

    public enum SinglePinyinType {
        VALID, NEED_MORE, INVALID
    }

    @Override
    public String toString() {
        return "PinyinTree：" + all;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(PinyinTree.isValidSinglePinyin("ng"));
    }
}
