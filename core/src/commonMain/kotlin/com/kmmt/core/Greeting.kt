package com.kmmt.core

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}