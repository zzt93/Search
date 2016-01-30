package inputMethod.syllable;

import inputMethod.lexicon.*;
import inputMethod.lm.HashLM;
import inputMethod.lm.LanguageModel;
import inputMethod.pinyin.NotPinyinException;
import inputMethod.pinyin.PinyinTree;
import mis.TreeIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by zzt on 1/27/16.
 * <p>
 * Usage:
 */
public class SyllableGraph {

    public static final int START_INDEX = 0;
    SyllableNode start;
    private final String input;
    private ArrayList<SyllableNode> possibleNoOutEdgeNode;

    public SyllableGraph(String string) throws NotPinyinException {
        input = string;
        construct(string);
    }

    /**
     * Try to split input pinyin to different part and construct map to show it
     *
     * @param input Input pinyin
     */
    public void construct(String input) throws NotPinyinException {
        if (input == null) {
            System.err.println(this.getClass().getName() + "input is null");
            return;
        }
        possibleNoOutEdgeNode = constructBasic(input);
        SyllableNode last = possibleNoOutEdgeNode.get(possibleNoOutEdgeNode.size() - 1);
        assert last.getEnd() == input.length();
        addEdge(possibleNoOutEdgeNode);
        removeInvalid(start);
    }

    private void removeInvalid(SyllableNode start) {
        boolean end = findEnd(start);
        assert end;
        removeNotEnd(start);
    }

    private void removeNotEnd(SyllableNode start) {
        ArrayList<SyllableEdge> out = start.getOut();
        for (Iterator<SyllableEdge> iter = out.iterator(); iter.hasNext(); ) {
            SyllableNode node = iter.next().getTo();
            if (node.isCanExit()) {
                removeNotEnd(node);
            } else {
                iter.remove();
            }
        }
    }

    private boolean findEnd(SyllableNode now) {
        if (isEnd(now)) {
            now.setCanExit(true);
            return true;
        }
        boolean res = false;
        for (SyllableEdge edge : now.getOut()) {
            SyllableNode to = edge.getTo();
            boolean end = findEnd(to);
            if (end) {
                now.setCanExit(true);
                res = true;
            }
        }
        return res;
    }

    private boolean isStart(SyllableNode now) {
        return now.getEnd() == START_INDEX;
    }

    private boolean isEnd(SyllableNode now) {
        return now.getEnd() == input.length();
    }

    /**
     * add the edge for node which doesn't have out edge:
     * in this problem, if a node has out edge, will not have more;
     * if a node doesn't have a edge, may be it will have a edge to the larger index
     * <p></p>
     * Notice: if a char of input doesn't
     * become a node in SyllableGraph#constructBasic(String, ArrayList) , i.e. it can't be the end of syllable
     * e.g. p, b ... it will not be a node later. So we just find more edge in current set of nodes, the number of
     * node will not change.
     *
     * @param possibleNoOutEdgeNode node list
     *
     * @see SyllableGraph#constructBasic(String)
     */
    private void addEdge(ArrayList<SyllableNode> possibleNoOutEdgeNode) {
        for (int i = 0; i < possibleNoOutEdgeNode.size(); i++) {
            SyllableNode node = possibleNoOutEdgeNode.get(i);
            if (node.noOutEdge()) {
                OUT:
                for (int later = i + 1; later < possibleNoOutEdgeNode.size(); later++) {
                    SyllableNode to = possibleNoOutEdgeNode.get(later);
                    String tmp = input.substring(node.getEnd(), to.getEnd());
                    switch (PinyinTree.isValidSinglePinyin(tmp)) {
                        case VALID:
                            node.addOut(new SyllableEdge(node, to));
                            break;
                        case NEED_MORE:
                            // if it can't be a syllable with closest char, it will not be with further one
                            break;
                        case INVALID:
                            break OUT;
                    }
                }
            }
        }
    }


    /**
     * postCondition: it will find all possible nodes of this graph(may be more than actual)
     *
     * @param input input String to analysis
     *
     * @throws NotPinyinException
     */
    private ArrayList<SyllableNode> constructBasic(String input) throws NotPinyinException {
        ArrayList<SyllableNode> possibleNoOutEdgeNode = new ArrayList<>();

        start = new SyllableNode(START_INDEX);

        TreeIterator<Character> iterator = PinyinTree.iterator();
        SyllableNode now = start;
        SyllableNode to = null;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (iterator.hasChild(c)) {
                // make it point to the char c
                iterator.move(c);
                if (iterator.canExit()) {
                    to = new SyllableNode(i + 1);
                    possibleNoOutEdgeNode.add(to);
                    SyllableEdge edge = new SyllableEdge(now, to);
                    now.addOut(edge);
                }
            } else {
                if (to == null) {
                    throw new NotPinyinException(input);
                }
                now = to;
                iterator = PinyinTree.iterator();
                i--;
            }
        }
        if (!iterator.canExit()) {
            throw new NotPinyinException(input);
        }

        return possibleNoOutEdgeNode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SyllableGraph{").append(input).append("}\n");
        describe(start, sb);
        return sb.toString();
    }

    private void describe(SyllableNode start, StringBuilder sb) {
        if (isEnd(start)) {
            return;
        }
        for (SyllableEdge edge : start.getOut()) {
            SyllableNode to = edge.getTo();
            sb.append(getSyllable(start.getEnd(), to.getEnd())).append(" ");
            describe(to, sb);
        }
    }

    private String getSyllable(int end, int end1) {
        return input.substring(end, end1);
    }

    public LexiconGraph toLexicon() {
        LexiconGraph res = new LexiconGraph();
        ArrayList<SyllableNode> nodes = new ArrayList<>();
        nodes.add(start);
        nodes.addAll(possibleNoOutEdgeNode);
        makeComplete(nodes);
        HashMap<SyllableNode, LexiconNode> nodeMap = new HashMap<>();
        toLexiconRecursive(start, res.getStart(), nodeMap);
        // both not have the start of graph, so should have the same size
        assert nodeMap.size() == possibleNoOutEdgeNode.size();
        assert nodeMap.get(possibleNoOutEdgeNode.get(possibleNoOutEdgeNode.size() - 1)).isEnd();
        res.setNodes(nodeMap.values());
        return res;
    }

    /**
     * make every node has edge to later node(compared by their index)
     * i.e. the ith node should have n - i - 1 out edge(i start from 0)
     *
     * @param nodes nodes list in the order of their index of input
     */
    private void makeComplete(ArrayList<SyllableNode> nodes) {
        Dictionary dictionary = HashDictionary.getInstance();
        for (int i = 0; i < nodes.size(); i++) {
            SyllableNode now = nodes.get(i);
            if (now.getOut().size() < nodes.size() - i - 1) {
                for (int x = i + now.getOut().size() + 1; x < nodes.size(); x++) {
                    SyllableNode to = nodes.get(x);
                    String syllable = getSyllable(now.getEnd(), to.getEnd());
                    ArrayList<String> lexicons = dictionary.find(syllable);
                    if (lexicons != null) {
                        now.addOut(new SyllableEdge(now, to));
                    }
                }
            }
        }
    }

    private void toLexiconRecursive(SyllableNode syllableNow, LexiconNode lexiconNow, HashMap<SyllableNode, LexiconNode> nodeMap) {
        if (isEnd(syllableNow)) {
            assert nodeMap.get(syllableNow) == lexiconNow;
            lexiconNow.setVisited(true);
            return;
        }
        Dictionary dictionary = HashDictionary.getInstance();
        LanguageModel model = HashLM.getInstance();

        for (SyllableEdge edge : syllableNow.getOut()) {
            SyllableNode to = edge.getTo();
            LexiconNode node;
            if (nodeMap.containsKey(to)) {
                node = nodeMap.get(to);
            } else {
                node = new LexiconNode();
                nodeMap.put(to, node);
            }

            String syllable = getSyllable(syllableNow.getEnd(), to.getEnd());
            for (String s : dictionary.find(syllable)) {
                lexiconNow.addOut(new LexiconEdge(
                        new Lexicon(s, model.getUnigram(syllable)), node));
            }
            if (node.isVisited()) {
                continue;
            }
            toLexiconRecursive(to, node, nodeMap);
        }
        lexiconNow.setVisited(true);
    }
}
