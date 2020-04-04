package com.example.assignment2.data

import android.provider.BaseColumns

class StudentListContract {

    object StudentListEntry : BaseColumns{
        const val  TABLE_NAME="student"
        const val  COLUMN_NAME_STUDENT_NAME="name"
        const val  COLUMN_NAME_ROLL_NO="rollno"
    }

}