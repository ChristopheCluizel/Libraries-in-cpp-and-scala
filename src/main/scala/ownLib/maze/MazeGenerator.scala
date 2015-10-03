package ownLib.maze

import ownLib.graph.graphList.Graph
import ownLib.maze.tools.Conversion
import ownLib.tools.Coordinate

import scala.collection.mutable.ArrayBuffer
import scala.util._

object MazeGenerator {

  def generateMaze(width: Int, height: Int, name: String = "maze"): Maze = {

    val graph = new Graph[Int]()
    val neighbours = Array.ofDim[Boolean](height, width)
    for (i <- 0 until height; j <- 0 until width) neighbours(i)(j) = false
    val rand = new Random()
    val departure = new Coordinate(width / 2, height / 2)
    var arrival: Coordinate = null
    // avoid to have the same departure and arrival square
    do {
      arrival = new Coordinate(rand.nextInt(width), rand.nextInt(height))
    } while (arrival == departure)

    val stack = new scala.collection.mutable.Stack[Int]
    var markedNode: ArrayBuffer[Int] = ArrayBuffer()

    var actualNodeKey = Conversion.coordinateToKey(departure, width)
    markedNode += actualNodeKey
    neighbours(Conversion.keyToCoordinate(actualNodeKey, width).y)(Conversion.keyToCoordinate(actualNodeKey, width).x) = true

    while (markedNode.length < width * height) {
      val falseNeighbours = getFalseNeighbours(actualNodeKey, neighbours, width, height)
      if (!falseNeighbours.isEmpty) {
        val randomFalseNeighbour = getRandomFalseSquareInArray(falseNeighbours)
        stack push actualNodeKey
        removeWallBetween(graph, actualNodeKey, randomFalseNeighbour)
        actualNodeKey = randomFalseNeighbour
        markedNode += actualNodeKey
        neighbours(Conversion.keyToCoordinate(actualNodeKey, width).y)(Conversion.keyToCoordinate(actualNodeKey, width).x) = true
      } else if (stack.nonEmpty) {
        actualNodeKey = stack.head
        stack.pop()
      } else {
        val areaSquareKey = getRandomFalseSquareInDoubleArray(neighbours, width, height)
        actualNodeKey = areaSquareKey
      }
    }
    new Maze(graph, departure, arrival, width, height, name)
  }

  private def getFalseNeighbours(actualNodeKey: Int, neighbours: Array[Array[Boolean]], width: Int, height: Int): Array[Int] = {
    val position = Conversion.keyToCoordinate(actualNodeKey, width)
    var minY = position.y - 1
    var maxY = position.y + 1
    var minX = position.x - 1
    var maxX = position.x + 1
    if (position.y == 0) minY = 0
    if (position.y == height - 1) maxY = height - 1
    if (position.x == 0) minX = 0
    if (position.x == width - 1) maxX = width - 1
    val falseNeighbours = for (i <- minY to maxY; j <- minX to maxX if !neighbours(i)(j) && (i == position.y || j == position.x) && !(i == position.y && j == position.x)) yield Conversion.coordinateToKey(new Coordinate(j, i), width)
    falseNeighbours.toArray
  }

  private def removeWallBetween(graph: Graph[Int], actualNodeKey: Int, randomFalseNeighbour: Int) = {
    if (!graph.nodePresent(actualNodeKey)) graph.addNode(actualNodeKey, actualNodeKey)
    if (!graph.nodePresent(randomFalseNeighbour)) graph.addNode(randomFalseNeighbour, randomFalseNeighbour)

    graph.addEdge(actualNodeKey, randomFalseNeighbour, 1)
    graph.addEdge(randomFalseNeighbour, actualNodeKey, 1)
  }

  private def getRandomFalseSquareInDoubleArray(neighbours: Array[Array[Boolean]], width: Int, height: Int): Int = {
    val falseSquares = for (i <- 0 until height; j <- 0 until width if !neighbours(i)(j)) yield Conversion.coordinateToKey(new Coordinate(j, i), width)
    getRandomFalseSquareInArray(falseSquares.toArray)
  }

  private def getRandomFalseSquareInArray(falseNeighbours: Array[Int]): Int = {
    val rand = new Random()
    val areaNumber = rand.nextInt(falseNeighbours.length)
    falseNeighbours(areaNumber)
  }
}
