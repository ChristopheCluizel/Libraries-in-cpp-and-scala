package ownLib.maze.tools

import ownLib.tools.Coordinate

object Conversion {

  def keyToCoordinates(key: Int, graphWidth: Int): Coordinate = new Coordinate(key % graphWidth, key / graphWidth)

  def coordinatesToKey(x: Int, y: Int, graphWidth: Int): Int = graphWidth * y + x
}
