/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CFD_VAL = 1.96;

    private final double mean;
    private final double stdDev;
    private final double cfdLow;
    private final double cfdHigh;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if ((n <= 0) || trials <= 0) {
            throw new IllegalArgumentException();
        }

        double[] results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation system = new Percolation(n);
            while (!system.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                system.open(row, col);
            }
            double result = (system.numberOfOpenSites() * 1.0) / (n * n);
            results[i] = result;
        }

        this.mean = StdStats.mean(results);
        this.stdDev = StdStats.stddev(results);
        this.cfdLow = this.mean - CFD_VAL * (this.stdDev / Math.sqrt(trials));
        this.cfdHigh = this.mean + CFD_VAL * (this.stdDev / Math.sqrt(trials));
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.cfdLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.cfdHigh;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        try {
            PercolationStats percolationStats = new PercolationStats(n, t);
            System.out.println(String.format("%-25s= %.20f", "mean", percolationStats.mean()));
            System.out.println(String.format("%-25s= %.20f", "stddev", percolationStats.stddev()));
            System.out.println(String.format("%-25s= [%.20f, %.20f]", "95% confidence interval",
                                             percolationStats.confidenceLo(),
                                             percolationStats.confidenceHi()));
        }
        catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
