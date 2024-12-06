package y2015

import util.Direction
import util.Position
import util.Util
import kotlin.math.min

private fun main() {
    val input = Util.loadInputLines(2015, 3).first()

    var current = Position(0,0)
    var currentr = Position(0,0)
    val visited = mutableSetOf(current)

    var roboturn = false

    for (c in input) {
        if (!roboturn) {
            when (c) {
                'v' -> visited.add(current.move(Direction.SOUTH).also { current = it })
                '^' -> visited.add(current.move(Direction.NORTH).also { current = it })
                '<' -> visited.add(current.move(Direction.WEST).also { current = it })
                '>' -> visited.add(current.move(Direction.EAST).also { current = it })
            }
        } else {
            when (c) {
                'v' -> visited.add(currentr.move(Direction.SOUTH).also { currentr = it })
                '^' -> visited.add(currentr.move(Direction.NORTH).also { currentr = it })
                '<' -> visited.add(currentr.move(Direction.WEST).also { currentr = it })
                '>' -> visited.add(currentr.move(Direction.EAST).also { currentr = it })
            }
        }

        roboturn = !roboturn
    }

    Util.printSolution(1, visited.size)
}