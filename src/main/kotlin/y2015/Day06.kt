package y2015

import util.Util
import kotlin.math.max

private fun main() {
    val input = Util.loadInputLines(2015, 6)

    var p1 = 0
    var p2 = 0

    data class Light(var on: Boolean, var level: Int)

    val grid = List(1000) { List(1000) { Light(false, 0) } }

    for (ln in input) {
        val direction = ln.takeWhile { it.isLetter() || it.isWhitespace() }.trim()
        val (first, second) = """\d+,\d+""".toRegex().findAll(ln).map { it.value }.map { it.split(",").let { it[0].toInt() to it[1].toInt() } }.toList()

        for (i in first.first..second.first) {
            for (j in first.second..second.second) {
                val light = grid[i][j]
                when (direction) {
                    "turn on" -> {
                        light.on = true
                        light.level += 1
                    }

                    "turn off" -> {
                        light.on = false
                        light.level = max(light.level - 1, 0)
                    }

                    "toggle" -> {
                        light.on = !light.on
                        light.level += 2
                    }
                }
            }
        }
    }

    p1 = grid.sumOf { it.count { it.on } }
    p2 = grid.sumOf { it.sumOf { it.level } }

    Util.printSolution(6, p1, p2)
}