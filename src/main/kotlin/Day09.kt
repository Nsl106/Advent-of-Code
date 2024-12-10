import util.Util
import java.util.stream.Collectors.toList

private fun main() {
    val demoInput = """
    2333133121414131402
    """.trimIndent()
//
    val input =
        Util.loadInputLines(2024, 9).first()
//        demoInput
            .toList()

    var p1 = 0L
    var p2 = 0L

    var currentID = 0
    var task = 1 // 1 = block, 0 = empty space

    val builtList = mutableListOf<Int>()
    for (character in input) {
        if (task == 1) {
            for (i in 1..character.digitToInt()) {
                builtList.add(currentID)
            }
            currentID++
            task = 0
        } else if (task == 0) {
            for (i in 1..character.digitToInt()) {
                builtList.add(-1)
            }
            task = 1
        }
    }

    val copy = builtList.toMutableList()

    for (id in currentID  downTo 0) {
        println("PROCESSING: $id")
        val targetItem = copy.filter { it == id }
        val targetItemSize = targetItem.size

        var firstAppropriateEmptyOrNull: Int? = null

//        println(copy.takeWhile { it != id })
        copy.takeWhile { it != id }.foldIndexed(0) { index, acc, id ->
            val retval = if (id == -1) acc + 1 else 0
            if (retval >= targetItemSize && firstAppropriateEmptyOrNull == null) firstAppropriateEmptyOrNull = index - acc
//            println(retval)
            retval
        }


        val firstAppropriateEmpty = firstAppropriateEmptyOrNull ?: continue


        while (true) {
            val indexToReplace = copy.indexOfLast { it == id }
            if (indexToReplace == -1) break
            copy.set(indexToReplace, -1)
        }

        for (i in 0 until targetItemSize) {
            copy.set(firstAppropriateEmpty + i, targetItem[i])
        }
        println(copy.map { if (it == -1) "." else it })
    }

    var count = 0L

    for ((index, id) in copy.withIndex()) {
        if (id != -1)
            count += id * index
    }

    println(count)
//
//    var index = 0L
//    p1 = copy.sumOf {
//        if (it != -1) {
//            index++
//            return@sumOf it * index
//        } else 0L
//    }

    Util.printSolution(9, p1, p2)
}


// Original speedrun solution
// Part 1: 00:00:00
// Part 2: 00:00:00