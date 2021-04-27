package com.jittyandiyan.shared.core.architecture.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jittyandiyan.shared.core.architecture.viewModel.BaseViewModel

abstract class KMMActivity<ViewModel> : AppCompatActivity() where ViewModel : BaseViewModel<*> {

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initializeViewModel()
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

    fun showPopUpMessage(message: String) {
        AlertDialog.Builder(this).setMessage(message)
            .setPositiveButton("OK"
            ) { dialog, which -> dialog?.dismiss() }
            .create().show()
    }

    fun showPopUpMessage(title:String,message: String) {
        AlertDialog.Builder(this).setTitle(title).setMessage(message)
            .setPositiveButton("OK"
            ) { dialog, which -> dialog?.dismiss() }
            .create().show()
    }
}


