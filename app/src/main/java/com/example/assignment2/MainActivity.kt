package com.example.assignment2

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.data.StudentListContract
import com.example.assignment2.data.StudentListDbHelper
import com.example.assignment2.data.TestUtil

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var mDb:SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var dbHelper=StudentListDbHelper(this)
        mDb=dbHelper.writableDatabase
        TestUtil.insertFakeData(mDb)

        var cursor:Cursor=getAllguests()

        viewManager = LinearLayoutManager(this)
        viewAdapter = StudentAdapter(this,cursor)

        recyclerView=findViewById<RecyclerView>(R.id.student_list).apply {
            layoutManager=viewManager
            adapter=viewAdapter
        }



    }

    private fun getAllguests(): Cursor {
        return mDb.query(StudentListContract.StudentListEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            StudentListContract.StudentListEntry.COLUMN_NAME_ROLL_NO)

    }


}
