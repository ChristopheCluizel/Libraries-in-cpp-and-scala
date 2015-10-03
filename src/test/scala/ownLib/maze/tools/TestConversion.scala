package ownLib.maze.tools

import org.scalatest.{FlatSpec, Matchers}
import ownLib.tools.Coordinate

import scala.collection.mutable.ArrayBuffer

class TestConversion extends FlatSpec with Matchers {

  "A Conversion" should "convert a key to a coordinate" in {
    val mazeWidth = 5
    Conversion.keyToCoordinate(3, mazeWidth) == new Coordinate(3, 0) should be(true)
    Conversion.keyToCoordinate(6, mazeWidth) == new Coordinate(1, 1) should be(true)
  }

  it should "convert a coordinate to a key" in {
    val mazeWidth = 5
    Conversion.coordinateToKey(new Coordinate(3, 0), mazeWidth) should be(3)
    Conversion.coordinateToKey(new Coordinate(1, 1), mazeWidth) should be(6)
  }

  it should "find neighbours equal to 0 of a square" in {
    val matrix: Array[Array[Int]] = Array(Array(0, 1, 1, 1), Array(0, 0, 0, 1), Array(1, 1, 0, 0))

    val neighboursCenter: ArrayBuffer[Int] = Conversion.findNeighbours(matrix, 3, 4, new Coordinate(2, 1))
    val neighboursEdge: ArrayBuffer[Int] = Conversion.findNeighbours(matrix, 3, 4, new Coordinate(1, 2))
    val neighboursCorner: ArrayBuffer[Int] = Conversion.findNeighbours(matrix, 3, 4, new Coordinate(3, 0))

    neighboursCenter should contain allOf(5, 10)
    neighboursEdge should contain allOf(5, 10)
    neighboursCorner shouldBe empty
  }

  it should "convert a matrix to a graph" in {
    val matrix: Array[Array[Int]] = Array(Array(0, 1, 1, 1), Array(0, 0, 0, 1), Array(1, 1, 0, 0))

    val graphGenerated = Conversion.matrixToGraph(matrix)

    graphGenerated.adjacency.size should be (12)
    graphGenerated.edgePresent(0,4) should be (true)
    graphGenerated.edgePresent(4,0) should be (true)
    graphGenerated.edgePresent(5,4) should be (true)
    graphGenerated.edgePresent(4,5) should be (true)
    graphGenerated.edgePresent(5,6) should be (true)
    graphGenerated.edgePresent(6,5) should be (true)
    graphGenerated.edgePresent(6,10) should be (true)
    graphGenerated.edgePresent(10,6) should be (true)
    graphGenerated.edgePresent(10,11) should be (true)
    graphGenerated.edgePresent(11,10) should be (true)
  }
}
