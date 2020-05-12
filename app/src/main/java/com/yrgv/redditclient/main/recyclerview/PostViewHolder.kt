package com.yrgv.redditclient.main.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso
import com.yrgv.redditclient.Post
import com.yrgv.redditclient.R
import com.yrgv.redditclient.utils.SimpleCallback
import com.yrgv.redditclient.utils.extensions.hide
import com.yrgv.redditclient.utils.extensions.show

/**
 *ViewHolder for a Post in the list
 *
 * Note: Kotlin synthetic import is not used here.
 * Synthetic imports inside RecyclerViews are known to cause issues.
 */
class PostViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        const val LAYOUT_ID = R.layout.layout_post_list_item

        /**
         * Invoke this get an instance of the ViewHolder
         * */
        fun get(parent: ViewGroup): PostViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(LAYOUT_ID, parent, false)
            return PostViewHolder(itemView)
        }
    }

    private val authorView: MaterialTextView = itemView.findViewById(R.id.post_list_item_author)
    private val titleView: MaterialTextView = itemView.findViewById(R.id.post_list_item_title)
    private val thumbnailView: ImageView = itemView.findViewById(R.id.post_list_item_thumbnail)

    fun bind(post: Post, onClicked: SimpleCallback) {
        itemView.setOnClickListener { onClicked() }
        authorView.text = post.author
        titleView.text = post.title

        //todo: implement dynamic image sizing, maintaining Aspect ratio
        post.thumbnailUrl?.let { url ->
            thumbnailView.show()
            Picasso.get()
                .load(url)
                .fit()
                .placeholder(R.drawable.post_thumbnail_placeholder)
                .into(thumbnailView)
        } ?: thumbnailView.hide()
    }

}