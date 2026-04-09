package org.iesra

import org.iesra.cli.CommandLineParser
import org.iesra.input_output.FileReader
import org.iesra.model.LogEntry
import org.iesra.parser.LogParser
import org.iesra.service.GenerateReport
import org.iesra.service.LogFilter
import org.iesra.service.LogStatsCalculator


fun main(args: Array<String>) {

    // -> PROCESAMIENTO DE COMANDO INTRODUCIDO <-
    val cliParser = CommandLineParser()
    val options = cliParser.parse(args) ?: return

    //-> INICIO DE GESTION DE LOGS <-

    val fileReader = FileReader()
    val parser = LogParser()
    val filter = LogFilter()
    val statsCalculator = LogStatsCalculator()
    val reportGenerator = GenerateReport()

    val lines = try {
        fileReader.readLines(options.inputFile)
    } catch (e: Exception) {
        println("Error: the file could not be read.")
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
            if (!options.ignoreInvalid) invalidLines++
        }

    }

    // FILTRO POR FECHA
    var filteredLogs = filter.filterByDate(
        validLogs,
        options.startDate,
        options.endDate
    )

    // FILTRO POR NIVEL
    if (options.levels != null) {
        filteredLogs = filteredLogs.filter { it.level in options.levels }
    }

    // ESTADÍSTICAS
    val stats = statsCalculator.calculate(
        filteredLogs,
        totalLines,
        invalidLines)

    // SALIDA SEGÚN COMANDO
    val output = if (options.statsOnly){
        stats.toString()
    }else {
        reportGenerator.generateReport(stats)
    }


    // OUTPUT
    if (options.stdout){
        println(output)
    }

    if (options.outputFile != null){
        try {
            java.io.File(options.outputFile).writeText(output)
        }catch (e: Exception) {
            println("Error: the output file could not be written.")
        }
    }

}