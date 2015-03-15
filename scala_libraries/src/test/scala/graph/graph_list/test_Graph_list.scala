package graph.graph_list

import org.scalatest._

class test_Graph_list extends FlatSpec with Matchers {

    def fixture_oriented_graph = new {
      val oriented_graph = new Graph[Int]
      oriented_graph.addNode(0, 0)
      oriented_graph.addNode(1, 1)
      oriented_graph.addNode(2, 2)
      oriented_graph.addNode(3, 3)
      oriented_graph.addEdge(0, 1, 1)
      oriented_graph.addEdge(1, 2, 1)
      oriented_graph.addEdge(2, 3, 1)
      oriented_graph.addEdge(2, 0, 1)
    }

    def fixture_non_oriented_graph = new {
      val non_oriented_graph = new Graph[Int]
      non_oriented_graph.addNode(0, 0)
      non_oriented_graph.addNode(1, 1)
      non_oriented_graph.addNode(2, 2)
      non_oriented_graph.addNode(3, 3)

      non_oriented_graph.addEdge(0, 1, 1)
      non_oriented_graph.addEdge(1, 2, 1)
      non_oriented_graph.addEdge(2, 3, 1)
      non_oriented_graph.addEdge(2, 0, 1)

      non_oriented_graph.addEdge(1, 0, 1)
      non_oriented_graph.addEdge(2, 1, 1)
      non_oriented_graph.addEdge(3, 2, 1)
      non_oriented_graph.addEdge(0, 2, 1)
    }

    "A oriented Graph" should "add a node" in {
        val oriented_graph = new Graph[Int]
        oriented_graph.adjacence.size should be (0)
        oriented_graph.addNode(2, 2)
        oriented_graph.adjacence.size should be (1)
        oriented_graph.nodes(2) should be (2)
    }

    it should "add an edge" in {
        val oriented_graph = new Graph[Int]
        oriented_graph.addNode(1, 1)
        oriented_graph.addNode(2, 2)
        oriented_graph.addEdge(1, 2, 1)
        oriented_graph.adjacence(1)(0) should be (2)
    }

    it should "remove an edge" in {
        val f = fixture_oriented_graph
        f.oriented_graph.getPredecessors(0).contains(2) should be (true)
        f.oriented_graph.getSuccessors(2).contains(0) should be (true)
        f.oriented_graph.removeEdge(2, 0)
        f.oriented_graph.getPredecessors(0).contains(2) should be (false)
        f.oriented_graph.getSuccessors(2).contains(0) should be (false)
    }

    it should "indicate if empty" in {
        var graphEmpty = new Graph[Int]
        graphEmpty.isEmpty should be (true)
        graphEmpty.addNode(0, 0)
        graphEmpty.isEmpty should be (false)
    }

    it should "indicate if node present" in {
        val f = fixture_oriented_graph
        f.oriented_graph.adjacence.size should be (4)
        f.oriented_graph.nodePresent(0) should be (true)
        f.oriented_graph.nodePresent(1) should be (true)
        f.oriented_graph.nodePresent(2) should be (true)
        f.oriented_graph.nodePresent(3) should be (true)
        f.oriented_graph.nodePresent(4) should be (false)
    }

    it should "indicate if edge present" in {
        val f = fixture_oriented_graph
        f.oriented_graph.edgePresent(0, 1) should be (true)
        f.oriented_graph.edgePresent(1, 2) should be (true)
        f.oriented_graph.edgePresent(2, 0) should be (true)
        f.oriented_graph.edgePresent(2, 3) should be (true)
        f.oriented_graph.edgePresent(1, 0) should be (false)
    }

    it should "get the successors" in {
        val f = fixture_oriented_graph

        val successorsOfZero = f.oriented_graph.getSuccessors(0)
        successorsOfZero.contains(0) should be (false)
        successorsOfZero.contains(1) should be (true)
        successorsOfZero.contains(2) should be (false)
        successorsOfZero.contains(3) should be (false)

        val successorsOfOne = f.oriented_graph.getSuccessors(1)
        successorsOfOne.contains(0) should be (false)
        successorsOfOne.contains(1) should be (false)
        successorsOfOne.contains(2) should be (true)
        successorsOfOne.contains(3) should be (false)

        val successorsOfTwo = f.oriented_graph.getSuccessors(2)
        successorsOfTwo.contains(0) should be (true)
        successorsOfTwo.contains(3) should be (true)
        successorsOfTwo.contains(2) should be (false)
        successorsOfTwo.contains(1) should be (false)

        val successorsOfThree = f.oriented_graph.getSuccessors(3)
        successorsOfThree.isEmpty should be (true)
    }

    it should "get the predecessors" in {
        val f = fixture_oriented_graph

        val predecessorsOfZero = f.oriented_graph.getPredecessors(0)
        predecessorsOfZero.contains(0) should be (false)
        predecessorsOfZero.contains(1) should be (false)
        predecessorsOfZero.contains(2) should be (true)
        predecessorsOfZero.contains(3) should be (false)

        val predecessorsOfOne = f.oriented_graph.getPredecessors(1)
        predecessorsOfOne.contains(0) should be (true)
        predecessorsOfOne.contains(1) should be (false)
        predecessorsOfOne.contains(2) should be (false)
        predecessorsOfOne.contains(3) should be (false)

        val predecessorsOfTwo = f.oriented_graph.getPredecessors(2)
        predecessorsOfTwo.contains(0) should be (false)
        predecessorsOfTwo.contains(1) should be (true)
        predecessorsOfTwo.contains(2) should be (false)
        predecessorsOfTwo.contains(3) should be (false)

        val predecessorsOfThree = f.oriented_graph.getPredecessors(3)
        predecessorsOfThree.contains(0) should be (false)
        predecessorsOfThree.contains(1) should be (false)
        predecessorsOfThree.contains(2) should be (true)
        predecessorsOfThree.contains(3) should be (false)
    }

    it should "breadth-first search" in {
        var graphBreadthFirstSearch = new Graph[Int]
        graphBreadthFirstSearch.addNode(0, 0)
        graphBreadthFirstSearch.addNode(1, 1)
        graphBreadthFirstSearch.addNode(2, 2)
        graphBreadthFirstSearch.addNode(3, 3)
        graphBreadthFirstSearch.addNode(4, 4)
        graphBreadthFirstSearch.addNode(5, 5)
        graphBreadthFirstSearch.addNode(6, 6)
        graphBreadthFirstSearch.addNode(7, 7)
        graphBreadthFirstSearch.addEdge(0, 1, 1)
        graphBreadthFirstSearch.addEdge(0, 2, 1)
        graphBreadthFirstSearch.addEdge(0, 3, 1)
        graphBreadthFirstSearch.addEdge(1, 4, 1)
        graphBreadthFirstSearch.addEdge(1, 5, 1)
        graphBreadthFirstSearch.addEdge(2, 5, 1)
        graphBreadthFirstSearch.addEdge(2, 6, 1)
        graphBreadthFirstSearch.addEdge(5, 7, 1)
        graphBreadthFirstSearch.breadthFirstSearch(0) should be ("0, 1, 2, 3, 4, 5, 6, 7")
    }

    it should "find the eccentricity" in {
        val graph = new Graph[Int]
        graph.addNode(0, 0)
        graph.addNode(1, 1)
        graph.addNode(2, 2)
        graph.addNode(3, 3)
        graph.addNode(4, 4)
        graph.addNode(5, 5)
        graph.addNode(6, 6)
        graph.addNode(7, 7)

        graph.addEdge(0, 1, 1)
        graph.addEdge(0, 2, 1)
        graph.addEdge(0, 3, 1)
        graph.addEdge(1, 4, 1)
        graph.addEdge(1, 5, 1)
        graph.addEdge(2, 5, 1)
        graph.addEdge(2, 6, 1)
        graph.addEdge(5, 7, 1)

        graph.addEdge(1, 0, 1)
        graph.addEdge(2, 0, 1)
        graph.addEdge(3, 0, 1)
        graph.addEdge(4, 1, 1)
        graph.addEdge(5, 1, 1)
        graph.addEdge(5, 2, 1)
        graph.addEdge(6, 2, 1)
        graph.addEdge(7, 5, 1)

        graph.calculateEccentricityOf(2) should be (3)
        graph.calculateEccentricityOf(3) should be (4)
        graph.calculateEccentricityOf(6) should be (4)
    }

    it should "shed its leaves" in {
      val f = fixture_oriented_graph
      f.oriented_graph.shedTheLeaves()
      f.oriented_graph.nodePresent(0) should be (true)
      f.oriented_graph.nodePresent(1) should be (true)
      f.oriented_graph.nodePresent(2) should be (true)
      f.oriented_graph.nodePresent(3) should be (false)
    }

    "A non-oriented Graph" should "get the successors" in {
        val f = fixture_non_oriented_graph

        val successorsZero = f.non_oriented_graph.getSuccessors(0)
        successorsZero.contains(0) should be (false)
        successorsZero.contains(1) should be (true)
        successorsZero.contains(2) should be (true)
        successorsZero.contains(3) should be (false)

        val successorsOne = f.non_oriented_graph.getSuccessors(1)
        successorsOne.contains(0) should be (true)
        successorsOne.contains(1) should be (false)
        successorsOne.contains(2) should be (true)
        successorsOne.contains(3) should be (false)

        val successorsTwo = f.non_oriented_graph.getSuccessors(2)
        successorsTwo.contains(0) should be (true)
        successorsTwo.contains(1) should be (true)
        successorsTwo.contains(2) should be (false)
        successorsTwo.contains(3) should be (true)

        val successorsThree = f.non_oriented_graph.getSuccessors(3)
        successorsThree.contains(0) should be (false)
        successorsThree.contains(1) should be (false)
        successorsThree.contains(2) should be (true)
        successorsThree.contains(3) should be (false)
    }

    it should "get the predecessors" in {
        val f = fixture_non_oriented_graph

        val predecessorsZero = f.non_oriented_graph.getPredecessors(0)
        predecessorsZero.contains(0) should be (false)
        predecessorsZero.contains(1) should be (true)
        predecessorsZero.contains(2) should be (true)
        predecessorsZero.contains(3) should be (false)

        val predecessorsOne = f.non_oriented_graph.getPredecessors(1)
        predecessorsOne.contains(0) should be (true)
        predecessorsOne.contains(1) should be (false)
        predecessorsOne.contains(2) should be (true)
        predecessorsOne.contains(3) should be (false)

        val predecessorsTwo = f.non_oriented_graph.getPredecessors(2)
        predecessorsTwo.contains(1) should be (true)
        predecessorsTwo.contains(0) should be (true)
        predecessorsTwo.contains(2) should be (false)
        predecessorsTwo.contains(3) should be (true)

        val predecessorsThree = f.non_oriented_graph.getPredecessors(3)
        predecessorsThree.contains(0) should be (false)
        predecessorsThree.contains(1) should be (false)
        predecessorsThree.contains(2) should be (true)
        predecessorsThree.contains(3) should be (false)
    }
}
