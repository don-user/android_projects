package ru.yundon.whois.utils

import android.content.Context
import android.util.Log
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class FileSaveAndGet(private val context: Context) {

    fun getWhoisFile(nameFile: String?): String {

        var fileInput: FileInputStream? = null
        var text = ""
        try {
            fileInput = context.openFileInput(nameFile)
            val bytes = ByteArray(fileInput.available())
            fileInput.read(bytes)
            text = String(bytes)

        } catch (e: IOException) {
            Log.d("TAG НЕТ ФАЙЛА", e.toString())
        } finally {
            try {
                Log.d("TAG", "FINALLY В СОХРАНЕНИИ ФАЙЛЫ, ЗАПУСК TRY")
                fileInput?.close()
            } catch (e: IOException) {
                Log.d("TAG finally, ошибка", e.toString())
            }
        }
        return text
    }

    fun saveWhoisFile(nameFile: String?, fileBody: String) {
        var fileOutput: FileOutputStream? = null
        try {
            fileOutput = if (nameFile == Constants.FILE_NAME) {
                context.openFileOutput(nameFile, Context.MODE_APPEND)
            } else context.openFileOutput(nameFile, Context.MODE_PRIVATE)

            fileOutput.write(fileBody.toByteArray())

        } catch (e: IOException) {
            Log.d("TAG SAVE MESSAGE", e.message.toString())
        } finally {
            try {
                fileOutput?.close()
            } catch (e: IOException) {
                Log.d("TAG SAVE FINALLY MSG", e.message.toString())
            }
        }
    }
}