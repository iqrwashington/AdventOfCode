#!/usr/bin/env kscript

import java.io.File

// Find and discard "corrupted lines" i.e. lines with unbalanced parens

val PAREN_MATCH = mapOf<Char, Char>(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>'
)

// take the first illegal character on the line and look it up in the following table

val ILLEGAL_PAREN_TO_VALUE = mapOf<Char, Int>(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
)

fun calculateSyntaxErrorScore(lines: List<CharArray>): Int {
    var totalSyntaxErrorScore = 0
    val parenStack = mutableListOf<Char>()

    for (line in lines) {
        inner@ for (char in line) {
            // println(char)
            if (char !in PAREN_MATCH) {
                if (parenStack.lastOrNull() != char) {
                    totalSyntaxErrorScore += ILLEGAL_PAREN_TO_VALUE[char]!!
                    break@inner
                } else {
                    parenStack.removeLast()
                }
            } else {
                parenStack.add(PAREN_MATCH[char]!!)
            }
        }
        parenStack.clear()
    }

    return totalSyntaxErrorScore
}

fun readInput(fileName: String): List<CharArray> {
    val lines: List<CharArray> = File(fileName).readLines().map { line -> line.toCharArray() }

    return lines
}

val fileName = "input.txt"
val lines: List<CharArray> = readInput(fileName)
val syntaxErrorScore: Int = calculateSyntaxErrorScore(lines)


println(syntaxErrorScore)
