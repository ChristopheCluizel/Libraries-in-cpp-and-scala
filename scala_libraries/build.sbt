lazy val commonSettings = Seq(
  scalaVersion := "2.11.6"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*)
  
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

ScoverageSbtPlugin.ScoverageKeys.coverageExcludedPackages := ".*benchmark.*"