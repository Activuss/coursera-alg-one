public class Percolation {

    private boolean[][] openedSites;
    private int gridLineSize;
    private boolean bottomSiteWasOpened;
    private boolean topSiteWasOpened;
    private WeightedQuickUnionUF unionFind;

    public Percolation(int N) { // create N-by-N openedSites, with all sites blocked
        if (N > 0) {
            unionFind = new WeightedQuickUnionUF(N * N);
            gridLineSize = N;
            openedSites = new boolean[N][N];
        } else {
            throw new IllegalArgumentException();
        }
    }               // create N-by-N openedSites, with all sites blocked

    private void printGrid() {
        for (int i = 0; i < gridLineSize; i++) {
            for (int j = 0; j < gridLineSize; j++) {
                System.out.print(openedSites[i][j]);
            }
            System.out.println();
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
        boolean siteOpened = openedSites[x - 1][y - 1];
        if (!siteOpened) {
            openedSites[x - 1][y - 1] = true;
            connectWithOpenedNeighbors(x, y);
        }

        if (!bottomSiteWasOpened && (x == gridLineSize))
            bottomSiteWasOpened = true;
        if (!topSiteWasOpened && (x == 1))
            topSiteWasOpened = true;
    }

    public boolean isOpen(int x, int y) { // is site (row i, column j) open?
        validate(x, y);
        return openedSites[x - 1][y - 1];
    }

    private void connectWithOpenedNeighbors(int x, int y) {
        int current1DCoordinate = xyTo1D(x, y);
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
        boolean currentSiteOpened = openedSites[x - 1][y - 1];
        if (!currentSiteOpened)
            return false;
        for (int i = 0; i < gridLineSize; i++) {
            boolean topSiteOpened = openedSites[0][i];
            if (topSiteOpened && unionFind.connected(xyTo1D(1, i + 1), xyTo1D(x, y)))
                return true;
        }
        return false;
    }

    public boolean percolates() {
        if (topSiteWasOpened && bottomSiteWasOpened) {
            for (int i = 0; i < gridLineSize; i++) {
                if (isFull(gridLineSize, i + 1)) {
                    return true;
                }
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
