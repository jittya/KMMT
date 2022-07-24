package com.kmmt.persistance.storage.sqlite

import com.kmmt.common.expectations.DispatcherDefault
import com.squareup.sqldelight.Query
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads

open class SQLDelightHelper  {

    /** Turns this [Query] into a [Flow] which emits whenever the underlying result set changes. */
    @JvmName("toFlow")
    fun <T : Any> Query<T>.asFlow(): Flow<Query<T>> = flow {
        emit(this@asFlow)

        val channel = Channel<Unit>(CONFLATED)
        val listener = object : Query.Listener {
            override fun queryResultsChanged() {
                channel.trySend(Unit).isSuccess
            }
        }
        addListener(listener)
        try {
            for (item in channel) {
                emit(this@asFlow)
            }
        } finally {
            removeListener(listener)
        }
    }


    @JvmOverloads
    fun <T : Any> Flow<Query<T>>.mapToOne(
        context: CoroutineContext = DispatcherDefault
    ): Flow<T> = map {
        withContext(context) {
            it.executeAsOne()
        }
    }

    @JvmOverloads
    fun <T : Any> Flow<Query<T>>.mapToOneOrDefault(
        defaultValue: T,
        context: CoroutineContext = DispatcherDefault
    ): Flow<T> = map {
        withContext(context) {
            it.executeAsOneOrNull() ?: defaultValue
        }
    }

    @JvmOverloads
    fun <T : Any> Flow<Query<T>>.mapToOneOrNull(
        context: CoroutineContext = DispatcherDefault
    ): Flow<T?> = map {
        withContext(context) {
            it.executeAsOneOrNull()
        }
    }

    @JvmOverloads
    fun <T : Any> Flow<Query<T>>.mapToOneNotNull(
        context: CoroutineContext = DispatcherDefault
    ): Flow<T> = mapNotNull {
        withContext(context) {
            it.executeAsOneOrNull()
        }
    }

    @JvmOverloads
    fun <T : Any> Flow<Query<T>>.mapToList(
        context: CoroutineContext = DispatcherDefault
    ): Flow<List<T>> = map {
        withContext(context) {
            it.executeAsList()
        }
    }
}