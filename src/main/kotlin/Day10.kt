import util.Direction
import util.Grid
import util.Util
import util.gridOf

// Slightly cleaned up speedrun solution
// Part 1: 00:16:30
// Part 2: 00:17:04
// Accidentally did part 2 first

private fun main() {
    val input = Util.loadInputLines(2024, 10)

    var p1 = 0
    var p2 = 0

    val grid = gridOf(input.map { it.toList().map { it.digitToInt() } })

    // Get all starting positions
    val trailheads = grid.all.filter { it.value == 0 }

    // Gets the number of summits reachable from all possible routes as a list
    fun getSummits(currentAlt: Int, currentPosition: Grid.GridIndex<Int>): List<Grid.GridIndex<Int>> {
        // If we are already at the top, return a list of this summit
        if (currentAlt == 9) return listOf(currentPosition)

        val summits = mutableListOf<Grid.GridIndex<Int>>()
        // For each direction we can move in
        for (dir in Direction.entries) {
            // Move in that direction or continue if it's out of bounds
            val nextTile = currentPosition.move(dir) ?: continue
            // If the tile's altitude is one larger than our current altitude
            val nextAltitude = currentAlt + 1
            if (nextTile.value == nextAltitude) {
                // Add all summits reachable from that tile to the list
                summits += getSummits(nextAltitude, nextTile)
            }
        }

        return summits
    }

    // For each trailhead
    for (trailhead in trailheads) {
        // Get all the paths to the top
        val pathsToTop = getSummits(0, trailhead)
        // Part one is all the unique summits
        p1 += pathsToTop.distinct().size
        // Part two allows the same summit to be reached multiple times
        p2 += pathsToTop.size
    }

    Util.printSolution(10, p1, p2)
}