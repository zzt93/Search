package searchPrompt.inputMethod.lexicon;

/**
 * Created by zzt on 1/29/16.
 * <p>
 * Usage:
 */
public class Lexicon implements Comparable<Lexicon> {

    private String word;
    private double ratio;

    public Lexicon(String word, double ratio) {
        this.word = word;
        this.ratio = ratio;
    }

    public String getWord() {
        return word;
    }

    public double getRatio() {
        return ratio;
    }

    @Override
    public String toString() {
        return "Lexicon{" +
                "word='" + word + '\'' +
                ", ratio=" + ratio +
                '}';
    }


    @Override
    public int compareTo(Lexicon o) {
        return - Double.compare(ratio, o.getRatio());
    }
}
