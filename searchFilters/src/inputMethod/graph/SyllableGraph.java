package inputMethod.graph;

import inputMethod.NotPinyinException;
import inputMethod.PinyinTree;
import util.TreeIterator;

import java.util.ArrayList;

/**
 * Created by zzt on 1/27/16.
 * <p>
 * Usage:
 */
public class SyllableGraph {

    public static final int START_INDEX = 0;
    SyllableNode start;
    private final String input;

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
        ArrayList<SyllableNode> possibleNoOutEdgeNode = constructBasic(input);
        SyllableNode last = possibleNoOutEdgeNode.get(possibleNoOutEdgeNode.size() - 1);
        assert last.getEnd() == input.length();
        addEdge(possibleNoOutEdgeNode);
        removeInvalid(last);
    }

    private void removeInvalid(SyllableNode last) {
        ArrayList<SyllableNode> nodes = new ArrayList<>();

        backTrace(last, nodes);

    }

    private void backTrace(SyllableNode last, ArrayList<SyllableNode> nodes) {
        if (last.getEnd() == START_INDEX) {
            nodes.add(last);
        }
        for (SyllableEdge edge : last.getIn()) {
            SyllableNode from = edge.getFrom();
            backTrace(from, nodes);
        }
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
                for (int later = i + 1; later < possibleNoOutEdgeNode.size(); later++) {
                    SyllableNode to = possibleNoOutEdgeNode.get(later);
                    String tmp = input.substring(node.getEnd(), to.getEnd());
                    if (PinyinTree.isValidSinglePinyin(tmp)) {
                        node.addOut(new SyllableEdge(node, to));
                    } else {
                        // if it can't be a syllable with closest char, it will not be with further one
                        break;
                    }
                }
            }
        }
    }


    /**
     * postCondition: it will find all nodes of this graph
     *
     * @param input                 input String to analysis
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
                    to.addIn(edge);
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

        return possibleNoOutEdgeNode;
    }
}
