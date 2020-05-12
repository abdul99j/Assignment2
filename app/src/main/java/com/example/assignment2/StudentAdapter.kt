package com.example.assignment2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    _context: Context,
    _students: ArrayList<Student>,
    val filterStudentList: java.util.ArrayList<Student>,
    val checkBox: CheckBox
    ) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>()
,Filterable{
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
    fun checkAllchecked():Boolean
    {
        var count:Int=0
        for (s in student)
        {
            if(s.isChecked==1)
            {
                count++
            }
        }
        if(count==student.size)
        {
            return true
        }
        return false

    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.name.text=student[position].name
        holder.rollNo.text=student[position].rollNo


        when(student[position].isChecked)
        {
            0->holder.check.isChecked=false
            1->holder.check.isChecked=true

        }
        holder.check.setOnClickListener {
            Log.v("CLICKED","SAVE HOGYA")
            val position = holder.adapterPosition
            val newValue = !holder.check.isChecked
            checkBox.isChecked=false
            if(checkAllchecked())
            {
                checkBox.isChecked=true
            }
            if(newValue){
                student[position].isChecked=0
                Log.e("Roll_NO",student[position].rollNo)
                Log.e("CHECKED",student[position].isChecked.toString())

            } else{
                student[position].isChecked=1
                Log.e("Roll_NO",student[position].rollNo)
                Log.e("CHECKED",student[position].isChecked.toString())

            }

            //display the text accordingly with the newValue value
        }
    }
    fun selectAll(checkBox: CheckBox) {
        if(checkBox.isChecked){
            for (s in student)
            {
                s.isChecked=1
            }
            notifyDataSetChanged()
        }
        else{
            for (s in student)
            {
                s.isChecked=0
            }
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        if(filter==null)
        {
            val filter= CustomFilter(filterStudentList, this)
        }
        return filter;
    }


}