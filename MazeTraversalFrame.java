import javax.swing.JFrame;

public class MazeTraversalFrame extends JFrame {
    private static final long serialVersionUID = -9018282878606968922L;
    private MazeTraversalModel model;
    private MazeTraversalPanel panel;

    public MazeTraversalFrame(int numRows, int numColumns) {
        setTitle("Maze Traversal - Josh Sodolak");
        setSize(numRows * 32, numColumns * 32);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        model = new MazeTraversalModel(numRows, numColumns);
        panel = new MazeTraversalPanel(this);
        add(panel);
        setVisible(true);
    }

    public MazeTraversalModel getModel() {
        return model;
    }
}