package ownLib.graph.graphMatrix

import scala.Array._
import scala.collection.mutable.ArrayBuffer

class Graph[X](nbNodes: Int) {
  var nodes: Map[Int, X] = Map()
  var adjacencyMatrix = ofDim[Int](nbNodes, nbNodes)

  for (i <- 0 until nbNodes) {
    for (j <- 0 until nbNodes) {
      adjacencyMatrix(i)(j) = 0
    }
  }

  def addNode(key: Int, node: X) = nodes += (key -> node)

  def addEdge(key1: Int, key2: Int, value: Int) = adjacencyMatrix(key1)(key2) = value

  def isEmpty: Boolean = nodes.isEmpty

  def nodePresent(key: Int): Boolean = nodes.contains(key)

  def edgePresent(key1: Int, key2: Int): Boolean = adjacencyMatrix(key1)(key2) > 0

  def breadthFirstSearch(key: Int): String = {
    var queue = new scala.collection.mutable.Queue[Int]
    var markedNode: ArrayBuffer[Int] = ArrayBuffer()
    var actualNodeKey = 0
    var listNodesVisited = ""

    queue += key
    while (queue.nonEmpty) {
      actualNodeKey = queue.dequeue()
      markedNode += actualNodeKey
      listNodesVisited += actualNodeKey.toString + ", " // for debug
      // treat actual node here
      for (i <- getSuccessors(actualNodeKey)) if (!markedNode.contains(i) && !queue.contains(i)) queue += i
    }
    listNodesVisited = listNodesVisited.dropRight(2)
    listNodesVisited
  }

  def getSuccessors(key: Int): ArrayBuffer[Int] = {
    var successors: ArrayBuffer[Int] = ArrayBuffer()
    for (j <- 0 until nbNodes) {
      if (adjacencyMatrix(key)(j) > 0) successors += j
    }
    successors
  }

  def calculateEccentricityOf(key: Int): (Int, Int) = {
    var queue = new scala.collection.mutable.Queue[Int]
    var actualNodeKey = 0
    var eccentricity = 0
    var distances: scala.collection.mutable.Map[Int, Int] = scala.collection.mutable.Map()

    nodes.keys.foreach(i => distances += (i -> -1))

    distances.update(key, 0)
    queue += key
    while (queue.nonEmpty) {
      actualNodeKey = queue.dequeue()
      for (i <- getSuccessors(actualNodeKey)) if (distances(i) == -1) {
        queue += i
        distances.update(i, distances(actualNodeKey) + 1)
        eccentricity = distances(i)
      }
    }
    (key, eccentricity)
  }

  // $COVERAGE-OFF$
  def display() {
    nodes.keys.foreach { i =>
      println("key : " + i + ", Node : " + nodes(i).toString + ", Successors : " + getSuccessors(i).mkString(", ") + ", Predecessors : " + getPredecessors(i).mkString(", "))
    }
  }

  def getPredecessors(key: Int): ArrayBuffer[Int] = {
    var predecessors: ArrayBuffer[Int] = ArrayBuffer()
    for (i <- 0 until nbNodes) {
      if (adjacencyMatrix(i)(key) > 0) predecessors += i
    }
    predecessors
  }
  // $COVERAGE-ON$
}
