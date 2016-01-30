package inputMethod.lexicon;

/**
 * Created by zzt on 1/28/16.
 * <p>
 * Usage:
 */
public class LexiconEdge implements Comparable<LexiconEdge> {

    private Lexicon lexicon;

    private LexiconNode to;
    private double length;

    public LexiconEdge(Lexicon lexicon, LexiconNode node) {
        this.lexicon = lexicon;
        length = - Math.log(this.lexicon.getRatio());
        to = node;
    }

    public Lexicon getLexicon() {
        return lexicon;
    }

    public String getPhrase() {
        return lexicon.getWord();
    }

    public LexiconNode getTo() {
        return to;
    }

    @Override
    public int compareTo(LexiconEdge o) {
        return Double.compare(length, o.length);
    }
}
