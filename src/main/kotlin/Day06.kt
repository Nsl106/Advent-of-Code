import util.*

private fun main() {
    val input =
        Util.loadInputLines(2024, 6)
            .map { it.toList() }

    var p1 = 0
    var p2 = 0

    val startRow = input.indexOfFirst { it.indexOf('^') != -1 }
    val startCol = input[startRow].indexOf('^')

    val grid = gridOf(input)

    val obstacles = mutableSetOf<BasePosition>()

    fun runLoop(bonusObstaclePosition: BasePosition, isObstacle: (Grid.GridIndex<Char>?) -> Boolean): MutableSet<Pair<BasePosition, Direction>> {
        val visited = mutableSetOf<Pair<BasePosition, Direction>>()

        var currentPosition: BasePosition = Position(startRow, startCol)
        var currentDirection = Direction.NORTH

        while (true) {
            // Spin until not facing an obstacle
            while (isObstacle(grid[currentPosition].move(currentDirection))) {
                currentDirection = currentDirection.right()
            }

            // Find the next position, or stop if it's outside the grid
            val nextPosition = grid[currentPosition].move(currentDirection) ?: break

            // If we've already visited this position while going the same direction we are stuck in a loop
            if (visited.any { it.first == currentPosition && it.second == currentDirection }) {
                obstacles.add(Position(bonusObstaclePosition.row, bonusObstaclePosition.col))
                break
            }

            // Track that we've been to this tile
            visited.add(currentPosition to currentDirection)

            // Move to the next position
            currentPosition = nextPosition.position
        }

        return visited
    }

    val partOnePath = runLoop(Position(-1, -1)) { it?.value == '#' }

    for (position in partOnePath.map { it.first }) {
        runLoop(position) { it?.value == '#' || (it?.position?.row == position.row && it?.position?.col == position.col) }
    }

    p1 = partOnePath.groupBy { it.first }.size
    p2 = obstacles.filter { grid[it].value !in listOf('#', '^') }.size

    Util.printSolution(6, p1, p2)
}


// Unfinished good solution:

/*
private fun main() {
    val input =
//        Util.loadInputLines(2024, 6)
        """
            ....#.....
            .........#
            ..........
            ..#.......
            .......#..
            ..........
            .#..^.....
            ........#.
            #.........
            ......#...
        """.trimIndent().lines()
            .map { it.toList() }

    var p1 = 0
    var p2 = 0

    val startRow = input.indexOfFirst { it.indexOf('^') != -1 }
    val startCol = input[startRow].indexOf('^')

    var currentPosition: BasePosition = Position(startRow, startCol)
    var currentDirection = Direction.NORTH

    println("Starting at $currentPosition")

    val grid = gridOf(input)

    val visited = mutableSetOf<BasePosition>()
    val obstacles = mutableSetOf<BasePosition>()

    visited.add(currentPosition)

    val lazyPaths = mutableSetOf(currentPosition to currentDirection)

    while (true) {
        for (pathData in lazyPaths) {
            val (pathPosition, pathDirection) = pathData

            // If we are intersecting an already traveled path that is going to the right of our current direction
            // Placing an obstacle in front of use will direct us into that path and create a loop
            if (pathPosition == currentPosition && pathDirection == currentDirection.right()) {
                // Place an obstacle in front of us
                val obstaclePosition = currentPosition.move(currentDirection)
                if (grid.contains(obstaclePosition)) {
                    obstacles.add(obstaclePosition)
                }
            }
        }

        // If we are in front of an obstacle
        if (grid[currentPosition].move(currentDirection)?.value == '#') {
            // Find the segment of path between two obstacles (or the border) we are in
            var searchPosition = grid[currentPosition]
            // Move the opposite of the current direction
            val directionToSearch = currentDirection.opposite()
            while (true) {
                lazyPaths.add(searchPosition.position to currentDirection)

                val nextPos = searchPosition.move(directionToSearch)

                // If the next space is an obstacle or out of bounds, stop searching
                if (nextPos?.value in listOf('#', null)) {
                    break
                } else {
                    // Else, set the search position and try again
                    searchPosition = nextPos!!
                }
            }
        }

        // Now we need to move to the next tile (finally)

        // Spin until not facing an obstacle
        while (grid[currentPosition].move(currentDirection)?.value == '#') {
            currentDirection = currentDirection.right()
        }

        // Find the next position, or stop if it's outside the grid
        val nextPosition = grid[currentPosition].move(currentDirection) ?: break

        // Move to the next position
        currentPosition = nextPosition.position

        // Track that we've been to this tile
        visited.add(currentPosition)
    }

    */
/*
    Position(row=6, col=3)
    Position(row=7, col=6)
    Position(row=8, col=1)
    Position(row=7, col=7)
    Position(row=8, col=3)
    Position(row=9, col=7)
     *//*


    p1 = visited.size

    println(obstacles)
    p2 = obstacles.filter { grid[it].value !in listOf('#', '^') }.size

    Util.printSolution(6, p1, p2)
}*/
