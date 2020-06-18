import queue


def createMaze():
    maze = []
    maze.append(["#", "#", "#", "#", "#", "#", "#", "#", "X", "#"])
    maze.append(["#", " ", " ", " ", " ", " ", " ", " ", " ", "#"])
    maze.append(["#", " ", "#", " ", "#", "#", "#", "#", " ", "#"])
    maze.append(["#", " ", "#", " ", "#", " ", " ", " ", " ", "#"])
    maze.append(["#", " ", "#", " ", " ", " ", "#", "#", " ", "#"])
    maze.append(["#", " ", "#", " ", "#", "#", "#", "#", " ", "#"])
    maze.append(["#", " ", " ", " ", "#", " ", " ", " ", " ", "#"])
    maze.append(["#", " ", "#", "#", "#", " ", "#", "#", " ", "#"])
    maze.append(["#", " ", " ", " ", " ", " ", " ", " ", " ", "#"])
    maze.append(["#", "O", "#", "#", "#", "#", "#", "#", "#", "#"])
    return maze


def printMaze(maze):
    for row in range(len(maze)):
        for col in range(len(maze[row])):
            print(maze[row][col] + " ", end='')
        print()


def findStartingPoint(maze):
    for i in range(len(maze)):
        for j in range(len(maze[i])):
            if maze[i][j] == "X":
                return i, j


# Main
maze = createMaze()
printMaze(maze)
startingRow, startingCol = findStartingPoint(maze)
print("Start at (" + str(startingRow) + ", " + str(startingCol) + ")")
#breadthFirstSearch(maze, startingRow, startingCol)
