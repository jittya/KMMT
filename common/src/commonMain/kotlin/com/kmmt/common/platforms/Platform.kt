package com.kmmt.common.platforms

sealed class Platform(val osName: String)
class AndroidPlatform(osName: String, val apiVersion: Int):Platform (osName)
class IOSPlatform(osName: String, val osVersion: Double):Platform (osName)