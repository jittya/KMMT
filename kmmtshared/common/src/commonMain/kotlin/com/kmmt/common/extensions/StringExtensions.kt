package com.kmmt.common.extensions

fun String.toWordCaps(): String {
    val words = this.split(" ")

    var newStr = ""

    words.forEach {
        newStr += it.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } + " "
    }
    return newStr.trim()
}