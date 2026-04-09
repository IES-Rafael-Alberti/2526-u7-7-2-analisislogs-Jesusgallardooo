package org.iesra.model

import java.time.LocalDateTime

data class LogStats(
    val totalLines: Int,
    val validLines: Int,
    val invalidLines: Int,
    val infoCount: Int,
    val warningCount: Int,
    val errorCount: Int,
    val firstDate: LocalDateTime?,
    val lastDate: LocalDateTime?
)