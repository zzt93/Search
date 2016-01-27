package inputMethod.graph;

import java.util.ArrayList;

/**
 * Created by zzt on 1/27/16.
 * <p>
 * Usage:
 */
public class SyllableNode {

    /**
     * the end of syllable, the whole syllable is [start, end)
     * start of syllable is store in the another side of edge
     */
    private int end;
    private ArrayList<SyllableEdge> out = new ArrayList<>();
    private ArrayList<SyllableEdge> in = new ArrayList<>();

    public SyllableNode(int end) {
        this.end = end;
    }

    public int getEnd() {
        return end;
    }

    public ArrayList<SyllableEdge> getIn() {
        return in;
    }

    public void addOut(SyllableEdge edge) {
        out.add(edge);
    }

    public void addIn(SyllableEdge edge) {
        in.add(edge);
    }

    public boolean noOutEdge() {
        return out.isEmpty();
    }
}
