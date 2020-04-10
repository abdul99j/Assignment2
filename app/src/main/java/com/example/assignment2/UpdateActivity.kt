package com.example.assignment2

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.R
import com.example.assignment2.data.AttendanceContract
import java.lang.UnsupportedOperationException

class UpdateActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var cursor:Cursor
    var course:String=""
    var date:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)



        var courseName:EditText=findViewById(R.id.course_name_update)
        var dated:EditText=findViewById(R.id.date_update)

        if(intent!=null)
        {
            course=intent.getStringExtra("course")
            date=intent.getStringExtra("date")

            courseName.setText(course)
            dated.setText(date)
        }

        var uri: Uri =
            AttendanceContract.AttendanceEntry.CONTENT_URI.buildUpon().appendPath("date").build()
        var final_uri=uri.buildUpon().appendPath("12").build()
        var columns = arrayOf(
            AttendanceContract.AttendanceEntry.COLUMN_NAME_ROLL_NO,
            AttendanceContract.AttendanceEntry.COLUMN_NAME_STUDENT_NAME,
            AttendanceContract.AttendanceEntry.COLUMN_NAME_CHECKED
        )


        if(course!="" && date!="") {

            var selection: String =
                "${AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE}=? AND " +
                        "${AttendanceContract.AttendanceEntry.COLUMN_NAME_COURSE}=?"
            var args = arrayOf(date, course)
            Log.w("TAG", uri.toString())
            cursor = contentResolver.query(final_uri, columns, selection, args, null)!!
            var studentList=ArrayList<Student>()
            viewManager = LinearLayoutManager(this)
            viewAdapter = UpdateAdapter(this, cursor!!,studentList)

            recyclerView = findViewById<RecyclerView>(R.id.student_list_update).apply {
                layoutManager = viewManager
                Log.w("TAG", "IN ADAPTER")
                adapter = viewAdapter
            }
            var button:Button=findViewById(R.id.button_update)
            button.setOnClickListener{
                for(student in studentList)
                {
                    val contentValues=ContentValues().apply {
                        put(AttendanceContract.AttendanceEntry.COLUMN_NAME_CHECKED,student.isChecked)
                    }
                    val where="${AttendanceContract.AttendanceEntry.COLUMN_NAME_COURSE}=? AND " +
                            "${AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE}=? AND " +
                            "${AttendanceContract.AttendanceEntry.COLUMN_NAME_STUDENT_NAME}=? AND " +
                            "${AttendanceContract.AttendanceEntry.COLUMN_NAME_ROLL_NO}=?"

                    val selectionArgs= arrayOf(course,date,student.name,student.rollNo)
                    var update=contentResolver.update(AttendanceContract.AttendanceEntry.CONTENT_URI,contentValues,
                        where,selectionArgs)
                    if(update>0)
                    {
                        Toast.makeText(this,"UPDATED",Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("date",date)
        outState.putString("course",course)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        date= savedInstanceState.getString("date").toString()
        course= savedInstanceState.getString("course").toString()
        super.onRestoreInstanceState(savedInstanceState)
    }
}


