package org.iesra.service

import org.iesra.model.LogEntry
import java.time.LocalDateTime

class LogFilter {

    fun filterByDate(logs: List<LogEntry>, startDate: LocalDateTime? = null, endDate: LocalDateTime? = null): List<LogEntry> {

        val filteredLogsByDate = logs.filter { log ->
            val afterStart = startDate == null || log.timestamp >= startDate
            val beforeEnd = endDate == null || log.timestamp <= endDate

            afterStart && beforeEnd
        }

        return filteredLogsByDate
    }

}