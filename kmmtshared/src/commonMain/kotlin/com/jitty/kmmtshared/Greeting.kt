package com.jitty.kmmtshared

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}