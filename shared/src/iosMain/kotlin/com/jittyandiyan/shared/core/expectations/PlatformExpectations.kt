package com.jittyandiyan.shared.core.expectations

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext

internal actual val ApplicationDispatcher: CoroutineDispatcher = NsQueueDispatcher(dispatch_get_main_queue())

internal class NsQueueDispatcher(
    private val dispatchQueue: dispatch_queue_t
) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        try {
            dispatch_async(dispatchQueue) {
                try {
                    block.run()
                }catch (e:Exception)
                {
                    print("ApplicationDispatcher Exception in ios block.run() "+e.message)
                }

            }
        }catch (e:Exception)
        {
            print("ApplicationDispatcher Exception in ios "+e.message)
        }
    }
}

actual val Dispatchers_Default: CoroutineDispatcher = Dispatchers.Main
