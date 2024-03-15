import java.util.Arrays;

import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using a 2D array.
public class ArrayPercolation implements Percolation {
    private int n; //instance variable
    private int openSites; // number of opensites
    private boolean[][] open; //percolation system

    // Constructs an n x n percolation system, with all sites blocked.
    public ArrayPercolation(int n) {
        //n x n system, but all sites blocked (closed)
         if (n <= 0){
            throw new IllegalArgumentException("Illegal n");

            // ArrayPercolation() should throw an IllegalArgumentException("Illegal n") if n ≤ 0.     
    }
            //declare instance variables
            this.n = n;
            this.openSites = 0;
            this.open = new boolean[n][n];

    }


    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        //throws an IOB exception if i or j is outside 0, n-1
        if (i < 0 || i > n-1 || j < 0 || j > n-1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        //if site i,j not open; open. proceed to increment openSites by one
        if (!open [i][j]) {
            open [i][j] = true;
                openSites++; 
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        // if ixj site is not open return false
         //throws an IOB exception if i or j is outside 0, n-1
         if (i < 0 || i > n-1 || j < 0 || j > n-1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return open[i][j];
    
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || i > n-1 || j < 0 || j > n-1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }

        boolean[][] full = new boolean[n][n];
        
        // site in first row
        for (int k = 0; k < n; k++) {
            floodFill(full, 0, k);

        }
        return full[i][j];
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        for (int k = 0; k < n; k++) {
            if (isFull(n-1, k)) {
                return true;
            }
        }
        return false;
    }

    // Recursively flood fills full[][] using depth-first exploration, starting at (i, j).
    private void floodFill(boolean[][] full, int i, int j) {
        if (i < 0 || i > n-1 || j < 0 || j > n-1 || full[i][j] || !isOpen(i, j)){
            // return if i or j is out bounds: i < 0, j < 0
            // return if site is not open (!) is Open
            // return if full: j > n-1
            // return if full: i > n-1
            // return if full: full[i][j]
        return; 
    }
    full[i][j] = true;
   

    floodFill(full, i, j-1); //left
    floodFill(full, i, j+1); //right
    floodFill(full, i-1, j); //down
    floodFill(full, i+1, j); //up 

}

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        ArrayPercolation perc = new ArrayPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}