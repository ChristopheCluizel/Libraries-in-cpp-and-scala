package ownLib.maze

import scala.util._
import java.io.BufferedWriter
import java.io.BufferedReader
import java.io.Reader
import java.io.Writer
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import scala.collection.mutable.ArrayBuffer
import ownLib.graph.graphList.Graph
import ownLib.tools.Coordinate
import ownLib.maze.tools.Conversion
import ownLib.tools.Coordinate

object MazeGenerator {

  def generateMaze(width: Int, height: Int): Maze = {

    val graph = new Graph[Int]("graph")
    val neighbours = Array.ofDim[Boolean](height, width)
    for (i <- 0 until height; j <- 0 until width) neighbours(i)(j) = false
    val rand = new Random();
    //val departure = new Coordinate(rand.nextInt(width), rand.nextInt(height))
    val departure = new Coordinate(0, 0)
    val arrival = new Coordinate(width / 2, height / 2)

    var stack = new scala.collection.mutable.Stack[Int]
    var markedNode: ArrayBuffer[Int] = ArrayBuffer()

    var actualNodeKey = Conversion.coordinatesToKey(departure, width)
    markedNode += actualNodeKey
    neighbours(Conversion.keyToCoordinates(actualNodeKey, width).y)(Conversion.keyToCoordinates(actualNodeKey, width).x) = true

    while (markedNode.length < width * height) {
      val falseNeighbours = getFalseNeighbours(actualNodeKey, neighbours, width, height)
      if (!falseNeighbours.isEmpty) {
        val randomFalseNeighbour = getRandomFalseSquareInArray(falseNeighbours)
        stack push actualNodeKey
        removeWallBetween(graph, actualNodeKey, randomFalseNeighbour)
        actualNodeKey = randomFalseNeighbour
        markedNode += actualNodeKey
        neighbours(Conversion.keyToCoordinates(actualNodeKey, width).y)(Conversion.keyToCoordinates(actualNodeKey, width).x) = true
      } else if (!stack.isEmpty) {
        actualNodeKey = stack.head
        stack.pop
      } else {
        val aleaSquareKey = getRandomFalseSquareInDoubleArray(neighbours, width, height)
        actualNodeKey = aleaSquareKey
      }
    }
    return new Maze(graph, arrival, departure, width, height)
  }

  private def getFalseNeighbours(actualNodeKey: Int, neighbours: Array[Array[Boolean]], width: Int, height: Int): Array[Int] = {
    val position = Conversion.keyToCoordinates(actualNodeKey, width)
    var minY = position.y - 1
    var maxY = position.y + 1
    var minX = position.x - 1
    var maxX = position.x + 1
    if (position.y == 0) minY = 0
    if (position.y == height - 1) maxY = height - 1
    if (position.x == 0) minX = 0
    if (position.x == width - 1) maxX = width - 1
    val voisins = for (i <- minY to maxY; j <- minX to maxX if (neighbours(i)(j) == false && (i == position.y || j == position.x) && !(i == position.y && j == position.x))) yield Conversion.coordinatesToKey(new Coordinate(j, i), width)
    return voisins.toArray
  }

  private def getRandomFalseSquareInArray(falseNeighbours: Array[Int]): Int = {
    val rand = new Random();
    val aleaNumber = rand.nextInt(falseNeighbours.length)
    return falseNeighbours(aleaNumber)
  }

  private def removeWallBetween(graph: Graph[Int], actualNodeKey: Int, randomFalseNeighbour: Int) = {
    if (!graph.nodePresent(actualNodeKey)) graph.addNode(actualNodeKey, actualNodeKey)
    if (!graph.nodePresent(randomFalseNeighbour)) graph.addNode(randomFalseNeighbour, randomFalseNeighbour)

    graph.addEdge(actualNodeKey, randomFalseNeighbour, 1)
    graph.addEdge(randomFalseNeighbour, actualNodeKey, 1)
  }

  private def getRandomFalseSquareInDoubleArray(neighbours: Array[Array[Boolean]], width: Int, height: Int): Int = {
    val falseSquares = for (i <- 0 until height; j <- 0 until width if (neighbours(i)(j) == false)) yield Conversion.coordinatesToKey(new Coordinate(j, i), width)
    return getRandomFalseSquareInArray(falseSquares.toArray)
  }
}
