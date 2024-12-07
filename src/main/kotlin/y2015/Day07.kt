package y2015

import util.Util

private fun main() {
    val puzzleInput = Util.loadInputLines(2015, 7)

    val wires = mutableMapOf<String, () -> Int>()
    val cache = mutableMapOf<String, Int>()

    for (ln in puzzleInput) {
        val (input, output) = ln.split(" -> ")

        val getOutputValue = {
            // Evaluate the value of a string, either a plain number or a wire to process
            fun getValueOfString(str: String) = cache.getOrPut(str) { ->
                if (str.all { it.isDigit() }) str.toInt()
                else wires.getValue(str).invoke()
            }

            when {
                input.contains("AND") -> input.split(" AND ").map(::getValueOfString).let { (l, r) -> l and r }
                input.contains("OR") -> input.split(" OR ").map(::getValueOfString).let { (l, r) -> l or r }
                input.contains("RSHIFT") -> input.split(" RSHIFT ").let { (l, r) -> getValueOfString(l) shr r.toInt() }
                input.contains("LSHIFT") -> input.split(" LSHIFT ").let { (l, r) -> getValueOfString(l) shl r.toInt() }
                input.contains("NOT") -> getValueOfString(input.removePrefix("NOT ")).toUShort().inv().toInt()
                else -> getValueOfString(input)
            }
        }

        wires[output] = getOutputValue
    }

    // Get the value of wire "a"
    val p1 = wires["a"]!!.invoke()

    // Reset
    cache.clear()
    // Override wire "b" to match the result of part one
    wires["b"] = { p1 }
    // Get the new value of wire "a"
    val p2 = wires["a"]!!.invoke()

    Util.printSolution(7, p1, p2)
}