public class Percolation {

    private Site[][] grid;
    private int gridLineSize;
    private WeightedQuickUnionUF unionFind;

    public Percolation(int N) { // create N-by-N grid, with all sites blocked
        if (N > 0) {
            unionFind = new WeightedQuickUnionUF(N * N);
            gridLineSize = N;
            grid = new Site[N][N];
            int count = 1;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    grid[i][j] = new Site(count++);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }               // create N-by-N grid, with all sites blocked

    private void printGrid() {
        for (int i = 0; i < gridLineSize; i++) {
            for (int j = 0; j < gridLineSize; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    private static class Site {
        private int mId;
        private boolean mIsOpened;

        public Site(int id) {
            this.mId = id;
        }

        public void setSiteOpened(boolean isOpened) {
            this.mIsOpened = isOpened;
        }

        public boolean isSiteOpened() {
            return mIsOpened;
        }

        @Override
        public String toString() {
            String isOpened;
            String id;
            if (isSiteOpened())
                isOpened = "O";
            else
                isOpened = "X";

            if (this.mId < 10)
                id = "0" + this.mId;
            else id = String.valueOf(this.mId);

            return isOpened + id + " ";
        }
    }

    private int xyTo1D(int x, int y) {
        return ((x - 1) * gridLineSize) + y - 1;
    }

    private void validate(int x, int y) {
        if (x < 1 || x > gridLineSize || y < 1 || y > gridLineSize)
            throw new IndexOutOfBoundsException("Wrong coordinates");
    }

    public void open(int x, int y) {        // open site (row i, column j) if it is not open already
        validate(x, y);
        Site site = grid[x - 1][y - 1];
        if (!site.mIsOpened) {
            site.setSiteOpened(true);
            connectWithOpenedNeighbors(x, y);
        }

    }

    public boolean isOpen(int x, int y) { // is site (row i, column j) open?
        validate(x, y);
        return grid[x - 1][y - 1].isSiteOpened();

    }

    private void connectWithOpenedNeighbors(int x, int y) {
        int current1DCoordinate = xyTo1D(x, y); //or need find before doing this
        if (((x - 1) > 0) && isOpen(x - 1, y) && !unionFind.connected(current1DCoordinate, xyTo1D(x - 1, y))) { //up
            unionFind.union(current1DCoordinate, xyTo1D(x - 1, y));
        }
        if ((x + 1) <= gridLineSize && isOpen(x + 1, y) && !unionFind.connected(current1DCoordinate, xyTo1D(x + 1, y))) { //down
            unionFind.union(current1DCoordinate, xyTo1D(x + 1, y));
        }
        if ((y + 1) <= gridLineSize && isOpen(x, y + 1) && !unionFind.connected(current1DCoordinate, xyTo1D(x, y + 1))) { //right
            unionFind.union(current1DCoordinate, xyTo1D(x, y + 1));
        }
        if ((y - 1) > 0 && isOpen(x, y - 1) && !unionFind.connected(current1DCoordinate, xyTo1D(x, y - 1))) { //left
            unionFind.union(current1DCoordinate, xyTo1D(x, y - 1));
        }
    }

    public boolean isFull(int x, int y) { // is site (row i, column j) full?
        validate(x, y);
        Site currentSite = grid[x - 1][y - 1];
        if (!currentSite.mIsOpened)
            return false;
        for (int i = 0; i < gridLineSize; i++) {
            Site topSite = grid[0][i];
            if (topSite.isSiteOpened() && unionFind.connected(topSite.mId - 1, currentSite.mId - 1))
                return true;
        }
        return false;
    }

    public boolean percolates() {
        for (int i = 0; i < gridLineSize; i++) {
            if (isFull(gridLineSize, i + 1)) {
                return true;
            }
        }
        return false;
    }             // does the system percolate?

    private WeightedQuickUnionUF getUnionDataStruture() {
        return unionFind;
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(4);
        WeightedQuickUnionUF unionUF = percolation.getUnionDataStruture();

        System.out.println("is opened 2.2: " + percolation.isOpen(2, 2));
        System.out.println("set 2.2 opened");
        percolation.open(2, 2);
        System.out.println("is opened 2.2: " + percolation.isOpen(2, 2));
        percolation.open(2, 3);
        System.out.println(unionUF.connected(percolation.xyTo1D(2, 2), percolation.xyTo1D(2, 3)));
        percolation.open(1, 2);
        percolation.open(1, 1);
        System.out.println(unionUF.connected(percolation.xyTo1D(2, 3), percolation.xyTo1D(1, 1)));
        percolation.open(3, 3);
        percolation.open(4, 4);
        System.out.println("Percolates " + percolation.percolates());
        percolation.open(3, 4);
        System.out.println("Percolates " + percolation.percolates());
        percolation.printGrid();
        System.out.println(unionUF.connected(percolation.xyTo1D(1, 2), percolation.xyTo1D(2, 2)));
        System.out.println(unionUF.connected(percolation.xyTo1D(1, 2), percolation.xyTo1D(2, 3)));
        System.out.println(unionUF.connected(percolation.xyTo1D(1, 2), percolation.xyTo1D(3, 3)));
        System.out.println(unionUF.connected(percolation.xyTo1D(1, 2), percolation.xyTo1D(3, 4)));
        System.out.println(unionUF.connected(percolation.xyTo1D(1, 2), percolation.xyTo1D(4, 4)));
        System.out.println(percolation.isFull(3, 3));
        System.out.println(percolation.isFull(3, 4));
        System.out.println(percolation.isFull(4, 3));
        System.out.println(percolation.isFull(4, 4));

    }   // test client (optional)
}
