package org.iesra.parser

import org.iesra.model.LogEntry
import org.iesra.model.LogLevel
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

class LogParser {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val regex = Regex("""(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}) (INFO|WARNING|ERROR): (.*)""")

    fun parse(line: String): LogEntry? {

        val match = regex.matchEntire(line) ?: return null

        try {
            val (timeStampStr, levelStr, message) = match.destructured

            val timestamp = LocalDateTime.parse(timeStampStr, formatter)
            val level = LogLevel.valueOf(levelStr)

            return LogEntry(timestamp, level, message)

        } catch (e: Exception) {
            return null // línea inválida
        }

    }

}