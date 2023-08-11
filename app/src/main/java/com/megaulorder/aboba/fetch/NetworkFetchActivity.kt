package com.megaulorder.aboba.fetch

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.megaulorder.aboba.App.Companion.appInstance
import com.megaulorder.aboba.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SHOULD_RESUME_LOADING_TAG = "shouldResumeLoading"
private const val TEXT_TAG = "text"

class NetworkFetchActivity : AppCompatActivity() {

    private var shouldResumeLoading: Boolean = false
    private var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_fetch)

        val button: Button = findViewById(R.id.fetch_network_button)
        val textView: TextView = findViewById(R.id.fetch_network_text)
        val progressBar: ProgressBar = findViewById(R.id.fetch_network_progress_bar)

        val repository: DataRepository = appInstance.repo

        shouldResumeLoading = savedInstanceState?.getBoolean(SHOULD_RESUME_LOADING_TAG) ?: false
        text = savedInstanceState?.getString(TEXT_TAG) ?: ""

        if (!text.isNullOrEmpty()) {
            textView.text = text
        }

        if (shouldResumeLoading) {
            lifecycleScope.launch {
                textView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE

                text = repository.getCachedData(isForced = false)
                textView.text = text
                shouldResumeLoading = false

                progressBar.visibility = View.GONE
                textView.visibility = View.VISIBLE
            }
        }

        button.setOnClickListener {
            shouldResumeLoading = true
            lifecycleScope.launch {
                textView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE

                text = repository.getCachedData(isForced = true)
                textView.text = text
                shouldResumeLoading = false

                progressBar.visibility = View.GONE
                textView.visibility = View.VISIBLE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SHOULD_RESUME_LOADING_TAG, shouldResumeLoading)
        outState.putString(TEXT_TAG, text ?: "")
    }
}