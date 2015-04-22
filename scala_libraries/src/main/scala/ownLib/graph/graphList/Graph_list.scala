package ownLib.graph.graphList

import math._
import scala.util._
import Array._
import scala.collection.mutable.ArrayBuffer

/**
 * Graph is a class which represents a graph structure. Its implementation is based on an adjacence list which is less heavy than an adjacence matrix. One can use whatever kind of node with Graph.
 *
 * @author christophe
 *
 * @param name The name of the graph.
 * @param <T> Enable any kind of node.
 */
class Graph[T](val name: String = "graph") {

  /**
   * Implementation of the adjacence list. The key corresponds to the key of a father node and the value to an array containing the key of the successor nodes of this father node.
   */
  var adjacence: Map[Int, ArrayBuffer[Int]] = Map()

  /**
   * Nodes is a list of all the nodes of the graph. It associates a key to any kind of node T.
   */
  var nodes: Map[Int, T] = Map()

  /**
   * Add a node to the graph.
   *
   * @param key The key of the node inserted.
   * @param node The node inserted in the graph.
   */
  def addNode(key: Int, node: T) = {
    nodes += (key -> node)
    adjacence += (key -> new ArrayBuffer())
  }

  /**
   * Remove a node of the graph.
   *
   * @param key The key of the node to remove.
   */
  def removeNode(key: Int) = {
    nodes -= key
    adjacence -= key
    for (i <- adjacence.keys) if (adjacence(i).contains(key)) adjacence(i) -= key
  }

  /**
   * Add an edge to the graph between two nodes.
   *
   * @param key1 The key of the first node.
   * @param key2 The key of the second node.
   * @param value Not used yet.
   */
  def addEdge(key1: Int, key2: Int, value: Int) = {
    adjacence(key1) += (key2)
  }

  /**
   * Remove an edge of the graph between two nodes.
   *
   * @param key1 The key of the first node.
   * @param key2 The key of the second node.
   */
  def removeEdge(key1: Int, key2: Int) = {
    if (adjacence(key1).contains(key2)) adjacence(key1) -= key2
    if (adjacence(key2).contains(key1)) adjacence(key2) -= key1
  }

  /**
   * Indicate if the graph is empty.
   *
   * @return Return whether the graph is empty or not.
   */
  def isEmpty: Boolean = adjacence.isEmpty

  /**
   * Indicate if a node is present in the graph.
   *
   * @param key The key of the node considered.
   * @return Return whether the node is present or not.
   */
  def nodePresent(key: Int): Boolean = adjacence.contains(key)

  /**
   * Indicate if an edge is present between two nodes in the graph.
   *
   * @param key1 The key of the first node.
   * @param key2 The key of the second node.
   * @return Return whether the edge is present or not.
   */
  def edgePresent(key1: Int, key2: Int): Boolean = adjacence(key1).contains(key2)

  /**
   * Indicate the number of nodes in the graph.
   *
   * @return The number of nodes.
   */
  def numberOfNode: Int = {
    nodes.size
  }

  /**
   * Get the keys of the nodes which are the predecessors of another one.
   *
   * @param key The key of the node whose one wants the predecessors.
   * @return The keys of the predecessor nodes.
   */
  def getPredecessors(key: Int): ArrayBuffer[Int] = {
    var predecessors: ArrayBuffer[Int] = ArrayBuffer()
    for (i <- adjacence.keys) {
      if (adjacence(i).contains(key)) {
        predecessors += i
      }
    }
    predecessors
  }
  /**
   * Get the keys of the nodes which are the successors of another one.
   *
   * @param key The key of the node whose one wants the successors.
   * @return The keys of the successor nodes.
   */
  def getSuccessors(key: Int): ArrayBuffer[Int] = adjacence(key)

  /**
   * Scan the graph by breadth first search.
   *
   * @param key The key of the start node.
   * @return A string of the nodes crossed, sorted by cross order.
   */
  def breadthFirstSearch(key: Int): String = {
    var queue = new scala.collection.mutable.Queue[Int]
    var markedNode: ArrayBuffer[Int] = ArrayBuffer()
    var actualNodeKey = 0
    var listNodesVisited = ""

    queue += key
    while (!queue.isEmpty) {
      actualNodeKey = queue.dequeue
      markedNode += actualNodeKey
      listNodesVisited += actualNodeKey.toString + ", "
      // treat actual node here
      for (i <- getSuccessors(actualNodeKey)) if (!markedNode.contains(i) && !queue.contains(i)) queue += i
    }
    listNodesVisited = listNodesVisited.dropRight(2)
    listNodesVisited
  }

  /**
   * Calculate the eccentricity of a node. The eccentricity is the longest distance between a node and all the other ones.
   *
   * @param key The key of the node whose one wants to calculate the eccentricity.
   * @return The value of the eccentricity of the node.
   */
  def calculateEccentricityOf(key: Int): Int = {
    var queue = new scala.collection.mutable.Queue[Int]
    var markedNode: ArrayBuffer[Int] = ArrayBuffer()
    var actualNodeKey = 0
    var eccentricity = 0
    var distances: scala.collection.mutable.Map[Int, Int] = scala.collection.mutable.Map()

    adjacence.keys.foreach(i => distances += (i -> -1))

    distances.update(key, 0)
    queue += key
    while (!queue.isEmpty) {
      actualNodeKey = queue.dequeue
      for (i <- getSuccessors(actualNodeKey)) if (distances(i) == -1) {
        queue += i
        distances.update(i, distances(actualNodeKey) + 1)
        eccentricity = distances(i)
      }
    }
    eccentricity
  }

  /**
   * Delete all the leaves of the graph. A leaf is a node which doesn't have any successors. This method has sense only for an oriented-graph.
   */
  def shedTheLeaves() = for (i <- adjacence.keys) if (getSuccessors(i).isEmpty) removeNode(i)

  /**
   * Display all the graph. Each node is displayed with its key, predecessors and successors.
   */
  def display = adjacence.keys.foreach { i =>
    println("key : " + i + ", Node : " + adjacence(i).toString +
      ", Successors : " + getSuccessors(i).mkString(", ") +
      ", Predecessors : " + getPredecessors(i).mkString(", "))
  }
}
