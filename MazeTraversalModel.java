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

    public MazeTraversalTile getTile(int row, int column) {
        return tiles[row][column];
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
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
            } else if (availableAdjacent[i].hasBeenDiscovered()) {
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