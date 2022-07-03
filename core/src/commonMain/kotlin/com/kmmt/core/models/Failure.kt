package com.kmmt.core.models

sealed class Failure(val message: String)

class NetworkFailure(exception: Exception) : Failure(exception.message.toString())
class DataBaseFailure(exception: Exception) : Failure(exception.message.toString())
