scalac -sourcepath ./srcTest/ -d classesTest/ srcTest/graph/testGraph.scala -classpath ./classes:./lib/scalatest_2.11-2.2.1.jar
scalac -sourcepath ./src -d classes src/graph/Graph.scala

scala -classpath ./lib/scalatest_2.11-2.2.1.jar org.scalatest.tools.Runner -R "./classesTest/ classes/"
