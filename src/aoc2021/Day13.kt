package aoc2021

import java.io.File
import java.util.*
import java.util.regex.Pattern

data class Coordinate(val x: Int, val y: Int)

data class Instruction(val direction: String, val foldLine: Int)

fun main() {
    val scanner = Scanner(File("src/aoc2021/inputs/13.txt"))
        .useDelimiter(Pattern.compile("^\\s*$", Pattern.MULTILINE))
    val coordinates = scanner.next().split("\r\n", "\n")
        .filter { it.isNotBlank() }
        .map { it.toCoordinate() }
    val maxX = coordinates.maxBy { it.x }!!.x
    val maxY = coordinates.maxBy { it.y }!!.y
    var paper = arrayOf<BooleanArray>()
    for (i in 0 .. maxY) {
        paper += BooleanArray(maxX+1)
    }
    coordinates.forEach{ paper[it.y][it.x] = true }

    val regex = """fold along ([xy])=(\d+)""".toRegex()
    val instructions = scanner.next().split("\r\n", "\n")
        .filter { it.isNotBlank() }
        .map {
            val (dir, v) = regex.find(it)!!.destructured
            Instruction(dir, v.toInt())
        }

    val newPaper = instructions[0].fold(paper)

    println("After first instruction there are ${countDots(newPaper)} dots")

    // newPaper.forEach { println(it.joinToString("") { v -> if (v) "#" else "." }) }

    var resultPaper = paper
    repeat(instructions.count()) {
        resultPaper = instructions[it].fold(resultPaper)
    }

    resultPaper.forEach { println(it.joinToString("") { v -> if (v) "#" else "." }) }
}

private fun countDots(newPaper: Array<BooleanArray>): Int {
    var n = 0
    newPaper.forEach { line ->
        line.forEach {
            if (it) n++
        }
    }
    return n
}

private fun Instruction.fold(paper: Array<BooleanArray>) : Array<BooleanArray> {
    return when (this.direction) {
        "x" -> return foldLeft(paper, this.foldLine)
        "y" -> return foldUp(paper, this.foldLine)
        else -> emptyArray()
    }
}

fun foldUp(paper: Array<BooleanArray>, foldLine: Int): Array<BooleanArray> {
    var y1 = foldLine - 1
    var y2 = foldLine + 1
    val newPaper = Array(foldLine) {
        BooleanArray(paper.first().size)
    }
    while (y1 >= 0 && y2 <= paper.lastIndex) {

        for (x in 0..paper.first().lastIndex) {
            if (paper[y1][x] || paper[y2][x]) {
                newPaper[y1][x] = true
            }
        }
        y1--
        y2++
    }
    return newPaper
}

fun foldLeft(paper: Array<BooleanArray>, foldLine: Int): Array<BooleanArray> {
    var x1 = foldLine - 1
    var x2 = foldLine + 1
    val newPaper = Array(paper.size) {
        BooleanArray(foldLine)
    }
    while (x1 >= 0 && x2 <= paper.first().lastIndex) {

        for (y in 0..paper.lastIndex) {
            if (paper[y][x1] || paper[y][x2]) {
                newPaper[y][x1] = true
            }
        }
        x1--
        x2++
    }
    return newPaper
}

private fun String.toCoordinate(): Coordinate {
    val c = this.split(",").map { it.toInt() }
    return Coordinate(c.first(), c.last())
}
