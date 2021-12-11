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

    var winnerCounter = 1000
    var winnerNumber = 0
    var winnerBoard = Array(5) { Array(5) { 0 } }

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
        drawnNumbers.forEach { d ->
            counter++
            if(board.any { it.contains(d) }) {
                val i = board.indexOfFirst { it.contains(d) }
                val j = board[i].indexOf(d)
                filledBoard[i][j] = true

                for (k in 0 until 5) {
                    if (filledBoard[k].all { it } or
                        (0 until 5).map { filledBoard[it][k] }.all { it }) {
                        if (counter < winnerCounter) {
                            winnerCounter = counter
                            winnerBoard = board
                            winnerNumber = d
                        }
                    }
                }
            }
        }
    }

    println("winning counter is $winnerCounter")
    println("winning number is $winnerNumber")
    println("winning board is:")
    winnerBoard.forEach { println(it.joinToString(" ")) }

    // calculate
    var sum = 0
    for (i in 0 until 5) {
        for (j in 0 until 5) {
            if (winnerBoard[i][j] !in drawnNumbers.take(winnerCounter)) {
                sum += winnerBoard[i][j]
            }
        }
    }
    val result = sum * winnerNumber

    println("Winner score is $result")
}