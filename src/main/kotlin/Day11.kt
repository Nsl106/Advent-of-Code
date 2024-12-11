import util.Util
import util.Util.isEven


// Cleaned up speedrun solution
// Part 1: 00:08:20
// Part 2: 00:20:30

private fun main() {
    val input = Util.loadInputLines(2024, 11).first()

    var p1 = 0L
    var p2 = 0L

    val stones = input.split(" ").map { it.toLong() }

    // Cache each stone and the number of blinks it still has to go
    val cache = mutableMapOf<Pair<Long, Int>, Long>()

    // Count the number of stones that will be created from a given stone after the specified blinks
    fun calculate(stone: Long, blinks: Int): Long {
        // If there are no more blinks, there is only the given stone
        if (blinks == 0) return 1

        val nextBlinks = blinks - 1

        return cache.getOrPut(stone to blinks) {
            // If the stone is 0, change to 1
            if (stone == 0L) {
                calculate(1, nextBlinks)
            } else {
                val stringStone = stone.toString()
                if (stringStone.length.isEven()) {
                    // If the stone is an even number of digits, turn into two stones
                    val firstStone = calculate(stringStone.take(stringStone.length / 2).toLong(), nextBlinks)
                    val secondStone = calculate(stringStone.takeLast(stringStone.length / 2).toLong(), nextBlinks)
                    firstStone + secondStone
                } else {
                    // Otherwise multiply the stone by 2024
                    calculate(stone * 2024, nextBlinks)
                }
            }
        }
    }

    // Final calculation starting with 25 blinks for part one and 75 blinks for part two
    p1 += stones.sumOf { calculate(it, 25) }
    cache.clear()
    p2 += stones.sumOf { calculate(it, 75) }

    Util.printSolution(11, p1, p2)
}