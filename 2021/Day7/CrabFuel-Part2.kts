#!/usr/bin/env kscript

import kotlin.math.abs
import kotlin.math.floor
import java.io.File

// More expensive further away, likely means MEAN is desired answer
// Walk through list and check distance from mean and add to get amount of fuel

fun findMinimalFuel(crabPositions: List<Int>): Int {
    val perfectValue = floor(crabPositions.average()).toInt()

    // Calculate fuel by summation -- get triangular number
    val minimalFuel = crabPositions.map { crabPosition ->
        val difference = abs(crabPosition - perfectValue)
        // Triangular number is (N * (N + 1)) / 2
        (difference * (difference + 1)) / 2
    }.sum()

    return minimalFuel
}


fun readInput(fileName: String): List<Int> {
    val crabPositions: List<Int> = File(fileName).readLines().map { line -> line.split(",") }.flatten().map { day -> day.toInt() }

    return crabPositions
}

val fileName = "day7-input.txt"
val crabPositions: List<Int> = readInput(fileName)
val minimalFuel: Int = findMinimalFuel(crabPositions)


println(minimalFuel)
