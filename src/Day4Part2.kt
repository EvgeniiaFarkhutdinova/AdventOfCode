import java.io.File
import java.util.*

fun main() {
    val scanner = Scanner(File("src/inputs/4.txt"))
    scanner.useDelimiter("\\r\n|\n\n")
    val test = scanner.next()
    val drawnNumbers = test.split(",").map { it.toInt() }

    scanner.nextLine()
    scanner.nextLine()
    scanner.useDelimiter("\\r\n|\n")

    var lastWinnerCounter = 1
    var lastWinnerNumber = 0
    var lastWinnerBoard = Array(5) { Array(5) { 0 } }

    while (scanner.hasNext()) {
        // reading board
        val board = Array(5) { Array(5) { 0 } }
        for (i in 0 until 5) {
            val row = scanner.next().trim().replace("  ", " ").split(" ").map { it.toInt() }
            board[i] = row.toTypedArray()
        }
        scanner.nextLine()

        // checking winning position
        val filledBoard = Array(5) { Array(5) { false } }
        var counter = 0
        var won = false
        drawnNumbers.forEach { d ->
            if(!won) {
                counter++
                if (board.any { it.contains(d) }) {
                    val i = board.indexOfFirst { it.contains(d) }
                    val j = board[i].indexOf(d)
                    filledBoard[i][j] = true


                    var winRow = 0
                    var winColumn = 0
                    for (i in 0 until 5) {
                        if (filledBoard[i].all { it }) {
                            winRow++
                        }
                        if ((0 until 5).map { filledBoard[it][i] }.all { it }) {
                            winColumn++
                        }
                    }
                    if (winRow + winColumn == 1) {
                        println()
                        filledBoard.forEach { println(it.joinToString(" ")) }
                        println(d)
                        println()
                        if (counter > lastWinnerCounter) {
                            lastWinnerCounter = counter
                            lastWinnerBoard = board
                            lastWinnerNumber = d
                        }
                        won = true
                    }
                }
            }
        }
    }

    println("last winner counter is $lastWinnerCounter")
    println("last winner number is $lastWinnerNumber")
    println("last winner board is:")
    lastWinnerBoard.forEach { println(it.joinToString(" ")) }

    // calculate
    var sum = 0
    for (i in 0 until 5) {
        for (j in 0 until 5) {
            if (lastWinnerBoard[i][j] !in drawnNumbers.take(lastWinnerCounter)) {
                sum += lastWinnerBoard[i][j]
            }
        }
    }
    val result = sum * lastWinnerNumber

    println("Last winner score is $result") // 24628
}