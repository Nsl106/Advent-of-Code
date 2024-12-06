package y2015

import util.Util

private fun main() {
    val input = Util.loadInputLines(2015, 1).first()

    var currentLevel = 0
    var partTwo: Int? = null
    for ((index, dir) in input.withIndex()) {
        when (dir) {
            '(' -> currentLevel++
            ')' -> currentLevel--
        }

        if (currentLevel < 0 && partTwo == null) partTwo = index + 1
    }

    Util.printSolution(1, currentLevel, partTwo)
}