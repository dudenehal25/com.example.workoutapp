package com.example.workoutapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenhelper(context: Context , factory: SQLiteDatabase.CursorFactory?) :
            SQLiteOpenHelper(context , DATABASE_NAME , factory , DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "SevenMinutesWorkout.db"
        private val TABLE_CONTACTS = "Table_History"
        private val COLUMN_ID = "_id"
        private val  COLUMN_COMPLETED_DATE = "completed_date"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_CONTACTS($COLUMN_ID INTEGER PRIMARY KEY , $COLUMN_COMPLETED_DATE TEXT) ")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }


    fun addDate( date : String): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COLUMN_COMPLETED_DATE, date)

        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }


    fun viewEmployee(): ArrayList<dateholder> {

        val dateList: ArrayList<dateholder> = ArrayList()

        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"

        val db = this.readableDatabase
        var cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var date: String


        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                date = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))

                val emp = dateholder(id = id, date = date)
                dateList.add(emp)

            } while (cursor.moveToNext())
        }
        return dateList
    }
}