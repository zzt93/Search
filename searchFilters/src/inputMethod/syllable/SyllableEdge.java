package inputMethod.syllable;

/**
 * Created by zzt on 1/27/16.
 * <p>
 * Usage:
 * A edge mean there exists a valid pinyin syllable from start node to end node
 */
public class SyllableEdge {

    private double length;

    private SyllableNode from;
    private SyllableNode to;

    public SyllableEdge(SyllableNode from, SyllableNode to) {
        this.from = from;
        this.to = to;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public SyllableNode getTo() {
        return to;
    }
}
