package com.example.assignment2

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.ParseException
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.data.AttendanceContract
import com.example.assignment2.data.AttendanceDbHelper
import java.text.SimpleDateFormat


class StudentActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var studentsList=ArrayList<Student>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        studentsList.add(Student("Abdul Samad","17L-4237",0))
        studentsList.add(Student("Waleed Iqbal","17L-4038",0))
        studentsList.add(Student("Shehzar","17L-4177",0))
        studentsList.add(Student("Osama Asif","17L-4192",0))

        viewManager = LinearLayoutManager(this)
        viewAdapter = StudentAdapter(this,studentsList)

        recyclerView=findViewById<RecyclerView>(R.id.student_list).apply {
            layoutManager=viewManager
            adapter=viewAdapter
        }

        var date:EditText=findViewById(R.id.date)
        var courseName:EditText=findViewById(R.id.course_name)
        if(checkDateFormat(date.toString())==false)
        {
            Toast.makeText(this,"ENTER VALID DATE",Toast.LENGTH_LONG)
            date.text.clear()
        }

        /*val dbHelper=AttendanceDbHelper(this)
        val db=dbHelper.writableDatabase
        val values=ContentValues().apply {
            put(AttendanceContract.AttendanceEntry.COLUMN_NAME_STUDENT_NAME,"Waleed Iqbal")
            put(AttendanceContract.AttendanceEntry.COLUMN_NAME_ROLL_NO,"17L-4032")
            put(AttendanceContract.AttendanceEntry.COLUMN_NAME_COURSE,"SS401")
            put(AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE,"7-4-20")
            put(AttendanceContract.AttendanceEntry.COLUMN_NAME_CHECKED,0)
        }
        var uri: Uri? =contentResolver.insert(AttendanceContract.AttendanceEntry.CONTENT_URI,values)
        if(uri!=null)
        {
            Toast.makeText(baseContext,uri.toString(),Toast.LENGTH_LONG).show()
        }*/



    }

    private fun checkDateFormat(date: String?): Boolean? {
        if (date == null || !date.matches(Regex("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$"))) return false
        val format = SimpleDateFormat("dd/MM/yyyy")
        return try {
            format.parse(date)
            true
        } catch (e: ParseException) {
            false
        }
    }

}
