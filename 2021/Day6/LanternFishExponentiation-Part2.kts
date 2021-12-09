#!/usr/bin/env kscript

import java.io.File

// Instead of iterating through array each day and changing values and appending, keep track of amount of fish in each day
// All of Day 0 become 6, and create 8s
// All of Day1 become 0, Day2 become 1, etc.

fun findTotalFish(fishAgeCounts: LongArray, days: Int): Long {
    for (day in 0 until days) {

        val creatingFishCount = fishAgeCounts[0]
        for (age in 1..8) {
            fishAgeCounts[age - 1] = fishAgeCounts[age]
        }

        fishAgeCounts[8] = creatingFishCount
        fishAgeCounts[6] += creatingFishCount
    }

    return fishAgeCounts.sum()
}

fun readInput(fileName: String): LongArray {
    val fishAges: List<Int> = File(fileName).readLines().map { line -> line.split(",") }.flatten().map { day -> day.toInt() }

    val fishAgeCounts = LongArray(9)
    for (fishAge in fishAges) {
        fishAgeCounts[fishAge] += 1L
    }

    return fishAgeCounts
}

val fileName = "day6-input.txt"
val fishAgeCounts: LongArray = readInput(fileName)
val days = 256
val totalFishCount: Long = findTotalFish(fishAgeCounts, days)

println(totalFishCount)
