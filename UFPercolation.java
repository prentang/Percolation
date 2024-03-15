import java.util.WeakHashMap;
import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation{
   
    private int n;
    private boolean[][] open;
    private int openSites;
    private WeightedQuickUnionUF uf;
    
    private WeightedQuickUnionUF ufbw;

    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
        if (n <= 0){
            throw new IllegalArgumentException("Illegal n");

            //Percolation() should throw an IllegalArgumentException("Illegal n") if n ≤ 0.     
    }
    //init instance variables
            this.n = n;
            this.openSites = 0;
            this.open = new boolean[n][n];
            uf = new WeightedQuickUnionUF(n * n + 2); //plust two for virtual top and bottom sites
           
            ufbw = new WeightedQuickUnionUF(n * n + 1);

    }


    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || i > n-1 || j < 0 || j > n-1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        //if sit i,j not open; open. proceed to increment openSites by one
        if (!open [i][j]) {
            open [i][j] = true;
                openSites++; 
        
    
   // If the site is in the first (or last) row, connect the corresponding uf site with the source (or sink)
        if (i == 0) {
            uf.union(encode(i, j), 0);
            
            ufbw.union(encode(i, j), 0);

        }
        
        if (i == n - 1) {
            uf.union(encode(i, j), n * n + 1); //bot
        }
   
        if (i - 1 >= 0 && isOpen(i - 1 , j)) { // above
            uf.union(encode(i, j), encode(i - 1, j));
           
            ufbw.union(encode(i, j), encode(i - 1, j));
        }
        }

        if (i + 1 < n && isOpen(i + 1 , j)) { //below
            uf.union(encode(i, j), encode(i + 1, j));
            
            ufbw.union(encode(i, j), encode(i + 1, j));
        }

        if (j - 1 >= 0 && isOpen(i, j - 1)) { //left
            uf.union(encode(i, j), encode(i, j - 1));
            
            ufbw.union(encode(i, j), encode(i, j - 1));
        }

        if (j < n - 1  && isOpen(i,j + 1)) { //right
            uf.union(encode(i, j), encode(i, j + 1));
            
            ufbw.union(encode(i, j), encode(i, j + 1));
        }

    }



    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
             // if ixj site is not open return false
         //throws an IOB exception if i or j is outside 0, n-1
         if (i < 0 || i > n - 1|| j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return open[i][j];
    //if open return open, if not, return false
    }
 

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        //return whether or not the site is full 
            return isOpen(i, j) && ufbw.connected(encode(i, j), 0);

    }


    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }


    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        
       return uf.connected(0, n*n +1);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        int iD = i * n + j + 1;
        return iD;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
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