#!/usr/bin/env kscript

import java.io.File
import java.net.URL

fun depthChange(depths: List<Int>): Int {
    var changeCount = 0

    var initialDepth = 0
    for (i in 1 until depths.size) {
        var currentDepth = depths[i]

        if (currentDepth > initialDepth) {
            changeCount += 1
        }

        initialDepth = currentDepth
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
