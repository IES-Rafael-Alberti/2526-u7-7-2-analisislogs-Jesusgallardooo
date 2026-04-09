package org.iesra.cli

import jdk.internal.opt.CommandLine
import org.iesra.model.LogLevel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CommandLineParser {

    fun parse(args: Array<String>): CommandLineOptions? {

        if (args.isEmpty()  || args.contains("--h") || args.contains("-help")) {
            printHelp()
            return null
        }

        var inputFile: String? = null
        var startDate: LocalDateTime? = null
        var endDate: LocalDateTime? = null
        var levels: Set<LogLevel>? = null
        var statsOnly = false
        var report = false
        var outputFile: String? = null
        var stdOut = false
        var ignoreInvalid = false

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")

        var i = 0

        while (i < args.size) {

            when (args[i]) {

                "-i", "--input" -> { inputFile = args.getOrNull(i++) }

                "-f", "--from" -> { startDate = LocalDateTime.parse(args.getOrNull(i++), formatter) }

                "-t", "--to" -> { endDate = LocalDateTime.parse(args.getOrNull(i++), formatter) }

                "-l", "--level" -> {
                    val raw = args.getOrNull(i++)
                    levels = raw?.split(',')?.map { LogLevel.valueOf(it) }?.toSet()
                }

                "-s", "--stats" -> { statsOnly = true }

                "-r", "--report" -> { report = true }

                "-o", "--output" -> { outputFile = args.getOrNull(i++) }

                "-p", "--stdout" -> { stdOut = true }

                "--ignore-invalid" -> { ignoreInvalid = true }

            }
            i++
        }

        // -> VALIDACIONES <-
        if(inputFile == null) {
            println("Error: --input is obligatory")
            return null
        }

        if (!stdOut && outputFile == null) {
            println("Error: u must point --stdout or --output")
            return null
        }

        // -> comportamiento default <-
        if(!statsOnly && !report) {
            report = true
        }

        return CommandLineOptions(
            inputFile,
            startDate,
            endDate,
            levels,
            statsOnly,
            report,
            outputFile,
            stdOut,
            ignoreInvalid
        )

    }

    private fun printHelp() {
        println(
            """
            Uso:
              logtool -i <fichero> [opciones]

            Descripción:
              Procesa un fichero de logs con formato:
                [YYYY-MM-DD HH:MM:SS] NIVEL Mensaje

            Opciones:
              -i, --input <fichero>        Fichero de entrada (obligatorio)
              -f, --from <fechaHora>       Fecha/hora inicial inclusive
              -t, --to <fechaHora>         Fecha/hora final inclusive
              -l, --level <niveles>        INFO, WARNING, ERROR
              -s, --stats                  Solo estadísticas
              -r, --report                 Informe completo
              -o, --output <fichero>       Guardar en fichero
              -p, --stdout                 Mostrar por consola
                  --ignore-invalid         Ignorar líneas inválidas
              -h, --help                   Mostrar ayuda
            """.trimIndent()
        )
    }



}