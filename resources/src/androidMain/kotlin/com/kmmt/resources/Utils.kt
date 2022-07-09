package com.kmmt.resources

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.StringResource

@Composable
fun stringResource(stringResource: StringResource): String =
    stringResource(stringResource.resourceId)

inline fun ColorResource.getColor(context: Context): Color {
    return when (this) {
        is ColorResource.Single -> Color(color.argb)
        is ColorResource.Themed -> {
            val configuration = context.resources.configuration
            when (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> Color(light.argb) // Night mode is not active, we're using the light theme
                Configuration.UI_MODE_NIGHT_YES -> Color(dark.argb) // Night mode is active, we're using dark theme
                else -> Color(light.argb) // No mode type has been set
            }
        }
    }
}

