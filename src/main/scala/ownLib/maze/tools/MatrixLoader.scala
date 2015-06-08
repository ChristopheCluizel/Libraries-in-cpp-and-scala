package ownLib.maze.tools

import java.io.BufferedReader
import java.io.FileReader

import scala.io.StdIn.readInt
import scala.io.StdIn.readLine

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
    val reader = new BufferedReader(new FileReader(fileName))
    val nbRows = reader.readLine().toInt
    val nbColumns = reader.readLine().toInt
    val map = Array.ofDim[Int](nbRows, nbColumns)

    for (i <- 0 until nbRows) {
      map(i) = reader.readLine.split(" ").map(x => x.toInt)
    }
    map
  }
}
