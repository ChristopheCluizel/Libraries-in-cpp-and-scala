package ownLib.maze.tools

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import ownLib.tools.Coordinate
import ownLib.tools.Coordinate

class TestConversion extends FlatSpec with Matchers {

  "A Conversion" should "convert a key to a coordinate" in {
    val mazeWidth = 5
    Conversion.keyToCoordinate(3, mazeWidth) == new Coordinate(3, 0) should be(true)
    Conversion.keyToCoordinate(6, mazeWidth) == new Coordinate(1, 1) should be(true)
  }

  it should ("convert a coordinate to a key") in {
    val mazeWidth = 5
    Conversion.coordinateToKey(new Coordinate(3, 0), mazeWidth) should be(3)
    Conversion.coordinateToKey(new Coordinate(1, 1), mazeWidth) should be(6)
  }
}
