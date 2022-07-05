package com.kmmt.common.expectations

import com.kmmt.common.platforms.IOSPlatform
import com.kmmt.common.platforms.Platform
import platform.UIKit.UIDevice

actual val platform: Platform = IOSPlatform(UIDevice.currentDevice.systemName(), UIDevice.currentDevice.systemVersion.toDouble())
