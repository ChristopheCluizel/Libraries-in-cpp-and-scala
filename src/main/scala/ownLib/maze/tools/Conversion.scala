package ownLib.maze.tools

import ownLib.graph.graphList.Graph
import ownLib.tools.Coordinate

import scala.collection.mutable.ArrayBuffer
import scala.math.{max, min}

/**
 * This object allows to make conversions between different types.
 */
object Conversion {

  /**
   * Convert a key node of a graph to a coordinate square of the maze associated.
   *
   * @param key The key node of a graph.
   * @param mazeWidth The width of the maze.
   * @return The coordinate of the maze square corresponding to the key node of the graph.
   * @see Coordinate
   */
  def keyToCoordinate(key: Int, mazeWidth: Int): Coordinate = new Coordinate(key % mazeWidth, key / mazeWidth)

  /**
   * Convert a matrix to a graph.
   *
   * @param matrix The matrix to convert.
   * @return The graph corresponding to the matrix.
   */
  def matrixToGraph(matrix: Array[Array[Int]]): Graph[Int] = {
    val nbRows = matrix.length
    val nbColumns = matrix(0).length
    val graph = new Graph[Int]

    for (i <- 0 until nbRows; j <- 0 until nbColumns) {
      val actualCoord = new Coordinate(j, i)
      val actualKey = coordinateToKey(actualCoord, nbColumns)
      graph.addNode(actualKey, 1)
      if (matrix(i)(j) == 0) {
        val neighbourKeys = findNeighbours(matrix, nbRows, nbColumns, actualCoord)
        neighbourKeys.foreach { key =>
          graph.addNode(key, 1)
          graph.addEdge(actualKey, key, 1)
        }
      }
    }
    graph
  }

  /**
   * Find the neighbours of a square which are not a wall (so equal 0).
   *
   * @param matrix The matrix of squares.
   * @param nbRows The number of rows of the matrix.
   * @param nbColumns The number of columns of the matrix.
   * @param square The coordinate of the square we search its neighbours.
   * @return The list of the keys for null neighbours next to the square.
   */
  def findNeighbours(matrix: Array[Array[Int]], nbRows: Int, nbColumns: Int, square: Coordinate): ArrayBuffer[Int] = {
    val fourNeighbourCoordinates = ArrayBuffer[Coordinate]()
    val neighbourKeys = ArrayBuffer[Int]()

    fourNeighbourCoordinates += new Coordinate(square.x, max(0, square.y - 1))
    fourNeighbourCoordinates += new Coordinate(square.x, min(nbRows - 1, square.y + 1))
    fourNeighbourCoordinates += new Coordinate(max(0, square.x - 1), square.y)
    fourNeighbourCoordinates += new Coordinate(min(square.x + 1, nbColumns - 1), square.y)

    for (coord <- fourNeighbourCoordinates) {
      if (matrix(coord.y)(coord.x) == 0 && !(coord == square)) neighbourKeys += coordinateToKey(coord, nbColumns)
    }
    neighbourKeys
  }

  /**
   * Convert a square coordinate of a maze to a key node of the graph associated.
   *
   * @param coordinate The square coordinate of the maze.
   * @param mazeWidth The width of the maze.
   * @return The key node of the graph corresponding to the maze square.
   * @see Coordinate
   */
  def coordinateToKey(coordinate: Coordinate, mazeWidth: Int): Int = mazeWidth * coordinate.y + coordinate.x
}
