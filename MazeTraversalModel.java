import java.util.LinkedList;
import java.util.Queue;

public class MazeTraversalModel {
    private MazeTraversalTile[][] tiles;
    private int numRows;
    private int numColumns;

    /**
     * Constructor for MazeTraversalModel
     */
    public MazeTraversalModel(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        tiles = new MazeTraversalTile[numRows][numColumns];
        instantiateTiles();
        buildMaze(/* 0 */1);
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
         * Sets the value stored in the tile to the String passed in as a parameter.
         * 
         * @param value the value to be stored in the tile
         */
        public void setValue(String value) {
            this.value = value;
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
     * Helper method for building the maze that fills the edges of the maze.
     */
    private void fillEdges() {
        for (int i = 0; i < numRows; i++) {
            tiles[i][0].setValue("#");
            tiles[i][numColumns - 1].setValue("#");
        }
        for (int i = 1; i < numColumns - 1; i++) {
            tiles[0][i].setValue("#");
            tiles[numRows - 1][i].setValue("#");
        }
    }

    /**
     * Constructs a maze based on a seed that is input. For testing purposes.
     * 
     * @param seed The seed of the maze to be built.
     */
    private void buildMaze(int seed) {
        fillEdges();
        if (seed == 0) {
            for (int i = 2; i < numRows - 2; i++) {
                tiles[i][2].setValue("#");
                tiles[i][numColumns - 3].setValue("#");
            }
            for (int i = 3; i < numColumns - 3; i++) {
                tiles[2][i].setValue("#");
                tiles[numRows - 3][i].setValue("#");
            }

            for (int i = 4; i < numRows - 4; i++) {
                tiles[i][4].setValue("#");
                tiles[i][numColumns - 5].setValue("#");
            }
            for (int i = 5; i < numColumns - 5; i++) {
                tiles[4][i].setValue("#");
                tiles[numRows - 5][i].setValue("#");
            }

            for (int i = 6; i < numRows - 6; i++) {
                tiles[i][6].setValue("#");
                tiles[i][numColumns - 7].setValue("#");
            }
            for (int i = 6; i < numColumns - 7; i++) {
                tiles[6][i].setValue("#");
                tiles[numRows - 7][i].setValue("#");
            }

            tiles[1][10].setValue("#");
            tiles[13][2].setValue("#");
            tiles[11][2].setValue(" ");
            tiles[3][16].setValue("#");
            tiles[6][4].setValue(" ");
            tiles[7][5].setValue("#");
            tiles[4][17].setValue(" ");
            tiles[11][9].setValue("#");
            tiles[9][22].setValue(" ");

            tiles[numRows - 1][1].setValue("O");
            tiles[1][numColumns - 1].setValue("X");
        } else if (seed == 1) {
            tiles[1][4].setValue("#");
            tiles[2][4].setValue("#");
            tiles[3][4].setValue("#");
            tiles[4][4].setValue("#");
            tiles[5][4].setValue("#");
            tiles[6][4].setValue("#");
            tiles[7][4].setValue("#");
            tiles[2][2].setValue("O");
            tiles[2][7].setValue("X");
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
            availableAdjacent[0] = tiles[tile.row - 1][tile.column];
        }

        if (tile.getColumn() == numColumns - 1) { // East Tile
            availableAdjacent[1] = null;
        } else {
            availableAdjacent[1] = tiles[tile.row][tile.column + 1];
        }

        if (tile.getRow() == numRows - 1) { // South Tile
            availableAdjacent[2] = null;
        } else {
            availableAdjacent[2] = tiles[tile.row + 1][tile.column];
        }

        if (tile.getColumn() == 0) { // West Tile
            availableAdjacent[3] = null;
        } else {
            availableAdjacent[3] = tiles[tile.row][tile.column - 1];
        }

        for (int i = 0; i < availableAdjacent.length; i++) {
            if (availableAdjacent[i] != null && availableAdjacent[i].value == "#") {
                availableAdjacent[i] = null;
            } else if (availableAdjacent[i].isDiscovered) {
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

        startingPosition.discover();
        queue.add(startingPosition);

        while (queue.size() > 0) {
            MazeTraversalTile parentTile = queue.poll();
            if (parentTile.value.equals("X")) {
                int distance = 1;
                parentTile = parentTile.parent;
                while (parentTile.parent != null) {
                    tiles[parentTile.row][parentTile.column].value = "+";
                    parentTile = parentTile.parent;
                    distance++;
                }
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
                    currentTile.parent = parentTile;
                    queue.add(currentTile);
                }
            }
        }
        return -1;
    }

    /**
     * Prints the maze in a text form.
     */
    public void printMaze() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                System.out.print(tiles[i][j].value + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
        System.out.println(
                "Starting position: (" + getStartingPosition().row + ", " + getStartingPosition().column + ")");
        System.out.println("Destination: (" + getDestination().row + ", " + getDestination().column + ")");

    }

}