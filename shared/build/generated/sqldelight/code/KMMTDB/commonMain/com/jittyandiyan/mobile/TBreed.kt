package com.jittyandiyan.mobile

public data class TBreed(
  public val id: Long,
  public val name: String,
  public val favorite: Boolean
) {
  public override fun toString(): String = """
  |TBreed [
  |  id: $id
  |  name: $name
  |  favorite: $favorite
  |]
  """.trimMargin()
}
