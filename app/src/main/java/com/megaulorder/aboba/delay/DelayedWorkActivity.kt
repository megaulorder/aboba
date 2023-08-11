package com.megaulorder.aboba.delay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.megaulorder.aboba.R

private const val TIMEOUT_MILLIS = 5000L
private const val TIMEOUT_EMPTY = 0L
private const val SCHEDULED_TIME_KEY = "time"

/**
 * Sample activity that shows how you can do some work after a delay without blocking the UI thread.
 */
class DelayedWorkActivity : AppCompatActivity() {

    private var scheduledTime: Long? = null
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delayed_work)

        handler = Handler(Looper.getMainLooper())

        val textView1: TextView = findViewById(R.id.delayed_work_text)
        val button1: Button = findViewById(R.id.delayed_work_button)
        val progressBar: ProgressBar = findViewById(R.id.delayed_work_progress_bar)

        val textBefore: String = getString(R.string.delayed_work_text_before)
        val textAfter: String = getString(R.string.delayed_work_text_after)
        val textAfterRotation: String = getString(R.string.delayed_work_text_after_rotate)

        val currentTime: Long = System.currentTimeMillis()
        scheduledTime = savedInstanceState?.getLong(SCHEDULED_TIME_KEY) ?: TIMEOUT_EMPTY

        if (scheduledTime != TIMEOUT_EMPTY && textView1.text == textBefore) {
            if (currentTime >= scheduledTime!!) {
                textView1.text = textAfterRotation
            } else {
                progressBar.visibility = View.VISIBLE
                makeToast(scheduledTime!! - currentTime)
                handler?.postDelayed({
                    progressBar.visibility = View.GONE
                    textView1.text = textAfterRotation
                }, scheduledTime!! - currentTime)
            }
            scheduledTime = TIMEOUT_EMPTY
        } else {
            textView1.text = textBefore
        }

        button1.setOnClickListener {
            scheduledTime = System.currentTimeMillis() + TIMEOUT_MILLIS
            progressBar.visibility = View.VISIBLE
            makeToast(TIMEOUT_MILLIS)
            handler?.postDelayed({
                progressBar.visibility = View.GONE
                textView1.text = textAfter
                scheduledTime = TIMEOUT_EMPTY
            }, TIMEOUT_MILLIS)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (scheduledTime != null) {
            outState.putLong(SCHEDULED_TIME_KEY, scheduledTime!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacksAndMessages(null)
    }

    private fun makeToast(durationMillis: Long) {
        Toast.makeText(
            this,
            getString(R.string.delayed_work_toast_text, durationMillis / 1000),
            Toast.LENGTH_SHORT
        ).show()
    }
}