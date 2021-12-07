#!/usr/bin/env kscript

import java.io.File
import java.net.URL

data class Movement(val direction: String, val amount: Int)

data class Position(val horizontal: Int, val depth: Int, val aim: Int)

fun calculatePosition(movements: List<Movement>): Position {
    var horizontal = 0
    var depth = 0
    var aim = 0

    for (movement in movements) {
        if (movement.direction == "forward") {
            horizontal += movement.amount
            depth += aim * movement.amount
        } else if (movement.direction == "up") {
            aim -= movement.amount
        } else if (movement.direction == "down") {
            aim += movement.amount
        }
    }

    return Position(horizontal = horizontal, depth = depth, aim = aim)
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
