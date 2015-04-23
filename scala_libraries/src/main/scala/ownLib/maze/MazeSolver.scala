package ownLib.maze

import ownLib.tools.Coordinate
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

object MazeSolver {

  def solveMaze(maze: Maze): ArrayBuffer[Int] = {
    val graph = maze.graph
    val departureKey = coordinatesToKey(maze.departure.x, maze.departure.y, maze.width)
    val arrivalKey = coordinatesToKey(maze.arrival.x, maze.arrival.y, maze.width)
    var queue = new scala.collection.mutable.Queue[Int]
    var markedNode: ArrayBuffer[Int] = ArrayBuffer()
    var actualNodeKey = 0
    var fathers: Map[Int, Int] = Map()
    var idZoneFather = 0
    var path: ArrayBuffer[Int] = ArrayBuffer()

    queue += departureKey
    breakable {
      while (!queue.isEmpty) {
        actualNodeKey = queue.dequeue
        markedNode += actualNodeKey

        if (actualNodeKey == arrivalKey) {
          idZoneFather = actualNodeKey
          while (fathers(idZoneFather) != departureKey) {
            idZoneFather = fathers(idZoneFather)
            path += idZoneFather
          }
          break
        }

        for (i <- graph.getSuccessors(actualNodeKey)) {
          if (!markedNode.contains(i) && !queue.contains(i)) {
            queue += i
            fathers += (i -> actualNodeKey)
          }
        }
      }
    }
    path.reverse += arrivalKey
  }

  def keyToCoordinates(key: Int, graphWidth: Int): Coordinate = new Coordinate(key % graphWidth, key / graphWidth)

  def coordinatesToKey(x: Int, y: Int, graphWidth: Int): Int = graphWidth * y + x
}
