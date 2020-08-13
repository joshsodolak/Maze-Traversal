import javax.swing.JButton;
import java.awt.Graphics;

/**
 * Internal class for storing all data at each tile in the maze.
 */
public class MazeTraversalTile extends JButton {
    private static final long serialVersionUID = 1L;
    private boolean isDiscovered;
    private String value;
    private int row;
    private int column;
    private MazeTraversalTile parent;
    private String savedValue;

    /**
     * Constructor for the internal MazeTraversalTile class.
     */
    public MazeTraversalTile(int row, int column) {
        // setIcon(NORMAL_TILE);
        this.isDiscovered = false;
        this.value = " ";
        this.row = row;
        this.column = column;
        this.parent = null;
        this.savedValue = value;
    }

    /**
     * Accesses and returns the value of isDiscovered
     * 
     * @return the value of isDiscovered.
     */
    public boolean hasBeenDiscovered() {
        return isDiscovered;
    }

    public void reset() {
        isDiscovered = false;
        parent = null;
    }

    /**
     * sets isDiscovered to true
     */
    public void discover() {
        isDiscovered = true;
    }

    public String getSavedValue() {
        return savedValue;
    }

    public void setSavedValue(String value) {
        this.savedValue = value;
    }

    /**
     * Accesses and returns the current value of the tile
     * 
     * @return the value of the tile.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value stored in the tile to the String passed in as a parameter.
     * 
     * @param value the value to be stored in the tile
     */
    public void setValue(String value) {
        this.value = value;
    }

    public MazeTraversalTile getParent() {
        return parent;
    }

    public void setParent(MazeTraversalTile parent) {
        this.parent = parent;
    }

    /**
     * Accesses and returns the row of the tile.
     * 
     * @return the row of the tile.
     */
    public int getRow() {
        return row;
    }

    /**
     * Accesses and returns the column of the tile.
     * 
     * @return the column of the tile.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Uses the tile's stored value to determine whether it is the start tile.
     * 
     * @return true if value = "O", false otherwise.
     */
    public boolean isStartingPoint() {
        return value.equals("O");
    }

    /**
     * Uses the tile's stored value to determine whether it is the destination.
     * 
     * @return true if value = "X", false otherwise.
     */
    public boolean isDestination() {
        return value.equals("X");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}