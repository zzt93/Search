package inputMethod;

import inputMethod.pinyin.NotPinyinException;
import inputMethod.syllable.SyllableGraph;
import mis.MyIn;

/**
 * Created by zzt on 1/27/16.
 * <p>
 * Usage:
 */
public class InputTest {

    public static void main(String[] args) {
        MyIn in = new MyIn(System.in);
        while (in.hasNext()) {
            try {
                System.out.println(new SyllableGraph(in.nextLine()));
            } catch (NotPinyinException e) {
                e.printStackTrace();
            }
        }
    }
}
