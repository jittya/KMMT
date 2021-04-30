package com.jittyandiyan.shared.core.architecture.viewModel

import com.jittyandiyan.shared.core.architecture.view.BaseView
import com.jittyandiyan.shared.core.architecture.viewModel.async.Async
import com.jittyandiyan.shared.core.architecture.viewModel.viewState.ViewState
import com.jittyandiyan.shared.core.extensions.getSerializable
import com.jittyandiyan.shared.core.extensions.toObject
import com.jittyandiyan.shared.core.models.BundleExtras
import com.jittyandiyan.shared.core.platform.Android
import com.jittyandiyan.shared.core.platform.expectations.BundleX
import com.jittyandiyan.shared.core.platform.expectations.platform
import com.jittyandiyan.shared.core.platform.iOS
import com.russhwolf.settings.Settings
import com.russhwolf.settings.contains
import com.russhwolf.settings.get
import kotlinx.serialization.KSerializer
import org.koin.core.component.get

abstract class BaseViewModel<View>(private var view: View) : Async() where View : BaseView {

    private var viewState = ViewState.UNKNOWN

    init {
        viewState = ViewState.INITIALIZED
    }

    abstract fun onStartViewModel()

    open fun onInit() {
        if (viewState == ViewState.INITIALIZED) {
            viewState = ViewState.STARTED
            onStartViewModel()
        }
    }

    fun onDetached() {
        viewState = ViewState.DETACHED
        cancelAllRunningCoroutines("ViewModel onDetached called")
    }

    fun getView(): View? {
        return if (viewState == ViewState.STARTED) {
            view
        } else {
            null
        }
    }

    fun getViewModel(): BaseViewModel<BaseView> {
        @Suppress("UNCHECKED_CAST")
        return this as BaseViewModel<BaseView>
    }

    var bundlesValues = mutableMapOf<String, Any?>()

    fun setBundle(bundle: BundleExtras) {
        bundlesValues = bundle.extras
    }

    fun setBundleValue(paramName: String, paramValue: Any?) {
        bundlesValues[paramName] = paramValue
    }

    inline fun <reified T> getBundleValue(paramName: String): T? where T : Any? {
        if (bundlesValues.containsKey(paramName)) {
            var value = bundlesValues[paramName]
            if (value != null) {
                if (value is String) {
                    return value.toObject<T>()
                } else {
                    return value as T
                }
            } else {
                return null
            }
        } else {
            return null
        }
    }

    fun Bundle(
        bundle: BundleExtras.() -> Unit
    ): BundleX {
        return BundleX(BundleExtras().also(bundle))
    }

    fun runOnAndroid(
        android: Android.() -> Unit
    ) {
        if (platform is Android) {
            android.invoke(platform)
        }
    }

    fun runOniOS(
        android: iOS.() -> Unit
    ) {
        if (platform is iOS) {
            android.invoke(platform)
        }
    }

    fun storeValue(
        keyValueStore: Settings.() -> Unit
    ) {
        keyValueStore.invoke(get())
    }

    inline fun <reified T> getStoreValue(key:String):T?
    {
        try {
            var value:T?= get<Settings>().get(key)
            return value
        }catch (e:IllegalArgumentException)
        {
            if (get<Settings>().contains(key))
            {
                throw IllegalArgumentException("KSerializer expected!. Use getStoreValue(key,KSerializer<T>)")
            }
            throw e
        }
    }

    inline fun <reified T> getStoreValue(key: String, serializer: KSerializer<T>):T?
    {
        return get<Settings>().getSerializable(key, serializer)
    }
}


