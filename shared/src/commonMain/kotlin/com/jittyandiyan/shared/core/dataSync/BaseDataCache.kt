package com.jittyandiyan.shared.core.dataSync

import com.jittyandiyan.shared.core.functional.Either
import com.jittyandiyan.shared.core.keyValueStore.getStoreValue
import com.jittyandiyan.shared.core.keyValueStore.storeValue
import com.jittyandiyan.shared.core.models.Failure
import com.soywiz.klock.DateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseDataCache<in RequestParamType, DataType>(
    private val backgroundCoroutineScope: CoroutineScope,
    private val SYNC_KEY: String
) where RequestParamType : Any, DataType : Any {


    abstract suspend fun getData(param: RequestParamType): Either<DataType, Failure>
    abstract suspend fun saveData(data: DataType): Either<Boolean, Failure>

    fun cacheData(
        param: RequestParamType,
        forceRefresh: Boolean = false,
        resultFunction: (cachedResult: Either<Boolean, Failure>) -> Unit = {}
    ) {
        if (isSyncExpired() || forceRefresh) {
            backgroundCoroutineScope.launch {
                getData(param).either({
                    resultFunction.invoke(Either.Failure(it))
                }, {
                    backgroundCoroutineScope.launch {
                        saveData(it).either({
                            resultFunction.invoke(Either.Failure(it))
                        }, {
                            storeValue { putLong(SYNC_KEY, DateTime.nowLocal().local.unixMillisLong) }
                            resultFunction.invoke(Either.Success(it))
                        })
                    }
                })
            }
        }
    }

    private fun isSyncExpired(): Boolean {
        val lastSyncTime = getStoreValue<Long>(SYNC_KEY)
        if (lastSyncTime != null) {
            val oneHourMS = 30 * 1000
            return ((lastSyncTime + oneHourMS) < DateTime.nowLocal().local.unixMillisLong)
        } else {
            return true
        }
    }
}