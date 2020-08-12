import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;

public class MazeTraversalPanel extends JPanel {
    private MazeTraversalFrame frame;
    private MazeTraversalTile[][] maze;

    public MazeTraversalPanel(MazeTraversalFrame frame) {
        this.frame = frame;
        setLayout(new GridLayout(frame.getModel().getNumRows(), frame.getModel().getNumColumns()));
        maze = new MazeTraversalTile[frame.getModel().getNumRows()][frame.getModel().getNumColumns()];
        for (int i = 0; i < frame.getModel().getNumRows(); i++) {
            for (int j = 0; j < frame.getModel().getNumColumns(); j++) {
                MazeTraversalButton button = new MazeTraversalButton(new MazeTraversalTile(i, j));
                if (i == frame.getModel().getNumRows() / 2 && j == (frame.getModel().getNumColumns() / 2) - 2) {
                    button.tile.setValue("O");
                    button.setIcon(button.STARTING_TILE);
                } else if (i == frame.getModel().getNumRows() / 2 && j == (frame.getModel().getNumColumns() / 2) + 2) {
                    button.tile.setValue("X");
                    button.setIcon(button.TARGET_TILE);
                }
                maze[i][j] = button.tile;
                add(button);

                button.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        switch (button.tile.getValue()) {
                            case " ":
                                button.tile.setValue("#");
                                button.setIcon(button.WALL_TILE);
                                break;
                            case "#":
                                button.tile.setValue(" ");
                                button.setIcon(button.NORMAL_TILE);
                                break;
                            case "O":
                                break;
                            case "X":
                                break;
                            default:
                                break;
                        }
                        System.out.println("Image press");
                    }
                });
            }
        }
    }

    private class MazeTraversalButton extends JButton {
        private MazeTraversalTile tile;
        private final ImageIcon NORMAL_TILE = new ImageIcon("Images/Normal_Tile.png");
        private final ImageIcon STARTING_TILE = new ImageIcon("Images/Starting_Tile.png");
        private final ImageIcon TARGET_TILE = new ImageIcon("Images/Target_Tile.png");
        private final ImageIcon WALL_TILE = new ImageIcon("Images/Wall_Tile.jpg");

        public MazeTraversalButton(MazeTraversalTile tile) {
            this.tile = tile;
            setIcon(NORMAL_TILE);
        }

        public void updateImage() {
            switch (tile.getValue()) {
                case " ":
                    setIcon(NORMAL_TILE);
                case "#":
                    setIcon(WALL_TILE);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}