import util.Util
import util.Util.firstLong
import util.Util.lastLong

private fun main() {
    val input = Util.loadInputLines(2024, 13)

    var p1 = 0L
    var p2 = 0L

    data class XY(val x: Long, val y: Long)
    data class Goal(val a: XY, val b: XY, val p1: XY, val p2: XY)

    val goals = input
        .chunked(4)
        .map { game ->
            game
                .filter { it.isNotEmpty() }
                .let { (a, b, g) ->
                    Goal(
                        XY(a.firstLong(), a.lastLong()),
                        XY(b.firstLong(), b.lastLong()),
                        XY(g.firstLong(), g.lastLong()),
                        XY(g.firstLong() + 10000000000000, g.lastLong() + 10000000000000)
                    )
                }
        }

    // https://stackoverflow.com/a/42280510
    fun solveSimultaneousEquations(a: Double, b: Double, c: Double, d: Double, e: Double, f: Double): Pair<Double, Double> {
        val det = (a * d - b * c)
        val x = (d * e - b * f) / det
        val y = (a * f - c * e) / det
        return x to y
    }

    for (goal in goals) {
        // Original part 1 solution:
        /*
        for (a in 1..100) {
            for (b in 1..100) {
                if (a * goal.a.x + b * goal.b.x == goal.goal.x && a * goal.a.y + b * goal.b.y == goal.goal.y) {
                    p1 += a * 3 + b * 1
                }
            }
        }
        */

        fun solve(target: XY): Long {
            val (a, b) = solveSimultaneousEquations(
                goal.a.x.toDouble(),
                goal.b.x.toDouble(),
                goal.a.y.toDouble(),
                goal.b.y.toDouble(),
                target.x.toDouble(),
                target.y.toDouble()
            )

            // This is a questionable way to check for decimal places
            if (a.toLong().toDouble() != a || b.toLong().toDouble() != b) return 0

            return a.toLong() * 3L + b.toLong() * 1L
        }
        p1 += solve(goal.p1)
        p2 += solve(goal.p2)
    }
    Util.printSolution(13, p1, p2)
}