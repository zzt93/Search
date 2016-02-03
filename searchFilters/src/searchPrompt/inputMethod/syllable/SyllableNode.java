package searchPrompt.inputMethod.syllable;

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
    private boolean canExit = false;
    private ArrayList<SyllableEdge> out = new ArrayList<>();

    public SyllableNode(int end) {
        this.end = end;
    }

    public int getEnd() {
        return end;
    }

    public ArrayList<SyllableEdge> getOut() {
        return out;
    }

    public void addOut(SyllableEdge edge) {
        out.add(edge);
    }

    public boolean isCanExit() {
        return canExit;
    }

    public void setCanExit(boolean canExit) {
        this.canExit = canExit;
    }

    public boolean noOutEdge() {
        return out.isEmpty();
    }

    @Override
    public String toString() {
        return "SyllableNode{" +
                "end=" + end +
                '}';
    }
}
