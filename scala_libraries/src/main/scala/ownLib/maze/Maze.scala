package ownLib.maze

import ownLib.tools.Coordinate
import ownLib.graph.graphList.Graph

/**
 *  Maze is a class to represent a maze with a graph design.
 */
class Maze(val graph: Graph[Int], val departure: Coordinate, val arrival: Coordinate, val width: Int, val height: Int) {

}
