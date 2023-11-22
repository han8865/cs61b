package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private int openNum;
    private boolean[][] grids;
    private WeightedQuickUnionUF uf;

    private int[]adjacent(int pos) {
        return new int[] {pos - 1, pos + 1, pos - N, pos + N};
    }

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.N = N;
        grids = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N){
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            grids[row][col] = true;
            openNum += 1;
        }
        int pos = row * N + col;
        if (row == 0) {
            uf.union(pos, N * N);
        }
        if (row == N) {
            uf.union(pos, N * N + 1);
        }
        int[] Adjacent = adjacent(pos);
        for (int i = 0; i < 4; i += 1) {
            int preAdj = Adjacent[i];
            if (preAdj >= 0 && preAdj < N * N && isOpen(preAdj / N, preAdj % N)) {
                uf.union(pos, preAdj);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grids[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return uf.connected(row * N + col, N * N);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(N * N, N * N + 1);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }
}
