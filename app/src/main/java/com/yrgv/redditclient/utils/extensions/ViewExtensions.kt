package com.yrgv.redditclient.utils.extensions

import android.view.View
import com.airbnb.lottie.LottieAnimationView

/**
 * Extension functions for the View classes for convenience
 */

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Makes the LottieAnimationView visible and starts the animation
 * */
fun LottieAnimationView.start() {
    progress = 0f
    show()
    playAnimation()
}

/**
 * Cancels the lottie animation and then hides the LottieAnimationView
 * */
fun LottieAnimationView.stop() {
    cancelAnimation()
    hide()
}