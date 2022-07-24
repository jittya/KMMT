package com.kmmt.resources

import dev.icerock.moko.graphics.toUIColor
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import platform.UIKit.UIColor
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle


fun ColorResource.getUIColor(): UIColor {
    var userInterfaceStyle: UIUserInterfaceStyle = UIScreen.mainScreen.traitCollection.userInterfaceStyle
    return when (this) {
        is ColorResource.Single -> {
            color
        }
        is ColorResource.Themed -> {
            when (userInterfaceStyle) {
                UIUserInterfaceStyle.UIUserInterfaceStyleDark -> dark
                UIUserInterfaceStyle.UIUserInterfaceStyleLight -> light
                UIUserInterfaceStyle.UIUserInterfaceStyleUnspecified -> light
                else -> light
            }
        }
    }.toUIColor()
}
