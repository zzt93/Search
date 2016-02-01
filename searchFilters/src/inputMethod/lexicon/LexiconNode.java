package inputMethod.lexicon;

import java.util.ArrayList;

/**
 * Created by zzt on 1/28/16.
 * <p>
 * Usage:
 */
public class LexiconNode {

    private boolean visited = false;

    // TODO: 2/1/16 may change to linkedList
    private ArrayList<LexiconEdge> out = new ArrayList<>();

    public void addOutEdge(LexiconEdge lexiconEdge) {
        out.add(lexiconEdge);
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isEnd() {
        return out.isEmpty();
    }

    public ArrayList<LexiconEdge> getOut() {
        return out;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
