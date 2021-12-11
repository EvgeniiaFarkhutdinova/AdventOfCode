package aoc2021

import java.io.File
import java.util.*

fun main() {
    val scanner = Scanner(File("src/aoc2021/inputs/11.txt"))
    val input = arrayListOf<IntArray>()
    while (scanner.hasNextLine()) {
        val lineArray = scanner.nextLine().map { it.toString().toInt() }.toIntArray()
        input.add(lineArray)
    }

    val t = 100
    var f = 0

    repeat(t) {
        f +=  step(input)
    }

    println("After $t steps, there have been a total of $f flashes")

    val scannerB = Scanner(File("src/aoc2021/inputs/11.txt"))
    val inputB = arrayListOf<IntArray>()
    while (scannerB.hasNextLine()) {
        val lineArray = scannerB.nextLine().map { it.toString().toInt() }.toIntArray()
        inputB.add(lineArray)
    }
    f = 0
    var s = 0

    do {
        val flashes = step(inputB)
        s++
    } while (flashes != 100)

    println("First sync flash is on $s step")
}

private fun step(input: ArrayList<IntArray>) : Int {
    var flashes = 0;
    val flashQueue = ArrayDeque<Pair<Int, Int>>()

    fun increase(y: Int, x: Int) {
        val l = ++input[y][x]
        if (l == 10)
        {
            flashQueue.push(Pair(y, x))
            flashes++
        }
    }

    for (x in 0..9) {
        for (y in 0..9) {
            increase(x, y)
        }
    }

    while (!flashQueue.isEmpty()) {
        flashQueue.removeLast().getAdjacent().forEach { increase(it.first, it.second) }
    }

    input.reset()

    return flashes
}

private fun ArrayList<IntArray>.reset() {
    this.forEachIndexed{ y, line ->
        line.forEachIndexed { x, level ->
            if (level >= 10) this[y][x] = 0
        }
    }
}

private fun adjacent() : List<Pair<Int, Int>>
    = listOf( Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0), Pair(1, 1) )
private fun Pair<Int, Int>.getAdjacent() : List<Pair<Int, Int>> {
    return adjacent().map { Pair(this.first + it.first, this.second + it.second) }.filter { it.first in 0..9 && it.second in 0..9}
}