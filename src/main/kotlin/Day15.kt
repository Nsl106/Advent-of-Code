import util.Util

private fun partTwo(input: List<String>): Int {
    var grid = input.takeWhile { it.isNotEmpty() }.map { ln ->
        ln.flatMap { char ->
            when (char) {
                '#' -> listOf('#', '#')
                'O' -> listOf('[', ']')
                '.' -> listOf('.', '.')
                '@' -> listOf('@', '.')
                else -> error("no")
            }
        }
    }.map { it.toMutableList() }
    val moves = input.dropWhile { it.isNotEmpty() }.drop(1).flatMap { it.toList() }

    var score = 0

    var currentRobot = 0 to 0

    for (y in grid.indices)
        for (x in grid[0].indices)
            if (grid[y][x] == '@') currentRobot = x to y

    for (move in moves) {
        val (dx, dy) = when (move) {
            '>' -> 1 to 0
            '^' -> 0 to -1
            'v' -> 0 to 1
            '<' -> -1 to 0
            else -> error("")
        }

        val (rx, ry) = currentRobot

        when (val nextValue = grid[ry + dy][rx + dx]) {
            '.' -> {
                grid[ry + dy][rx + dx] = '@'
                grid[ry][rx] = '.'
                currentRobot = rx + dx to ry + dy
            }

            '#' -> {}

            in "[]" -> {
                if (move in "<>") {
                    var spaceOnEnd: Char
                    var travelToEnd = 0
                    do {
                        travelToEnd++
                        spaceOnEnd = grid[ry + dy * travelToEnd][rx + dx * travelToEnd]
                    } while (spaceOnEnd in "[]")

                    if (spaceOnEnd == '#') {
                        // Can't move
                    } else {
                        var currentDistance = 0
                        while (true) {
                            currentDistance++
                            val currentChar = grid[ry + dy * currentDistance][rx + dx * currentDistance]
                            if (currentChar == '.') {
                                grid[ry + dy * currentDistance][rx + dx * currentDistance] = "[]".filterNot { it == nextValue }.first()
                                break
                            }
                            if (currentChar == '[') grid[ry + dy * currentDistance][rx + dx * currentDistance] = ']'
                            if (currentChar == ']') grid[ry + dy * currentDistance][rx + dx * currentDistance] = '['
                        }
                        grid[ry + dy][rx + dx] = '@'
                        grid[ry][rx] = '.'
                        currentRobot = rx + dx to ry + dy
                    }
                } else {
                    val gridCopy = grid.map { it.toMutableList() }.toMutableList()
                    fun move(part: Pair<Int, Int>): Boolean {
                        val firstX = part.first
                        val boxY = part.second

                        val first = gridCopy[boxY][firstX]
                        val (leftX, rightX) = if (first == '[') firstX to firstX + 1 else firstX - 1 to firstX

                        var leftAboveChar = gridCopy[boxY + dy][leftX]
                        var rightAboveChar = gridCopy[boxY + dy][rightX]

                        if (leftAboveChar == '#' || rightAboveChar == '#') return false

                        var isMovable = true
                        if (leftAboveChar == ']') isMovable = isMovable && move(leftX to boxY + dy)
                        if (rightAboveChar == '[') isMovable = isMovable && move(rightX to boxY + dy)
                        if (leftAboveChar == '[' && rightAboveChar == ']') isMovable = isMovable && move(rightX to boxY + dy)
                        if (!isMovable) return false

                        leftAboveChar = gridCopy[boxY + dy][leftX]
                        rightAboveChar = gridCopy[boxY + dy][rightX]

                        if (leftAboveChar == '.' && rightAboveChar == '.') {
                            gridCopy[boxY + dy][leftX] = '['
                            gridCopy[boxY + dy][rightX] = ']'
                            gridCopy[boxY][leftX] = '.'
                            gridCopy[boxY][rightX] = '.'
                        } else error("")

                        return true
                    }

                    val moveSuccess = move(rx + dx to ry + dy)

                    if (moveSuccess) {
                        grid = gridCopy
                        grid[ry + dy][rx + dx] = '@'
                        grid[ry][rx] = '.'
                        currentRobot = rx + dx to ry + dy
                    }

                }
            }
        }
    }

    for (y in grid.indices)
        for (x in grid[0].indices)
            if (grid[y][x] == '[') score += (100 * y) + x

    return score
}

private fun partOne(input: List<String>): Int {
    val grid = input.takeWhile { it.isNotEmpty() }.map { it.toMutableList() }
    val moves = input.dropWhile { it.isNotEmpty() }.drop(1).flatMap { it.toList() }

    var score = 0

    var currentRobot = 0 to 0
    for (y in grid.indices)
        for (x in grid[0].indices)
            if (grid[y][x] == '@') currentRobot = x to y

    for (move in moves) {
        val (dx, dy) = when (move) {
            '>' -> 1 to 0
            '^' -> 0 to -1
            'v' -> 0 to 1
            '<' -> -1 to 0
            else -> error("")
        }

        val (rx, ry) = currentRobot

        // Get the next wall or open space
        var steps = 0
        var currentValue: Char
        do {
            steps++
            currentValue = grid[ry + dy * steps][rx + dx * steps]
        } while (currentValue !in listOf('#', '.'))

        if (currentValue == '#') {
            // Do nothing
        } else if (currentValue == '.') {
            grid[ry + dy * steps][rx + dx * steps] = 'O'
            grid[ry + dy][rx + dx] = '@'
            grid[ry][rx] = '.'
            currentRobot = rx + dx to ry + dy
        }
    }

    for (y in grid.indices)
        for (x in grid[0].indices)
            if (grid[y][x] == 'O') score += (100 * y) + x

    return score
}

private fun main() {
    val input = Util.loadInputLines(2024, 15)
    val p1 = partOne(input)
    val p2 = partTwo(input)

    Util.printSolution(15, p1, p2)
}