package com.example.assignment2

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.AsyncTaskLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.data.AttendanceContentProvider
import com.example.assignment2.data.AttendanceContract
import com.example.assignment2.data.AttendanceDbHelper

class MainActivity : AppCompatActivity() {
    private  val TAG="Myactivity"
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var uri:Uri=AttendanceContract.AttendanceEntry.CONTENT_URI.buildUpon().appendPath("12").build()
        var columns= arrayOf(AttendanceContract.AttendanceEntry.COLUMN_NAME_COURSE,AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE)
        Log.w(TAG,uri.toString())
        var cursor=contentResolver.query(uri,columns,null,null,null)

        viewManager = LinearLayoutManager(this)
        viewAdapter = CourseAdapter (this, cursor!!)

        recyclerView=findViewById<RecyclerView>(R.id.course_attendance).apply {
            layoutManager=viewManager
            Log.w(TAG,"IN ADAPTER")
            adapter=viewAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id=item.itemId
        when(id){
            R.id.add_new->{
                    intent=Intent(this,StudentActivity::class.java)
                    startActivity(intent)
                    return true
            }
            R.id.delete_record->{
                Toast.makeText(applicationContext,"TODO",Toast.LENGTH_LONG).show()
                return true

            }
        }
        return true
    }

}
