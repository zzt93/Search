package inputMethod.lexicon;

import java.util.PriorityQueue;

/**
 * Created by zzt on 1/28/16.
 * <p>
 * Usage:
 */
public class LexiconNode {

    private boolean visited = false;

    private PriorityQueue<LexiconEdge> out = new PriorityQueue<>();

    public void addOut(LexiconEdge lexiconEdge) {
        out.add(lexiconEdge);
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isEnd() {
        return out.isEmpty();
    }

    public PriorityQueue<LexiconEdge> getOut() {
        return out;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
