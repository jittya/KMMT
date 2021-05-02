package com.jittyandiyan.mobile

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter

public interface TBreedQueries : Transacter {
  public fun <T : Any> selectAll(mapper: (
    id: Long,
    name: String,
    favorite: Boolean
  ) -> T): Query<T>

  public fun selectAll(): Query<TBreed>

  public fun <T : Any> selectById(id: Long, mapper: (
    id: Long,
    name: String,
    favorite: Boolean
  ) -> T): Query<T>

  public fun selectById(id: Long): Query<TBreed>

  public fun <T : Any> selectByName(name: String, mapper: (
    id: Long,
    name: String,
    favorite: Boolean
  ) -> T): Query<T>

  public fun selectByName(name: String): Query<TBreed>

  public fun insertBreed(id: Long?, name: String): Unit

  public fun deleteAll(): Unit

  public fun updateFavorite(favorite: Boolean, id: Long): Unit
}
