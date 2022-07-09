package com.kmmt.core.architecture.view

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.kmmt.core.architecture.presenter.BasePresenter
import com.kmmt.core.liveData.kLifecycle
import com.kmmt.core.platform.expectations.BundleParcel

abstract class KMMActivity<Presenter, UIViewBinding> :
    AppCompatActivity() where Presenter : BasePresenter<*>, UIViewBinding : ViewBinding {

    private lateinit var presenter: Presenter
    private var progressDialog: ProgressDialog? = null
    lateinit var binding: UIViewBinding

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewBindingInflate()
        presenter = initializePresenter()
        presenter.setLifeCycle(this.kLifecycle())
        intent.extras?.keySet()?.filterNotNull()?.forEach { key ->
            getPresenter().setBundleValue(key, intent.extras!![key])
        }
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        presenter.onInit()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetached()
    }

    fun getPresenter(): Presenter {
        return presenter
    }

    abstract fun initializePresenter(): Presenter
    abstract fun viewBindingInflate(): UIViewBinding

    fun setPageTitle(title: String) {
        this.title = title
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

    fun openActivity(activity: Class<*>, bundleParcel: BundleParcel) {
        var intent = Intent(this, activity)
        intent.putExtras(bundleParcel.bundle)
        startActivity(intent)
    }

    fun openActivity(activity: Class<*>) {
        var intent = Intent(this, activity)
        startActivity(intent)
    }
}


