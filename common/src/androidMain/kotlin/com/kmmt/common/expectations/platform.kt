package com.kmmt.common.expectations

import android.os.Build
import com.kmmt.common.platforms.AndroidPlatform
import com.kmmt.common.platforms.Platform

actual val platform: Platform = AndroidPlatform("Android", Build.VERSION.SDK_INT)
