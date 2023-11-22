package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] thresholds;
    private int T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.T = T;
        thresholds = new double[T];
        for (int i = 0; i < T; i += 1) {
            int cnt = 0;
            Percolation percolation = pf.make(N);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(N), col = StdRandom.uniform(N);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    cnt += 1;
                }
            }
            thresholds[i] = cnt / (N * N * 1.0);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(T));
    }
}
