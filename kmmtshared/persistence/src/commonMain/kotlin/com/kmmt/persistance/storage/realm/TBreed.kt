package com.kmmt.persistance.storage.realm

import com.kmmt.models.demo.domain.Breed
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class BreedR : RealmObject {
    @PrimaryKey
    var id: Long = 0
    var name: String = ""
    var favorite: Boolean = false
}

fun BreedR.mapToBreed(): Breed {
    return Breed(id, name, favorite)
}


fun List<BreedR>.mapToBreed(): List<Breed> {
    return map {
        it.mapToBreed()
    }
}

fun Realm.nextBreedID():Int{
    val currentIdNum: Long? = this.query<BreedR>().max("id",Long::class).find()
    val nextId: Int = if (currentIdNum == null) {
        1
    } else {
        currentIdNum.toInt() + 1
    }

    return nextId
}