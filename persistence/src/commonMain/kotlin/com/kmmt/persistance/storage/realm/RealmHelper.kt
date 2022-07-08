package com.kmmt.persistance.storage.realm

import com.kmmt.common.expectations.DispatcherDefault
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.types.BaseRealmObject
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.JvmOverloads

open class RealmHelper {
    val realm: Realm

    constructor(realmObject: RealmObject) {
        val config = RealmConfiguration.Builder(schema = setOf(realmObject::class))
            .build()
        realm = Realm.open(config)
    }

    @JvmOverloads
    fun <T : BaseRealmObject> Flow<ResultsChange<T>>.mapToList(
        context: CoroutineContext = DispatcherDefault
    ): Flow<List<T>> = map {
        withContext(context) {
            it.list
        }
    }
}