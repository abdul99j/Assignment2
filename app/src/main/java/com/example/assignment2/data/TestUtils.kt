package com.example.assignment2.data
import android.content.ContentValues
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import java.util.*


object TestUtil {
    fun insertFakeData(db: SQLiteDatabase?) {
        if (db == null) {
            return
        }
        //create a list of fake guests
        val list: MutableList<ContentValues> =
            ArrayList()
        var cv = ContentValues()
        cv.put(StudentListContract.StudentListEntry.COLUMN_NAME_ROLL_NO, "17L-4237")
        cv.put(StudentListContract.StudentListEntry.COLUMN_NAME_STUDENT_NAME, "Abdul Samad")
        list.add(cv)

        cv = ContentValues()
        cv.put(StudentListContract.StudentListEntry.COLUMN_NAME_ROLL_NO, "17L-4032")
        cv.put(StudentListContract.StudentListEntry.COLUMN_NAME_STUDENT_NAME, "Waleed Iqbal")
        list.add(cv)

        cv = ContentValues()
        cv.put(StudentListContract.StudentListEntry.COLUMN_NAME_ROLL_NO, "17L-4182")
        cv.put(StudentListContract.StudentListEntry.COLUMN_NAME_STUDENT_NAME, "Talha Sattar")
        list.add(cv)

        try {
            db.beginTransaction()
            //clear the table first
            db.delete(StudentListContract.StudentListEntry.TABLE_NAME, null, null)
            //go through the list and add one by one
            for (c in list) {
                db.insert(StudentListContract.StudentListEntry.TABLE_NAME, null, c)
            }
            db.setTransactionSuccessful()
        } catch (e: SQLException) { //too bad :(
        } finally {
            db.endTransaction()
        }
    }
}