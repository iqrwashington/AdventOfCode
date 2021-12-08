#!/usr/bin/env kscript

import java.io.File

data class Report(val gammaRate: Int, val epsilonRate: Int)

data class Diagnostic(val bitValue: String)

// 12 bit values
val BIT_COUNT = 12

fun processReport(diagnostics: List<Diagnostic>): Report {

    var zeros = IntArray(BIT_COUNT)
    var ones = IntArray(BIT_COUNT)

    for (diagnostic in diagnostics) {
        for ((index, bit) in diagnostic.bitValue.withIndex()) {
            if (bit == '0') {
                zeros[index] += 1
            } else if (bit == '1') {
                ones[index] += 1
            }
        }
    }

    var gamma = IntArray(BIT_COUNT)
    var epsilon = IntArray(BIT_COUNT)
    for (index in 0 until BIT_COUNT) {
        if (ones[index] > zeros[index]) {
            gamma[index] = 1
        } else if (zeros[index] > ones[index]) {
            epsilon[index] = 1
        }
    }

    return Report(gammaRate = gamma.joinToString("").toInt(2), epsilonRate = epsilon.joinToString("").toInt(2))
}

fun readInput(fileName: String): List<Diagnostic> {
    val diagnostics: List<Diagnostic> = File(fileName).readLines().map { line -> Diagnostic(bitValue = line) }

    return diagnostics
}

val fileName = "day3-input.txt"
val diagnostics: List<Diagnostic> = readInput(fileName)
val report: Report = processReport(diagnostics)

val powerConsumption: Int = report.gammaRate * report.epsilonRate

println(report)
println(powerConsumption)
