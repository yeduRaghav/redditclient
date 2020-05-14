package com.yrgv.redditclient.utils.resourceprovider

import android.app.Application
import android.content.Context

/**
 * Implementation for Resource Provider
 */
class DefaultResourceProvider private constructor(private val context: Context) :
    ResourceProvider {

    companion object {
        fun newInstance(application: Application): ResourceProvider {
            return DefaultResourceProvider(application)
        }
    }

    override fun getString(resId: Int): String? {
        return context.getString(resId)
    }

}