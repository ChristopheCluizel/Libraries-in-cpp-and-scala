package ownLib.tools

import scala.Boolean

class Coordinate(var x: Int, var y: Int) {

  /**
   * @param that The coordinate to compare with.
   * @return true if the 2 coordinates are equal
   */
  def ==(that: Coordinate): Boolean = {
    this.x == that.x && this.y == that.y
  }

  /**
   * Redefine the "toString" method for the Coordinate class
   */
  override def toString: String = "(" + x + ", " + y + ")"
}
