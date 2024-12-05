import util.Util

// Slightly fixed speedrun solution
// Part 1: 00:20:52
// Part 2: 01:03:07

// Notes for improvement:
// Don't forget .groupBy()
// Don't get stuck on solutions that aren't working, mentally figure it out before writing something bad

private fun main() {
    val input = Util.loadInputLines(2024, 5)

    // Collect rules until the first empty line, then split around the "|" and group into a Map<Int, List<Pair<Int, Int>>> based on the number and the rules that apply to it.
    val rules = input.takeWhile(String::isNotEmpty).map { ln -> ln.split("|").let { it[0].toInt() to it[1].toInt() } }.groupBy { it.first }
    // Collect pages in reverse until the first empty line, then split around the "," and map to a List<Int>.
    val pages = input.takeLastWhile(String::isNotEmpty).map { ln -> ln.split(",").map(String::toInt) }

    // Returns a list of all rules broken by the given page
    fun getBrokenRules(page: List<Int>): List<Pair<Int, Int>> {
        val allBrokenRules = mutableListOf<Pair<Int, Int>>()
        val exploredNumbers = mutableListOf<Int>()
        for (item in page) {
            // A rule is broken if the second number in the pair has already been passed as signified by exploredNumbers
            val brokenRules = rules[item]?.filter { it.second in exploredNumbers }

            // If any rules have been broken, add them to the list
            if (brokenRules != null) allBrokenRules.addAll(brokenRules)

            // Track which numbers have been looked at
            exploredNumbers.add(item)
        }
        return allBrokenRules
    }

    // Fixes any issues in the given page according to the rules
    fun fix(page: List<Int>): List<Int> {
        val currentlyBrokenRules = getBrokenRules(page)
        // If no rules are broken, return the current list
        if (currentlyBrokenRules.isEmpty()) return page

        // Choose a rule to fix (the first one in the list) and split it into the wrongly placed number and the number it should be before
        val (wrongNumber, ruleNumber) = currentlyBrokenRules.first()

        // Create a fixed version of the page by removing the incorrect number and re-adding it directly before the number the rule specifies it must be before
        val fixed = page.toMutableList()
            .apply {
                remove(wrongNumber)
                add(indexOf(ruleNumber), wrongNumber)
            }

        // Run the method again to fix the next rule in line
        return fix(fixed)
    }


    var p1 = 0
    var p2 = 0

    for (page in pages) {
        val brokenRules = getBrokenRules(page)

        if (brokenRules.isEmpty()) {
            // If no rules were broken, add the center number to part one
            p1 += page[page.size / 2]
        } else {
            // Else, fix the page and add the center number to part two
            val fixed = fix(page)
            p2 += fixed[fixed.size / 2]
        }
    }

    Util.printSolution(5, p1, p2)
}
