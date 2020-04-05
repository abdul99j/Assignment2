package com.example.assignment2

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.data.StudentListContract

class StudentAdapter(_context:Context,_cursor:Cursor) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    var cursor:Cursor=_cursor
    var context:Context = _context


    class StudentViewHolder(view: View):RecyclerView.ViewHolder(view){
        var name:TextView=itemView.findViewById(R.id.student_name)
        val rollNo: TextView=itemView.findViewById(R.id.student_rollno)
        var check:CheckBox=itemView.findViewById(R.id.select)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutInflator : LayoutInflater = LayoutInflater.from(context)
        val view :View=layoutInflator.inflate(R.layout.student_item,parent,false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        if(!cursor.moveToPosition(position))
            return
        var name = cursor.getString(cursor.getColumnIndex
            (StudentListContract.StudentListEntry.COLUMN_NAME_STUDENT_NAME))
        var rollNo=cursor.getString(cursor.getColumnIndex
            (StudentListContract.StudentListEntry.COLUMN_NAME_ROLL_NO))

        holder.name.text = name
        holder.rollNo.text = rollNo

    }


}