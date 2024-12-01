import java.io.File
import kotlin.math.abs

data object Day1: Solution() {
    private val input by lazy { File(javaClass.getResource("1")?.toURI() ?: error("Missing input!")).readLines() }

    override fun solve(): Pair<Int, Int> {
        var one = input.map {
            it.split("   ").first()
        }

        var two = input.map {
            it.split("   ").last()
        }

        one = one.sortedDescending()
        two = two.sortedDescending()

        val f = one.zip(two)

        val partOne = f.sumOf { abs(it.first.toInt() - it.second.toInt()) }
        val partTwo = f.sumOf { two.count { etu -> etu == it.first } * it.first.toInt() }

        return Pair(partOne, partTwo)
    }
}