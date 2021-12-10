#!/usr/bin/env kscript

import kotlin.math.abs
import java.io.File

// Sort crab positions
// Get median -- likely smallest variation
// Walk through list and check distance from median and add to get amount of fuel

fun findMinimalFuel(crabPositions: List<Int>): Int {
    val crabAmount = crabPositions.size
    val medianIndex = crabAmount / 2

    val perfectValue = crabPositions[medianIndex]

    val minimalFuel = crabPositions.map { crabPosition -> abs(crabPosition - perfectValue) }.sum()

    return minimalFuel
}


fun readInput(fileName: String): List<Int> {
    val crabPositions: List<Int> = File(fileName).readLines().map { line -> line.split(",") }.flatten().map { day -> day.toInt() }

    return crabPositions.sorted()
}

val fileName = "day7-input.txt"
val crabPositions: List<Int> = readInput(fileName)
val minimalFuel: Int = findMinimalFuel(crabPositions)


println(minimalFuel)
