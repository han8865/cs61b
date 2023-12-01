package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;
import java.util.*;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        for (int i = 0; i < maze.V(); i += 1) {
            if (i != s) {
                distTo[i] = maze.V();
            }
        }
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    private class Score implements Comparable<Score> {
        private int v;
        private int score;

        public Score(int v) {
            this.v = v;
            this.score = distTo[v] + h(v);
        }

        @Override
        public int compareTo(Score o) {
            return this.score - o.score;
        }
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        Set<Integer> openSet = new HashSet<>();
        openSet.add(s);
        MinPQ<Score> score = new MinPQ<>();
        score.insert(new Score(s));
        while (!openSet.isEmpty()) {
            Score node = score.delMin();
            marked[node.v] = true;
            announce();
            if (node.v == t) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }
            openSet.remove(node.v);
            for (int neighbor : maze.adj(node.v)) {
                int tmpDist = distTo[node.v] + 1;
                if (tmpDist < distTo[neighbor]) {
                    edgeTo[neighbor] = node.v;
                    distTo[neighbor] = tmpDist;
                    score.insert(new Score(neighbor));
                    openSet.add(neighbor);
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

