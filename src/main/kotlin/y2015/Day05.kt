package y2015

import util.Util

private fun main() {
    val input = Util.loadInputLines(2015, 5)

    var p1 = 0
    var p2 = 0

    for (line in input) {
        // Count vowels
        val vowelCheck = line.count { it in "aeiou" } >= 3

        // Check for any double letters
        var lastLetter = ' '
        val doubleLetterCheck = line.any { letter ->
            val isPair = lastLetter == letter
            lastLetter = letter
            return@any isPair
        }

        // Check for invalid strings
        val invalidCheck = listOf("ab", "cd", "pq", "xy").all { !line.contains(it) }

        // Increment part one if all checks pass
        if (vowelCheck && doubleLetterCheck && invalidCheck) p1++

        // Collect all letter pairs and map them to strings
        val letterPairs = line.zipWithNext().map { (a, b) -> "$a$b" }


        val appearanceCount = mutableMapOf<String, Int>()

        var lastPair = ""
        for (pair in letterPairs) {
            // If the same pair appears twice, it's sharing a letter and should be ignored (ie "aaa" is not valid)
            if (pair == lastPair) {
                // Also reset the last pair tracker because "aaaa" is valid
                lastPair = ""
                continue
            }
            // Increment the number of times this pairing has occurred
            appearanceCount[pair] = appearanceCount.getOrDefault(pair, 0) + 1
            lastPair = pair
        }

        // Check if any pairs appeared at least two times
        val pairCheck = appearanceCount.any { it.value >= 2 }

        // Check for repeating letter pairs (ie "aoa" or "eue")
        // Done by using regex to find if there are any matches in a string
        val repeatingLetter = """(\w)\w\1""".toRegex().findAll(line).any()

        // Increment part two if all checks pass
        if (pairCheck && repeatingLetter) p2++
    }

    Util.printSolution(5, p1, p2)
}