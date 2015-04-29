package ownLib.maze.tools

import ownLib.tools.Coordinate

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
  def keyToCoordinates(key: Int, mazeWidth: Int): Coordinate = new Coordinate(key % mazeWidth, key / mazeWidth)

  /**
   * Convert a square coordinate of a maze to a key node of the graph associated.
   *
   * @param coordinate The square coordinate of the maze.
   * @param mazeWidth The width of the maze.
   * @return The key node of the graph corresponding to the maze square.
   * @see Coordinate
   */
  def coordinatesToKey(coordinate: Coordinate, mazeWidth: Int): Int = mazeWidth * coordinate.y + coordinate.x
}
