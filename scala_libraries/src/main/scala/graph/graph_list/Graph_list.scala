package graph.graph_list

import math._
import scala.util._
import Array._
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable

class Graph[X](nbNodes: Int) {
    var adjacence: Map[Int, ArrayBuffer[Int]] = Map()

    def addNode(key: Int, node: X) = {
        adjacence += (key -> new ArrayBuffer())
    }
    def addEdge(key1 :Int, key2:Int, value: Int) = {
        adjacence(key1) += (key2)
    }
    def isEmpty: Boolean = adjacence.isEmpty
    def nodePresent(key: Int): Boolean = adjacence.contains(key)
    def edgePresent(key1: Int, key2: Int): Boolean = adjacence(key1).contains(key2)
    def getPredecessors(key: Int): ArrayBuffer[Int] = {
        var predecessors: ArrayBuffer[Int] = ArrayBuffer()
        for(i <- 0 until adjacence.size) {
            if(adjacence(i).contains(key)) {
                predecessors += i
            }
        }
        predecessors
    }
    def getSuccessors(key: Int): ArrayBuffer[Int] = adjacence(key)

    def breadthFirstSearch(key: Int): String = {
        var queue = new scala.collection.mutable.Queue[Int]
        var markedNode: ArrayBuffer[Int] = ArrayBuffer()
        var actualNodeKey = 0
        var listNodesVisited = ""

        queue += key
        while(!queue.isEmpty) {
            actualNodeKey = queue.dequeue
            markedNode += actualNodeKey
            listNodesVisited += actualNodeKey.toString + ", "   // for debug
            // treat actual node here
            for(i <- getSuccessors(actualNodeKey)) if(!markedNode.contains(i) && !queue.contains(i)) queue += i
        }
        listNodesVisited = listNodesVisited.dropRight(2)
        listNodesVisited
    }

    def calculateEccentricityOf(key: Int): (Int, Int) = {
        var queue = new scala.collection.mutable.Queue[Int]
        var markedNode: ArrayBuffer[Int] = ArrayBuffer()
        var actualNodeKey = 0
        var listNodesVisited = ""
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
        (key, eccentricity)
    }

    def display = adjacence.keys.foreach {i =>
        println("key : " + i + ", Node : " + adjacence(i).toString +
                ", Successors : " + getSuccessors(i).mkString(", ") +
                ", Predecessors : " + getPredecessors(i).mkString(", "))
    }
}
