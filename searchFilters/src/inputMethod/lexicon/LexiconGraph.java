package inputMethod.lexicon;

import inputMethod.lm.HashLM;
import inputMethod.lm.LanguageModel;
import inputMethod.syllable.SyllableGraph;
import mis.Selection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

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
    public ArrayList<Lexicon> shortestPath(int resultSize) {
        ArrayList<Lexicon> res = new ArrayList<>();
        collapse();
        ArrayList<LexiconEdge> out = start.getOut();
        if (out.size() > resultSize) {
            Selection.selection(out, resultSize);
        }
        System.out.println(out.size());
        for (int i = 0; i < resultSize && i < out.size(); i++) {
            LexiconEdge edge = out.get(i);
            assert edge.getTo().isEnd();
            res.add(edge.getLexicon());
        }
        Collections.sort(res);
        return res;
    }

    /**
     * postCondition: only two vertices
     */
    private void collapse() {
        LanguageModel lm = HashLM.getInstance();
        boolean notFinish = true;
        while (notFinish) {
            ArrayList<LexiconEdge> tmp = new ArrayList<>();
            notFinish = false;
            for (Iterator<LexiconEdge> iter = start.getOut().iterator(); iter.hasNext(); ) {
                LexiconEdge edge = iter.next();
                LexiconNode second = edge.getTo();
                boolean isEnd = true;
                for (LexiconEdge next : second.getOut()) {
                    isEnd = false;
                    LexiconNode third = next.getTo();
                    double bigram = lm.getBigram(edge.getPhrase(), next.getPhrase());
                    if (bigram < LanguageModel.BIGRAM_POSSIBILITY_THRESHOLD) {
                        continue;
                    }
                    tmp.add(new LexiconEdge(
                            new Lexicon(edge.getPhrase() + next.getPhrase(), bigram),
                            third));
                }
            /*
            when all out edges are visited, this node can be removed(except start and end) by removing
            the edge to it
             */
                assert isEnd == second.isEnd();
                if (!isEnd) {
                    iter.remove();
                    notFinish = true;
                }
            }
            start.getOut().addAll(tmp);
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
