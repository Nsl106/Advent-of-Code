import util.Direction
import util.Grid
import util.Util
import util.gridOf
import java.util.*
import kotlin.math.abs

// Cleaned up speedrun solution
// Part 1: 00:36:09
// Part 2: 02:03:20

private fun main() {
    val input = Util.loadInputLines(2024, 12)

    val grid = gridOf(input.map { it.toList() })

    var p1 = 0
    var p2 = 0

    // Create a list of regions
    // Each region is represented as a list of the grid indices it contains
    val regions = mutableListOf<MutableSet<Grid.GridIndex<Char>>>()

    // For each position in the grid
    for (position in grid.all) {
        // Collect all regions (if any) that this region is a part of
        val existingRegions = regions.filter { it.contains(position) }
        val region = if (existingRegions.isEmpty()) {
            // If it isn't in any regions, create a new one and add it to the list of all regions
            val newRegion = mutableSetOf<Grid.GridIndex<Char>>()
            regions.add(newRegion)
            newRegion
        } else {
            // If it is already in one or more regions, create a new region that combines all the ones it's in
            val newRegion = mutableSetOf<Grid.GridIndex<Char>>()
            existingRegions.forEach { newRegion.addAll(it) }
            // Remove all the previous uncombined smaller regions from the main list
            regions.removeAll(existingRegions)
            // Add the new combined region to the list
            regions.add(newRegion)
            newRegion
        }

        // Now that we have a region, add the current position to it
        region.add(position)

        // For each direction
        for (direction in Direction.entries) {
            // Move from the current position, or skip if out of bounds
            val movedPos = position.move(direction) ?: continue
            // If the tile is moved to has the same character, add it to the region as well.
            // This means the region only "spreads" by one for each grid position checked, but
            // because we check which region to add to at the beginning (or combine several existing
            // ones) it results in them all being mapped correctly in the end.
            if (movedPos.value == position.value) {
                region.add(movedPos)
            }
        }
    }

    // Calculate points for regions individually
    for (region in regions) {

        // Get all region edges by pairing each cell with the directions of the cells it can move to that are outside the region
        val outerEdges = mutableSetOf<Pair<Grid.GridIndex<Char>, Direction>>()
        region.forEach { tile ->
            Direction.entries.forEach { direction ->
                if (tile.move(direction) !in region) {
                    outerEdges.add(tile to direction)
                }
            }
        }


        // This is implemented pretty poorly, but I have more interesting things to do than write it again

        // Sides are stored as a list of sides, and each side is represented by a set of the positions it contains
        val sides = mutableListOf<Set<Grid.GridIndex<Char>>>()

        // Group the edges by the direction they'd need to move to exit the region (there are duplicate positions for the ones that border two+ cells)
        // This means we now have a set of positions that all have cells outside the region on the same side (likely a side)
        val edgesByDirection = outerEdges.groupBy { it.second }.mapValues { it.value.map { it.first } }
        // For the set of positions that have outside cells in the same direction
        for ((direction, positions) in edgesByDirection) {
            // At this point in the process, we have a list of cells that all have cells outside the region in the same direction.
            // However, there's no guarantee they are part of the same side.
            // There are two cases that need accounting for:

            // First is if each position is not in the same row/column. All the cells labeled A have cells
            // outside the region on the north side (labeled with "o"), but they aren't all part of the same side.
            // XoXXXXX
            // XAXoooX
            // XXXAAAX

            // Second is if there is a gap in the side. In this case, all the "A"s in the middle row are part of the same region,
            // and they all have outside cells to the north, but they don't form a single side.
            // XoXoooX
            // XAoAAAX
            // XAAAAAX

            // Unfortunately it currently handles it differently for north/south and east/west (same process though)
            when (direction) {
                Direction.NORTH, Direction.SOUTH -> {
                    // If the outside cells are to the north or south, any valid sides will all be in the same row
                    val byRow = positions.groupBy { it.position.row }

                    // For each row, we now have to deal with case two as described above
                    byRow.values.forEach { row ->
                        // Start by sorting the list in order of columns and making a queue
                        val queue: Queue<Grid.GridIndex<Char>> = LinkedList(row.sortedBy { it.position.col })

                        // While the queue is not empty, we want to take a value and compare it to the next one.
                        // If the difference between the columns is one, they are adjacent and therefore part of the same side.
                        // Otherwise, start a new side
                        while (queue.isNotEmpty()) {
                            // The current side being built
                            val side = mutableSetOf<Grid.GridIndex<Char>>()
                            // The most recent value checked
                            var last = queue.poll()

                            // Start by adding the first position to the side
                            side.add(last)

                            // While there are more values
                            while (queue.isNotEmpty()) {
                                // Peek at the next value and make sure it is adjacent to the last segment.
                                // Because the list was sorted earlier, the side is finished if this is not true
                                // and a new side should be started.
                                if (abs(last.position.col - queue.peek().position.col) == 1) {
                                    // Actually retrieve the next position, set it as the last one, and add it to the side
                                    val next = queue.poll()
                                    last = next
                                    side.add(next)
                                } else {
                                    // Otherwise break this loop and start a new side
                                    break
                                }
                            }

                            // Add this side to the list of sides
                            sides.add(side)
                        }
                    }
                }

                // The process for east/west is the same as above, just with rows and columns flipped
                Direction.EAST, Direction.WEST -> {
                    val byRow = positions.groupBy { it.position.col }

                    byRow.values.forEach { col ->
                        val queue: Queue<Grid.GridIndex<Char>> = LinkedList(col.sortedBy { it.position.row })
                        while (queue.isNotEmpty()) {
                            val edge = mutableSetOf<Grid.GridIndex<Char>>()
                            var first = queue.poll()
                            edge.add(first)

                            while (queue.isNotEmpty()) {
                                if (abs(first.position.row - queue.peek().position.row) == 1) {
                                    val next = queue.poll()
                                    first = next
                                    edge.add(next)
                                } else {
                                    break
                                }
                            }
                            sides.add(edge)
                        }
                    }
                }
            }
        }

        // Getting the region's area and perimeter is relatively easy
        val area = region.size
        val perimeter = outerEdges.size

        p1 += area * perimeter
        p2 += area * sides.size
    }

    Util.printSolution(12, p1, p2)
}