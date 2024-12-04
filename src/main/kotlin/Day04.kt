import util.Util
import util.Util.getOrNull
import util.Util.times

private fun main() {
    val input = Util.loadInputLines(2024, 4)

    // Turn the input into a 2d list of characters
    val grid = input.map { it.toList() }

    var p1 = 0
    var p2 = 0

    for (row in grid.indices) {
        for (col in grid.first().indices) {
            val char = grid[row][col]

            // If the character is potentially the start of "XMAS"
            if (char == 'X') {
                // For the remaining letters ("MAS"), check increasingly further away in each direction and count the number of successes
                p1 += Util.gridDirectionsCardinalAndDiagonal.count { direction ->
                    var distance = 0
                    "MAS".all { char ->
                        distance++
                        val travel = direction.times(distance)
                        char == grid.getOrNull(row + travel.first, col + travel.second)
                    }
                }
            }

            // If the character is potentially the center of an X-"MAS"
            if (char == 'A') {
                // Positions of each diagonal relative to the current position
                val positions = listOf(listOf(1 to 1, -1 to -1), listOf(-1 to 1, 1 to -1))
                // Is valid if the characters in each diagonal are 'M' and 'S'
                val isValid = positions.all { directions ->
                    directions.map { grid.getOrNull(row + it.first, col + it.second) }.containsAll("MS".toList())
                }

                if (isValid) p2++
            }
        }
    }


    Util.printSolution(4, p1, p2)
}

// Original speedrun solution
// Part 1: 00:07:11
// Part 2: 00:13:06

private fun originalPartOne() {
    val input = Util.loadInputLines(2024, 4)

    val grid = input.map { it.toList() }

    var count = 0

    for (col in grid.indices) {
        for (row in grid.first().indices) {
            val char = grid[col][row]
            if (char != 'X') continue

            if (
                grid.getOrNull(col + 1)?.getOrNull(row) == 'M' &&
                grid.getOrNull(col + 2)?.getOrNull(row) == 'A' &&
                grid.getOrNull(col + 3)?.getOrNull(row) == 'S'
            ) count++
            if (
                grid.getOrNull(col - 1)?.getOrNull(row) == 'M' &&
                grid.getOrNull(col - 2)?.getOrNull(row) == 'A' &&
                grid.getOrNull(col - 3)?.getOrNull(row) == 'S'
            ) count++
            if (
                grid.getOrNull(col)?.getOrNull(row + 1) == 'M' &&
                grid.getOrNull(col)?.getOrNull(row + 2) == 'A' &&
                grid.getOrNull(col)?.getOrNull(row + 3) == 'S'
            ) count++
            if (
                grid.getOrNull(col)?.getOrNull(row - 1) == 'M' &&
                grid.getOrNull(col)?.getOrNull(row - 2) == 'A' &&
                grid.getOrNull(col)?.getOrNull(row - 3) == 'S'
            ) count++
            if (
                grid.getOrNull(col + 1)?.getOrNull(row + 1) == 'M' &&
                grid.getOrNull(col + 2)?.getOrNull(row + 2) == 'A' &&
                grid.getOrNull(col + 3)?.getOrNull(row + 3) == 'S'
            ) count++
            if (
                grid.getOrNull(col - 1)?.getOrNull(row - 1) == 'M' &&
                grid.getOrNull(col - 2)?.getOrNull(row - 2) == 'A' &&
                grid.getOrNull(col - 3)?.getOrNull(row - 3) == 'S'
            ) count++
            if (
                grid.getOrNull(col - 1)?.getOrNull(row + 1) == 'M' &&
                grid.getOrNull(col - 2)?.getOrNull(row + 2) == 'A' &&
                grid.getOrNull(col - 3)?.getOrNull(row + 3) == 'S'
            ) count++
            if (
                grid.getOrNull(col + 1)?.getOrNull(row - 1) == 'M' &&
                grid.getOrNull(col + 2)?.getOrNull(row - 2) == 'A' &&
                grid.getOrNull(col + 3)?.getOrNull(row - 3) == 'S'
            ) count++
        }
    }


    Util.printSolution(4, count)
}

private fun originalPartTwo() {
    val input = Util.loadInputLines(2024, 4)

    val grid = input.map { it.toList() }

    var count = 0

    for (col in grid.indices) {
        for (row in grid.first().indices) {
            val char = grid[col][row]
            if (char != 'A') continue

            var ic = 0
            if (
                grid.getOrNull(col + 1)?.getOrNull(row + 1) == 'M' &&
                grid.getOrNull(col - 1)?.getOrNull(row - 1) == 'S'
            ) ic++
            if (
                grid.getOrNull(col + 1)?.getOrNull(row - 1) == 'M' &&
                grid.getOrNull(col - 1)?.getOrNull(row + 1) == 'S'
            ) ic++
            if (
                grid.getOrNull(col - 1)?.getOrNull(row - 1) == 'M' &&
                grid.getOrNull(col + 1)?.getOrNull(row + 1) == 'S'
            ) ic++
            if (
                grid.getOrNull(col - 1)?.getOrNull(row + 1) == 'M' &&
                grid.getOrNull(col + 1)?.getOrNull(row - 1) == 'S'
            ) ic++

            if (ic == 2) count++
        }
    }


    Util.printSolution(4, count)
}