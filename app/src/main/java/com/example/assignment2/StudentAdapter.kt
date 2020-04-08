package com.example.assignment2

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(_context:Context,_students:ArrayList<Student>) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    var student=_students
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
        return student.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.name.text=student[position].name
        holder.rollNo.text=student[position].rollNo

        when(student[position].isChecked)
        {
            0->holder.check.isChecked=false
            1->holder.check.isChecked=true
        }
 }




}