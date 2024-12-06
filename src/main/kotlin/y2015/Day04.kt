package y2015

import util.Util
import util.Util.md5

private fun main() {
    val input = Util.loadInputLines(2015, 4).first()

    var p1 = 0
    var p2 = 0

    var i = 0
    while (true) {
        val hash = (input + i).md5()
        if (hash.startsWith("00000") && p1 == 0) p1 = i
        if (hash.startsWith("000000") && p2 == 0) { p2 = i; break }
        i++
    }

    Util.printSolution(4, p1, p2)
}