import java.util.LinkedList;
import java.util.Queue;

public class MazeTraversalModel {
    private MazeTraversalTile[][] tiles;
    private int numRows;
    private int numColumns;

    /**
     * Constructor for MazeTraversalModel
     * 
     * @param numRows    the number of rows in the maze
     * @param numColumns the number of columns in the maze
     */
    public MazeTraversalModel(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        tiles = new MazeTraversalTile[numRows][numColumns];
        instantiateTiles();
        // buildMaze(/* 0 */1);
    }

    /**
     * Internal class for storing all data at each tile in the maze.
     */
    private class MazeTraversalTile {
        private boolean isDiscovered;
        private String value;
        private int row;
        private int column;
        private MazeTraversalTile parent;

        /**
         * Constructor for the internal MazeTraversalTile class.
         */
        public MazeTraversalTile(int row, int column) {
            this.isDiscovered = false;
            this.value = " ";
            this.row = row;
            this.column = column;
            this.parent = null;
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
    }

    public MazeTraversalTile getTile(int row, int column) {
        return tiles[row][column];
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setTileValue(int row, int column, String value) {
        tiles[row][column].setValue(value);
    }

    public String getTileValue(int row, int column) {
        return tiles[row][column].getValue();
    }

    /**
     * Creates an instance of a MazeTraversalTile for every index of the
     * two-dimensional array tiles.
     */
    private void instantiateTiles() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                tiles[i][j] = new MazeTraversalTile(i, j);
            }
        }
    }

    /**
     * Finds the starting position in the maze.
     * 
     * @return the starting position tile, or null if no starting position can be
     *         found.
     */
    private MazeTraversalTile getStartingPosition() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (tiles[i][j].isStartingPoint()) {
                    return tiles[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Finds the destination in the maze.
     * 
     * @return the destination tile, or null if no destination can be found.
     */
    private MazeTraversalTile getDestination() {
        int[] destination = new int[2];
        destination[0] = -1;
        destination[1] = -1;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (tiles[i][j].isDestination()) {
                    return tiles[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Finds and returns all available adjacent tiles to the passed in tile
     * 
     * @param tile The tile whose available adjacent are to be found
     * @return all available adjacent tiles. If null, that tile is unavailable
     */
    private MazeTraversalTile[] getAvailableAdjacent(MazeTraversalTile tile) {
        MazeTraversalTile[] availableAdjacent = new MazeTraversalTile[4];

        if (tile.getRow() == 0) { // North Tile
            availableAdjacent[0] = null;
        } else {
            availableAdjacent[0] = tiles[tile.getRow() - 1][tile.getColumn()];
        }

        if (tile.getColumn() == numColumns - 1) { // East Tile
            availableAdjacent[1] = null;
        } else {
            availableAdjacent[1] = tiles[tile.getRow()][tile.getColumn() + 1];
        }

        if (tile.getRow() == numRows - 1) { // South Tile
            availableAdjacent[2] = null;
        } else {
            availableAdjacent[2] = tiles[tile.getRow() + 1][tile.getColumn()];
        }

        if (tile.getColumn() == 0) { // West Tile
            availableAdjacent[3] = null;
        } else {
            availableAdjacent[3] = tiles[tile.getRow()][tile.getColumn() - 1];
        }

        for (int i = 0; i < availableAdjacent.length; i++) {
            if (availableAdjacent[i] != null && availableAdjacent[i].getValue() == "#") {
                availableAdjacent[i] = null;
            } else if (availableAdjacent[i] != null && availableAdjacent[i].hasBeenDiscovered()) {
                availableAdjacent[i] = null;
            }
        }

        return availableAdjacent;
    }

    /**
     * Performs the breadth first search algorithm to find the shortest path from
     * the starting point to the destination
     * 
     * @return The distance from the starting point to the destination, or -1 if no
     *         such path exists.
     */
    public int breadthFirstSearch() {
        Queue<MazeTraversalTile> queue = new LinkedList<MazeTraversalTile>();
        MazeTraversalTile startingPosition = getStartingPosition();
        long startingTime = System.currentTimeMillis();

        startingPosition.discover();
        queue.add(startingPosition);

        while (queue.size() > 0) {
            MazeTraversalTile parentTile = queue.poll();
            if (parentTile.getValue().equals("X")) {
                int distance = 1;
                parentTile = parentTile.getParent();
                while (parentTile.getParent() != null) {
                    tiles[parentTile.getRow()][parentTile.getColumn()].setValue("+");
                    parentTile = parentTile.getParent();
                    distance++;
                }
                System.out.println("Completed in " + (System.currentTimeMillis() - startingTime));
                System.out.println("All done!");
                return distance;
            }
            MazeTraversalTile[] adjacentTiles = getAvailableAdjacent(parentTile);
            for (int i = 0; i < adjacentTiles.length; i++) {
                if (adjacentTiles[i] == null) {
                    continue;
                }

                MazeTraversalTile currentTile = adjacentTiles[i];
                if (!currentTile.hasBeenDiscovered()) {
                    currentTile.discover();
                    currentTile.setParent(parentTile);
                    queue.add(currentTile);
                }
            }
        }
        System.out.println("Completed in " + (System.currentTimeMillis() - startingTime));
        System.out.println("All done!");
        return -1;
    }

    public void resetDiscoveredTiles() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                tiles[i][j].reset();
            }
        }
    }

    /**
     * Prints the maze in a text form.
     */
    public void printMaze() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                System.out.print(tiles[i][j].getValue() + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
        System.out.println("Starting position: (" + getStartingPosition().getRow() + ", "
                + getStartingPosition().getColumn() + ")");
        System.out.println("Destination: (" + getDestination().getRow() + ", " + getDestination().getColumn() + ")");

    }

}