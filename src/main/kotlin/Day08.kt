import util.BasePosition
import util.Position
import util.Util
import util.Util.allPossiblePairs
import util.gridOf

private fun main() {
    val input = Util.loadInputLines(2024, 8)

    // Create grid of the input
    val grid = gridOf(input.map { it.toList() })

    // A map of each antenna to all the positions it appears at
    // Each group can be evaluated independently as they don't interact at all
    // This is done by filtering out any blank tiles ('.') and then grouping each position by its antenna frequency
    val antennaPositions = grid.all.filter { it.value != '.' }.groupBy({ it.value }, { it.position })

    val p1Antinodes = mutableSetOf<Position>()
    val p2Antinodes = mutableSetOf<Position>()

    // Check each frequency independently as they are all separate problems
    for (frequency in antennaPositions.keys) {
        // Start by getting all possible pairs of antennas, this is how we will calculate the antinodes
        val positions = antennaPositions[frequency]!!
        for (pair in positions.allPossiblePairs()) {
            val (a, b) = pair

            // Take the difference between the two positions
            val difference = Position(a.row - b.row, a.col - b.col)

            // Increase distance away from the signals in steps according to the distance until both sides exit the grid
            var distance = 0
            while (true) {
                // For each node, get the next in line at the current distance
                val antinodeA = Position(
                    a.row + (difference.row * distance),
                    a.col + (difference.col * distance)
                )

                val antinodeB = Position(
                    b.row - (difference.row * distance),
                    b.col - (difference.col * distance)
                )

                // If neither are in the grid, stop
                if (!grid.contains(antinodeA) && !grid.contains(antinodeB)) break

                // For each potential antinode, make sure it's within the grid
                if (grid.contains(antinodeA)) {
                    // If the distance is one, add to the part one list
                    if (distance == 1) p1Antinodes.add(antinodeA)
                    // Always add to part two
                    p2Antinodes.add(antinodeA)
                }

                // Same here
                if (grid.contains(antinodeB)) {
                    if (distance == 1) p1Antinodes.add(antinodeB)
                    p2Antinodes.add(antinodeB)
                }

                // Increment distance
                distance++
            }
        }
    }

    Util.printSolution(8, p1Antinodes.size, p2Antinodes.size)
}

// Original speedrun solution
// Part 1: 00:30:17
// Part 2: 00:34:18

private fun original() {
    val input = Util.loadInputLines(2024, 8)

    val uniqueCharacters = input.flatMap { it.filterNot { it == '.' }.toList() }.toSet()

    val grid = gridOf(input.map { it.toList() })


    val signalPositions = mutableMapOf<Char, MutableList<BasePosition>>()

    for (pos in grid.all) {
        if (pos.value != '.') signalPositions.getOrPut(pos.value) { mutableListOf() }.add(pos.position)
    }

    val antinodes = mutableSetOf<Position>()

    for (char in uniqueCharacters) {
        val positions = signalPositions[char]!!

        for (pair in positions.allPossiblePairs()) {
            val (a, b) = pair

            val difference = Position(a.row - b.row, a.col - b.col)

            var incrementer = 0
            while (true) {
                val row = a.row + difference.row * incrementer
                val col = a.col + difference.col * incrementer

                if (row !in grid.rowIndices || col !in grid.colIndices) break

                val antiOne = Position(row, col)
                antinodes.add(antiOne)
                incrementer++
            }
            var incrementer2 = 0
            while (true) {
                val row = b.row - difference.row * incrementer2
                val col = b.col - difference.col * incrementer2

                if (row !in grid.rowIndices || col !in grid.colIndices) break

                val antiOne = Position(row, col)
                antinodes.add(antiOne)
                incrementer2++
            }
        }
    }

    val allAntinodes = antinodes.filter { grid.contains(it) }

    println(allAntinodes)
    println(allAntinodes.size)
}