package inputMethod.graph;

/**
 * Created by zzt on 1/27/16.
 * <p>
 * Usage:
 * A edge mean there exists a valid pinyin syllable from start node to end node
 */
public class SyllableEdge {

    private String pinyin;
    private SyllableNode from;
    private SyllableNode to;

    public SyllableEdge(SyllableNode from, SyllableNode to) {
        this.from = from;
        this.to = to;
    }

    public SyllableEdge(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPinyin() {
        return pinyin;
    }

    public SyllableNode getFrom() {
        return from;
    }
}
