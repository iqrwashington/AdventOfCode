#!/usr/bin/env kscript

import java.io.File
import java.net.URL

data class Movement(val direction: String, val amount: Int)

data class Position(val horizontal: Int, val depth: Int)

fun calculatePosition(movements: List<Movement>): Position {
    var horizontal = 0
    var depth = 0

    for (movement in movements) {
        if (movement.direction == "forward") {
            horizontal += movement.amount
        } else if (movement.direction == "up") {
            depth -= movement.amount
        } else if (movement.direction == "down") {
            depth += movement.amount
        }
    }

    return Position(horizontal = horizontal, depth = depth)
}

fun readInput(fileName: String): List<Movement> {
    val movements: List<Movement> = File(fileName).readLines().map { line ->
        val (direction, amount) = line.split(" ")
        Movement(direction = direction, amount = amount.toInt())
    }

    return movements
}

val fileName = "day2-input.txt"
val movements: List<Movement> = readInput(fileName)
val position: Position = calculatePosition(movements)

println(position)
println(position.horizontal * position.depth)
