package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workoutapp.R.id
import kotlinx.android.synthetic.main.activity_bmi.*

class BmiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setSupportActionBar(toolbar_bmi_activity)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.title = "Bmi calculator"
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }


        rgmetrics.setOnCheckedChangeListener { radioGroup, i ->
           when(i){
               rbMetricUnits.id -> {
                  llMetricsunit.visibility = View.VISIBLE
                   llUSunit.visibility  = View.GONE
               }
               
               rbUsunits.id -> {
                   llMetricsunit.visibility = View.GONE
                   llUSunit.visibility  = View.VISIBLE
               }
           }
        }



        btnCheck.setOnClickListener {
            var bmi = 0.0


           if (rbMetricUnits.isChecked) {

               if (!(etweight.text.toString().isEmpty() || etheight.text.toString().isEmpty())) {
                   var weight: Double = etweight.text.toString().toDouble()
                   var height: Double = (etheight.text.toString().toDouble()) / 100

                   bmi = weight / (height * height)
               }
               else Toast.makeText(this, "wrong info given", Toast.LENGTH_SHORT).show()
           }
            else if (rbUsunits.isChecked){

               if (!(etweightUS.text.toString().isEmpty() || etheightUS.text.toString().isEmpty() || etheightUScm.text.toString().isEmpty())) {
                   var weight: Double = etweightUS.text.toString().toDouble()
                   var heightFeet: Double = (etheightUS.text.toString().toDouble())
                   var heightInches: Double = (etheightUScm.text.toString().toDouble())

                   heightInches += (heightFeet * 12)

                   bmi = 703 * (weight / (heightInches * heightInches))

               }
               else Toast.makeText(this, "wrong info given", Toast.LENGTH_SHORT).show()
           }
            else Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()


            tvBMIvalue.text = bmi.toString()

            when(true){
                bmi < 18.5 -> { tvDescriptiton.text = "UNDERWEIGHT"}
                bmi > 18.5 && bmi < 25  -> { tvDescriptiton.text = "NORMAL"}
                bmi > 25 && bmi < 30  -> { tvDescriptiton.text = "OVERWEIGHT"}
                bmi > 30 -> {tvDescriptiton.text = "OBESE"}

            }
        }
    }
}