package com.example.assignment2

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.data.AttendanceContract

class UpdateAdapter(_context: Context, _cursor: Cursor,_students:ArrayList<Student>): RecyclerView.Adapter<UpdateAdapter.UpdateViewHolder>() {
    var context=_context
    var cursor=_cursor
    var students=_students
    class UpdateViewHolder(v: View): RecyclerView.ViewHolder(v){
        var studentName: TextView =itemView.findViewById(R.id.student_name)
        var rollNo: TextView =itemView.findViewById(R.id.student_rollno)
        var checkBox:CheckBox=itemView.findViewById(R.id.select)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateViewHolder {
        val layoutInflator : LayoutInflater = LayoutInflater.from(context)
        val view : View =layoutInflator.inflate(R.layout.student_item,parent,false)
        return UpdateAdapter.UpdateViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    override fun onBindViewHolder(holder: UpdateViewHolder, position: Int) {
        var nameId =
            cursor.getColumnIndex(AttendanceContract.AttendanceEntry.COLUMN_NAME_STUDENT_NAME)
        var rollNoId = cursor.getColumnIndex(AttendanceContract.AttendanceEntry.COLUMN_NAME_ROLL_NO)
        var checkId = cursor.getColumnIndex(AttendanceContract.AttendanceEntry.COLUMN_NAME_CHECKED)

        cursor.moveToPosition(position)


        var name = cursor.getString(nameId)
        var rollNo = cursor.getString(rollNoId)
        var checked = cursor.getInt(checkId)

        holder.studentName.text = name
        holder.rollNo.text = rollNo
        when (checked) {
            0 -> holder.checkBox.isChecked = false
            1 -> holder.checkBox.isChecked = true
        }
        holder.checkBox.setOnClickListener {
            Log.v("CLICKED", "SAVE HOGYA")
            val position = holder.adapterPosition
            val newValue = !holder.checkBox.isChecked
            if (newValue) {
                var index=students.indexOf(Student(name,rollNo,1))
                if(index>0)
                {
                    students[index].isChecked=0
                }
                else{
                    students.add(Student(name,rollNo,0))
                }
            } else {
                var index=students.indexOf(Student(name,rollNo,0))
                if(index>0)
                {
                    students[index].isChecked=1
                }
                else{
                    students.add(Student(name,rollNo,1))
                }

            }
        }
    }
}