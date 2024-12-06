package y2015

import util.Util
import kotlin.math.min

private fun main() {
    val input = Util.loadInputLines(2015, 2).map { it.split("x").map { it.toInt() } }

    fun getWrappingPaper(w: Int, l: Int, h: Int) = 2 * l * w + 2 * w * h + 2 * h * l + min(2 * l * w, min(2 * w * h, 2 * h * l)) / 2 to listOf(l, w, h).sorted().take(2).sum().times(2).plus(l*w*h)

    val p1 = input.sumOf { getWrappingPaper(it[0], it[1], it[2]).first }
    val p2 = input.sumOf { getWrappingPaper(it[0], it[1], it[2]).second }

    Util.printSolution(1, p1, p2)
}