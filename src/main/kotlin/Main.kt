package org.iesra

import org.iesra.input_output.FileReader
import org.iesra.model.LogEntry
import org.iesra.parser.LogParser
import org.iesra.service.GenerateReport
import org.iesra.service.LogFilter
import org.iesra.service.LogStatsCalculator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) {

    // -> PROCESAMIENTO DE COMANDO INTRODUCIDO <-

    // -> PROCESAMIENTO DE ENTRADA <-
    if (args.isEmpty()) {
        println("Uso: <fichero> [startDate] [endDate] [outputFile]")
        return
    }

    val filePath = args[0]

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val startDate = try {
        if (args.size > 1) LocalDateTime.parse(args[1], formatter) else null
    } catch (e: Exception) {
        println("Format Error: incorrect start date")
        return
    }

    val endDate = try {
        if (args.size > 2) LocalDateTime.parse(args[2], formatter) else null
    } catch (e: Exception) {
        println("Format Error: incorrect end date")
        return
    }

    val outputFile = if (args.size > 3) args[3] else null

    //-> INICIO DE GESTION DE LOGS <-

    val fileReader = FileReader()
    val parser = LogParser()
    val filter = LogFilter()
    val statsCalculator = LogStatsCalculator()
    val reportGenerator = GenerateReport()

    val lines = try {
        fileReader.readLines(filePath)
    } catch (e: Exception) {
        println("Error: no se pudo leer el fichero")
        return
    }

    var totalLines = 0
    var invalidLines = 0
    val validLogs = mutableListOf<LogEntry>()

    for (line in lines) {
        totalLines ++

        val parsed = parser.parse(line)
        if (parsed != null) {
            validLogs.add(parsed)
        }else {
            invalidLines++
        }
    }

    // Filtrado de logs
    val filteredLogs = filter.filterByDate(validLogs, startDate, endDate)

    // Calcular Estadísticas
    val stats = statsCalculator.calculate(filteredLogs, totalLines, invalidLines)

    // Generar informe
    val report = reportGenerator.generateReport(stats)

    // -> SALIDA <-
    if (outputFile != null) {
        try {
            java.io.File(outputFile).writeText(report)
        } catch (e: Exception) {
            println("Error: no se pudo escribir el fichero de salida")
        }
    } else {
        println(report)
    }


}