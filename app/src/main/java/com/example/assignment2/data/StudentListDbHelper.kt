package com.example.assignment2.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentListDbHelper(private val context: Context?) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        const val DATABASE_NAME:String="studentlist.db"

        const val DATABASE_VERSION=1

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val SQL_CREATE_STUDENT_LIST_TABLE="CREATE TABLE"+
                StudentListContract.StudentListEntry.TABLE_NAME + "(" +
                StudentListContract.StudentListEntry.COLUMN_NAME_ROLL_NO + "TEXT PRIMARY KEY,"
                StudentListContract.StudentListEntry.COLUMN_NAME_STUDENT_NAME +"TEXT NOT NULL"+ ");"
        if (db != null) {
            db.execSQL(SQL_CREATE_STUDENT_LIST_TABLE)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS"+StudentListContract.StudentListEntry.TABLE_NAME)
        onCreate(db)
    }

}