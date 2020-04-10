package com.example.assignment2

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.data.AttendanceContract


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
        var selectAllCheck:CheckBox=findViewById(R.id.select_All_check)


        viewManager = LinearLayoutManager(this)
        viewAdapter = StudentAdapter(this,studentsList,studentsList)
        selectAllCheck.setOnClickListener{
            (viewAdapter as StudentAdapter).selectAll(selectAllCheck)
        }

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
        var searchView:SearchView=findViewById(R.id.mSearch)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                var searchQuery=query;
                (viewAdapter as StudentAdapter).getFilter().filter(query);
                return false;
            }

        })



    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("students",studentsList)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.getSerializable("students")
        super.onRestoreInstanceState(savedInstanceState)
    }
}
