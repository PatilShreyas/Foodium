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

package dev.shreyaspatil.foodium.ui.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.app.ShareCompat
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import dev.shreyaspatil.foodium.R
import dev.shreyaspatil.foodium.databinding.ActivityPostDetailsBinding
import dev.shreyaspatil.foodium.model.Post
import dev.shreyaspatil.foodium.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_post_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PostDetailsActivity : BaseActivity<PostDetailsViewModel, ActivityPostDetailsBinding>() {

    override val mViewModel: PostDetailsViewModel by viewModels()

    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val postId = intent.extras?.getInt(POST_ID)
            ?: throw IllegalArgumentException("`postId` must be non-null")

        initPost(postId)
    }

    private fun initPost(postId: Int) {
        mViewModel.getPost(postId).observe(this) { post ->
            mViewBinding.postContent.apply {
                this@PostDetailsActivity.post = post

                postTitle.text = post.title
                postAuthor.text = post.author
                postBody.text = post.body
            }
            mViewBinding.imageView.load(post.imageUrl)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun getViewBinding(): ActivityPostDetailsBinding =
        ActivityPostDetailsBinding.inflate(layoutInflater)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }

            R.id.action_share -> {
                val shareMsg = getString(
                    R.string.share_message,
                    post.title,
                    post.author
                )

                val intent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(shareMsg)
                    .intent

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val POST_ID = "postId"
    }
}
