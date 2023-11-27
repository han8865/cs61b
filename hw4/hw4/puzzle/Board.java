package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[][] board;
    private int N;
    private int dist;

    public Board(int[][] tiles) {
        N = tiles.length;
        dist = -1;
        board = new int[N][N];
        for (int i = 0; i < N; i += 1) {
            System.arraycopy(tiles[i], 0, board[i], 0, N);
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i >= N || j < 0 || j >= N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return board[i][j];
    }

    public int size() {
        return N;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int blankX = -1;
        int blankY = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) == 0) {
                    blankX = i;
                    blankY = j;
                }
            }
        }
        int[][] neighborBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                neighborBoard[i][j] = tileAt(i, j);
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (Math.abs(-blankX + i) + Math.abs(j - blankY) - 1 == 0) {
                    neighborBoard[blankX][blankY] = neighborBoard[i][j];
                    neighborBoard[i][j] = 0;
                    Board neighbor = new Board(neighborBoard);
                    neighbors.enqueue(neighbor);
                    neighborBoard[i][j] = neighborBoard[blankX][blankY];
                    neighborBoard[blankX][blankY] = 0;
                }
            }
        }
        return neighbors;

    }

    public int hamming() {
        int hammingDist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != i * N + j + 1 && board[i][j] != 0) {
                    hammingDist += 1;
                }
            }
        }
        return hammingDist;
    }

    public int manhattan() {
        int manhattanDist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int num = board[i][j];
                if (num != 0) {
                    manhattanDist += Math.abs(i - (num - 1) / N);
                    manhattanDist += Math.abs(j - (num - 1) % N);
                }
            }
        }
        return manhattanDist;
    }

    public int estimatedDistanceToGoal() {
        if (dist == -1) {
            dist = manhattan();
        }
        return dist;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.N != that.N) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
