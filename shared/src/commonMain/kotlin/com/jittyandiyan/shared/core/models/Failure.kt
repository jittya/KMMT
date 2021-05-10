package com.jittyandiyan.shared.core.models

sealed class Failure(val message: String)

class NetworkFailure(exception: Exception) : Failure(exception.message.toString())
