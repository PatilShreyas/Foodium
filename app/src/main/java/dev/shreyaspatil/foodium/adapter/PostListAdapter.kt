package dev.shreyaspatil.foodium.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.shreyaspatil.foodium.databinding.ItemPostBinding
import dev.shreyaspatil.foodium.model.Post
import dev.shreyaspatil.foodium.viewholder.PostViewHolder

class PostListAdapter(private val mPostList: MutableList<Post> = mutableListOf()) :
    RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = createPostViewHolder(parent)

    override fun getItemCount() = mPostList.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) =
        holder.bind(mPostList[position])

    private fun createPostViewHolder(parent: ViewGroup) =
        PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    fun addPosts(postList: List<Post>) {
        mPostList.addAll(postList)
        notifyDataSetChanged()
    }

    fun clearAllPosts() {
        mPostList.clear()
        notifyDataSetChanged()
    }

}
