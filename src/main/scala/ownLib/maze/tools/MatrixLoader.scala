package ownLib.maze.tools

import scala.io.StdIn.{ readInt, readLine }

/**
 * MatrixLoader is a loader for a matrix stored in a text file.
 */
object MatrixLoader {

  /**
   * Load a matrix from a text file.
   *
   * @param fileName The name of the text file where the matrix is stored.
   * @return The matrix with a double array of Int design.
   */
  def load(fileName: String): Array[Array[Int]] = {
    val nbRow = readInt()
    val nbColumns = readInt()
    val map = Array.ofDim[Int](nbRow, nbColumns)

    for (i <- 0 until nbRow) {
      map(i) = readLine.split(" ").map(x => x.toInt)
    }
    map
  }
}
