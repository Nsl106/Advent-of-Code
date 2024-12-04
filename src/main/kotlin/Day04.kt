import util.Util

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