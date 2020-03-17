package dev.shreyaspatil.foodium.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.shreyaspatil.MaterialDialog.MaterialDialog
import dagger.android.AndroidInjection
import dev.shreyaspatil.foodium.R
import dev.shreyaspatil.foodium.utils.NetworkUtils

abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var mViewModel: VM
    protected lateinit var mViewBinding: VB

    private val mDialog by lazy {
        MaterialDialog.Builder(this)
            .setTitle("No Internet!")
            .setMessage("Please turn on mobile data or Wi-Fi.")
            .setPositiveButton("OK", R.drawable.ic_check) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        mViewBinding = getViewBinding()
        mViewModel = getViewModel()

        setContentView(mViewBinding.root)
    }

    fun checkConnectivity() {
        NetworkUtils.getNetworkLiveData(this).observe(this, Observer { isConnected ->
            if (!isConnected) {
                mDialog.show()
            } else {
                mDialog.dismiss()
            }
        })
    }

    abstract fun getViewModel(): VM
    abstract fun getViewBinding(): VB
}