package dev.shreyaspatil.foodium.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Abstract Activity which binds [ViewModel] [VM] and [ViewBinding] [VB]
 */
abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

    @Inject
    protected lateinit var mViewModelProvider: ViewModelProvider.Factory

    protected val mViewModel by lazy { getViewModel() }

    protected lateinit var mViewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        mViewBinding = getViewBinding()
    }

    /**
     * It returns [VB] which is assigned to [mViewBinding] and used in [onCreate]
     */
    abstract fun getViewBinding(): VB

    /**
     * It returns [VM] which is assigned to [mViewModel] and initialized in [onCreate]
     */
    abstract fun getViewModel(): VM
}