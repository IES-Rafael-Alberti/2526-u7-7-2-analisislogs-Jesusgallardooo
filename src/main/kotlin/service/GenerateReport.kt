package org.iesra.service

import org.iesra.model.LogStats

class GenerateReport {

    fun generateReport(stats: LogStats): String {
        return """
            === INFORME DE LOGS ===
            Total líneas procesadas: ${stats.totalLines}
            Líneas válidas: ${stats.validLines}
            Líneas inválidas: ${stats.invalidLines}
            
            INFO: ${stats.infoCount}
            WARNING: ${stats.warningCount}
            ERROR: ${stats.errorCount}
            
            Primera fecha: ${stats.firstDate}
            Última fecha: ${stats.lastDate}
        """.trimIndent()
    }

}