package com.example.workoutapp

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_excersice.*
import kotlinx.android.synthetic.main.custom_alert_dialog.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExcersiceActivity : AppCompatActivity() , TextToSpeech.OnInitListener {
    //rest activities
    private var countDowntimer : CountDownTimer? = null
    var restProgress = 0

    //excercise activities
    private var excerciseTimer : CountDownTimer? = null
    var excerciseProgress = 0

    //counters
    var exerciselist:ArrayList<ExcerciseModel> = Constants.defaultExcercise()
    var exerciseno = 1

    //text to speech
    private var tts:TextToSpeech? = null

    //recyclerview adapter
    var myAdapter : ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excersice)

        setSupportActionBar(toolbar_exercise_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_exercise_activity.setNavigationOnClickListener{
            setupdialogBox()
        }

        tts = TextToSpeech(this , this)
        setTimer()
        recyclerViewAdapterSetup()
    }

    private fun setupdialogBox(){
        var dialog = Dialog(this)

        dialog.setContentView(R.layout.custom_alert_dialog)
        dialog.tvYes.setOnClickListener {
            finish()
            dialog.dismiss()
        }
        dialog.tvNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    override fun onDestroy() {
        if (countDowntimer != null){
            countDowntimer!!.cancel()
            restProgress = 0
        }
        if (excerciseTimer != null){
            excerciseTimer!!.cancel()
            excerciseProgress = 0
        }

        if (countDowntimer != null){
            countDowntimer!!.cancel()
            restProgress = 0
        }
        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    private fun setTimer(){
        if (countDowntimer != null){
            countDowntimer!!.cancel()
            restProgress = 0
        }
        tvUpcomingExercise.text = exerciselist[exerciseno - 1].name

        setRestProgress()
    }

    private fun setRestProgress(){
        progressBar.progress = restProgress
        countDowntimer = object : CountDownTimer(3000, 1000){
            override fun onTick(p0: Long) {
                restProgress++
                progressBar.progress = 3 - restProgress
                tvTimer.text = (3 - restProgress).toString()

            }

            override fun onFinish() {
                setTimerExcercise()
            }
        }.start()
    }

    fun setTimerExcercise(){
        llRestView.visibility = View.GONE
        llExcersiceView.visibility = View.VISIBLE

        exerciselist[exerciseno - 1].isSelected = true
        myAdapter?.notifyDataSetChanged()

        if (excerciseTimer != null){
            excerciseTimer!!.cancel()
            excerciseProgress = 0
        }

        speakOut( exerciselist[exerciseno - 1].name)
        ivExcercises.setImageResource(exerciselist[exerciseno -1].image)
        tvExcercises.text = exerciselist[exerciseno - 1].name
        setExcerciseProgress()
    }

    private fun setExcerciseProgress() {
        progressBarExcercise.progress = excerciseProgress
        excerciseTimer = object : CountDownTimer(5000, 1000){
            override fun onTick(p0: Long) {
                excerciseProgress++
                progressBar.progress = 5 - excerciseProgress
                tvTimerExcercise.text = (5 - excerciseProgress).toString()

            }

            override fun onFinish() {
                llExcersiceView.visibility = View.GONE
                llRestView.visibility = View.VISIBLE

                exerciselist[exerciseno-1].isSelected = false
                exerciselist[exerciseno-1].isCompleted = true
                myAdapter!!.notifyDataSetChanged()

                exerciseno++
                if (exerciseno < 13) {
                    setTimer()
                }
                else{
                    finish()
                    val intent = Intent(this@ExcersiceActivity , FinishActicity::class.java)
                    startActivity(intent)
                }
              try {
                  var player: MediaPlayer? = MediaPlayer.create(this@ExcersiceActivity, R.raw.press_start)
                  player!!.isLooping = false
                  player.start()
              }
              catch (e:Exception){
                  e.printStackTrace()
              }
            }
        }.start()
    }

    override fun onInit(status: Int) {
       if (status == TextToSpeech.SUCCESS){
           var result = tts?.setLanguage(Locale.US)
           if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
               Toast.makeText(this, "not suppourted", Toast.LENGTH_SHORT).show()
           }
       }
       else{
           Log.i("tts" , "initialixation failed")

       }
    }

    private fun speakOut(text:String){
     tts!!.speak(text ,TextToSpeech.QUEUE_FLUSH , null , "")

    }

    fun recyclerViewAdapterSetup(){
        rvExerciseno.layoutManager = LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false)

        myAdapter = ExerciseStatusAdapter(exerciselist!! , this)

        rvExerciseno.adapter = myAdapter
    }


}