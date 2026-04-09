package org.iesra.cli

import java.time.LocalDateTime
import org.iesra.model.LogLevel

data class CommandLineOptions(
    val inputFile: String,
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
    val levels: Set<LogLevel>? = null,
    val statsOnly: Boolean = false,
    val report: Boolean = true, // por defecto
    val outputFile: String? = null,
    val stdout: Boolean = false,
    val ignoreInvalid: Boolean = false,
    val help: Boolean = false
)
