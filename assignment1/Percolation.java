import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP_CELL = 0;
    private static final byte CELL_OPEN = (1 << 0);
    private static final byte TOP_CONNECTED = (1 << 1);
    private static final byte BOTTOM_CONNECTED = (1 << 2);

    private int size;
    private byte[] cellStatus;
    private WeightedQuickUnionUF uf;
    private int percolates;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        size = n;
        cellStatus = new byte[size*size+1];       // +1 so we can use base-1 numbering
        uf = new WeightedQuickUnionUF(size * size + 1);  // +1 also for base-1 numbering

        for (int i = 1; i <= size; i++)
        {
            cellStatus[xyTo1D(1, i)] |= TOP_CONNECTED;      // top row of cell are connected to the top
            cellStatus[xyTo1D(size, i)] |= BOTTOM_CONNECTED;    // bottom row of cells are connected to the bottom
        }
    }

    private void checkBounds(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size)
            throw new java.lang.IndexOutOfBoundsException();
    }

    private int xyTo1D(int row, int col) {
        return ((row-1) * size + col);
    }

    private void mergeCells(int currCell, int nbrCell) {
        int nbrRoot;

        if ((cellStatus[nbrCell] & CELL_OPEN) > 0) {     // neighboring cell is open
            nbrRoot = uf.find(nbrCell);     // find the root of the neighoring cell
            cellStatus[currCell] |= cellStatus[nbrRoot];    // merge root status to current cell
            uf.union(currCell, nbrCell);
        }
    }


    public void open(int row, int col) {
        int cell, nbrCell, cellRoot;
        byte nbrStatus;

        checkBounds(row, col);

        cell = xyTo1D(row, col);

        if ((cellStatus[cell] & CELL_OPEN) == 0) {      // cell is not open
            cellStatus[cell] |= CELL_OPEN;      // mark cell as open

            if (row > 1) {      // cell is not in the top row
                nbrCell = xyTo1D(row-1, col);       // cell above
                mergeCells(cell, nbrCell);
            }

            if (row < size) {      // cell is not in bottom row
                nbrCell = xyTo1D(row+1, col);       // cell below
                mergeCells(cell, nbrCell);
            }

            if (col > 1) {      // cell is not in the left column
                nbrCell = xyTo1D(row, col-1);       // cell to the left
                mergeCells(cell, nbrCell);
            }

            if (col < size) {      // cell is not in right column
                nbrCell = xyTo1D(row, col+1);       // cell to the right
                mergeCells(cell, nbrCell);
            }

            cellRoot = uf.find(cell);
            cellStatus[cellRoot] |= cellStatus[cell];

            if (((cellStatus[cellRoot] & TOP_CONNECTED) > 0) &&
                ((cellStatus[cellRoot] & BOTTOM_CONNECTED) > 0))
                percolates = 1;

        }

    }

    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        int cell = xyTo1D(row, col);

        return ((cellStatus[cell] & CELL_OPEN) > 0);
    }

    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        int cell = xyTo1D(row, col);

        int rootCell = uf.find(cell);

        return (((cellStatus[rootCell] & TOP_CONNECTED) > 0) &&
                ((cellStatus[rootCell] & CELL_OPEN) > 0));
    }

    public boolean percolates() {
        return (percolates == 1);
    }

    public static void main(String[] args) {
        StdOut.println("Opening " + args[0]);
        In in = new In(args[0]);
        int n = in.readInt();

        Percolation perc = new Percolation(n);

        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            StdOut.println("Opened: " + i + ", " + j);
            StdOut.println("isOpen(" + i + ", " + j + "): " + perc.isOpen(i, j));
            StdOut.println("isFull(" + i + ", " + j + "): " + perc.isFull(i, j));
            StdOut.println("Percolates: " + perc.percolates());
        }
    }
}
