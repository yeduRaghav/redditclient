package com.yrgv.redditclient.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.airbnb.lottie.LottieAnimationView
import com.yrgv.redditclient.R
import com.yrgv.redditclient.utils.extensions.hide
import com.yrgv.redditclient.utils.extensions.show
import com.yrgv.redditclient.utils.extensions.start
import com.yrgv.redditclient.utils.extensions.stop


/**
 * A simple view that can be dropped in to show loading animation.
 *
 * Note: Kotlin synthetic import is not used here, as this view can be used
 * inside a RecyclerView and synthetic imports inside recyclerViews are known to cause issues.
 */
class LoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_loading, this, true)
    }

    private val progressView = findViewById<LottieAnimationView>(R.id.loading_view_lottie_view)


    /**
     * Shows and animates the loading animation
     * */
    fun startLoading() {
        show()
        progressView.start()
    }

    /**
     * Stops and hides the loading animation
     * */
    fun stopLoading() {
        hide()
        progressView.stop()
    }

}