//
//  AppDelegate.swift
//  KMMT App
//
//  Created by Innovateq on 26/04/2021.
//

import UIKit
import shared

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    let appInfo = AppInfo(
        deviceID: "DeviceID",
        deviceName: "DeviceName",
        deviceModel: "test",
        IP: "IP",
        OS: "IOS",
        OSVersion: "deviceOsVersion",
        appID: "BuildConfig.APPLICATION_ID",
        appVersion: "123",
        appVersionCode: "123",
        appBuildType: BuildType.debug,
        appBuildFlavor: "BuildConfig.FLAVOR"
    )

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
//        KMMTApp.Companion().doInit(context: self)
        Injector.shared.doInitKoin(application: Application(uiApplication: application),appInfo: appInfo)
        EventAppOpened().logEvent()
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }


}

