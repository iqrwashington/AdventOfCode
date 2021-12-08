#!/usr/bin/env kscript

import java.io.File

data class Report(val oxygenRating: Int, val co2Rating: Int)

data class Diagnostic(val bitValue: String)

// 12 bit values
val BIT_COUNT = 12

fun findXCommon(diagnostics: List<Diagnostic>, index: Int): Pair<Int, Int> {
    var zeros = 0
    var ones = 0

    for (diagnostic in diagnostics) {
        val bit = diagnostic.bitValue[index]
        if (bit == '0') {
            zeros += 1
        } else if (bit == '1') {
            ones += 1
        }
    }

    return Pair(zeros, ones)
}

fun processReport(diagnostics: List<Diagnostic>): Report {
    var oxygen: Diagnostic? = null
    var co2: Diagnostic? = null

    var filteredDiagnostics = diagnostics

    for (index in 0 until BIT_COUNT) {
        val (zeros, ones) = findXCommon(filteredDiagnostics, index)

        filteredDiagnostics = filteredDiagnostics.mapNotNull { diagnostic ->
            if (ones >= zeros && diagnostic.bitValue[index] != '1') {
                null
            } else if (zeros > ones && diagnostic.bitValue[index] != '0') {
                null
            } else {
                diagnostic
            }
        }

        println("${index} • oxygen • ${filteredDiagnostics.size}")

        if (filteredDiagnostics.size == 1) {
            oxygen = filteredDiagnostics.first()
            break
        }
    }

    filteredDiagnostics = diagnostics

    for (index in 0 until BIT_COUNT) {
        val (zeros, ones) = findXCommon(filteredDiagnostics, index)
        filteredDiagnostics = filteredDiagnostics.mapNotNull { diagnostic ->
            if (ones < zeros && diagnostic.bitValue[index] != '1') {
                null
            } else if (zeros <= ones && diagnostic.bitValue[index] != '0') {
                null
            } else {
                diagnostic
            }
        }

        println("${index} • co2 • ${filteredDiagnostics.size}")

        if (filteredDiagnostics.size == 1) {
            co2 = filteredDiagnostics.first()
            break
        }
    }

    println(oxygen)
    println(co2)

    return Report(oxygenRating = oxygen!!.bitValue.toInt(2), co2Rating = co2!!.bitValue.toInt(2))
}

fun readInput(fileName: String): List<Diagnostic> {
    val diagnostics: List<Diagnostic> = File(fileName).readLines().map { line -> Diagnostic(bitValue = line) }

    return diagnostics
}

val fileName = "day3-input.txt"
val diagnostics: List<Diagnostic> = readInput(fileName)
val report: Report = processReport(diagnostics)

val lifeSupportRating: Int = report.oxygenRating * report.co2Rating

println(report)
println(lifeSupportRating)
