import util.Util
import util.Util.firstInt

private fun main() {
    val input = Util.loadInputLines(2024, 7).map { it.split(": ").let { it[0].toLong() to it[1] } }

    var p1 = 0L
    var p2 = 0L

    val operators = listOf('+', '*', '|')

    for ((target, numberString) in input) {
        fun buildPossibilities(input: String): List<String> {
            if (!input.contains(" ")) return listOf(input)

            val options = mutableListOf<String>()

            for (operator in operators) {
                options.addAll(buildPossibilities(input.replaceFirst(' ', operator)))
            }

            return options
        }

        val expressions = buildPossibilities(numberString)

        for (expression in expressions) {
            val isPartTwo = expression.contains('|')

            tailrec fun process(acc: Long, currentString: String): Long {
                if (currentString.isEmpty()) return acc
                val operator = currentString.first()
                val number = currentString.firstInt()

                val resultingNumber = when (operator) {
                    '*' -> acc * number
                    '+' -> acc + number
                    '|' -> (acc.toString() + number.toString()).toLong()
                    else -> TODO("$operator not implemented!")
                }

                return process(resultingNumber, currentString.drop(1).dropWhile { it.isDigit() })
            }

            val result = process(expression.takeWhile { it.isDigit() }.toLong(), expression.dropWhile { it.isDigit() })

            if (result == target) {
                if (isPartTwo) {
                    p2 += target
                } else {
                    p1 += target
                    p2 += target
                }
                break
            }
        }

    }

    Util.printSolution(7, p1, p2)
}