package com.yrgv.redditclient.main.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yrgv.redditclient.Post

/**
 * Adapter responsible for rendering a list of Posts
 */
class PostsRecyclerViewAdapter : RecyclerView.Adapter<PostViewHolder>() {

    private val posts = arrayListOf<Post>()
    private lateinit var listener: Listener

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    /**
     * Reloads the entire list.
     * */
    fun reload(newPosts: List<Post>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyItemRangeInserted(0, itemCount)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.get(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post) {
            listener.onPostClicked(post)
        }
    }

    /**
     * Interface that defines the callbacks from the adapter
     * */
    interface Listener {
        fun onPostClicked(post: Post)
    }
}