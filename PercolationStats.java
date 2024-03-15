import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

public class PercolationStats {
 
    private int m; // number of indep. experi
    private double[] x; //precolation thresholds

    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        if (n <= 0 || m <= 0){
            throw new IllegalArgumentException("Illegal n or m");
    }
     //instance variables
        this.m = m;
        x = new double[m];
       
        //perform experiment m times
        for (int i = 0; i < m; i++) {
            UFPercolation p = new UFPercolation(n); //nxn perc
            while (!(p.percolates())) {
                int Row = StdRandom.uniform(n); //row
                int Col = StdRandom.uniform(n); //col

                if (!p.isOpen(Row, Col)) { //if site not open, open
                    p.open(Row, Col);
                }
            }
            x[i] = (double) p.numberOfOpenSites() / (n * n); //calc perc thresh- stored in x
        }
    }
       

    // Returns sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(x);
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(x);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return StdStats.mean(x) - 1.96 * StdStats.stddev(x) / Math.sqrt(m);
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return StdStats.mean(x) + 1.96 * StdStats.stddev(x) / Math.sqrt(m);
    }


    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }
}