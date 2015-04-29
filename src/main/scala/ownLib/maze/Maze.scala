package ownLib.maze

import ownLib.tools.Coordinate
import ownLib.graph.graphList.Graph
import java.io.FileWriter
import java.io.BufferedWriter
import java.io.BufferedReader
import java.io.FileReader
import ownLib.tools.Coordinate
import ownLib.tools.Coordinate
import ownLib.tools.Coordinate

/**
 *  Maze is a class to represent a maze with a graph design.
 */
class Maze(val graph: Graph[Int], val departure: Coordinate, val arrival: Coordinate, val width: Int, val height: Int, val name: String = "maze") {

  override def toString: String = {
    name + " " + width + " " + height + "\n" +
      departure.x + " " + departure.y + "\n" +
      arrival.x + " " + arrival.y + "\n" +
      graph.toString()
  }

  /**
   * Save the maze in a text file.
   *
   * @param filePath The file path where will be stored the maze.
   *
   * The name of the file will be the name of the maze.
   */
  def save(filePath: String = "") = {
    val writer = new BufferedWriter(new FileWriter(filePath + name))
    writer.write(toString)
    writer.close()
  }
}

object Maze {

  /**
   * Load a maze from a text file.
   *
   * @param fileName The name of the text file where a maze is saved.
   * @return The maze loaded.
   */
  def load(fileName: String): Maze = {
    val reader = new BufferedReader(new FileReader(fileName))
    val nameSizeArray = reader.readLine().split(" ")
    val name = nameSizeArray(0)
    val width = nameSizeArray(1).toInt
    val height = nameSizeArray(2).toInt
    val departureArray = reader.readLine().split(" ")
    val departure = new Coordinate(departureArray(0).toInt, departureArray(1).toInt)
    val arrivalArray = reader.readLine().split(" ")
    val arrival = new Coordinate(arrivalArray(0).toInt, arrivalArray(1).toInt)

    val nbEdgesGraph = reader.readLine.toInt
    val graphName = reader.readLine.split(" ")(1)

    val graph = new Graph[Int](graphName)
    for (i <- 0 until nbEdgesGraph) {
      val Array(key1, key2) = for (i <- reader.readLine split " -> ") yield i.toInt
      if (!graph.nodePresent(key1)) graph.addNode(key1, key1)
      if (!graph.nodePresent(key2)) graph.addNode(key2, key2)
      graph.addEdge(key1, key2, 1)
    }
    reader.close()
    new Maze(graph, departure, arrival, width, height, name)
  }
}
