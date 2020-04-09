package com.example.assignment2

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.assignment2.data.AttendanceContract

class DeleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        Log.w("In","Delete")
        var uri:Uri=
            AttendanceContract.AttendanceEntry.CONTENT_URI.buildUpon().appendPath("12").build()
        var course: EditText=findViewById(R.id.course_delete)
        var date:EditText=findViewById(R.id.date_delete)




        val button:Button=findViewById(R.id.delete_button)
        button.setOnClickListener{
            var args=arrayOf(date.text.toString(),course.text.toString())
            Log.w("DEL/ARGS",args[0])
            Log.w("DEl/ARGS",args[1])

            if(contentResolver.delete(uri,null,args)<0)
            {
                Toast.makeText(this,"UKNOWN ERROR",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"DELETED SUCCESFULLY",Toast.LENGTH_LONG).show()
            }
        }


    }
}
