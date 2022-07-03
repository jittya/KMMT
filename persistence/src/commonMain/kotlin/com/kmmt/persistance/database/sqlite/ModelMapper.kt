package com.kmmt.persistance.database.sqlite

import com.jittyandiyan.mobile.TBreed
import com.kmmt.persistance.dataSources.breed.Breed

fun TBreed.mapToBreed(): Breed {
    return Breed(id, name, favorite)
}

fun List<TBreed>.mapToBreed(): List<Breed> {
    return map {
        it.mapToBreed()
    }
}
