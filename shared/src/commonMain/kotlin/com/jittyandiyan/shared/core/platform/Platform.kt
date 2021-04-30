package com.jittyandiyan.shared.core.platform

sealed class Platform(val osName: String)
class Android(osName: String,val apiVersion: Int):Platform (osName)
class iOS(osName: String,val osVersion: Double):Platform (osName)
