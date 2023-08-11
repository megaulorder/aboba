package com.megaulorder.aboba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.megaulorder.aboba.delay.DelayedWorkActivity
import com.megaulorder.aboba.fetch.NetworkFetchActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val delayedWorkButton: Button = findViewById(R.id.open_delayed_work)
        val networkFetchButton: Button = findViewById(R.id.open_network_fetch)

        delayedWorkButton.setOnClickListener {
            startActivity(Intent(this, DelayedWorkActivity::class.java))
        }
        networkFetchButton.setOnClickListener {
            startActivity(Intent(this, NetworkFetchActivity::class.java))
        }
    }
}