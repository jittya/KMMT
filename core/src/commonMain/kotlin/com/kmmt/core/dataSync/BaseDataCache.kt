package com.kmmt.core.dataSync

import com.kmmt.common.functional.Either
import com.kmmt.common.models.Failure
import com.kmmt.persistance.keyValueStore.settings.getStoreValue
import com.kmmt.persistance.keyValueStore.settings.storeValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

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
                            storeValue { putLong(SYNC_KEY, Clock.System.now().toEpochMilliseconds()) }
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
            return ((lastSyncTime + oneHourMS) < Clock.System.now().toEpochMilliseconds())
        } else {
            return true
        }
    }
}