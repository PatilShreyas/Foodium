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
import kotlinx.android.synthetic.main.activity_main.*
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

        initLiveData()

        handleNetworkChanges()
    }

    private fun initLiveData() {
        println("ViewModel = $mViewModel")
        mViewModel.postsLiveData.observe(this, Observer { state ->
            when (state) {
                is Loading -> {
                    showToast("Loading")
                }
                is Success -> mAdapter.setPosts(state.data)
                is Error -> showToast(state.message)
            }
        })
    }

    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this, Observer { isConnected ->
            if (!isConnected) {
                networkStatusLayout.setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                textViewNetworkStatus.text = getString(R.string.text_no_connectivity)
                networkStatusLayout.apply {
                    alpha = 0f
                    show()

                    animate()
                        .alpha(1f)
                        .setDuration(ANIMATION_TIMEOUT)
                        .setListener(null)
                }
            } else {
                networkStatusLayout.setBackgroundColor(getColorRes(R.color.colorStatusConnected))
                textViewNetworkStatus.text = getString(R.string.text_connectivity)
                networkStatusLayout.animate()
                    .alpha(0f)
                    .setStartDelay(ANIMATION_TIMEOUT)
                    .setDuration(ANIMATION_TIMEOUT)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            networkStatusLayout.hide()
                        }
                    })
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
