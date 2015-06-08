package ownLib.maze.tools

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class TestMatrixLoader extends FlatSpec with Matchers {

  "A MatrixLoader" should "load a matrix from text file" in {
  val matrix = MatrixLoader.load("src/test/resources/matrix")
  matrix.length should be (5)
  matrix(0).length should be (10)
  matrix(0).sum should be (3)
  matrix(1).sum should be (6)
  matrix(2).sum should be (5)
  matrix(3).sum should be (7)
  matrix(4).sum should be (2)
  }
}
