import util.Position
import util.Util
import kotlin.math.abs

private fun main() {
    val input = Util.loadInputLines(2024, 14)
        .map { ln ->
            ln
                .split(" ") // Split between velocity and position
                .map {
                    it
                        .drop(2) // Drop the "p=" or "v="
                        .split(",") // Split to two values
                        .map(String::toInt) // Map to integers
                        .let { (x, y) -> Position(x, y) } // Turn into a position
                }
                .let { (pos, vel) -> pos to vel }
        }

    // Wraps a negative robot back around
    fun Int.teleportCompensate(limit: Int) = if (this < 0) limit - abs(this) else this

    val WIDTH = 101
    val HEIGHT = 103

    // Get the final positions of the robots
    val finalPositions =
        input.map { (pos, vel) ->
            val finalRow = ((pos.row + (vel.row * 100)) % WIDTH).teleportCompensate(WIDTH)
            val finalCol = ((pos.col + (vel.col * 100)) % HEIGHT).teleportCompensate(HEIGHT)
            Position(finalRow, finalCol)
        }


    // Count robots in each quadrant
    var q1 = 0
    var q2 = 0
    var q3 = 0
    var q4 = 0

    for (position in finalPositions) {
        if (position.col < (HEIGHT - 1) / 2 && position.row < (WIDTH - 1) / 2) q1++
        if (position.col < (HEIGHT - 1) / 2 && position.row > (WIDTH - 1) / 2) q2++
        if (position.col > (HEIGHT - 1) / 2 && position.row < (WIDTH - 1) / 2) q3++
        if (position.col > (HEIGHT - 1) / 2 && position.row > (WIDTH - 1) / 2) q4++
    }

    val p1 = q1 * q2 * q3 * q4

    // Print the part one solution
    Util.printSolution(14, p1, "Indeterminate")

    // Part two prints any pictures with less than 5 overlapping robots (theoretically if they're making a picture less of them will overlap)
    val shouldRunPartTwo = false

    var seconds = 0
    while (shouldRunPartTwo) {
        // Get the positions of the robots
        val robotsAtTime = input.map { (pos, vel) ->
            val finalRow = ((pos.row + (vel.row * seconds)) % WIDTH).teleportCompensate(WIDTH)
            val finalCol = ((pos.col + (vel.col * seconds)) % HEIGHT).teleportCompensate(HEIGHT)

            Position(finalRow, finalCol)
        }

        // Make a grid of "."s and replace them with "#"s where the robots are
        val array = Array(HEIGHT) { Array(WIDTH) { '.' } }

        for (robot in robotsAtTime) array[robot.col][robot.row] = '#'

        // If there are less than five (this was found by guessing) overlapping robots, print the picture and the seconds at which it happened
        if (robotsAtTime.distinct().size > input.size - 5) {
            println(array.joinToString("\n") { it.joinToString("") })
            println(seconds)
            Thread.sleep(800)
        }

        // Increment seconds
        seconds++
    }
}
