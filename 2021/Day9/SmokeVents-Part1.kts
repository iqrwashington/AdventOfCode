#!/usr/bin/env kscript

import java.io.File

// Put input into 2D array
// Walk through array checking bounds for each
// Add risk level if low area (value + 1)
// Return sum

infix fun <T : Comparable<T>> T?.isGreaterThan(other: T?): Boolean =
    if (this != null && other != null) this > other else true

infix fun <T : Comparable<T>> T?.isGreaterThanOrEqual(other: T?): Boolean =
    if (this != null && other != null) this >= other else true

infix fun <T : Comparable<T>> T?.isLessThan(other: T?): Boolean =
    if (this != null && other != null) this < other else true

infix fun <T : Comparable<T>> T?.isLessThanOrEqual(other: T?): Boolean =
    if (this != null && other != null) this <= other else true


fun calculateRiskLevel(smokeVents: Array<Array<Int>>): Int {
    var total = 0

    val m = smokeVents.size
    val n = smokeVents.getOrNull(0)!!.size

    for (i in 0 until m) {
        for (j in 0 until n) {
            val currentPoint = smokeVents[i][j]

            val leftPoint = smokeVents.getOrNull(i - 1)?.getOrNull(j)
            val rightPoint = smokeVents.getOrNull(i + 1)?.getOrNull(j)
            val topPoint = smokeVents.getOrNull(i)?.getOrNull(j - 1)
            val bottomPoint = smokeVents.getOrNull(i)?.getOrNull(j + 1)

            val isLowestPoint =
                (leftPoint isGreaterThan currentPoint) &&
                (rightPoint isGreaterThan currentPoint) &&
                (topPoint isGreaterThan currentPoint) &&
                (bottomPoint isGreaterThan currentPoint)

            // println("Current: [${i},${j}] • ${currentPoint} • LP: ${isLowestPoint}")

            if (isLowestPoint) {
                total += (currentPoint + 1)
            }
        }
    }

    return total
}

fun readInput(fileName: String): Array<Array<Int>> {
    val smokeVents = File(fileName).readLines().map { line -> line.toCharArray().map { it.digitToInt() }.toTypedArray() }.toTypedArray()

    return smokeVents
}

val fileName = "input.txt"
val smokeVents: Array<Array<Int>> = readInput(fileName)
val riskLevel: Int = calculateRiskLevel(smokeVents)


println(riskLevel)
