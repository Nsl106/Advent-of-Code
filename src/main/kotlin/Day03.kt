import util.Util

private fun main() {
    // Regex to filter out any valid statements (mul(x,x), do() and don't())
    val regex = """(mul\(\d+,\d+\))|(do\(\))|(don't\(\))""".toRegex()
    // Take the input and extract anything that matches the above regex into a List<String>
    val input = Util.loadInputLines(2024, 3).flatMap { regex.findAll(it).map { it.value } }

    var partOneCount = 0
    var partTwoCount = 0

    var stopped = false

    for (expression in input) {
        when (expression) {
            // Stop and start accordingly
            "do()" -> stopped = false
            "don't()" -> stopped = true

            else -> {
                // Extract the two values from the mul(x,x) statement by splitting at the "," and removing anything that isn't a digit
                val (a, b) = expression.split(",").map { it.filter(Char::isDigit).toInt() }
                val result = a * b

                // Add the result to the part one solution, and part two if not stopped
                partOneCount += result
                if (!stopped) partTwoCount += result
            }
        }
    }

    Util.printSolution(3, partOneCount, partTwoCount)
}


// Original speedrun solution
// Part 1: 00:09:15
// Part 2: 00:13:38

private fun originalPartOne() {
    val regex = """mul\(\d+,\d+\)""".toRegex()

    val input = Util.loadInputLines(2024, 3)
        .flatMap { regex.findAll(it).map { it.value } }

    val one = input.sumOf {
        val (a, b) = it.split(",").map { it.filter { it.isDigit() } }.map { it.toInt() }
        a * b
    }
    println(one)
}

private fun originalPartTwo() {
    val regex = """(mul\(\d+,\d+\))|(do\(\))|(don't\(\))""".toRegex()

//    val input = listOf("""xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))""")
    val input = Util.loadInputLines(2024, 3)
        .flatMap { regex.findAll(it).map { it.value } }


    var totalCount = 0
    var stopped = false

    for (it in input) {
        when (it) {
            "do()" -> {
                stopped = false; continue
            }
            "don't()" -> {
                stopped = true ;continue
            }
        }

        if (!stopped) {
            val (a, b) = it.split(",").map { it.filter { it.isDigit() } }.map { it.toInt() }
            totalCount += a * b
        }
    }

    println(totalCount)
}