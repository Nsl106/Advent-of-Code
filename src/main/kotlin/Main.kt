fun main() {
    val day = 1

    val solution = Day1.solve()

    val title = "Day $day"
    val partOne = "part one: ${solution.first}"
    val partTwo = "part two: ${solution.second}"

    val maxWidth = maxOf(partOne.length, partTwo.length)
    val widthHalved = (((maxWidth - title.length) + 1) / 2)
    val header = "+" + "-".repeat(widthHalved) + " $title " + "-".repeat(widthHalved) + "+"

    println(header)
    println("| ${partOne.padEnd(header.length - 4)} |")
    println("| ${partTwo.padEnd(header.length - 4)} |")
    println("+" + "-".repeat(header.length - 2) + "+")
}