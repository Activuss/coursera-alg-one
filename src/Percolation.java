import java.util.Random;

public class Percolation {

    private Site[][] grid;
    private Random rand;
    private int count;
    private int gridLineSize;
    private WeightedQuickUnionUF unionFind;

    public Percolation(int N) throws IndexOutOfBoundsException {// create N-by-N grid, with all sites blocked
        if (N > 0){
            unionFind = new WeightedQuickUnionUF(N);
            gridLineSize = N;

            grid = new Site[N][N];
            rand = new Random();

            count = 1;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    grid[i][j] = new Site(count++) ;
                    System.out.print(grid[i][j]);
                }
                System.out.println();
            }

        } else {
            throw new IndexOutOfBoundsException();
        }
    }               // create N-by-N grid, with all sites blocked

    private void printGrid(){
        for (int i = 0; i < gridLineSize; i++) {
            for (int j = 0; j < gridLineSize; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    private class Site{
        private int id;
        private boolean isOpened;

        public Site(int id) {
            this.id = id;
        }

        public void setSiteOpened(boolean isOpened) {
            this.isOpened = isOpened;
        }

        public int getId() {
            return id;
        }

        public boolean isSiteOpened() {
            return isOpened;
        }

        @Override
        public String toString() {
            String isOpened;
            String id;
            if(isSiteOpened())
                isOpened = "O";
            else isOpened = "X";

            if (this.id < 10)
                id = "0"+this.id;
            else id = String.valueOf(this.id);

            return isOpened + id +" ";
        }
    }

    private int xyTo1D(int x, int y){
        return ((x-1) * gridLineSize) + y;
    }

    private void validate(int x, int y){
        if (x < 1 || x > gridLineSize || y < 1 || y > gridLineSize)
            throw new IllegalArgumentException("Wrong coordinates");
    }

    public void open(int x, int y) throws IndexOutOfBoundsException {        // open site (row i, column j) if it is not open already
        validate(x, y);

        if (!isOpen(x, y)){
            grid[x -1][y -1].setSiteOpened(true);
        }

        connectWithOpenedNeighbors(x, y);

    }
    public boolean isOpen(int x, int y) throws IndexOutOfBoundsException {// is site (row i, column j) open?
        validate(x, y);
        return grid[x -1][y -1].isSiteOpened();

    }

    private void connectWithOpenedNeighbors(int x, int y){
        int current1DCoordinate = xyTo1D(x, y);//or need find before doing this
        if(((x - 1) > 0) && isOpen(x - 1, y)){//up
            unionFind.union(current1DCoordinate,xyTo1D(x - 1, y));

        } else if ((x + 1) <= gridLineSize && isOpen(x + 1, y)) { //down
            unionFind.union(current1DCoordinate,xyTo1D(x + 1, y));
        } else if ((y + 1) <= gridLineSize && isOpen(x, y + 1)) { //right
            unionFind.union(current1DCoordinate,xyTo1D(x, y + 1));
        } else if ((y - 1) > 0 && isOpen(x, y - 1)) { //left
            unionFind.union(current1DCoordinate,xyTo1D(x, y - 1));
        }

    }

    public boolean isFull(int x, int y) throws IndexOutOfBoundsException {return false;}     // is site (row i, column j) full?
    public boolean percolates() {return false;}             // does the system percolate?

    public WeightedQuickUnionUF getUnionDataStruture(){
        return unionFind;
    }

    public static void main(String[] args) {
//        System.out.println("Hi");
        Percolation percolation = new Percolation(4);
        WeightedQuickUnionUF unionUF = percolation.getUnionDataStruture();

        System.out.println("is opened 2.2: " + percolation.isOpen(2, 2));
        System.out.println("set 2.2 opened");
        percolation.open(2,2);
        percolation.printGrid();

     /*   percolation.open(2,3);
        percolation.printGrid();*/

    }   // test client (optional)

    /*
    initialize all sites to be blocked.
    Repeat the following until the system percolates:
    Choose a site (row i, column j) uniformly at random among all blocked sites.
    Open the site (row i, column j).
    The fraction of sites that are opened when the system percolates provides an estimate of the percolation thres
    */
}