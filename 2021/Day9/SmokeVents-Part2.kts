#!/usr/bin/env kscript

import java.io.File

// Put input into 2D array
// Walk through array checking bounds for each
// Find boundaries -- 9's show where islands are
// Find 3 largest basins

// Recursively find island, only size matters
// Keep track of locations using visited set
// Sort largest sizes (could use max heap for best speed)
// multiply at end

infix fun <T : Comparable<T>> T?.isGreaterThan(other: T?): Boolean =
    if (this != null && other != null) this > other else true


fun recursivelyFindIslandSize(smokeVents: Array<Array<Int>>, visitedIndices: MutableSet<Pair<Int, Int>>, i: Int, j: Int, m: Int, n: Int, depth: Int): Int {
    if (Pair(i, j) in visitedIndices) {
        return 0
    }

    visitedIndices.add(Pair(i, j))
    // println(visitedIndices) recursivelyFindIslandSize(smokeVents, visitedIndices, i, j - 1, m, n, depth + 1) else 0

    val down = if (i + 1 < m && smokeVents[i + 1][j] != 9) recursivelyFindIslandSize(smokeVents, visitedIndices, i + 1, j, m, n, depth + 1) else 0
    val up = if (i - 1 >= 0 && smokeVents[i - 1][j] != 9) recursivelyFindIslandSize(smokeVents, visitedIndices, i - 1, j, m, n, depth + 1) else 0
    val right = if (j + 1 < n && smokeVents[i][j + 1] != 9) recursivelyFindIslandSize(smokeVents, visitedIndices, i, j + 1, m, n, depth + 1) else 0
    val left = if (j - 1 >= 0 && smokeVents[i][j - 1] != 9) recursivelyFindIslandSize(smokeVents, visitedIndices, i, j - 1, m, n, depth + 1) else 0

    val islandSize = down + up + right + left

    // println("Pair [${i}, ${j}] • Value: ${smokeVents[i][j]} • Size: ${islandSize} • Depth: ${depth}")
    return islandSize + 1
}

fun calculateBasinSize(smokeVents: Array<Array<Int>>): Int {
    val islandSizes = mutableListOf<Int>()
    val visitedIndices = mutableSetOf<Pair<Int, Int>>()

    val m = smokeVents.size
    val n = smokeVents.getOrNull(0)!!.size

    for (i in 0 until m) {
        for (j in 0 until n) {
            if (Pair(i, j) in visitedIndices) {
                continue
            }

            if (smokeVents[i][j] == 9) {
                visitedIndices.add(Pair(i, j))
                continue
            }

            val islandSize = recursivelyFindIslandSize(smokeVents, visitedIndices, i, j, m, n, 0)

            // println("Current: [${i},${j}] • ${currentPoint} • LP: ${isLowestPoint}")

            // println(Pair(i, j))
            islandSizes.add(islandSize)
            visitedIndices.add(Pair(i, j))
        }
    }

    islandSizes.sortDescending()

    println(islandSizes)

    return islandSizes.take(3).reduce { acc, i ->  acc * i }
}

fun readInput(fileName: String): Array<Array<Int>> {
    val smokeVents = File(fileName).readLines().map { line -> line.toCharArray().map { it.digitToInt() }.toTypedArray() }.toTypedArray()

    return smokeVents
}

val fileName = "input.txt"
val smokeVents: Array<Array<Int>> = readInput(fileName)
val basinSize: Int = calculateBasinSize(smokeVents)


println(basinSize)
