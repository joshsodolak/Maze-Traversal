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
        private final ImageIcon NORMAL_TILE = new ImageIcon("Images/Normal_Tile.png");
        private final ImageIcon STARTING_TILE = new ImageIcon("Images/Starting_Tile.png");
        private final ImageIcon TARGET_TILE = new ImageIcon("Images/Target_Tile.png");
        private final ImageIcon WALL_TILE = new ImageIcon("Images/Wall_Tile.png");
        private final ImageIcon PATH_TILE = new ImageIcon("Images/Path_Tile.png");
        private final ImageIcon PATHSTARTING_TILE = new ImageIcon("Images/PathStarting_Tile.png");
        private final ImageIcon PATHTARGET_TILE = new ImageIcon("Images/PathTarget_Tile.png");
        private final ImageIcon NOPATHSTARTING_TILE = new ImageIcon("Images/NoPathStarting_Tile.png");
        private final ImageIcon NOPATHTARGET_TILE = new ImageIcon("Images/NoPathTarget_Tile.png");

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
                case "Path Starting":
                    setIcon(PATHSTARTING_TILE);
                    break;
                case "Path Target":
                    setIcon(PATHTARGET_TILE);
                    break;
                case "No Path Starting":
                    setIcon(NOPATHSTARTING_TILE);
                    break;
                case "No Path Target":
                    setIcon(NOPATHTARGET_TILE);
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
                } else if (frame.getModel().getTile(i, j).getValue().equals("O")) {
                    buttons[i][j].setImage("Start");
                } else if (frame.getModel().getTile(i, j).getValue().equals("X")) {
                    buttons[i][j].setImage("Target");
                }
            }
        }
        frame.getModel().resetDiscoveredTiles();
    }

    private void paintPath(int distance) {
        for (int i = 0; i < frame.getModel().getNumRows(); i++) {
            for (int j = 0; j < frame.getModel().getNumColumns(); j++) {
                if (frame.getModel().getTile(i, j).getValue().equals("+")) {
                    buttons[i][j].setImage("Path");
                } else if (frame.getModel().getTile(i, j).getValue().equals("O")) {
                    if (distance == -1) {
                        buttons[i][j].setImage("No Path Starting");
                    } else {
                        buttons[i][j].setImage("Path Starting");
                    }
                } else if (frame.getModel().getTile(i, j).getValue().equals("X")) {
                    if (distance == -1) {
                        buttons[i][j].setImage("No Path Target");
                    } else {
                        buttons[i][j].setImage("Path Target");
                    }
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
            int distance = frame.getModel().breadthFirstSearch();
            System.out.println("Distance: " + distance);
            paintPath(distance);
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