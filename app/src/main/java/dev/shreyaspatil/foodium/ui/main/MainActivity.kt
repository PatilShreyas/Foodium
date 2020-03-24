package dev.shreyaspatil.foodium.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shreyaspatil.MaterialDialog.MaterialDialog
import dev.shreyaspatil.foodium.R
import dev.shreyaspatil.foodium.databinding.ActivityMainBinding
import dev.shreyaspatil.foodium.model.Post
import dev.shreyaspatil.foodium.ui.base.BaseActivity
import dev.shreyaspatil.foodium.ui.details.PostDetailsActivity
import dev.shreyaspatil.foodium.ui.main.adapter.PostListAdapter
import dev.shreyaspatil.foodium.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    PostListAdapter.OnItemClickListener {

    private lateinit var mAdapter: PostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)  // Set AppTheme before setting content view.

        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        mAdapter = PostListAdapter(this)

        // Initialize RecyclerView
        mViewBinding.postsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = mAdapter
        }

        initPosts()

        handleNetworkChanges()
    }

    private fun initPosts() {
        mViewModel.postsLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> showLoading(true)
                is State.Success -> {
                    mAdapter.setPosts(state.data)
                    showLoading(false)
                }
                is State.Error -> {
                    showToast(state.message)
                    showLoading(false)
                }
            }
        })

        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            getPosts()
        }

        // If State isn't `Success` then reload posts.
        if (mViewModel.postsLiveData.value !is State.Success) {
            getPosts()
        }
    }

    private fun getPosts() {
        mViewModel.getPosts()
    }

    private fun showLoading(isLoading: Boolean) {
        mViewBinding.swipeRefreshLayout.isRefreshing = isLoading
    }

    /**
     * Observe network changes i.e. Internet Connectivity
     */
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this, Observer { isConnected ->
            println("STATE CHANGED = $isConnected")
            if (!isConnected) {
                mViewBinding.textViewNetworkStatus.text = getString(R.string.text_no_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    show()
                    setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                }
            } else {
                if (mViewModel.postsLiveData.value is State.Error || mAdapter.itemCount == 0) {
                    getPosts()
                }
                mViewBinding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                    animate()
                        .alpha(1f)
                        .setStartDelay(ANIMATION_DURATION)
                        .setDuration(ANIMATION_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                hide()
                            }
                        })
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_theme -> {
                // Get new mode.
                val mode =
                    if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                        Configuration.UI_MODE_NIGHT_NO
                    ) {
                        AppCompatDelegate.MODE_NIGHT_YES
                    } else {
                        AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                    }

                // Change UI Mode
                AppCompatDelegate.setDefaultNightMode(mode)
                true
            }

            else -> true
        }
    }

    override fun onBackPressed() {
        MaterialDialog.Builder(this)
            .setTitle("Exit?")
            .setMessage("Are you sure want to exit?")
            .setPositiveButton("Yes") { dialogInterface, _ ->
                dialogInterface.dismiss()
                super.onBackPressed()
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .build()
            .show()
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun getViewModel() = viewModelOf<MainViewModel>(mViewModelProvider)

    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }

    override fun onItemClicked(post: Post, imageView: ImageView) {
        val intent = Intent(this, PostDetailsActivity::class.java)
        intent.putExtra(PostDetailsActivity.POST_ID, post.id)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageView,
            imageView.transitionName
        )

        startActivity(intent, options.toBundle())
    }
}
