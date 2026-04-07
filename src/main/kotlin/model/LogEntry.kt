package org.iesra.model

import java.time.LocalDateTime


data class LogEntry(
    val timestamp: LocalDateTime,
    val level: LogLevel,
    val message: String
)