package inputMethod.lexicon;

/**
 * Created by zzt on 1/28/16.
 * <p>
 * Usage:
 */
public class LexiconEdge implements Comparable<LexiconEdge> {

    private Lexicon word;

    private LexiconNode to;
    private double length;

    public LexiconEdge(Lexicon lexicon, LexiconNode node) {
        word = lexicon;
        length = - Math.log(word.getRatio());
        to = node;
    }

    public Lexicon getWord() {
        return word;
    }

    public LexiconNode getTo() {
        return to;
    }

    @Override
    public int compareTo(LexiconEdge o) {
        return Double.compare(length, o.length);
    }
}
