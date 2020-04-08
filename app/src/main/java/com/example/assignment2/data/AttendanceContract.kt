package com.example.assignment2.data

import android.net.Uri
import android.provider.BaseColumns

object AttendanceContract {

    val AUTHORITY:String="com.example.assignment2"
    val BASE_CONTENT_URI:Uri=Uri.parse("content://$AUTHORITY")
    val PATH_ATTENDANCE:String="Attendance"

    object AttendanceEntry:BaseColumns{
        val CONTENT_URI:Uri= BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_ATTENDANCE).build()

        const val TABLE_NAME="attendance"
        const val COLUMN_NAME_STUDENT_NAME="name"
        const val COLUMN_NAME_ROLL_NO="rollNo"
        const val COLUMN_NAME_CHECKED="checked"
        const val COLUMN_NAME_COURSE="course"
        const val COLUMN_NAME_DATE="date"
    }
}