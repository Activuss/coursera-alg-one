public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(final int gridSize, final int numberOfExperiments) {
        // perform T independent experiments on an N-by-N grid
        double[] results;
        if (gridSize <= 0 || numberOfExperiments <= 0) {
            throw new IllegalArgumentException("T and N must be more then zero");
        } else {
            results = new double[numberOfExperiments];
            for (int i = 0; i < numberOfExperiments; i++) {
                Percolation percolation = new Percolation(gridSize);
                double count = 0;
                while (!percolation.percolates()) {
                    int x = StdRandom.uniform(1, gridSize + 1);
                    int y = StdRandom.uniform(1, gridSize + 1);
                    if (!percolation.isOpen(x, y)) {
                        percolation.open(x, y);
                        count++;
                    }
                }
                results[i] = count / (double) (gridSize * gridSize);
            }
        }
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        confidenceHi =  mean() + ((1.96 * stddev()) / Math.sqrt(numberOfExperiments));
        confidenceLo = mean() - ((1.96 * stddev()) / Math.sqrt(numberOfExperiments));

    }

    public double mean() {                      // sample mean of percolation threshold
        return mean;
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return stddev;
    }

    public double confidenceLo() {              // low  endpoint of 95% confidence interval
        return confidenceLo;
    }

    public double confidenceHi() {              // high endpoint of 95% confidence interval
        return confidenceHi;
    }

    public static void main(String[] args) {    // test client (described below)
        PercolationStats stats = new PercolationStats(100, 60);
        System.out.println(stats.mean());
        System.out.println(stats.stddev());
        System.out.println(stats.confidenceLo() + " , " + stats.confidenceHi());

    }
}
