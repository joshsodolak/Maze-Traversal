import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeTraversalPanel extends JPanel implements KeyListener {
    private final MazeTraversalFrame frame;
    private final MazeTraversalButton[][] buttons;

    public MazeTraversalPanel(final MazeTraversalFrame frame) {
        this.frame = frame;
        buttons = new MazeTraversalButton[frame.getModel().getNumRows()][frame.getModel().getNumColumns()];
        setLayout(new GridLayout(frame.getModel().getNumRows(), frame.getModel().getNumColumns()));
        for (int i = 0; i < frame.getModel().getNumRows(); i++) {
            for (int j = 0; j < frame.getModel().getNumColumns(); j++) {
                final MazeTraversalButton button = new MazeTraversalButton(new MazeTraversalTile(i, j));
                buttons[i][j] = button;
                if (i == frame.getModel().getNumRows() / 2 && j == (frame.getModel().getNumColumns() / 2) - 2) {
                    frame.getModel().getTile(i, j).setValue("O");
                    // button.tile.setValue("O");
                    button.setImage("Start");
                } else if (i == frame.getModel().getNumRows() / 2 && j == (frame.getModel().getNumColumns() / 2) + 2) {
                    frame.getModel().getTile(i, j).setValue("X");
                    // button.tile.setValue("X");
                    button.setImage("Target");
                }
                add(button);
                button.addKeyListener(this);
                button.addMouseListener(new MouseAdapter() {
                    public void mousePressed(final MouseEvent e) {
                        switch (frame.getModel().getTile(button.tile.getRow(), button.tile.getColumn()).getValue()) {
                            case " ":
                                frame.getModel().getTile(button.tile.getRow(), button.tile.getColumn()).setValue("#");
                                button.setImage("Wall");
                                break;
                            case "#":
                                frame.getModel().getTile(button.tile.getRow(), button.tile.getColumn()).setValue(" ");
                                button.setImage("Normal");
                                break;
                            case "O":
                                break;
                            case "X":
                                break;
                            default:
                                break;
                        }
                        frame.getModel().printMaze();
                        System.out.println();
                    }
                });
            }
        }
    }

    private class MazeTraversalButton extends JButton {
        private final MazeTraversalTile tile;
        private ImageIcon NORMAL_TILE = new ImageIcon("Images/Normal_Tile.png");
        private ImageIcon STARTING_TILE = new ImageIcon("Images/Starting_Tile.png");
        private ImageIcon TARGET_TILE = new ImageIcon("Images/Target_Tile.png");
        private ImageIcon WALL_TILE = new ImageIcon("Images/Wall_Tile.png");
        private ImageIcon PATH_TILE = new ImageIcon("Images/Path_Tile.png");

        public MazeTraversalButton(final MazeTraversalTile tile) {
            this.tile = tile;
            setIcon(NORMAL_TILE);
        }

        public void setImage(String name) {
            switch (name) {
                case "Normal":
                    setIcon(NORMAL_TILE);
                    break;
                case "Wall":
                    setIcon(WALL_TILE);
                    break;
                case "Start":
                    setIcon(STARTING_TILE);
                    break;
                case "Target":
                    setIcon(TARGET_TILE);
                    break;
                case "Path":
                    setIcon(PATH_TILE);
                    break;
                default:
                    setIcon(NORMAL_TILE);
                    break;
            }
        }
    }

    private void resetMaze() {
        for (int i = 0; i < frame.getModel().getNumRows(); i++) {
            for (int j = 0; j < frame.getModel().getNumColumns(); j++) {
                if (frame.getModel().getTile(i, j).getValue().equals("+")) {
                    frame.getModel().getTile(i, j).setValue(" ");
                    buttons[i][j].setImage("Normal");
                }
            }
        }
        frame.getModel().resetDiscoveredTiles();
    }

    private void paintPath() {
        for (int i = 0; i < frame.getModel().getNumRows(); i++) {
            for (int j = 0; j < frame.getModel().getNumColumns(); j++) {
                if (frame.getModel().getTile(i, j).getValue().equals("+")) {
                    buttons[i][j].setImage("Path");
                }
            }
        }
    }

    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
    }

    public void keyTyped(final KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("Distance: " + frame.getModel().breadthFirstSearch());
            paintPath();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("Resetting... ");
            resetMaze();
            System.out.println("Done");
        }
    }

    public void keyReleased(final KeyEvent e) {
        // TODO Auto-generated method stub

    }
}