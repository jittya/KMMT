package com.kmmt.persistance.storage.sqlite

import com.jittyandiyan.mobile.TBreed
import com.kmmt.models.demo.domain.Breed

fun TBreed.mapToBreed(): Breed {
    return Breed(id, name, favorite)
}

fun List<TBreed>.mapToBreed(): List<Breed> {
    return map {
        it.mapToBreed()
    }
}
