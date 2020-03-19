package dev.shreyaspatil.foodium.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dev.shreyaspatil.foodium.R
import dev.shreyaspatil.foodium.databinding.ActivityMainBinding
import dev.shreyaspatil.foodium.ui.Error
import dev.shreyaspatil.foodium.ui.Loading
import dev.shreyaspatil.foodium.ui.Success
import dev.shreyaspatil.foodium.ui.adapter.PostListAdapter
import dev.shreyaspatil.foodium.ui.viewmodel.MainViewModel
import dev.shreyaspatil.foodium.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    @Inject
    lateinit var mAdapter: PostListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)  // Set AppTheme before setting content view.

        super.onCreate(savedInstanceState)

        mViewBinding.postsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        initPosts()

        handleNetworkChanges()
    }

    private fun initPosts() {
        mViewModel.postsLiveData.observe(this, Observer { state ->
            when (state) {
                is Loading -> showLoading(true)
                is Success -> {
                    mAdapter.setPosts(state.data)
                    showLoading(false)
                }
                is Error -> {
                    showToast(state.message)
                    showLoading(false)
                }
            }
        })

        mViewBinding.swipeRefreshLayout.setOnRefreshListener {
            println("Trying to get data = ${mViewModel.postsLiveData.value}")
            getPosts()
        }

        if (mViewModel.postsLiveData.value !is Success) {
            getPosts()
        }
    }

    private fun getPosts() {
        mViewModel.getPosts()
    }

    private fun showLoading(isLoading: Boolean) {
        mViewBinding.swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this, Observer { isConnected ->
            if (!isConnected) {

                mViewBinding.textViewNetworkStatus.text = getString(R.string.text_no_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    alpha = 0f
                    show()
                    setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                    animate()
                        .alpha(1f)
                        .setDuration(ANIMATION_TIMEOUT)
                        .setListener(null)
                }
            } else {
                if (mViewModel.postsLiveData.value is Error) {
                    getPosts()
                }
                mViewBinding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                    animate()
                        .alpha(0f)
                        .setStartDelay(ANIMATION_TIMEOUT)
                        .setDuration(ANIMATION_TIMEOUT)
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
                        AppCompatDelegate.MODE_NIGHT_NO
                    }

                // Change UI Mode
                AppCompatDelegate.setDefaultNightMode(mode)
                true
            }

            else -> true
        }
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun getViewModel() = viewModelOf<MainViewModel>(mViewModelProvider)

    companion object {
        const val ANIMATION_TIMEOUT = 1000.toLong()
    }
}
