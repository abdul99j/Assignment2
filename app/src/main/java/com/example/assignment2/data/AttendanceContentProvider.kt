package com.example.assignment2.data

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log


class AttendanceContentProvider:ContentProvider() {

    private lateinit var mAttendanceDbHelper:AttendanceDbHelper
    private var ATTENDANCE:Int=100
    private var ATTENDANCE_BY_COURSE=101
    private var ATTENDANCE_BY_DATE_COURSE=102


    private var sUriMatcher:UriMatcher=buildUriMatcher()

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var db: SQLiteDatabase = mAttendanceDbHelper.writableDatabase
        var match: Int = sUriMatcher.match(uri)
        when (match) {
            ATTENDANCE -> {
                // COMPLETED (3) Insert new values into the database
// Inserting values into tasks table
                val id = db.insert(AttendanceContract.AttendanceEntry.TABLE_NAME, null, values)
                if (id > 0) {
                    var returnUri =
                        ContentUris.withAppendedId(
                            AttendanceContract.AttendanceEntry.CONTENT_URI,
                            id
                        )
                    return returnUri
                } else {
                    throw SQLException("Failed to insert row into $uri")
                }
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }
        context?.contentResolver?.notifyChange(uri,null)
    }

    private fun buildUriMatcher():UriMatcher
    {
        return UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AttendanceContract.AUTHORITY,AttendanceContract.PATH_ATTENDANCE,ATTENDANCE)
            addURI(AttendanceContract.AUTHORITY,AttendanceContract.PATH_ATTENDANCE+"/#", ATTENDANCE_BY_COURSE
            )
            addURI(AttendanceContract.AUTHORITY,AttendanceContract.PATH_ATTENDANCE+"/#/#",ATTENDANCE_BY_DATE_COURSE)
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var db:SQLiteDatabase=mAttendanceDbHelper.readableDatabase
        var match:Int=sUriMatcher.match(uri)
        var retCursor:Cursor
        when(match)
        {
            ATTENDANCE_BY_COURSE->{
                retCursor=db.query(true,AttendanceContract.AttendanceEntry.TABLE_NAME,projection,null,null,
                    null,null,sortOrder,null)
                return retCursor
            }
            ATTENDANCE_BY_DATE_COURSE->{
                retCursor=db.query(false,AttendanceContract.AttendanceEntry.TABLE_NAME,projection,
                        selection,selectionArgs,null,null,null,null)
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }
        retCursor=db.query(false,AttendanceContract.AttendanceEntry.TABLE_NAME,null,
            "${AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE}=? " +
                    "AND ${AttendanceContract.AttendanceEntry.COLUMN_NAME_COURSE}=?",selectionArgs,null,null,null,null)
        retCursor.setNotificationUri(context?.contentResolver,uri)
        throw java.lang.UnsupportedOperationException("Not Yet Implemented")

    }

    override fun onCreate(): Boolean {
        mAttendanceDbHelper=AttendanceDbHelper(context)
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        var db:SQLiteDatabase=mAttendanceDbHelper.writableDatabase
        var match:Int=sUriMatcher.match(uri)
        var retId:Int
        Log.w("DEL/ARGs", selectionArgs?.get(0))
        Log.w("DEL/ARGS", selectionArgs?.get(1))
        when(match)
        {
            ATTENDANCE_BY_COURSE->{
                retId=db.delete(AttendanceContract.AttendanceEntry.TABLE_NAME,"${AttendanceContract.AttendanceEntry.COLUMN_NAME_DATE}=?" +
                        "AND ${AttendanceContract.AttendanceEntry.COLUMN_NAME_COURSE}=?",selectionArgs)
                if(retId>0)
                {
                    return retId
                }
                else{
                    return -1
                }
            }
        }
        return throw UnsupportedOperationException("UNKWON OPERATION $uri")
    }

    override fun getType(uri: Uri): String? {
        var match:Int=sUriMatcher.match(uri)
        return when(match){
            ATTENDANCE-> "BASE CONTENT URI"
            ATTENDANCE_BY_COURSE-> "ATTENDANCE BY COURSE"
            else-> "NOT MATCHED"
        }
    }
}