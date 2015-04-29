package ownLib.maze

import ownLib.tools.Coordinate
import ownLib.maze.tools.Conversion
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

object MazeSolver {

  def solveMaze(maze: Maze): ArrayBuffer[Int] = {
    val graph = maze.graph
    val departureKey = Conversion.coordinatesToKey(maze.departure, maze.width)
    val arrivalKey = Conversion.coordinatesToKey(maze.arrival, maze.width)
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
}
