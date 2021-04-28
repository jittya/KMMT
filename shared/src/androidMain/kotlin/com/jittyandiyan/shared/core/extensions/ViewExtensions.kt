package com.jittyandiyan.shared.core.extensions

import android.view.View

fun View.setClickAction(onClick: () -> Unit)
{
    setOnClickListener {
        onClick.invoke()
    }
}