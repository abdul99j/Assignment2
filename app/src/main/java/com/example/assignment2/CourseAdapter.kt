package com.example.assignment2

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.data.AttendanceContract

class CourseAdapter(_context: Context,_cursor:Cursor):RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    var context=_context
    var cursor=_cursor
    class CourseViewHolder(v:View):RecyclerView.ViewHolder(v){
        var courseName: TextView =itemView.findViewById(R.id.course)
        var dated: TextView =itemView.findViewById(R.id.dated)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
         val layoutInflator : LayoutInflater = LayoutInflater.from(context)
        val view :View=layoutInflator.inflate(R.layout.course_attendance_item,parent,false)
        return CourseAdapter.CourseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        var courseId=cursor.getColumnIndex(AttendanceContract.AttendanceEntry.COLUMN_NAME_COURSE)
        var dateId=cursor.getColumnIndex(AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE)

        cursor.moveToPosition(position)
        Log.w("BIND","SAHI JA RHA")


        var course=cursor.getString(courseId)
        var date=cursor.getString(dateId)

        holder.itemView.setOnClickListener{
            Log.w("CLICKED","ITEM")
            var intent:Intent=Intent(holder.itemView.context,StudentActivity::class.java)
            intent.putExtra("course",course)
            intent.putExtra("date",date)
            holder.itemView.context.startActivity(intent)
        }

        holder.courseName.text=course
        holder.dated.text=date
    }
}