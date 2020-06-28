import queue
from tkinter import *
import tkinter


class GUI:
    def __init__(self):
        root = Tk()
        root.geometry("1080x810")
        for i in range(15):
            root.rowconfigure(i, weight=1)
        for i in range(20):
            root.columnconfigure(i, weight=1)
        root.title("Path-Finding Visualizer")
        defaultImage = PhotoImage(file="Images/Normal_Tile.png")
        for i in range(15):
            for j in range(20):
                tile = Button(root, image=defaultImage)
                tile.grid(row=i, column=j)
        root.mainloop()


class Tile:
    def __init__(self, row, col, parent):
        self.row = row
        self.col = col
        self.parent = parent


def createMaze(seed):
    maze = []
    if seed == 1:
        maze.append(["#", "#", "#", "#", "#", "#",
                     "#", "#", "#", "#", "#", "#", "#"])
        maze.append(["#", " ", " ", " ", " ", " ",
                     " ", " ", "#", " ", " ", " ", "#"])
        maze.append(["#", " ", "#", " ", "#", "#",
                     "#", "#", "#", " ", "#", " ", "#"])
        maze.append(["#", " ", "#", " ", "#", " ",
                     " ", " ", "#", " ", "#", " ", "X"])
        maze.append(["#", " ", "#", " ", "#", " ",
                     "#", " ", " ", " ", "#", " ", "#"])
        maze.append(["#", "#", "#", " ", "#", "#",
                     "#", "#", "#", "#", "#", " ", "#"])
        maze.append(["#", " ", " ", " ", " ", " ",
                     "#", " ", " ", " ", "#", " ", "#"])
        maze.append(["#", " ", "#", "#", "#", " ",
                     "#", " ", "#", " ", "#", " ", "#"])
        maze.append(["#", " ", "#", " ", " ", " ",
                     " ", " ", "#", " ", " ", " ", "#"])
        maze.append(["#", "O", "#", "#", "#", "#",
                     "#", "#", "#", "#", "#", "#", "#"])
    elif seed == 2:
        maze.append(["#", "#", "#", "#", "#", "#",
                     "#", "#", "#", "#", "#", "#", "#"])
        maze.append(["#", " ", " ", " ", " ", " ",
                     " ", " ", " ", " ", " ", " ", "#"])
        maze.append(["#", "#", "#", "#", " ", " ",
                     " ", " ", " ", " ", " ", " ", "#"])
        maze.append(["#", "#", " ", "#", " ", " ",
                     " ", " ", " ", " ", " ", " ", "#"])
        maze.append(["#", "#", "X", "#", " ", " ",
                     " ", " ", " ", " ", " ", "O", "#"])
        maze.append(["#", "#", "#", "#", " ", " ",
                     " ", " ", " ", " ", " ", " ", "#"])
        maze.append(["#", " ", " ", " ", " ", " ",
                     " ", " ", " ", " ", " ", " ", "#"])
        maze.append(["#", " ", " ", " ", " ", " ",
                     " ", " ", " ", " ", " ", " ", "#"])
        maze.append(["#", " ", " ", " ", " ", " ",
                     " ", " ", " ", " ", " ", " ", "#"])
        maze.append(["#", "#", "#", "#", "#", "#",
                     "#", "#", "#", "#", "#", "#", "#"])

    isDiscovered = [False] * len(maze)
    for row in range(len(isDiscovered)):
        isDiscovered[row] = [False] * len(maze[row])

    for row in range(len(isDiscovered)):
        for col in range(len(isDiscovered[row])):
            if maze[row][col] == "#":
                isDiscovered[row][col] = True
    return maze, isDiscovered


def printMaze(maze):
    for row in range(len(maze)):
        for col in range(len(maze[row])):
            print(str(maze[row][col]) + " ", end="")
        print()


def findStartingPoint(maze):
    for i in range(len(maze)):
        for j in range(len(maze[i])):
            if maze[i][j] == "X":
                return i, j


def getAvailableAdjacent(maze, tile):
    availableAdj = []
    availableAdj.append(Tile(tile.row-1, tile.col, tile))
    availableAdj.append(Tile(tile.row, tile.col-1, tile))
    availableAdj.append(Tile(tile.row, tile.col+1, tile))
    availableAdj.append(Tile(tile.row+1, tile.col, tile))

    i = 0
    # Filters adjacent tiles to the only valid AND open adjacent tiles
    while i < len(availableAdj):
        if availableAdj[i].row < 0 or availableAdj[i].row == len(maze):
            availableAdj.pop(i)
            i -= 1
        elif availableAdj[i].col < 0 or availableAdj[i].col == len(maze[i]):
            availableAdj.pop(i)
            i -= 1

        if maze[availableAdj[i].row][availableAdj[i].col] == "#":
            availableAdj.pop(i)
            i -= 1
        i += 1

    return availableAdj


def debug(bfsQueue):
    while not bfsQueue.empty:
        print(str(bfsQueue.get()), end=" -> ")


def breadthFirstSearch(maze, discovered, startingTile):
    bfsQueue = queue.Queue()
    discovered[startingTile.row][startingTile.col] = True
    bfsQueue.put(startingTile)
    # debug(bfsQueue)
    while bfsQueue.qsize() > 0:
        parentTile = bfsQueue.get()
        if maze[parentTile.row][parentTile.col] == "O":  # End has been found
            i = 0
            while parentTile.parent != None:
                maze[parentTile.row][parentTile.col] = "+"
                parentTile = parentTile.parent
                i += 1
            return i
        adjacentTiles = getAvailableAdjacent(maze, parentTile)
        for i in range(len(adjacentTiles)):
            currentTile = adjacentTiles[i]
            if not discovered[currentTile.row][currentTile.col]:
                discovered[currentTile.row][currentTile.col] = True
                bfsQueue.put(currentTile)


def main():
    gui = GUI()
    # maze, discovered = createMaze(2)
    # printMaze(maze)
    # startingRow, startingCol = findStartingPoint(maze)
    # startingTile = Tile(startingRow, startingCol, None)
    # print("Start at (" + str(startingTile.row) + ", " + str(startingTile.col) + ")")
    # distance = breadthFirstSearch(maze, discovered, startingTile)
    # printMaze(maze)
    # if distance == None:
    #     print("Unable to find path!")
    # else:
    #     print("Distance from starting point: " + str(distance) + " tiles")


main()
