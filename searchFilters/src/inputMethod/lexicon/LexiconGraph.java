package inputMethod.lexicon;

import inputMethod.lm.HashLM;
import inputMethod.lm.LanguageModel;
import inputMethod.syllable.SyllableGraph;
import mis.Selection;

import java.util.*;

/**
 * Created by zzt on 1/28/16.
 * <p>
 * Usage:
 */
public class LexiconGraph {

    LexiconNode start = new LexiconNode();
//    private Collection<LexiconNode> nodes;

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
    public ArrayList<Lexicon> bestPath(int resultSize) {
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
     * @see SyllableGraph#addEdge(ArrayList)
     */
    private void collapse() {
        LanguageModel lm = HashLM.getInstance();
        boolean notFinish = true;
        HashSet<String> duplicateCheck = new HashSet<>();

        while (notFinish) {
            ArrayList<LexiconEdge> tmp = new ArrayList<>();
            notFinish = false;
            for (ListIterator<LexiconEdge> iter = start.getOut().listIterator(start.getOut().size()); iter.hasPrevious(); ) {
                // visiting list in reversed order for access longer word first
                // @see SyllableGraph#addEdge(ArrayList)
                LexiconEdge edge = iter.previous();
                LexiconNode second = edge.getTo();
                // already used phrase
                duplicateCheck.add(edge.getPhrase());
                boolean isEnd = true;
                for (LexiconEdge next : second.getOut()) {
                    String word = edge.getPhrase() + next.getPhrase();
                    if (duplicateCheck.contains(word)) {
                        continue;
                    }
                    isEnd = false;
                    LexiconNode third = next.getTo();
                    double bigram = lm.getBigram(edge.getPhrase(), next.getPhrase());
                    if (bigram < LanguageModel.BIGRAM_POSSIBILITY_THRESHOLD) {
                        continue;
                    }
                    Lexicon lexicon = new Lexicon(word, bigram);
                    tmp.add(new LexiconEdge(lexicon, third));
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


//    public void setNodes(Collection<LexiconNode> nodes) {
//        this.nodes = nodes;
//    }
}
