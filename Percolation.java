import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.Scanner;

public class Percolation{
    private boolean[][] grid;
    private WeightedQuickUnionUF wqfGrid;
    private WeightedQuickUnionUF wqfFull;
    private int gridSize;
    private int gridSquared;
    private int source; //virtual top
    private int sink; //virtual bottom
    private int openSites;
    
     // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n <= 0) throw new IllegalArgumentException("N must be > 0");
        gridSize = n;
        gridSquared = n * n;
        grid = new boolean[gridSize][gridSize];
        wqfGrid = new WeightedQuickUnionUF(gridSquared + 2); // includes virtual top bottom
        wqfFull = new WeightedQuickUnionUF(gridSquared + 1); // includes virtual top
        sink = gridSquared + 1;
        source = 0;
        openSites = 0;
    }

     // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        int shiftRow = row -1;
        int shiftCol = col -1;
        int val = encodes(row, col);
        validateSite(val);
        //Value is on the top row
        if(isOpen(shiftRow, shiftCol)){
            return;
        }
        grid[shiftRow][shiftCol] = true;
        openSites++;
        
        if(row == 1){
            wqfGrid.union(source, val);
            wqfFull.union(source, val);
        }
        //Value is on the bottom row
        if(row == gridSize){
            wqfGrid.union(sink, val);
        }
        //Value top
        if(isPresent(val-5) && isOpen(shiftRow, shiftCol)){
            wqfGrid.union(val, val-5);
            wqfFull.union(val, val-5);
        }
        //Value bottom
        if(isPresent(val+5) && isOpen(shiftRow, shiftCol)){
            wqfGrid.union(val, val+5);
            wqfFull.union(val, val+5);
        }
        //Value right
        if(isPresent(val+1) && isOpen(shiftRow, shiftCol)){
            wqfGrid.union(val, val+1);
            wqfFull.union(val, val+1);
        }
        //Value left
        if(isPresent(val-1) && isOpen(shiftRow, shiftCol)){
            wqfGrid.union(val, val-1);
            wqfFull.union(val, val-1);
        }
    }
 
     // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        int value = encodes(row+1, col+1);
        validateSite(value);
        if(grid[row][col] == true){
            return true;
        }else{
            return false;
        }
    }
 
     // is the site (row, col) full?
    public boolean isFull(int row, int col){
        validateSite((gridSize*row+col+1));
        return wqfFull.connected(source, (gridSize*row+col+1));
    }
 
     // returns the number of open sites
    public int numberOfOpenSites(){
        return openSites;
    }
 
     // does the system percolate?
    public boolean percolates(){
        //display();
        return wqfGrid.connected(source, sink);
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int size = Integer.valueOf(args[0]);

        Percolation percolation = new Percolation(size);
        int argCount = args.length;
        int x = 0;
        for (int i = 1; argCount>=2 ; i += 2) {
            int row = Integer.valueOf(args[i]);
            int col = Integer.valueOf(args[i + 1]);
            StdOut.printf("Adding row: "+row+" col: "+col);
            System.out.println();
            percolation.open(row, col);
            if (percolation.percolates()) {
                StdOut.printf("The System percolates");
                System.out.println();
            }
            argCount -= 2;
        }
        if (!percolation.percolates()) {
            StdOut.printf("Does not percolate");
            System.out.println();
        }

    }
    private int encodes(int r, int c){
        return gridSize*(r-1) + c;
    }
    private void validateSite(int value) {
        if (value>gridSquared || value<1) {
            System.out.println(value);
            throw new java.lang.IndexOutOfBoundsException("Index is out of bounds");
        }
    }
    private boolean isPresent(int value){
        if(value>=1 && value<= gridSquared){
            return true;
        }else{
            return false;
        }
    }
}
