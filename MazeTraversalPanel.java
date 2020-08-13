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
    private String pressedTileValue;
    private boolean mousePressed;

    public MazeTraversalPanel(final MazeTraversalFrame frame) {
        this.frame = frame;
        buttons = new MazeTraversalButton[frame.getModel().getNumRows()][frame.getModel().getNumColumns()];
        pressedTileValue = "";
        mousePressed = false;

        setLayout(new GridLayout(frame.getModel().getNumRows(), frame.getModel().getNumColumns()));
        for (int i = 0; i < frame.getModel().getNumRows(); i++) {
            for (int j = 0; j < frame.getModel().getNumColumns(); j++) {
                final MazeTraversalButton button = new MazeTraversalButton(i, j);
                buttons[i][j] = button;
                if (i == frame.getModel().getNumRows() / 2 && j == (frame.getModel().getNumColumns() / 2) - 2) {
                    frame.getModel().setTileValue(i, j, "O");
                    button.setImage("Start");
                } else if (i == frame.getModel().getNumRows() / 2 && j == (frame.getModel().getNumColumns() / 2) + 2) {
                    frame.getModel().setTileValue(i, j, "X");
                    button.setImage("Target");
                }
                add(button);
                button.addKeyListener(this);
                button.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        if (mousePressed) {
                            switch (pressedTileValue) {
                                case "O":
                                    for (int i = 0; i < frame.getModel().getNumRows(); i++) {
                                        for (int j = 0; j < frame.getModel().getNumColumns(); j++) {
                                            if (frame.getModel().getTileValue(i, j).equals("O")) {
                                                frame.getModel().setTileValue(i, j, " ");
                                                buttons[i][j].setImage("Normal");
                                            }
                                        }
                                    }
                                    frame.getModel().setTileValue(button.getRow(), button.getColumn(), "O");
                                    button.setImage("Start");
                                    break;
                                case "X":
                                    for (int i = 0; i < frame.getModel().getNumRows(); i++) {
                                        for (int j = 0; j < frame.getModel().getNumColumns(); j++) {
                                            if (frame.getModel().getTileValue(i, j).equals("X")) {
                                                frame.getModel().setTileValue(i, j, " ");
                                                buttons[i][j].setImage("Normal");
                                            }
                                        }
                                    }
                                    frame.getModel().setTileValue(button.getRow(), button.getColumn(), "X");
                                    button.setImage("Target");
                                    break;
                                case " ":
                                    if (frame.getModel().getTileValue(button.getRow(), button.getColumn())
                                            .equals(" ")) {
                                        frame.getModel().setTileValue(button.getRow(), button.getColumn(), "#");
                                        button.setImage("Wall");
                                    }
                                    break;
                                case "#":
                                    if (frame.getModel().getTileValue(button.getRow(), button.getColumn())
                                            .equals("#")) {
                                        frame.getModel().setTileValue(button.getRow(), button.getColumn(), " ");
                                        button.setImage("Normal");
                                        break;
                                    }
                            }
                        }
                    }

                    public void mousePressed(MouseEvent e) {
                        mousePressed = true;
                        pressedTileValue = frame.getModel().getTileValue(button.getRow(), button.getColumn());
                        switch (pressedTileValue) {
                            case " ":
                                frame.getModel().setTileValue(button.getRow(), button.getColumn(), "#");
                                button.setImage("Wall");
                                break;
                            case "#":
                                frame.getModel().setTileValue(button.getRow(), button.getColumn(), " ");
                                button.setImage("Normal");
                                break;
                        }
                    }

                    public void mouseReleased(MouseEvent e) {
                        switch (pressedTileValue) {
                            case " ":
                                button.setImage("Wall");
                                break;
                            case "#":
                                button.setImage("Normal");
                                break;
                        }
                        pressedTileValue = "";
                        mousePressed = false;
                    }
                });
            }
        }
    }

    private class MazeTraversalButton extends JButton {
        private int row;
        private int column;
        private final ImageIcon NORMAL_TILE = new ImageIcon("Images/Normal_Tile.png");
        private final ImageIcon STARTING_TILE = new ImageIcon("Images/Starting_Tile.png");
        private final ImageIcon TARGET_TILE = new ImageIcon("Images/Target_Tile.png");
        private final ImageIcon WALL_TILE = new ImageIcon("Images/Wall_Tile.png");
        private final ImageIcon PATH_TILE = new ImageIcon("Images/Path_Tile.png");
        private final ImageIcon PATHSTARTING_TILE = new ImageIcon("Images/PathStarting_Tile.png");
        private final ImageIcon PATHTARGET_TILE = new ImageIcon("Images/PathTarget_Tile.png");
        private final ImageIcon NOPATHSTARTING_TILE = new ImageIcon("Images/NoPathStarting_Tile.png");
        private final ImageIcon NOPATHTARGET_TILE = new ImageIcon("Images/NoPathTarget_Tile.png");

        public MazeTraversalButton(int row, int column) {
            this.row = row;
            this.column = column;
            setIcon(NORMAL_TILE);
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
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
                if (frame.getModel().getTileValue(i, j).equals("+")) {
                    frame.getModel().setTileValue(i, j, " ");
                    buttons[i][j].setImage("Normal");
                } else if (frame.getModel().getTileValue(i, j).equals("O")) {
                    buttons[i][j].setImage("Start");
                } else if (frame.getModel().getTileValue(i, j).equals("X")) {
                    buttons[i][j].setImage("Target");
                }
            }
        }
        frame.getModel().resetDiscoveredTiles();
    }

    private void paintPath(int distance) {
        for (int i = 0; i < frame.getModel().getNumRows(); i++) {
            for (int j = 0; j < frame.getModel().getNumColumns(); j++) {
                if (frame.getModel().getTileValue(i, j).equals("+")) {
                    buttons[i][j].setImage("Path");
                } else if (frame.getModel().getTileValue(i, j).equals("O")) {
                    if (distance == -1) {
                        buttons[i][j].setImage("No Path Starting");
                    } else {
                        buttons[i][j].setImage("Path Starting");
                    }
                } else if (frame.getModel().getTileValue(i, j).equals("X")) {
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