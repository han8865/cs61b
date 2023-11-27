package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.*;

public class Solver {
    private MinPQ<SearchNode> nodes;
    private List<WorldState> answer;
    private int answerMoves;

    private class SearchNode implements Comparable<SearchNode> {
        private WorldState wState;
        private int moves;
        private SearchNode previous;

        public SearchNode(WorldState wState, int moves, SearchNode previous) {
            this.wState = wState;
            this.moves = moves;
            this.previous = previous;
        }

        @Override
        public int compareTo(SearchNode o) {
            return moves + wState.estimatedDistanceToGoal() - o.moves - o.wState.estimatedDistanceToGoal();
        }
    }

    public Solver(WorldState initial) {
        nodes = new MinPQ<>();
        nodes.insert(new SearchNode(initial, 0, null));
        SearchNode minNode = nodes.delMin();
        while (!minNode.wState.isGoal()) {
            for (WorldState wState : minNode.wState.neighbors()) {
                if (minNode.wState.equals(initial) || !wState.equals(minNode.previous.wState)) {
                    nodes.insert(new SearchNode(wState, minNode.moves + 1, minNode));
                }
            }
            minNode = nodes.delMin();
        }
        answer = new ArrayList<>();
        List<WorldState> reverseAnswer = new ArrayList<>();
        answerMoves = 0;
        while (minNode.previous != null) {
            reverseAnswer.add(minNode.wState);
            answerMoves += 1;
            minNode = minNode.previous;
        }
        reverseAnswer.add(minNode.wState);
        for (int i = 0; i <= answerMoves; i += 1) {
            answer.add(reverseAnswer.get(answerMoves - i));
        }
    }

    public int moves() {
        return answerMoves;
    }

    public Iterable<WorldState> solution() {
        return answer;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
