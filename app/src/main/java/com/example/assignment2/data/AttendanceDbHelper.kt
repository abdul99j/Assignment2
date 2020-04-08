package com.example.assignment2.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${AttendanceContract.AttendanceEntry.TABLE_NAME} (" +
            "${AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE} TEXT NOT NULL," +
            "${AttendanceContract.AttendanceEntry.COLUMN_NAME_STUDENT_NAME} TEXT NOT NULL," +
            "${AttendanceContract.AttendanceEntry.COLUMN_NAME_ROLL_NO} TEXT NOT NULL,"+
            "${AttendanceContract.AttendanceEntry.COLUMN_NAME_COURSE} TEXT NOT NULL,"+
            "${AttendanceContract.AttendanceEntry.COLUMN_NAME_CHECKED} INTEGER NOT NULL," +
            "PRIMARY KEY (${AttendanceContract.AttendanceEntry.COLUMN_NAME_ROLL_NO}," +
            "${AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE}," +
            "${AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE})"+")"


private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${AttendanceContract.AttendanceEntry.TABLE_NAME}"

class AttendanceDbHelper(context: Context?): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION)
{

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db,oldVersion,newVersion)
    }


    companion object{
        const val DATABASE_NAME="Attendance.db"
        const val DATABASE_VERSION=1
    }

}