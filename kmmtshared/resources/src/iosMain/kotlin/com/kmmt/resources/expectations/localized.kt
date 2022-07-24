package com.kmmt.resources.expectations

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

actual fun StringResource.localized():String = StringDesc.Resource(this).localized()