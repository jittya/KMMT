package com.kmmt.persistance.database.realm

import com.kmmt.persistance.expectations.Dispatchers_Default
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

    constructor(emptyRealmObject: RealmObject) {
        val config = RealmConfiguration.Builder(schema = setOf(emptyRealmObject::class))
            .build()
        realm = Realm.open(config)
    }

    @JvmOverloads
    fun <T : BaseRealmObject> Flow<ResultsChange<T>>.mapToList(
        context: CoroutineContext = Dispatchers_Default
    ): Flow<List<T>> = map {
        withContext(context) {
            it.list
        }
    }
}