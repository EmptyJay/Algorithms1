import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;
    private int numTrials;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();

        results = new double[trials];
        numTrials = trials;

        int i = 0;
        while (i < trials) {
            results[i] = runTrial(n);

            i++;

        }

    }

    private double runTrial(int n) {
        Percolation perc = new Percolation(n);

        while (!perc.percolates()) {
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;

            perc.open(row, col);

        }

        double openCount = 0;
        for (int row = 1; row <= n; row++) 
            for (int col = 1; col <= n; col++)
                if (perc.isOpen(row, col))
                    openCount++;

        return openCount / (n*n);
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return (mean() - ((1.96 * stddev()) / Math.sqrt(numTrials)));
    }

    public double confidenceHi() {
        return (mean() + ((1.96 * stddev()) / Math.sqrt(numTrials)));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(n, trials);
        StdOut.println("mean =                    " + percStats.mean());

        StdOut.println("stddev =                  " + percStats.stddev());
        String confStr = "95% confidence interval = ";
        StdOut.println(confStr + percStats.confidenceLo() + ", " + percStats.confidenceHi());
    }
}


