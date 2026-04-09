package org.iesra.service

import org.iesra.model.LogEntry
import org.iesra.model.LogLevel
import org.iesra.model.LogStats

class LogStatsCalculator {

    fun calculate(logs: List<LogEntry>, totalLines: Int, invalidLines: Int): LogStats {

        val infoCount = logs.count { it.level == LogLevel.INFO }
        val warningCount = logs.count { it.level == LogLevel.WARNING }
        val errorCount = logs.count { it.level == LogLevel.ERROR }

        val firstDate = logs.minByOrNull { it.timestamp }?.timestamp
        val lastDate = logs.maxByOrNull { it.timestamp }?.timestamp

        return LogStats(
            totalLines = totalLines,
            validLines = logs.size,
            invalidLines = invalidLines,
            infoCount = infoCount,
            warningCount = warningCount,
            errorCount = errorCount,
            firstDate = firstDate,
            lastDate = lastDate
        )
    }

}