package com.yrgv.redditclient.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.yrgv.redditclient.R
import com.yrgv.redditclient.utils.SimpleCallback

/**
 * A simple view that can be used for error cases, shows a message and a button.
 */
class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_error, this, true)
    }

    private val retryButton = findViewById<Button>(R.id.error_view_retry_button)

    fun setRetryButtonClickListener(listener: SimpleCallback) {
        retryButton.setOnClickListener {
            listener()
        }
    }

}