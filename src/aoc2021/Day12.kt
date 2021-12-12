package aoc2021

import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class Vertex(val data: String)

data class Ref(var value: Int)

data class Edge(
    val source: Vertex,
    val destination: Vertex
)

interface Graph {
    fun createVertex(data: String): Vertex
    fun addEdge(source: Vertex, destination: Vertex)
    fun edges(source: Vertex): ArrayList<Edge>
}

class AdjacencyList : Graph {

    private val adjacency: HashMap<Vertex, ArrayList<Edge>> = HashMap()

    override fun createVertex(data: String): Vertex {
        val vertex = Vertex(data)
        if (!adjacency.containsKey(vertex)) adjacency[vertex] = ArrayList()
        return vertex
    }

    override fun addEdge(source: Vertex, destination: Vertex) {
        val edge = Edge(source, destination)
        adjacency[source]?.add(edge)
        val edge2 = Edge(destination, source)
        adjacency[destination]?.add(edge2)
    }

    override fun edges(source: Vertex) = adjacency[source] ?: arrayListOf()

    fun numberOfPaths( source: Vertex, destination: Vertex): Int {
        val numberOfPaths = Ref(0)
        val visited: MutableSet<Vertex> = mutableSetOf()
        paths(source, destination, visited, numberOfPaths)
        return numberOfPaths.value
    }

    private fun paths(source: Vertex, destination: Vertex, visited: MutableSet<Vertex>, pathCount: Ref) {
        if (source.data.isLowerCase()) visited.add(source)
        if (source == destination) {
            pathCount.value += 1
        } else {
            val neighbors = edges(source)
            neighbors.forEach { edge ->
                if (edge.destination !in visited) {
                    paths(edge.destination, destination, visited, pathCount)
                }
            }
        }
        visited.remove(source)
    }

    fun numberOfPaths2( source: Vertex, destination: Vertex ): Int {
        val numberOfPaths = Ref(0)
        val visited: MutableMap<Vertex, Int> = mutableMapOf()
        paths2(source, destination, visited, numberOfPaths)
        return numberOfPaths.value
    }

    private fun paths2(source: Vertex, destination: Vertex, visited: MutableMap<Vertex, Int>, pathCount: Ref) {
        if (source.data !in reserved() && source.data.isLowerCase()) visited[source] = (visited[source] ?: 0) + 1

        if (source == destination) {
            pathCount.value += 1
        } else {
            val neighbors = edges(source)
            neighbors.forEach { edge ->
                if (edge.destination.data != "start" && !visited.wantVisitIllegalSecondTime(edge.destination)) {
                    paths2(edge.destination, destination, visited, pathCount)
                }
            }
        }
        visited[source] = (visited[source] ?: 0) - 1
    }
}

private fun Map<Vertex, Int>.wantVisitIllegalSecondTime(destination: Vertex): Boolean {
    val n = this[destination] ?: 0
    return this.any { it.value > 1 } && n >= 1
}

private fun reserved() : List<String> = listOf( "start", "end" )

private fun String.isLowerCase(): Boolean {
    return this.all { it in CharCategory.LOWERCASE_LETTER }
}

fun main() {

    //    val scanner = Scanner(File("src/aoc2021/inputs/test12-1.txt")) // 10, 36
    //    val scanner = Scanner(File("src/aoc2021/inputs/test12-2.txt")) // 19
    //    val scanner = Scanner(File("src/aoc2021/inputs/test12-3.txt")) // 226
    val scanner = Scanner(File("src/aoc2021/inputs/12.txt"))
    val input = AdjacencyList()
    while (scanner.hasNextLine()) {
        val edge = scanner.nextLine().split("-")
        val v1 = input.createVertex(edge.first())
        val v2 = input.createVertex(edge.last())
        input.addEdge(v1, v2)
    }

    val n = input.numberOfPaths(Vertex("start"), Vertex("end"))
    val n2 = input.numberOfPaths2(Vertex("start"), Vertex("end"))

    println("Number of paths is $n")
    println("Number of paths for part 2 is $n2")
}