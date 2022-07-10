package com.jittyandiyan.androidApp

import android.app.Application
import android.os.Build
import android.provider.Settings
import com.kmmt.analytics.core.AppInfo
import com.kmmt.analytics.core.BuildType
import com.kmmt.analytics.event.log.events.EventAppOpened
import com.kmmt.analytics.event.log.logEvent
import com.kmmt.injector.koin.Injector

class KMMTApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Injector.initKoin(com.kmmt.common.expectations.Application(this),getAppInfo())

        EventAppOpened().logEvent()
    }

    private fun getAppInfo(): AppInfo {
        var myIP: String = ""
        /*  try {
              val wifiManager =
                  this.getSystemService(WIFI_SERVICE) as WifiManager
              val myIPAddress: ByteArray =
                  BigInteger.valueOf(wifiManager.connectionInfo.ipAddress.toLong()).toByteArray()
              myIPAddress.reverse()
              val myInetIP: InetAddress = InetAddress.getByAddress(myIPAddress)
              myIP = myInetIP.hostAddress
          } catch (e: Exception) {
              e.printStackTrace()
          }
         */


        var DeviceManufacturer = Build.MANUFACTURER
        var Device = Build.DEVICE
        var DeviceModel = Build.MODEL

        var IP = myIP

        var DeviceID = Build.ID

        var DeviceName = ""

        Settings.Secure.getString(
            contentResolver,
            "bluetooth_name"
        )?.let { DeviceName = it }


        val deviceOsVersion: String = Build.VERSION.RELEASE + "(" + Build.VERSION.SDK_INT + ")"
        return AppInfo(
            DeviceID,
            DeviceName,
            "$DeviceModel($DeviceManufacturer $Device)",
            IP,
            "Android",
            deviceOsVersion,
            BuildConfig.APPLICATION_ID,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE.toString(),
            if (BuildConfig.BUILD_TYPE == "release") BuildType.RELEASE else BuildType.DEBUG,
            BuildConfig.BUILD_TYPE
        )
    }

}