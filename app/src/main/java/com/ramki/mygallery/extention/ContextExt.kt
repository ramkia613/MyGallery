package com.ramki.mygallery.extention

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Context.getAppSettingsIntent(): Intent {
    return Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", packageName, null)
    }
}
