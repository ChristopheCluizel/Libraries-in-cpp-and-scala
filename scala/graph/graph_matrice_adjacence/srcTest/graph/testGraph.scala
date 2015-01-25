package graph

import org.scalatest._

class testGraph extends FlatSpec with Matchers {

    var graph = new Graph[Int](10)
    graph.addNode(0, 0)
    graph.addNode(1, 1)
    graph.addNode(2, 2)
    graph.addNode(3, 3)
    graph.addEdge(0, 1)
    graph.addEdge(1, 2)
    graph.addEdge(2, 3)
    graph.addEdge(2, 0)

    "A Graph" should "add nodes" in {
        graph.nodes(0) should be (0)
        graph.nodes(1) should be (1)
    }

    it should "add edges" in {
        graph.adjacenceMatrix(0)(1) should be (true)
        graph.adjacenceMatrix(2)(3) should be (true)
    }

    it should "indicate if empty" in {
        var graphEmpty = new Graph[Int](2)
        graphEmpty.isEmpty should be (true)
        graphEmpty.addNode(0, 0)
        graphEmpty.isEmpty should be (false)
    }

    it should "indicate if node present" in {
        graph.nodePresent(1) should be (true)
        graph.nodePresent(4) should be (false)
    }

    it should "indicate if edge present" in {
        graph.edgePresent(0, 1) should be (true)
        graph.edgePresent(1, 0) should be (false)
    }

    it should "get the successors in oriented graph" in {
        var successors = graph.getSuccessors(2)
        successors.contains(0) should be (true)
        successors.contains(3) should be (true)
        successors.contains(2) should be (false)
        successors.contains(1) should be (false)
    }

    it should "get the successors in non-oriented graph" in {
        var graph = new Graph[Int](10)
        graph.addNode(0, 0)
        graph.addNode(1, 1)
        graph.addNode(2, 2)
        graph.addNode(3, 3)

        graph.addEdge(0, 1)
        graph.addEdge(1, 2)
        graph.addEdge(2, 3)
        graph.addEdge(2, 0)

        graph.addEdge(1, 0)
        graph.addEdge(2, 1)
        graph.addEdge(3, 2)
        graph.addEdge(0, 2)
        var successors = graph.getSuccessors(2)
        successors.contains(0) should be (true)
        successors.contains(3) should be (true)
        successors.contains(2) should be (false)
        successors.contains(1) should be (true)
    }

    it should "get the predecessors in oriented graph" in {
                var graph = new Graph[Int](10)
        graph.addNode(0, 0)
        graph.addNode(1, 1)
        graph.addNode(2, 2)
        graph.addNode(3, 3)

        graph.addEdge(0, 1)
        graph.addEdge(1, 2)
        graph.addEdge(2, 3)
        graph.addEdge(2, 0)

        var predecessors = graph.getPredecessors(2)
        predecessors.contains(1) should be (true)
        predecessors.contains(0) should be (false)
        predecessors.contains(2) should be (false)
        predecessors.contains(3) should be (false)
    }

    it should "get the predecessors in non-oriented graph" in {
                var graph = new Graph[Int](10)
        graph.addNode(0, 0)
        graph.addNode(1, 1)
        graph.addNode(2, 2)
        graph.addNode(3, 3)
        graph.addNode(4, 4)

        graph.addEdge(0, 1)
        graph.addEdge(1, 2)
        graph.addEdge(2, 3)
        graph.addEdge(2, 0)
        graph.addEdge(3, 4)

        graph.addEdge(1, 0)
        graph.addEdge(2, 1)
        graph.addEdge(3, 2)
        graph.addEdge(0, 2)
        graph.addEdge(4, 0)

        var predecessors = graph.getPredecessors(2)
        predecessors.contains(1) should be (true)
        predecessors.contains(0) should be (true)
        predecessors.contains(2) should be (false)
        predecessors.contains(3) should be (true)
        predecessors.contains(4) should be (false)
    }

    it should "breadth-first search" in {
        var graphBreadthFirstSearch = new Graph[Int](8)
        graphBreadthFirstSearch.addNode(0, 0)
        graphBreadthFirstSearch.addNode(1, 1)
        graphBreadthFirstSearch.addNode(2, 2)
        graphBreadthFirstSearch.addNode(3, 3)
        graphBreadthFirstSearch.addNode(4, 4)
        graphBreadthFirstSearch.addNode(5, 5)
        graphBreadthFirstSearch.addNode(6, 6)
        graphBreadthFirstSearch.addNode(7, 7)
        graphBreadthFirstSearch.addEdge(0, 1)
        graphBreadthFirstSearch.addEdge(0, 2)
        graphBreadthFirstSearch.addEdge(0, 3)
        graphBreadthFirstSearch.addEdge(1, 4)
        graphBreadthFirstSearch.addEdge(1, 5)
        graphBreadthFirstSearch.addEdge(2, 5)
        graphBreadthFirstSearch.addEdge(2, 6)
        graphBreadthFirstSearch.addEdge(5, 7)
        graphBreadthFirstSearch.breadthFirstSearch(0) should be ("0, 1, 2, 3, 4, 5, 6, 7")
    }

    it should "find the eccentricity" in {
        var graphFindEccentricity = new Graph[Int](8)
        graphFindEccentricity.addNode(0, 0)
        graphFindEccentricity.addNode(1, 1)
        graphFindEccentricity.addNode(2, 2)
        graphFindEccentricity.addNode(3, 3)
        graphFindEccentricity.addNode(4, 4)
        graphFindEccentricity.addNode(5, 5)
        graphFindEccentricity.addNode(6, 6)
        graphFindEccentricity.addNode(7, 7)

        graphFindEccentricity.addEdge(0, 1)
        graphFindEccentricity.addEdge(0, 2)
        graphFindEccentricity.addEdge(0, 3)
        graphFindEccentricity.addEdge(1, 4)
        graphFindEccentricity.addEdge(1, 5)
        graphFindEccentricity.addEdge(2, 5)
        graphFindEccentricity.addEdge(2, 6)
        graphFindEccentricity.addEdge(5, 7)

        graphFindEccentricity.addEdge(1, 0)
        graphFindEccentricity.addEdge(2, 0)
        graphFindEccentricity.addEdge(3, 0)
        graphFindEccentricity.addEdge(4, 1)
        graphFindEccentricity.addEdge(5, 1)
        graphFindEccentricity.addEdge(5, 2)
        graphFindEccentricity.addEdge(6, 2)
        graphFindEccentricity.addEdge(7, 5)

        val (key, eccentricity) = graphFindEccentricity.calculateEccentricityOf(2)
        key should be (2)
        eccentricity should be (3)

        val (key1, eccentricity1) = graphFindEccentricity.calculateEccentricityOf(3)
        key1 should be (3)
        eccentricity1 should be (4)

        val (key2, eccentricity2) = graphFindEccentricity.calculateEccentricityOf(6)
        key2 should be (6)
        eccentricity2 should be (4)
    }

}
