package com.example.patri.refinado.Backup

import android.content.Context
import android.content.res.AssetManager
import android.os.Environment

import com.google.gson.Gson

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

object IOHelper {

    fun stringFromStream(`is`: InputStream): String? {
        try {
            val reader = BufferedReader(InputStreamReader(`is`))
            val sb = StringBuilder()
            var line: String? = null

            while ((line == reader.readLine()) != null)
                sb.append(line).append("\n")
            reader.close()
            return sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    @Throws(IOException::class)
    fun stringFromFile(f: File): String? {
        val fis = FileInputStream(f)
        val str = stringFromStream(fis)
        fis.close()
        return str
    }

    @Throws(IOException::class)
    fun writeToFile(f: File, str: String) {
        val fos = FileOutputStream(f)
        fos.write(str.toByteArray())
        fos.close()

    }

    fun writeToFile(fileName: String, conteudo: String, diretorioApp: String) {
        try {
            val fileExt = File(diretorioApp, fileName)
            fileExt.parentFile.mkdirs()
            var fosExt: FileOutputStream? = null
            fosExt = FileOutputStream(fileExt)
            fosExt.write(conteudo.toByteArray())
            fosExt.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


}
