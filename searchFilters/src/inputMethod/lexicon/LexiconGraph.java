package inputMethod.lexicon;

import inputMethod.lm.HashLM;
import inputMethod.lm.LanguageModel;
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
        collapse();
        return sb.toString();
    }

    /**
     * postCondition: only two vertices
     */
    private void collapse() {
        LanguageModel lm = HashLM.getInstance();
        for (LexiconEdge edge : start.getOut()) {
            LexiconNode second = edge.getTo();
            for (LexiconEdge e : second.getOut()) {
                LexiconNode third = e.getTo();
                start.addOut(new LexiconEdge(new Lexicon(edge.getPhrase() + e.getPhrase(), lm.getBigram("", "")),
                        third));
            }
            /*
            when all out vertices are visited, this node can be removed(except start and end)
             */
        }
    }

    public void setNotVisited(Collection<LexiconNode> nodes) {
        for (LexiconNode node : nodes) {
            node.setVisited(false);
        }
    }

    //    private void shortestPathRecursive(LexiconNode now, StringBuilder sb, boolean finished) {
    //        if (now.isVisited() || finished) {
    //            return;
    //        }
    //        for (LexiconEdge edge : now.getOut()) {
    //            LexiconNode to = edge.getTo();
    //            sb.append(edge.getLexicon());
    //            if (to.isEnd()) {
    //                finished = true;
    //                return;
    //            }
    //            shortestPathRecursive(to, sb, finished);
    //        }
    //    }


    public void setNodes(Collection<LexiconNode> nodes) {
        this.nodes = nodes;
    }
}
