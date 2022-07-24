//
//  UXCamConfiguration.h
//  UXCam
//
//  Created by Ankit Karna on 25/08/2021.
//  Copyright © 2021 UXCam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UXCam/UXCamOcclusion.h>

NS_ASSUME_NONNULL_BEGIN

@interface UXCamConfiguration : NSObject

/**
 *  @brief AppKey for the current UXCam user
 */
@property (strong, nonatomic, readonly) NSString *userAppKey;

/**
 *	Set whether to record multiple sessions or not
 *
 *  YES to record a new session automatically when the device comes out of the background. If NO then a single session is recorded, when stopped (either programmatically with @c stopApplicationAndUploadData or by the app going to the background) then no more sessions are recorded until @c startWithKey is called again).
 *  @note The default setting is to record a new session each time a device comes out of the background. This flag can be set to NO to stop that. You can also set this with the appropriate startWithKey: variant. (This will be reset each time startWithKey is called)
 */
@property (nonatomic, assign) BOOL enableMultiSessionRecord;

/**
 *  @brief Set this to false before calling startWithKey to disable UXCam from capturing crashed sessions
 *
 *  NO to disable crash capture
 *  @note By default crashhandling is enabled, with it disabled then sessions that crash will be lost
 */
@property (nonatomic, assign) BOOL enableCrashHandling;

/**
 *	@brief Enable / disable the automatic tagging of screen names
 *
 *	By default UXCam will tag new screen names automatically. You can override this using the @c tagScreenName: method, or use this property to disable the automatic tagging. Build a list of screen names to ignore with the @c addScreenNamesToIgnore: method
 *
 *	Set to TRUE to enable automatic screen name tagging (the default) or FALSE to disable it
 */
@property (nonatomic, assign) BOOL enableAutomaticScreenNameTagging;

/**
 * Control the gesture recognizers used by UXCam
 * TRUE to enable the full range of gesture recognizers (the default), or FALSE to limit it to basic touches
 * @note Disabling the advanced gesture recognizers (swipes, zoom etc.) can be useful if you have another SDK integrated that doesn't cooperate properly with gesture recognizers installed in other views.
 * @note This property can only be called before `startWIthKey` has been called
 */
@property (nonatomic, assign) BOOL enableAdvancedGestureRecognition;

/**
 *  Set this method from @c applicationDidFinishLaunching before @c startWithKey: method to capture network logs.
 *  This will control the capture of summary logs about the applications network activity. By default this is disabled.
 *
 *  @brief Enable or disable capturing logs of network activity
 *  YES to enable capturing logs. If NO, UXCam will not capture any network logs until it is set to YES again.
 */
@property (nonatomic, assign) BOOL enableNetworkLogging;

@property (nullable, nonatomic, strong) UXCamOcclusion *occlusion;

/**
 *	@brief Designated initializer for the configuration object
 *  @param userAppKey    The key to identify your UXCam account - find it in the UXCam dashboard for your account at https://dashboard.uxcam.com/user/settings
 */
- (instancetype)initWithAppKey:(NSString *)userAppKey NS_DESIGNATED_INITIALIZER;

/**
 * Use  `initWithAppKey` to create a configuration instance
 */
- (instancetype)init NS_UNAVAILABLE;

@end

NS_ASSUME_NONNULL_END
