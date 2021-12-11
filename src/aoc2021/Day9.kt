package aoc2021

import java.io.File
import java.util.*

fun main() {
    val scanner = Scanner(File("src/aoc2021/inputs/9.txt"))
    val inputs = mutableListOf<List<Int>>()

    while (scanner.hasNext()) {
        val row = mutableListOf<Int>()
        scanner.nextLine().split("").forEach { s -> repeat(s.length) { row.add(s.toInt()) } }
        inputs.add(row)
    }

    val basins = mutableListOf<Int>()

    fun basinMembers(j: Int, i: Int): Set<Pair<Int,Int>> {
        val n = inputs[j][i]
        val members = mutableSetOf<Pair<Int, Int>>()
        if (n == 9) {
            return members
        }
        members.add(Pair(j, i))
        val left = if (i > 0) inputs[j][i - 1] else Int.MAX_VALUE
        val right = if (i < inputs[j].count() - 1) inputs[j][i + 1] else Int.MAX_VALUE
        val up = if (j > 0) inputs[j - 1][i] else Int.MAX_VALUE
        val bottom = if (j < inputs.count() - 1) inputs[j + 1][i] else Int.MAX_VALUE
        if (n < left && left != Int.MAX_VALUE) members.addAll(basinMembers(j, i - 1))
        if (n < right && right != Int.MAX_VALUE) members.addAll(basinMembers(j, i + 1))
        if (n < up && up != Int.MAX_VALUE) members.addAll(basinMembers(j - 1, i))
        if (n < bottom && bottom != Int.MAX_VALUE) members.addAll(basinMembers(j + 1, i))
        return members
    }

    inputs.forEachIndexed { j, row ->
        row.forEachIndexed { i, n ->
            val left = if(i > 0) inputs[j][i - 1] else Int.MAX_VALUE
            val right = if(i < inputs[j].count() - 1) inputs[j][i + 1] else Int.MAX_VALUE
            val up = if(j > 0) inputs[j - 1][i] else Int.MAX_VALUE
            val bottom = if(j < inputs.count() - 1) inputs[j + 1][i] else Int.MAX_VALUE
            if (n.isLowPoint(left, right, up, bottom)) {
                val members = basinMembers(j, i)
                if (members.isNotEmpty()) basins.add(members.count())
            }
        }
    }

    val sortedBasins = basins.sortedDescending().subList(0, 3)

    println("multiply is ${sortedBasins[0] * sortedBasins[1] * sortedBasins[1]}") // 839420 is too high, should be 821560
}

private fun Int.isLowPoint(left: Int, right: Int, up: Int, bottom: Int): Boolean {
    return this < left && this < right && this < up && this < bottom
}
