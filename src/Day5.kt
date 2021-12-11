import java.io.File
import java.util.*
import kotlin.math.abs

fun main() {
    val scanner = Scanner(File("src/inputs/5.txt"))
    scanner.useDelimiter("\\r\n|\n\n")

    val covered = mutableMapOf<Pair<Int,Int>, Int>()
    while (scanner.hasNext()) {
        val row = scanner.nextLine()
            .split(" -> ")
            .map { point -> point.split(',').map { it.toInt() } }.map { Pair(it.first(), it.last()) }
            .let { Pair(it.first(), it.last()) }
        if (row.isOnlyHorizontal()) {
            covered.addHorizontal(row.first.first, row.second.first, row.first.second)
        }
        if (row.isOnlyVertical()) {
            covered.addVertical(row.first.second, row.second.second, row.first.first)
        }
        if(row.isDiagonal() and !row.isOnlyVertical() and !row.isOnlyHorizontal()) {
            covered.addDiagonal(row.first.first, row.first.second, row.second.first, row.second.second)
        }
//        covered.printAsMatrix(10,10)
//        println()
    }
    covered.printAsMatrix(10,10)

    println()
    println(covered.filter { it.value > 1 }.count())
}

private fun MutableMap<Pair<Int,Int>, Int>.addDiagonal(x1: Int, y1: Int, x2: Int, y2: Int) {
    if (x1 < x2 && y1 > y2) {
        for (d in 0 until x2 - x1 + 1) {
            this[Pair(x1 + d, y1 - d)] = (this[Pair(x1 + d, y1 - d)] ?: 0) + 1
        }
    }
    if (x1 > x2 && y1 < y2) {
        for (d in 0 until x1 - x2 + 1) {
            this[Pair(x1 - d, y1 + d)] = (this[Pair(x1 - d, y1 + d)] ?: 0) + 1
        }
    }
    if (x1 > x2 && y1 > y2) {
        for (d in 0 until x1 - x2 + 1) {
            this[Pair(x1 - d, y1 - d)] = (this[Pair(x1 - d, y1 - d)] ?: 0) + 1
        }
    }
    if (x1 < x2 && y1 < y2) {
        for (d in 0 until x2 - x1 + 1) {
            this[Pair(x1 + d, y1 + d)] = (this[Pair(x1 + d, y1 + d)] ?: 0) + 1
        }
    }
}

private fun MutableMap<Pair<Int,Int>, Int>.addVertical(y1: Int, y2: Int, x: Int) {
    if (y1 < y2 + 1) {
        for (y in y1 until y2 + 1) {
            this[Pair(x, y)] = (this[Pair(x, y)] ?: 0) + 1
        }
    } else {
        for (y in y1 downTo y2) {
            this[Pair(x, y)] = (this[Pair(x, y)] ?: 0) + 1
        }
    }
}

private fun MutableMap<Pair<Int,Int>, Int>.addHorizontal(x1: Int, x2:Int, y: Int) {
    if (x1 < x2 + 1) {
        for (x in x1 until x2 + 1) {
            this[Pair(x, y)] = (this[Pair(x, y)] ?: 0) + 1
        }
    } else {
        for (x in x1 downTo x2) {
            this[Pair(x, y)] = (this[Pair(x, y)] ?: 0) + 1
        }
    }
}

private fun Map<Pair<Int,Int>, Int>.printAsMatrix(i: Int, j: Int) {
    for(y in 0 until i) {
        for (x in 0 until j) {
            if (this.containsKey(Pair(x,y))) {
                print(this[Pair(x,y)])
            } else {
                print('.')
            }
        }
        println()
    }
}

private fun Pair<Pair<Int,Int>, Pair<Int, Int>>.isOnlyHorizontal(): Boolean =
    (this.first.second == this.second.second) and (this.first.first != this.second.first)

private fun Pair<Pair<Int,Int>, Pair<Int, Int>>.isOnlyVertical(): Boolean =
    (this.first.first == this.second.first) and (this.first.second != this.second.second)

private fun Pair<Pair<Int,Int>, Pair<Int, Int>>.isDiagonal(): Boolean =
    abs(this.first.first - this.second.first) == abs(this.first.second - this.second.second)