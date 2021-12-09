#!/usr/bin/env kscript

import java.io.File

data class Point(val x: Int, val y: Int)

data class VentLine(val startPoint: Point, val endPoint: Point)

fun findAmountOfOverlappingPoints(ventLines: List<VentLine>): Int {
    val allPoints = mutableSetOf<Point>()
    var overlappingPoints = mutableSetOf<Point>()

    for ((startPoint, endPoint) in ventLines) {
        val (startX, startY) = startPoint
        val (endX, endY) = endPoint
        if (startX == endX) {
            val x = startX
            // Walk through all y points
            for (y in minOf(startY, endY)..maxOf(startY, endY)) {
                val newPoint = Point(x, y)
                if (newPoint in allPoints) {
                    overlappingPoints.add(newPoint)
                }
                allPoints.add(newPoint)
            }
        } else if (startY == endY) {
            val y = startY
            // Walk through all x points
            for (x in minOf(startX, endX)..maxOf(startX, endX)) {
                val newPoint = Point(x, y)
                if (newPoint in allPoints) {
                    overlappingPoints.add(newPoint)
                }
                allPoints.add(newPoint)
            }
        } else {
            // Guaranteed to be 1 as slope so lines can work in 2d array
            // Find slope sign
            // Walk from left to right, incrementing down on Y if negative slope
            val isNegativeSlope = (startX < endX && startY > endY) || (startX > endX && startY < endY)

            // Go from left to right but increment Y depending on slope
            val beginX = minOf(startX, endX)
            val endX = maxOf(startX, endX)
            val beginY = if (beginX == startX) startY else endY
            val endY = if (beginX == startX) endY else startY

            var currentY = beginY

            for (x in beginX..endX) {
                val newPoint = Point(x, currentY)
                if (newPoint in allPoints) {
                    overlappingPoints.add(newPoint)
                }
                allPoints.add(newPoint)

                if (isNegativeSlope) {
                    currentY -= 1
                } else {
                    currentY += 1
                }
            }
        }
    }

    return overlappingPoints.size
}

fun readInput(fileName: String): List<VentLine> {
    val parseLineRegex = "(\\d+),(\\d+) -> (\\d+),(\\d+)".toRegex()
    val ventLines: List<VentLine> = File(fileName).readLines().map { line ->
        val result = parseLineRegex.matchEntire(line)
        val (_, startX, startY, endX, endY) = result!!.groupValues
        VentLine(startPoint = Point(startX.toInt(), startY.toInt()), endPoint = Point(endX.toInt(), endY.toInt()))
    }

    return ventLines
}

val fileName = "day5-input.txt"
val ventLines: List<VentLine> = readInput(fileName)
val overlappingPointCount: Int = findAmountOfOverlappingPoints(ventLines)

println(overlappingPointCount)
