import util.Util
import kotlin.math.abs

private fun main() {
    // Input is a List<Int> for each line
    val input = Util.loadInputLines(2024, 2).map { it.split(" ").map(String::toInt) }

    // Process a report and return true if safe
    fun check(report: List<Int>): Boolean {
        // Make sure the difference between each pair is within 1..3
        val stepSize = report.zipWithNext { a, b -> abs(a - b) in 1..3 }.all { it }
        // Make sure the numbers are ascending or descending by comparing with the sorted version of the list
        val singleDirection = report == report.sorted() || report == report.sortedDescending()
        // Safe if both are true
        return stepSize && singleDirection
    }

    var partOne = 0
    var partTwo = 0

    for (report in input) {
        // If the line is safe, add one to both puzzles and continue
        if (check(report)) {
            partOne++
            partTwo++
            continue
        }

        // If it's not safe, check the list without each element individually. If at any point it is safe, increment part two and move to the next report.
        for (i in report.indices) {
            // Copy the list without the element
            val newLine = report.toMutableList().apply { removeAt(i) }
            // If it's safe with the element removed, add one to part two and move to the next report
            if (check(newLine)) {
                partTwo++
                break
            }
        }
    }

    Util.printSolution(2, partOne, partTwo)
}


// Original speedrun solution
// Part 1: 00:12:43
// Part 2: 01:21:55

// Just part one
private fun originalPartOne() {
    val input = Util.loadInputLines(2024, 2).map {
        it.split(" ").map { it.toInt() }
    }

    val x = input.count { ln ->
        (ln == ln.sorted() || ln == ln.sortedDescending()) &&
                ln.zipWithNext { a, b -> val v = abs(a - b); v <= 3 && v >= 1 }.all { it }
    }

    Util.printSolution(2, x)
}

// Both parts
private fun originalPartTwo() {
    var partOne = 0
    var partTwo = 0

    fun process(ln: List<Int>): Boolean {
        fun List<Int>.getAdjacent(index: Int) = listOfNotNull(
            this.getOrNull(index - 1),
            this.getOrNull(index + 1),
        )

        var safe = true
        for (i in ln.indices) {
            val adjacent = ln.getAdjacent(i)
            safe = safe && adjacent.all { abs(ln[i] - it) in 1..3 }
        }
        return safe && (ln == ln.sorted() || ln == ln.sortedDescending())
    }

    val input = Util.loadInputLines(2024, 2).map { it.split(" ").map { it.toInt() } }

    for (ln in input) {
        if (process(ln)) {
            partOne++
            partTwo++
            continue
        }

        for (i in ln.indices) {
            val newLine = ln.toMutableList()
            newLine.removeAt(i)
            if (process(newLine)) {
                partTwo++
                break
            }
        }
    }

    Util.printSolution(2, partOne, partTwo)
}