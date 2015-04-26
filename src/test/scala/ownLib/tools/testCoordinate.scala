package ownLib.tools

import org.scalatest.Matchers
import org.scalatest.FlatSpec
import org.scalatest.FixtureEngine

class testCoordinate extends FlatSpec with Matchers {

  def fixtureCoordinate = new {
    val coordinate = new Coordinate(20, 10)
  }

  "A Coordinate" should "have a toString method" in {
    val f = fixtureCoordinate
    f.coordinate.toString() should be ("(20, 10)")
  }
}
