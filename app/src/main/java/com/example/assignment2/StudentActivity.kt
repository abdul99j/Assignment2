package com.example.assignment2

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.net.ParseException
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        var date:EditText=findViewById(R.id.date)
        var courseName:EditText=findViewById(R.id.course_name)


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
        for (students in studentsList)
        {
            Log.w("CHECKED",students.isChecked.toString())
        }
        var button=findViewById<Button>(R.id.button_save)
        button.setOnClickListener{
            for(student in studentsList) {
                val values=ContentValues().apply {
                    put(AttendanceContract.AttendanceEntry.COLUMN_NAME_STUDENT_NAME,student.name)
                    put(AttendanceContract.AttendanceEntry.COLUMN_NAME_ROLL_NO,student.rollNo)
                    put(AttendanceContract.AttendanceEntry.COLUMN_NAME_COURSE,courseName.text.toString())
                    put(AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE,date.text.toString())
                    put(AttendanceContract.AttendanceEntry.COLUMN_NAME_CHECKED,student.isChecked)
                }
                var uri: Uri? = contentResolver.insert(
                    AttendanceContract.AttendanceEntry.CONTENT_URI,
                    values
                )
                Toast.makeText(this,"ADDED ATTENDANCE RECORD",Toast.LENGTH_LONG).show()
            }
        }

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
