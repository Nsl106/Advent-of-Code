import java.io.File
import kotlin.math.abs

data object Day1: Solution() {
    private val input by lazy { File(javaClass.getResource("1")?.toURI() ?: error("Missing input!")).readLines() }

    override fun solve(): Pair<Int, Int> {
        val (first, second) = input.map { it.split("   ").let { (a, b) -> a.toInt() to b.toInt() } }.unzip()

        val partOne = first.sorted().zip(second.sorted()).sumOf { abs(it.first - it.second) }
        val partTwo = first.sumOf { n -> second.count { it == n } * n }

        return Pair(partOne, partTwo)
    }


// Original speedrun solution

    /*
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
    */
}