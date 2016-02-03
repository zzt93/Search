package searchPrompt.corrector;

import mis.MyIn;
import mis.MyOut;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zzt on 2/1/16.
 * <p>
 * Usage:
 */
public class WordPossibilities {

    private HashMap<String, Integer> count = new HashMap<>();
    private int all;

    public WordPossibilities(String path) throws FileNotFoundException {
        MyIn in = new MyIn(path);
        while (in.hasNextLine()) {
            parseLine(in.nextLine());
        }
    }

    /**
     * read a pure book consist of many sentences and words to count ratio
     * @param in book source
     * @param map result map
     */
    public static void trainPureData(MyIn in, HashMap<String, Integer> map) {
        Pattern p = Pattern.compile("\\w+");
        for (String temp = ""; in.hasNextLine(); temp = in.nextLine()) {
            Matcher m = p.matcher(temp.toLowerCase());
            while (m.find()) {
                map.put((temp = m.group()), map.containsKey(temp) ? map.get(temp) + 1 : 1);
            }
        }
        in.close();
    }

    private void addIfContain(String key, String num) {
        int i = Integer.parseInt(num);
        all += i;
        if (count.containsKey(key)) {
            count.put(key, count.get(key) + i);
        } else {
            count.put(key, i);
        }
    }

    private void parseLine(String s) {
        String[] split = s.split("\t");
        addIfContain(split[1], split[0]);
        addIfContain(split[2], split[0]);
    }

    public void output(String path) {
        MyOut out = new MyOut(path);
        StringBuilder sb = new StringBuilder(count.size());
        count.forEach((s, i) -> {
            sb.append(s).append("\t").append(i * 1.0 / all).append("\n");
        });
        out.print(sb.toString());
    }

    public static void main(String[] args) throws FileNotFoundException {
        WordPossibilities wordPossibilities = new WordPossibilities("/home/zzt/work/resources/search/corrector/w2_.txt");
        System.out.println(wordPossibilities.all);
        wordPossibilities.output("unigram.data");
    }
}
