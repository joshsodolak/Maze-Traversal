public class MazeTraversal {
    public static void main(String[] args) {
        MazeTraversalModel model = new MazeTraversalModel();

        model.printMaze();

        int distance = model.breadthFirstSearch();

        model.printMaze();
        if (distance == -1) {
            System.out.println("Unable to find path!");
        } else {
            System.out.println("Distance from starting point: " + distance + " tiles");
        }
    }
}