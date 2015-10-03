package ownLib.tools

import org.scalatest.{FlatSpec, Matchers}

class testCoordinate extends FlatSpec with Matchers {

  def fixtureCoordinate = new {
    val coordinate = new Coordinate(20, 10)
  }

  "A Coordinate" should "have a toString method" in {
    val f = fixtureCoordinate
    f.coordinate.toString() should be("(20, 10)")
  }

  it should "be compared with another Coordinate" in {
    val f = fixtureCoordinate
    f.coordinate == new Coordinate(20, 10) should be(true)
    f.coordinate == new Coordinate(20, 20) should be(false)
  }
}
