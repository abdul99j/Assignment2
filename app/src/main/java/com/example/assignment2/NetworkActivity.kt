package com.example.assignment2

import android.app.ActivityManager
import android.content.*
import android.os.*
import android.util.Log
import android.util.Xml
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.alert_dialog.view.*
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader


class NetworkActivity : AppCompatActivity() {

    private var messenger: Messenger? = null
    private var exportMessenger:Messenger?=null
    private var bound: Boolean = false
    private var exportBound:Boolean=false
    lateinit var replyMessenger: Messenger
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    var studentList=ArrayList<Student>()
    lateinit var courseCode:String
    lateinit var courseName:String
    lateinit  var editText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
        class ReplyHandler:Handler()
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                updateUI(msg.obj.toString())
            }
        }
        replyMessenger= Messenger(ReplyHandler())
    }
    fun parseXML(string: String):Bundle
    {
        Log.w("content",string)
        var bundle=Bundle()
        try {
            var parser = Xml.newPullParser()
            parser.setInput(StringReader(string))

            var event = parser.eventType
            while (event != XmlPullParser.END_DOCUMENT) {
                if (event == XmlPullParser.START_TAG && parser.name == "course") {
                    var courseCode = parser.getAttributeValue(null, "code")
                    var courseName = parser.getAttributeValue(null, "title")
                    bundle.putString("code",courseCode)
                    bundle.putString("name",courseName)

                }
                if (event == XmlPullParser.START_TAG && parser.name == "student") {
                    var rollNo = parser.getAttributeValue(null, "rollno").toString()
                    var name = parser.getAttributeValue(null, "name").toString()
                    var student = Student(name, rollNo, 0)
                    studentList.add(student)
                }
                event = parser.next()
            }
            return bundle
        }
        catch (e:Exception)
        {
            return bundle
        }
    }

    fun updateUI(string: String)
    {

        var bundle=parseXML(string)
        var code: EditText =findViewById(R.id.network_course_code)
        var name: EditText =findViewById(R.id.network_course_name)
        courseCode= bundle.getString("code")!!
        courseName= bundle.getString("name")!!
        code.setText(courseCode)
        name.setText(courseName)


        var selectAllCheck: CheckBox =findViewById(R.id.network_select_All_check)

        viewManager = LinearLayoutManager(this)
        viewAdapter = StudentAdapter(this,studentList,studentList,selectAllCheck)
        selectAllCheck.setOnClickListener{
            (viewAdapter as StudentAdapter).selectAll(selectAllCheck)
        }

        recyclerView=findViewById<RecyclerView>(R.id.network_student_list).apply {
            layoutManager=viewManager
            adapter=viewAdapter
        }


    }
    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_network, menu)
        return true
    }





    private fun alertDialogBuilder(title: String, context: Context) {
        val mDialog = LayoutInflater.from(applicationContext).inflate(R.layout.alert_dialog, null)
        var alertDialog = AlertDialog.Builder(applicationContext)
            .setTitle(title).setView(mDialog)
        Log.w("START", "WORKING")
        var url_alert=""
        alertDialog.setPositiveButton("Submit", DialogInterface.OnClickListener() { dialog, p1 ->
            url_alert = mDialog.url.text.toString()
        })
        alertDialog.setNegativeButton(
            "CANCEL",
            DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                url_alert=""
                dialogInterface.cancel()
            })
        val mAlertDialog = alertDialog.show()


    }
    fun showChangeLangDialog(title:String) {


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        when (id) {
            R.id.import_item -> {
                val dialogBuilder =
                    AlertDialog.Builder(this)
                val inflater = this.layoutInflater
                val dialogView: View = inflater.inflate(R.layout.alert_dialog, null)
                dialogBuilder.setView(dialogView)
                val edt = dialogView.findViewById(R.id.url) as EditText
                var url:String=""
                dialogBuilder.setTitle("IMPORT ATTENDANCE")
                dialogBuilder.setPositiveButton(
                    "Submit"
                ) { dialog, whichButton ->
                    url=edt.text.toString()
                    val mConnection = object : ServiceConnection {
                        override fun onServiceDisconnected(name: ComponentName?) {
                            messenger = null
                            bound = false
                        }

                        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                            if(isMyServiceRunning(ImportAttendance::class.java)) {
                                messenger = Messenger(service)
                                var message = Message.obtain(null, 1)
                                message.replyTo = replyMessenger
                                message.obj = url

                                try {
                                    messenger!!.send(message)
                                } catch (e: RemoteException) {

                                }
                                bound = true
                            }

                        }


                    }
                    intent = Intent(this, ImportAttendance::class.java).also { intent ->
                        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
                    }
                }
                dialogBuilder.setNegativeButton(
                    "Cancel"
                ) { dialog, whichButton ->
                    //pass
                }
                val b = dialogBuilder.create()
                b.show()



            }
            R.id.export_item -> {
                val dialogBuilder =
                    AlertDialog.Builder(this)
                val inflater = this.layoutInflater
                val dialogView: View = inflater.inflate(R.layout.alert_dialog, null)
                dialogBuilder.setView(dialogView)
                val edt = dialogView.findViewById(R.id.url) as EditText
                var url:String=""
                dialogBuilder.setTitle("EXPORT ATTENDANCE")
                dialogBuilder.setPositiveButton(
                    "Submit"
                ) { dialog, whichButton ->
                    url=edt.text.toString()
                    val exportConnection = object : ServiceConnection {
                        override fun onServiceDisconnected(name: ComponentName?) {
                            exportMessenger = null
                            exportBound = false
                        }

                        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                            if (isMyServiceRunning(ExportAttendance::class.java)) {
                                exportMessenger = Messenger(service)
                                var message = Message.obtain(null, 1)
                                message.obj = url
                                var bundle = Bundle()

                                bundle.putSerializable("students", studentList)
                                bundle.putString("code", courseCode)
                                bundle.putString("name", courseName)
                                var date = findViewById<EditText>(R.id.network_date)
                                bundle.putString("date", date.text.toString())

                                message.data = bundle
                                try {
                                    exportMessenger!!.send(message)
                                } catch (e: RemoteException) {

                                }
                                exportBound = true
                            }
                        }


                    }
                    intent = Intent(this, ExportAttendance::class.java).also { intent ->
                        bindService(intent, exportConnection, Context.BIND_AUTO_CREATE)
                    }
                }
                dialogBuilder.setNegativeButton(
                    "Cancel"
                ) { dialog, whichButton ->
                    //pass
                }
                val b = dialogBuilder.create()
                b.show()


            }
        }
        return super.onOptionsItemSelected(item);
    }



}



