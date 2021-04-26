package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    var job : Job ? = null;
    var jobArray : ArrayList<Job>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        xBtnStart.setOnClickListener {
            GlobalScope.launch {
                lunchJob()
            }

        }
        xBtnStop.setOnClickListener {
            jobArray!!.get(0).cancel()
            jobArray!!.removeAt(0);
          
        }


    }
    suspend fun lunchJob(){
        job =  GlobalScope.launch (Dispatchers.Default){
            try{
                work()
            }finally {
                withContext(NonCancellable){
                    delay(1000L)
                    println("Cleanup done!")
                }

            }
        }
        jobArray!!.add(job!!)
        delay(1000L)
        println("Cancel!")
        //job!!.cancel()
        println("Done!")
    }
    suspend fun work(){
        val startTime = System.currentTimeMillis()
        var nextPrintTime = startTime
        var i = 0
        while (i < 50) {
            yield()
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("Hello ${i++}")
                nextPrintTime += 2000L
            }
        }
    }
}