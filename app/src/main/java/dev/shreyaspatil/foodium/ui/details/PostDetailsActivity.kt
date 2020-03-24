package dev.shreyaspatil.foodium.ui.details

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import coil.api.load
import dev.shreyaspatil.foodium.databinding.ActivityPostDetailsBinding
import dev.shreyaspatil.foodium.ui.base.BaseActivity
import dev.shreyaspatil.foodium.utils.viewModelOf
import kotlinx.android.synthetic.main.activity_post_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PostDetailsActivity : BaseActivity<PostDetailsViewModel, ActivityPostDetailsBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(mViewBinding.root)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val postId = intent.extras?.getInt(POST_ID)
            ?: throw IllegalArgumentException("postId must be non-null")

        initPost(postId)
    }

    private fun initPost(postId: Int) {
        mViewModel.getPost(postId).observe(this, Observer { post ->
            mViewBinding.postContent.apply {
                postTitle.text = post.title
                postAuthor.text = post.author
                postBody.text = post.body
            }
            mViewBinding.imageView.load(post.imageUrl)
        })
    }

    companion object {
        const val POST_ID = "postId"
    }

    override fun getViewBinding(): ActivityPostDetailsBinding =
        ActivityPostDetailsBinding.inflate(layoutInflater)

    override fun getViewModel() = viewModelOf<PostDetailsViewModel>(mViewModelProvider)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}