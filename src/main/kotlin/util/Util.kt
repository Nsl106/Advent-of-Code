package util

import java.io.File
import java.security.MessageDigest

object Util {

    /** Returns the first int present in the string. Determined by [Char.isDigit]. */
    fun String.firstInt() = dropWhile { !it.isDigit() }.takeWhile { it.isDigit() }.toInt()

    /** Returns the last int present in the string. Determined by [Char.isDigit]. */
    fun String.lastInt() = dropLastWhile { !it.isDigit() }.takeLastWhile { it.isDigit() }.toInt()

    /** Returns the first word present in the string. Determined by [Char.isLetter]. */
    fun String.firstWord() = dropWhile { !it.isLetter() }.takeWhile { it.isLetter() }

    /** Returns the last word present in the string. Determined by [Char.isLetter]. */
    fun String.lastWord() = dropLastWhile { !it.isLetter() }.takeLastWhile { it.isLetter() }

    fun Int.squared() = this * this
    fun Long.squared() = this * this

    /** Returns and removes the first element from the list. */
    fun <T> MutableList<T>.pop() = first().also { removeFirst() }

    /** Flips the axis of this list. For example a list of [[1, 2], [3, 4]] would be turned into [[1, 3], [2, 4]] */
    fun <T> List<List<T>>.flipAxis(): List<List<T>> {
        val rowCount = this.size
        val colCount = this[0].size
        val rows = this
        val cols = mutableListOf<List<T>>()

        for (i in 0..<colCount) {
            val col = mutableListOf<T>()
            rows.forEachIndexed { index, _ ->
                col.add(this[index][i])
            }
            cols.add(col)
        }
        return cols
    }


    /** Returns the greatest common denominator of two numbers. */
    fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a
        else gcd(b, a % b)
    }

    /** Returns the greatest common denominator of many numbers. */
    fun gcd(vararg values: Int): Int {
        assert(values.isNotEmpty())
        if (values.size == 1) return values[0]
        return values.fold(values[0]) { acc, v -> gcd(acc, v) }
    }

    /** Returns the lowest common multiple of two numbers. */
    fun lcm(a: Int, b: Int): Int {
        return a * (b / gcd(a, b))
    }

    /** Returns the lowest common multiple of many numbers. */
    fun lcm(vararg values: Int): Int {
        assert(values.isNotEmpty())
        if (values.size == 1) return values[0]
        return values.fold(values[0]) { acc, v -> lcm(acc, v) }
    }

    fun Int.isEven() = this % 2 == 0
    fun Int.isOdd() = this % 2 != 0

    fun Long.isEven() = this % 2 == 0L
    fun Long.isOdd() = this % 2 != 0L

    /** Returns the MD5 hash of a string. */
    @OptIn(ExperimentalStdlibApi::class) fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(this.toByteArray())
        return digest.toHexString()
    }

    /** Steps in cardinal directions that may be taken from a given position in a 2d grid. */
    val gridDirectionsCardinal = setOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
    /** Diagonal steps that may be taken from a given position in a 2d grid. */
    val gridDirectionsDiagonal = setOf(1 to 1, 1 to -1, -1 to 1, -1 to -1)
    /** Steps that may be taken from a given position in a 2d grid including cardinal directions and diagonals. */
    val gridDirectionsCardinalAndDiagonal = gridDirectionsCardinal + gridDirectionsDiagonal

    /** Multiplies each number in this pair by [other]*/
    fun Pair<Int, Int>.times(other: Int) = first * other to second * other

    /** Returns the position in this grid. Equivalent to `list[position.first][position.second]`*/
    operator fun <T> List<List<T>>.get(position: Pair<Int, Int>) = this[position.first][position.second]
    /** Returns the position in this grid. Equivalent to `list[a][b]` */
    operator fun <T> List<List<T>>.get(a: Int, b: Int) = this[a][b]
    /** Returns the position in this grid or null if out of bounds. Equivalent to `list.getOrNull(a)?.getOrNull(b)` */
    fun <T> List<List<T>>.getOrNull(a: Int, b: Int) = this.getOrNull(a)?.getOrNull(b)
    /** Returns the position in this grid or null if out of bounds. Equivalent to `list.getOrNull(position.first)?.getOrNull(position.second)` */
    fun <T> List<List<T>>.getOrNull(position: Pair<Int, Int>) = this.getOrNull(position.first)?.getOrNull(position.second)

    fun printSolution(day: Int, partOne: Any? = null, partTwo: Any? = null) {
        val title = "Day $day"
        val partOneText = "part one: $partOne"
        val partTwoText = "part two: $partTwo"
        val emptyText = "No Solution"

        val maxWidth = maxOf(partOneText.length, partTwoText.length)
        val headerHalfSpacing = (((maxWidth - title.length) + 1) / 2)
        val header = "+" + "-".repeat(headerHalfSpacing) + " $title " + "-".repeat(headerHalfSpacing) + "+"
        val emptyHalfSpacing = (((maxWidth - emptyText.length) + 1) / 2)
        val empty = "|" + " ".repeat(emptyHalfSpacing) + " $emptyText " + " ".repeat(emptyHalfSpacing) + "|"

        println(header)
        if (partOne != null) println("| ${partOneText.padEnd(header.length - 4)} |")
        if (partTwo != null) println("| ${partTwoText.padEnd(header.length - 4)} |")
        if (partOne == null && partTwo == null) println(empty)
        println("+" + "-".repeat(header.length - 2) + "+")
    }

    /** Loads the puzzle input from a resource file with the integer name of the day. Returns a `List<String>` representing each line. */
    fun loadInputLines(year: Int, day: Int) = File(javaClass.getResource("/$year/$day")?.toURI() ?: error("Missing input for $year/$day!")).readLines().dropLastWhile { it.isBlank() }
}