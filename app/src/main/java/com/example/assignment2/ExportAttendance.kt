package com.example.assignment2

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.Toast
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class ExportAttendance : Service() {
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

                    var executor= Executors.newSingleThreadExecutor()
                    var future=executor.submit(Callable {
                        fun postXML(){
                            var url=URL("htttp://10.0.2.2/notes/upload.php")
                            var connection=url.openConnection() as HttpURLConnection
                            connection.readTimeout=10000
                            connection.connectTimeout=15000
                            connection.requestMethod="POST"
                            connection.setRequestProperty("Content-type","text/xml")
                            connection.doOutput=true
                            connection.connect()

                            var writer=BufferedWriter(OutputStreamWriter(connection.outputStream))
                            var bundle=msg.data
                            var course=bundle.getString("code")
                            var title=bundle.getString("title")
                            var date=bundle.getString("date")
                            writer.write("<course code={'$course'} title={'$title'} date={'$date'}>")
                            var studentList=bundle.getSerializable("students") as ArrayList<Student>
                            for (student in studentList)
                            {
                                writer.write("<student rollno={'${student.rollNo}' name='${student.name}' attendance='${student.isChecked}'/>")
                            }
                            writer.write("</course>")

                        }
                    })

                    var replyMessenger=msg.replyTo
                    var replyMessage=Message()
                    replyMessage.obj=future.get()
                    replyMessenger.send(replyMessage)
                }
            }
        }
    }



}
private fun postXML(bundle: Bundle):String {
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