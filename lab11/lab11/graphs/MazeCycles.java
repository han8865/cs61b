package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int[] edge;
    private boolean targetFound = false;
    private Maze maze;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = 0;
        edgeTo[s] = s;
        edge = new int[maze.V()];
        edge[s] = s;
    }

    @Override
    public void solve() {
        cycle(s);
    }

    private void cycle(int v) {
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                edge[w] = v;
                announce();
                cycle(w);
            } else if (w != edge[v]) {
                edgeTo[w] = v;
                for (int i = v; i != w; i = edge[i]) {
                    edgeTo[i] = edge[i];
                }
                announce();
                targetFound = true;
            }
            if (targetFound) {
                return;
            }
        }
    }
}

