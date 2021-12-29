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

val UNMATCHED_PAREN_TO_VALUE = mapOf<Char, Long>(
    ')' to 1L,
    ']' to 2L,
    '}' to 3L,
    '>' to 4L
)

fun calculateAutocompleteWinner(lines: List<CharArray>): Long {
    var autocompleteScores = mutableListOf<Long>()
    val parenStack = mutableListOf<Char>()

    for (line in lines) {
        inner@ for (char in line) {
            if (char !in PAREN_MATCH) {
                if (parenStack.lastOrNull() != char) {
                    parenStack.clear()
                    break@inner
                } else {
                    parenStack.removeLast()
                }
            } else {
                parenStack.add(PAREN_MATCH[char]!!)
            }
        }

        var autocompleteScore = 0L
        parenStack.reverse()
        for (paren in parenStack) {
            autocompleteScore *= 5L
            autocompleteScore += UNMATCHED_PAREN_TO_VALUE[paren]!!
        }

        if (parenStack.isNotEmpty()) {
            // println("Stack: ${parenStack} • Score: ${autocompleteScore}")
            autocompleteScores.add(autocompleteScore)
        }

        parenStack.clear()
    }

    // println(autocompleteScores)

    return calculateMedian(autocompleteScores)
}

fun calculateMedian(scores: MutableList<Long>): Long {
    scores.sort()

    // println(scores)
    // println(scores.size)
    // println(scores[27])

    return scores[scores.size / 2]!!
}

fun readInput(fileName: String): List<CharArray> {
    val lines: List<CharArray> = File(fileName).readLines().map { line -> line.toCharArray() }

    return lines
}

val fileName = "input.txt"
val lines: List<CharArray> = readInput(fileName)
val autocompleteWinnerScore: Long = calculateAutocompleteWinner(lines)


println(autocompleteWinnerScore)
