package org.iesra.input_output

import java.io.File

class FileReader{

    fun readLines(path: String): List<String>{
        val file = File(path)

        if (!file.exists()){
            throw IllegalArgumentException("File does not exist: $path")
        }
        return file.readLines()
    }

}