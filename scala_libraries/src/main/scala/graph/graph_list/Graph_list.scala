package graph.graph_list

/**
 * Graph is a class which represents a graph structure. Its implementation is based on an adjacence list which is less heavy than an adjacence matrix. One can use whatever kind of node with Graph.
 *
 * @author christophe
 */

import math._
import scala.util._
import Array._
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable

class Graph[X](nbNodes: Int) {
    var adjacence: Map[Int, ArrayBuffer[Int]] = Map()
    var nodes: Map[Int, X] = Map()

    /**
     * Add a node to the graph.
     *
     * @param key The key of the node inserted.
     * @param node The node inserted in the graph.
     */
    def addNode(key: Int, node: X) = {
        nodes += (key -> node)
        adjacence += (key -> new ArrayBuffer())
    }

    /**
     * Add an edge to the graph between two nodes.
     *
     * @param key1 The key of the first node.
     * @param key2 The key of the second node.
     * @param value Not used yet.
     */
    def addEdge(key1 :Int, key2:Int, value: Int) = {
        adjacence(key1) += (key2)
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
     * Get the keys of the nodes which are the predecessors of another one.
     *
     * @param key The key of the node whose one wants the predecessors.
     * @return The keys of the predecessor nodes.
     */
    def getPredecessors(key: Int): ArrayBuffer[Int] = {
        var predecessors: ArrayBuffer[Int] = ArrayBuffer()
        for(i <- 0 until adjacence.size) {
            if(adjacence(i).contains(key)) {
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
        while(!queue.isEmpty) {
            actualNodeKey = queue.dequeue
            markedNode += actualNodeKey
            listNodesVisited += actualNodeKey.toString + ", "
            // treat actual node here
            for(i <- getSuccessors(actualNodeKey)) if(!markedNode.contains(i) && !queue.contains(i)) queue += i
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
        while(!queue.isEmpty) {
            actualNodeKey = queue.dequeue
            for(i <- getSuccessors(actualNodeKey)) if(distances(i) == -1) {
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
    def shedTheLeaves() = {
        val leaves: ArrayBuffer[Int] = ArrayBuffer()
        adjacence.keys.foreach {i =>
            if(getSuccessors(i).isEmpty) {
                adjacence -= i
                leaves += i
            }
        }
        for(j <- 0 until leaves.size) {
            adjacence.keys.foreach {i =>
                if(adjacence(i).contains(leaves(j)))
                adjacence(i) -= leaves(j)
            }
        }
    }

    /**
     * Display all the graph. Each node is displayed with its key, predecessors and successors.
     */
    def display = adjacence.keys.foreach {i =>
        println("key : " + i + ", Node : " + adjacence(i).toString +
                ", Successors : " + getSuccessors(i).mkString(", ") +
                ", Predecessors : " + getPredecessors(i).mkString(", "))
    }
}
