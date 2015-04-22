package ownLib.maze

import scala.util._
import java.io.BufferedWriter
import java.io.BufferedReader
import java.io.Reader
import java.io.Writer
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import scala.collection.mutable.ArrayBuffer

import ownLib.graph.graphList.Graph
import ownLib.tools.Coordinate

object MazeGenerator {

    def saveMaze(maze: Maze, filePath: String) = {
        val writer = new BufferedWriter(new FileWriter(filePath + maze.graph.name + ".dot"))
        writer.write(maze.width * maze.height + "\n")
        writer.write("graph " + maze.graph.name + " {\n")
        maze.graph.adjacence.keys.foreach { i =>
            for(j <- 0 until maze.graph.adjacence(i).size) {
                writer.write(i + " -> " + maze.graph.adjacence(i)(j) + "\n")
            }
        }
        writer.write("}")
        writer.close()
    }

    def loadGraph(filePath: String): Graph[Int] = {
        val reader = new BufferedReader(new FileReader(filePath))
        val nbEdges = reader.readLine.toInt
        println("nb edges : " + nbEdges)
        val firstLine = reader.readLine.toString
        val result = firstLine.split("\\s");
        val graphName = result(1)

        val graph = new Graph[Int](graphName)
        for(i <- 0 until nbEdges){
            val Array(key1, key2) = for(i <- reader.readLine split " -> ") yield i.toInt
            if(!graph.nodePresent(key1)) graph.addNode(key1, key1)
            //if(!graph.nodePresent(key2)) graph.addNode(key2, key2)
            graph.addEdge(key1, key2, 1)
            //graph.addEdge(key2, key1, 1)
        }
        graph
    }

    def generateMaze(largeur: Int, hauteur: Int): Maze = {
        val graphe = new Graph[Int]("graph")
        val tabVoisins = Array.ofDim[Boolean](hauteur, largeur)
        for(i <- 0 until hauteur; j <- 0 until largeur) tabVoisins(i)(j) = false
        val rand = new Random();
        //val positionDepart = new Coordonnees(rand.nextInt(largeur), rand.nextInt(hauteur))
        val positionDepart = new Coordinate(0, 0)
        val positionArrivee = new Coordinate(largeur / 2, hauteur / 2)

        var stack = new scala.collection.mutable.Stack[Int]
        var markedNode: ArrayBuffer[Int] = ArrayBuffer()

        var actualNodeKey = coordinatesToKey(positionDepart.x, positionDepart.y, largeur)
        markedNode += actualNodeKey
        tabVoisins(keyToCoordinates(actualNodeKey, largeur).y)(keyToCoordinates(actualNodeKey, largeur).x) = true

        while(markedNode.length < largeur * hauteur) {
            val falseNeighbours = getFalseNeighbours(actualNodeKey, tabVoisins, largeur, hauteur)
            if(! falseNeighbours.isEmpty) {
                val randomFalseNeighbour = getRandomFalseSquareInArray(falseNeighbours)
                stack push actualNodeKey
                removeWallBetween(graphe, actualNodeKey, randomFalseNeighbour)
                actualNodeKey = randomFalseNeighbour
                markedNode += actualNodeKey
                tabVoisins(keyToCoordinates(actualNodeKey, largeur).y)(keyToCoordinates(actualNodeKey, largeur).x) = true
            }
            else if(! stack.isEmpty) {
                actualNodeKey = stack.head
                stack.pop
            }
            else {
                val aleaSquareKey = getRandomFalseSquareInDoubleArray(tabVoisins, largeur, hauteur)
                actualNodeKey = aleaSquareKey
            }
        }
        return new Maze(graphe, positionArrivee, positionDepart, largeur, hauteur)
    }

    private def getFalseNeighbours(actualNodeKey: Int, tabVoisins: Array[Array[Boolean]], largeur: Int, hauteur: Int): Array[Int] = {
        val position = keyToCoordinates(actualNodeKey, largeur)
        var minY = position.y - 1
        var maxY = position.y + 1
        var minX = position.x - 1
        var maxX = position.x + 1
        if(position.y == 0) minY = 0
        if(position.y == hauteur-1) maxY = hauteur-1
        if(position.x == 0) minX = 0
        if(position.x == largeur-1) maxX = largeur-1
        val voisins = for(i <- minY to maxY; j <- minX to maxX if(tabVoisins(i)(j) == false && (i == position.y || j == position.x) && !(i == position.y && j == position.x))) yield coordinatesToKey(j, i, largeur)
        return voisins.toArray
    }

    private def getRandomFalseSquareInArray(falseNeighbours: Array[Int]): Int = {
        val rand = new Random();
        val aleaNumber = rand.nextInt(falseNeighbours.length)
        return falseNeighbours(aleaNumber)
    }
    private def removeWallBetween(graphe: Graph[Int], actualNodeKey: Int, randomFalseNeighbour: Int) = {
        if(!graphe.nodePresent(actualNodeKey)) graphe.addNode(actualNodeKey, actualNodeKey)
        if(!graphe.nodePresent(randomFalseNeighbour)) graphe.addNode(randomFalseNeighbour, randomFalseNeighbour)

        graphe.addEdge(actualNodeKey, randomFalseNeighbour, 1)
        graphe.addEdge(randomFalseNeighbour, actualNodeKey, 1)
    }
    private def getRandomFalseSquareInDoubleArray(tabVoisins: Array[Array[Boolean]], largeur: Int, hauteur: Int): Int = {
        val falseSquares = for(i <- 0 until hauteur; j <- 0 until largeur if(tabVoisins(i)(j) == false)) yield coordinatesToKey(i, j, largeur)
        return getRandomFalseSquareInArray(falseSquares.toArray)
    }
    def keyToCoordinates(key: Int, graphWidth: Int): Coordinate = new Coordinate(key % graphWidth, key / graphWidth)
    def coordinatesToKey(x: Int, y: Int, graphWidth: Int): Int = graphWidth * y + x
}
