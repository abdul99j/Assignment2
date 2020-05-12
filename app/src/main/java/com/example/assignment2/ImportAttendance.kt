package com.example.assignment2

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.Executors


class ImportAttendance() : Service() {
    lateinit var messenger:Messenger

    override fun onBind(intent: Intent): IBinder {
        messenger= Messenger(IncomingHandler(this))
        return messenger.binder
    }

    internal class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1->{
                    var executor=Executors.newSingleThreadExecutor()
                    var callable=Callable{fetchXML()}
                    var future=executor.submit(callable)
                    var replyMessenger=msg.replyTo
                    var replyMessage=Message()
                    replyMessage.obj=future.get()
                    replyMessenger.send(replyMessage)
                }
            }
        }
    }



}
private fun fetchXML():String {
    var line = ""
    try {
        var url =
            URL("https://sites.google.com/site/farooqahmedrana/Home/students.xml?attredirects=0&d=1")
        var connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.readTimeout = 10000
        connection.connectTimeout = 15000
        connection.requestMethod = "GET"
        connection.doInput = true
        connection.connect()

        var content = StringBuilder()
        var reader = BufferedReader(InputStreamReader(connection.inputStream))


        while (true) {
            val line = reader.readLine() ?: break
            content.append(line)
        }

        line=content.toString()
        return line


    } catch (e: Exception) {
        line=e.toString()
        return line
    }
}


