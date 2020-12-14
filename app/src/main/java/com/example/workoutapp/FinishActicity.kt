package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_finish_acticity.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActicity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_acticity)

        btnFinish.setOnClickListener {
            finish()
        }
        adddateToDatabase()
    }

    private fun adddateToDatabase(){
        val calendar = Calendar.getInstance()
        val date = calendar.time

        Log.i("DATE" ,date.toString())
        val sdf = SimpleDateFormat("dd / MMM / YYYY HH:MM:SS" , Locale.getDefault())
        val datetime = sdf.format(date)

        val dbhandler = SqliteOpenhelper(this, null)
        dbhandler.addDate(datetime)
        Log.i("DATE" ,datetime)

    }
}
