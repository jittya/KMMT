package com.jittyandiyan.shared.core.architecture.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel
import com.jittyandiyan.shared.core.liveData.kLifecycle
import com.jittyandiyan.shared.core.platform.expectations.BundleX

abstract class KMMActivity<ViewModel,UIViewBinding> : AppCompatActivity() where ViewModel : BaseViewModel<*>,UIViewBinding:ViewBinding {

    private lateinit var viewModel: ViewModel
    private var progressDialog: ProgressDialog? = null
    lateinit var binding:UIViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=viewBindingInflate()
        viewModel = initializeViewModel()
        viewModel.setLifeCycle(this.kLifecycle())
        intent.extras?.keySet()?.filterNotNull()?.forEach { key ->
            getViewModel().setBundleValue(key, intent.extras!![key])
        }
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onInit()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDetached()
    }

    fun getViewModel(): ViewModel {
        return viewModel
    }

    abstract fun initializeViewModel(): ViewModel
    abstract fun viewBindingInflate():UIViewBinding

    fun setPageTitle(title: String)
    {
        this.title=title
    }

    fun showPopUpMessage(message: String) {
        AlertDialog.Builder(this).setMessage(message)
            .setPositiveButton(
                "OK"
            ) { dialog, which -> dialog?.dismiss() }
            .create().show()
    }

    fun showPopUpMessage(title: String, message: String) {
        AlertDialog.Builder(this).setTitle(title).setMessage(message)
            .setPositiveButton(
                "OK"
            ) { dialog, which -> dialog?.dismiss() }
            .create().show()
    }

    fun showLoading(loadingLabel: String) {
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage(loadingLabel)
        progressDialog?.show()

    }

    fun dismissLoading() {
        progressDialog?.dismiss()
    }

    fun openActivity(activity: Class<*>, bundle: BundleX) {
        var intent = Intent(this, activity)
            intent.putExtras(bundle.bundle)
            startActivity(intent)
    }

    fun openActivity(activity: Class<*>) {
        var intent = Intent(this, activity)
        startActivity(intent)
    }
}


