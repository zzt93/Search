package inputMethod.lexicon;

import inputMethod.syllable.SyllableGraph;

import java.util.Collection;

/**
 * Created by zzt on 1/28/16.
 * <p>
 * Usage:
 */
public class LexiconGraph {

    LexiconNode start = new LexiconNode();
    private Collection<LexiconNode> nodes;

    public LexiconNode getStart() {
        return start;
    }

    /**
     * Precondition: for this graph is created by SyllableGraph using dfs, so
     * all node is already set visited.
     *
     * @return connected shortest path
     *
     * @see SyllableGraph#toLexicon()
     */
    public String shortestPath() {
        setNotVisited(nodes);
        StringBuilder sb = new StringBuilder();
        boolean finished = false;
        shortestPathRecursive(start, sb, finished);
        return sb.toString();
    }

    public void setNotVisited(Collection<LexiconNode> nodes) {
        for (LexiconNode node : nodes) {
            node.setVisited(false);
        }
    }

    private void shortestPathRecursive(LexiconNode now, StringBuilder sb, boolean finished) {
        if (now.isVisited() || finished) {
            return;
        }
        for (LexiconEdge edge : now.getOut()) {
            LexiconNode to = edge.getTo();
            sb.append(edge.getWord());
            if (to.isEnd()) {
                finished = true;
                return;
            }
            shortestPathRecursive(to, sb, finished);
        }
    }


    public void setNodes(Collection<LexiconNode> nodes) {
        this.nodes = nodes;
    }
}
