/*
 * MIT License
 *
 * Copyright (c) 2020 Shreyas Patil
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
