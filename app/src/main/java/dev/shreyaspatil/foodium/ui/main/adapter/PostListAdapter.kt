package dev.shreyaspatil.foodium.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.shreyaspatil.foodium.databinding.ItemPostBinding
import dev.shreyaspatil.foodium.model.Post
import dev.shreyaspatil.foodium.ui.main.viewholder.PostViewHolder

/**
 * Adapter class [RecyclerView.Adapter] for [RecyclerView] which binds [Post] along with [PostViewHolder]
 */
class PostListAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<Post, PostViewHolder>(DIFF_CALLBACK) {

    private val mPostList: MutableList<Post> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = createPostViewHolder(parent)

    override fun getItemCount() = mPostList.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) =
        holder.bind(mPostList[position], onItemClickListener)

    private fun createPostViewHolder(parent: ViewGroup) =
        PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    fun setPosts(postList: List<Post>) {
        clearAllPosts()
        mPostList.addAll(postList)
        notifyDataSetChanged()
    }

    fun clearAllPosts() {
        mPostList.clear()
    }

    interface OnItemClickListener {
        fun onItemClicked(post: Post, imageView: ImageView)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem == newItem

        }
    }
}
