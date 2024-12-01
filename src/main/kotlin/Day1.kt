import java.io.File
import kotlin.math.abs

data object Day1: Solution() {
    private val input by lazy { File(javaClass.getResource("1")?.toURI() ?: error("Missing input!")).readLines() }

    override fun solve(): Pair<Int, Int> {
        // Split the input into a List<Pair<String, String>>, then convert to List<Pair<Int, Int>>. Finally use .unzip() to convert it to two List<Int> that represent the columns.
        val (first, second) = input.map { it.split("   ").let { (a, b) -> a.toInt() to b.toInt() } }.unzip()

        // Sort each list and recombine them to a List<Pair<Int, Int>> using zip(). Then take the sum of the differences of each paring.
        val partOne = first.sorted().zip(second.sorted()).sumOf { abs(it.first - it.second) }
        // For each element in the first list, find how many times it appears in the second list and multiply by itself. Probably shouldn't count the second list each time, but oh well.
        val partTwo = first.sumOf { n -> second.count { it == n } * n }

        return Pair(partOne, partTwo)
    }


    // Original speedrun solution
    // Part 1: 00:4:43
    // Part 2: 00:7:30

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