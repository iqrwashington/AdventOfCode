#!/usr/bin/env kscript

import java.io.File
import java.net.URL

fun depthChange(depths: List<Int>): Int {
    var changeCount = 0

    var initialDepthSum = depths[0] + depths[1] + depths[2]
    // Sliding window, so only need to add new value and subtract falling off value
    for (i in 3 until depths.size) {
        var currentDepthSum = initialDepthSum + depths[i] - depths[i - 3]

        if (currentDepthSum > initialDepthSum) {
            changeCount += 1
        }

        initialDepthSum = currentDepthSum
    }

    return changeCount
}

fun readInput(fileName: String): List<Int> {
    val depths: List<Int> = File(fileName).readLines().map { line -> line.toInt() }
    return depths
}

fun readInputFromURL(url: String): List<Int> {
    val depths: List<Int> = URL(url).readText().lines().map { line -> line.toInt() }
    return depths
}


// val day1URL = "https://adventofcode.com/2021/day/1/input"
// val depths: List<Int> = readInputFromURL(day1URL)

val day1FileName = "day1-input.txt"
val depths: List<Int> = readInput(day1FileName)
val actualDepthChange: Int = depthChange(depths)

println(actualDepthChange)
