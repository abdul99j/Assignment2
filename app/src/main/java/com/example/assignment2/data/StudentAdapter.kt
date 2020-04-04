package com.example.assignment2.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.R
import com.example.assignment2.Student

class StudentAdapter(private val student:ArrayList<Student>) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(view: View):RecyclerView.ViewHolder(view){
        val name:TextView=itemView.findViewById(R.id.student_name)
        val rollNo: TextView=itemView.findViewById(R.id.student_rollno)
        val check:CheckBox=itemView.findViewById(R.id.select)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutInflator : LayoutInflater = LayoutInflater.from(parent.context)
        val view :View=layoutInflator.inflate(R.layout.student_item,parent,false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}