package dev.shreyaspatil.foodium.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import dev.shreyaspatil.foodium.R
import dev.shreyaspatil.foodium.databinding.ItemPostBinding
import dev.shreyaspatil.foodium.model.Post

class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.postTitle.text = post.title
        binding.postAuthor.text = post.author
        binding.imageView.load(post.imageUrl) {
            placeholder(R.drawable.ic_photo)
            error(R.drawable.ic_broken_image)
        }
    }
}
