package dev.shreyaspatil.foodium.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

    @Inject
    protected lateinit var mViewModelProvider: ViewModelProvider.Factory

    protected lateinit var mViewModel: VM

    protected lateinit var mViewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        mViewBinding = getViewBinding()
        if (!this::mViewModel.isInitialized) {
            mViewModel = getViewModel()
        }

        setContentView(mViewBinding.root)
    }

    abstract fun getViewBinding(): VB

    abstract fun getViewModel(): VM
}