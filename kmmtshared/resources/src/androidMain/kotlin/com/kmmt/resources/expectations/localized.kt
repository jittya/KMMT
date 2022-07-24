package com.kmmt.resources.expectations

import com.kmmt.common.dependencyInjection.Android
import dev.icerock.moko.resources.StringResource

actual fun StringResource.localized():String {
   return Android.application.application.getString(this.resourceId)
}