package ownLib.maze

import org.scalatest.{FlatSpec, Matchers}
import ownLib.graph.graphList.Graph
import ownLib.maze.tools.Conversion
import ownLib.tools.Coordinate

import scala.collection.mutable.ArrayBuffer

class MazeSolver$Test extends FlatSpec with Matchers {

  "A MazeSolver" should "resolves a maze" in {
    val matrix: Array[Array[Int]] = Array(Array(0, 1, 1, 1), Array(0, 0, 0, 1), Array(1, 1, 0, 0))
    val graph: Graph[Int] = Conversion.matrixToGraph(matrix)
    val maze: Maze = new Maze(graph, new Coordinate(0, 0), new Coordinate(3, 2), 4, 3)

    val solution: ArrayBuffer[Int] = MazeSolver.solveMaze(maze)

    solution.size should be(5)
    solution should contain allOf(4, 5, 6, 10, 11)
  }

}
