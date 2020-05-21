package com.example.assignment2

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
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
                    var exporturl=msg.obj.toString()
                    Toast.makeText(applicationContext,exporturl,Toast.LENGTH_LONG).show()
                    var executor=Executors.newSingleThreadExecutor()
                    var future=executor.submit(
                        Callable<String> {
                            try {
                                var url = URL(msg.obj.toString())
                                var connection = url.openConnection() as HttpURLConnection
                                connection.readTimeout = 10000
                                connection.connectTimeout = 15000
                                connection.requestMethod = "POST"
                                connection.setRequestProperty("Content-type", "text/xml")
                                connection.doOutput = true
                                connection.connect()

                                var writer =
                                    BufferedWriter(OutputStreamWriter(connection.outputStream))
                                var bundle = msg.data
                                var course = bundle.getString("code")
                                var title = bundle.getString("name")
                                var date = bundle.getString("date")
                                var studentList=bundle.getSerializable("students") as ArrayList<Student>
                                writer.write("<course code={'$course'} title={'$title'} date={'$date'}>")
                                for (student in studentList){
                                    writer.write("<student rollno={'${student.rollNo}'} name={'${student.name}'} attendance={'${student.isChecked}'}")
                                }
                                writer.write("</course>")
                                writer.flush()
                                connection.responseCode.toString()
                            }
                            catch (e:Exception)
                            {
                                e.toString()
                            }
                        }
                    )
                    Toast.makeText(applicationContext,future.get().toString(),Toast.LENGTH_LONG).show()
                }
            }
        }
    }



}
