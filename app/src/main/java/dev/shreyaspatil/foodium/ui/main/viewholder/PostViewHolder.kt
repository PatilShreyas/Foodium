package dev.shreyaspatil.foodium.ui.main.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import dev.shreyaspatil.foodium.R
import dev.shreyaspatil.foodium.databinding.ItemPostBinding
import dev.shreyaspatil.foodium.model.Post
import dev.shreyaspatil.foodium.ui.main.adapter.PostListAdapter

/**
 * [RecyclerView.ViewHolder] implementation to inflate View for RecyclerView.
 * See [PostListAdapter]]
 */
class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post, onItemClickListener: PostListAdapter.OnItemClickListener? = null) {
        binding.postTitle.text = post.title
        binding.postAuthor.text = post.author
        binding.imageView.load(post.imageUrl) {
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }

        onItemClickListener?.let { listener ->
            binding.root.setOnClickListener {
                listener.onItemClicked(post, binding.imageView)
            }
        }
    }
}
