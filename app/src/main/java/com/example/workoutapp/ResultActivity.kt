package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setSupportActionBar(toolbar_result_activity)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.title = "HISTORY PAGE"
        toolbar_result_activity.setNavigationOnClickListener {
            onBackPressed()
        }

    setupListofDataIntoRecyclerView()

    }

    private fun setupListofDataIntoRecyclerView() {

        if (getItemsList().size > 0) {



            // Set the LayoutManager that this RecyclerView will use.
            rvHistory.layoutManager = LinearLayoutManager(this)
            // Adapter class is initialized and list is passed in the param.
            val itemAdapter = ItemAdapter(this, getItemsList())
            // adapter instance is set to the recyclerview to inflate the items.
            rvHistory.adapter = itemAdapter
        }
    }

    private fun getItemsList(): ArrayList<dateholder> {
        //creating the instance of DatabaseHandler class
        val databaseHandler = SqliteOpenhelper(this , null)
        //calling the viewEmployee method of DatabaseHandler class to read the records

        return databaseHandler.viewEmployee()
    }




}