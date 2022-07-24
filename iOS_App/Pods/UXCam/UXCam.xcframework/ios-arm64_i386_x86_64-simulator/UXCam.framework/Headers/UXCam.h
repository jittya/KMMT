//
//  UXCam.h
//
//  Copyright (c) 2013-2021 UXCam Ltd. All rights reserved.
//
//  UXCam SDK VERSION: 3.4.0 - BETA
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <UserNotifications/UNNotification.h>
#import <UXCam/UXCamConfiguration.h>
#import <UXCam/UXCamOcclusion.h>

NS_ASSUME_NONNULL_BEGIN

/**
 *	UXCam SDK captures user experience data when a user uses an app, analyses this data on the cloud and provides insights to improve usability of the app.
 */
@interface UXCam : NSObject

/**
 * This notification is sent when a session verify completes.
 * The userInfo dictionary contains an NSNumber wrapped BOOL in the key @c UXCam_VerifyNotification_StartedKey that indicates if the verify result was YES or NO
 */
extern NSNotificationName const UXCam_VerifyNotification;

/// The key in the @c userInfo dictionary that contains the NSNumber wrapped BOOL indicating if the verification was sucessful or not
extern NSString* const UXCam_VerifyNotification_StartedKey;

/**
 * This notification is sent when the app returns to the foreground after an interruption where the @c allowShortBreak flag was set
 * @discussion The @c userInfo dictionary contains keys @c UXCam_Notification_Key_AllowShortBreakContinuing and @c UXCam_Notification_Key_AllowShortBreakDuration giving details of how long the break was, and if the original session is continuing.
 */
extern NSNotificationName const UXCam_Notification_AllowShortBreak;

/// The key in the @c userInfo dictionary that contains the NSNumber wrapped BOOL indicating if the original session is continuing or not
extern NSString* const UXCam_Notification_Key_AllowShortBreakContinuing;
/// The key in the @c userInfo dictionary that contains the NSNumber wrapped NSTimeInterval duration of the break (in seconds)
extern NSString* const UXCam_Notification_Key_AllowShortBreakDuration;


/**
 * The current configuration of the SDK. Value will be nil if called before @c startWithConfiguration method.
 */
@property (class, nullable, nonatomic, assign, readonly) UXCamConfiguration *configuration;

#pragma mark Methods for controlling if this device is opted out of session and/or schematic recordings
/**
 *	This will cancel any current session recording and opt this device out of future session recordings until @c optInOverall is called
 *	@note The default is to opt-in to session recordings, but not to schematic recordings, and the defaults will be reset if the user un-installs and re-installs the app
 */
+ (void) optOutOverall;

/**
 *	This will opt this device out of schematic recordings for future settings
 *	- any current session will be stopped and restarted with the last settings passed to @c startWithKey:
 */
+ (void) optOutOfSchematicRecordings;

/**
 *	This will opt this device into session recordings
 *	- any current session will be stopped and a new session will be started with the last settings passed to @c startWithKey:
 */
+ (void) optInOverall;

/**
 *	This will opt this device into schematic recordings for future sessions (subject to dashboard/verify settings settings as well)
 *	- any current session will be stopped and a new session will be started with the last settings passed to @c startWithKey:
 */
+ (void) optIntoSchematicRecordings;

/**
 *	Returns the opt-in status of this device
 *	@return YES if the device is opted in to session recordings, NO otherwise. The default is YES.
 */
+ (BOOL) optInOverallStatus;

/** Returns the opt-in status of this device for schematic recordings
 *	@returns YES if the device is opted in to schematic recordings, NO otherwise. The default is NO.
 *	@note Use in conjunction with optInOverallStatus to control the overall recording status for the device
 */
+ (BOOL) optInSchematicRecordingStatus;


#pragma mark Starting the UXCam system capturing data

/**
 *	Call this method from applicationDidFinishLaunching to start UXCam recording your application's session.
 *	This will start the UXCam system, get the settings configurations from our server and start capturing the data according to the configuration.
 *
 *	@brief Start the UXCam session
 *	@param configuration	The configuration to identify your UXCam account - find app key of configuration in the UXCam dashboard for your account at https://dashboard.uxcam.com/user/settings
 */
+ (void) startWithConfiguration:(UXCamConfiguration *)configuration;

/**
 *	Call this method from applicationDidFinishLaunching to start UXCam recording your application's session.
 * 	This will start the UXCam system, get the settings configurations from our server and start capturing the data according to the configuration.
 *
 *	@brief Start the UXCam session
 *	@param configuration		The configuration to identify your UXCam account - find app key of configuration in the UXCam dashboard for your account at https://dashboard.uxcam.com/user/settings
 *	@param sessionStartedBlock	This block will be called once the app settings have been checked against the UXCam server - the parameter will be TRUE if recording has started.  @b NOTE @b: This block is captured and called each time a new session starts.
 */
+ (void) startWithConfiguration:(UXCamConfiguration *)configuration
				completionBlock:(nullable void (^)(BOOL started))sessionStartedBlock;


#pragma mark Manage session once UXCam has started

/**
 *	@brief Stops the UXCam session and sends captured data to the server.
 *
 *	Use this to start sending the data to the UXCam dashboard without the app going into the background.
 *
 *	@note This starts an asynchronous process and returns immediately.
 */
+ (void) stopSessionAndUploadData;


/**
 *	Stops the UXCam application and sends captured data to the server.
 *	Use this to start sending the data to the UXCam dashboard without the app going into the background.
 *
 *	@brief Stop the UXCam session and send data to the server
 *	@param block Code block to call once upload process completes - will be run on the main thread. There might still be sessions waiting for upload if there was a problem with uploading any of them
 * 	@note This starts an asynchronous process and returns immediately.
 */
+ (void) stopSessionAndUploadData:(nullable void (^)(void))block;


/**
 *	By default UXCam will end a session immediately when your app goes into the background. But if you are switching over to another app for authorisation, or some other short action, and want the session to continue when the user comes back to your app then call this method with a value of TRUE before switching away to the other app.
 *	UXCam will pause the current session as your app goes into the background and then continue the session when your app resumes. If your app resumes after more than two minutes the original session will be closed and a new session will start - see note about app being killed while in background.
 *
 *	@brief Prevent a short trip to another app causing a break in a session
 *	@param continueSession Set to TRUE to continue the current session after a short trip out to another app. Default is FALSE - stop the session as soon as the app enters the background.
 *	@note If the app is in the background for more than the 'background time allowed' (3 minutes on iOS <=12 or 30 seconds since iOS >=13) and the app is killed then the session will be lost.
 *	@note Notification `UXCam_Notification_AllowShortBreak` is sent when the app returns from a break, and the payload of that indicates if the session continues or was too long and a new sessions is started
 */
+ (void) allowShortBreakForAnotherApp:(BOOL)continueSession;


/**
 * Get the current status of the @c allowShortBreakForAnotherApp flag
 */
+ (BOOL)getAllowShortBreakStatus;


/**
 * Sets the maximum duration of break allowed when the @c allowShortBreakForAnotherApp flag is @c TRUE
 *
 * @param duration Set to the duration of break allowed (in milliseconds)
 * @note The default duration is (180 * 1000) milliseconds
 */
+ (void)setAllowShortBreakMaxDuration:(double)duration;


/**
 * Get the maximum duration allowed for the short break (in milliseconds).
 */
+ (double)getAllowShortBreakMaxDuration;


/**
 * 	Starts a new session after the @c stopApplicationAndUploadData method has been called.
 *	This happens automatically when the app returns from background.
 *
 *	@note Any completion block registered during startWithKey: will be called as the session starts
 */
+ (void) startNewSession;


/**
 *	Cancels the recording of the current session and discards the data
 *
 *  @note A new session will start as normal when the app nexts come out of the background (depending on the state of the MultiSessionRecord flag), or if you call @c startNewSession
 */
+ (void) cancelCurrentSession;


/**
 *	Returns the current recording status
 *
 *	@return YES if the session is being recorded
 */
+ (BOOL) isRecording;


/**
 *	Pause the schematic recording
 *
 *  @note With this method gestures are not captured while schematic recording is paused. Use @c occludeSensitiveScreen:hideGestures: to control the capture of gestures when the screen is hidden.
 */
+ (void) pauseScreenRecording;


/**
 *	Resumes a paused schematic recording
 */
+ (void) resumeScreenRecording;


#pragma mark Manage sessions waiting for upload

/**
 *	@brief Returns how many sessions are waiting to be uploaded
 *
 *	Sessions can be in the Pending state if UXCam was unable to upload them at the end of the last session. Normally they will be sent at the end of the next session.
 */
+ (NSUInteger) pendingUploads;


/**
 *	@brief Begins upload of any pending sessions
 *
 *	@param block Code block to call once upload process completes - will be run on the main thread. There might still be sessions waiting for upload if there was a problem with uploading any of them
 *
 *	@note Advanced use only. This is not needed for most developers. This can't be called until @c [UXCam @c startWithKey:] has completed
 */
+ (void) uploadingPendingSessions:(nullable void (^)(void))block;


/**
 *	@brief Deletes any sessions that are awaiting upload
 *	@note Advanced use only. This is not needed for most developers. This can't be called until @c [UXCam @c startWithKey:] has completed
 */
+ (void) deletePendingUploads;


#pragma mark Methods for hiding sensitive information on the schematic recording

/**
	Hide a view that contains sensitive information or that you do not want recording on the schematic video.

	@param sensitiveView The view to occlude in the schematic recording
*/
+ (void) occludeSensitiveView:(UIView*)sensitiveView;


/**
 	Hide a view that contains sensitive information or that you do not want recording on the schematic video.
 	Additionally for a view hidden using this method any gesture that starts in the views frame will not be captured.
 
 	@param sensitiveView The view to occlude in the schematic recording
*/
+ (void) occludeSensitiveViewWithoutGesture:(UIView*)sensitiveView;


+ (void) applyOcclusion:(id<UXCamOcclusionSetting>)setting;
+ (void) removeOcclusionOfType:(UXOcclusionType)type;
+ (void) removeOcclusion;

/**
 	Stop hiding a view that was previously hidden
 
 	@param view The view to show again in the schematic recording

 	@note If the view passed in was not previously occluded then no action is taken and this method will just return
 */
+ (void) unOccludeSensitiveView:(UIView*)view;


/**
	Hide / un-hide the whole screen from the recording
 
	Call this when you want to hide the whole screen from being captured on the schematic recording - useful in situations where you don't have access to the exact view to occlude
	Once turned on with a TRUE parameter it will continue to hide the screen until called with FALSE
 
	@param hideScreen Set TRUE to hide the screen from the recording, FALSE to start schematic recording of the screen contents again
 
 	@note With this method gestures are not captured on the hidden screen. Use @c occludeSensitiveScreen:hideGestures: to control the capture of gestures when the screen is hidden.
*/
+ (void) occludeSensitiveScreen:(BOOL)hideScreen;


/**
 	Hide / un-hide the whole screen from being captured on the schematic recording and control whether gestures are till captured while the screen is hidden
 
 	Call this when you want to hide the whole screen from being captured - useful in situations where you don't have access to the exact view to occlude
 	Once turned on with a hideScreen TRUE parameter it will continue to hide the screen until called with FALSE
 
 	@param hideScreen Set TRUE to hide the screen from the recording, FALSE to start schematic recording of the screen contents again
 	@param hideGestures Set TRUE to hide the gestures while the screen is hidden, FALSE to still capture gesture locations while the screen is hidden (has no effect when @c hideScreen is FALSE)
 */
+ (void) occludeSensitiveScreen:(BOOL)hideScreen hideGestures:(BOOL)hideGestures;


/**
	Hide / un-hide all UITextField views on the schematic recording
 
	Call this when you want to hide the contents of all UITextFields from the schematic capture. Default is NO.
 
	@param occludeAll Set YES to hide all UITextField views on the screen in the schematic recording, NO to stop occluding them from the schematic recording.
 */
+ (void) occludeAllTextFields:(BOOL)occludeAll;


#pragma mark Methods for controlling screen naming

/**
	UXCam normally captures the view controller name automatically but in cases where it this is not sufficient (such as in OpenGL applications)
	or where you would like to set a different unique name, use this function to set the name.

	@note Call this in @c [UIViewController @c viewDidAppear:] after the call to @c [super ...] or automatic screen name tagging will override your value.

	Screen names added with this method will not be filtered by the ignore list.

	@param screenName Name to apply to the current screen in the session
 */
+ (void) tagScreenName:(NSString*)screenName;

/**
	Add a name to the list of screens names that wont be added to the timeline in automatic screen name tagging mode

	This will not impact gesture or action recording - just that the timeline on the dashboard will not contain an entry for this screen name if it appears after this call.
	Use this if you have view controllers that are presented but which are not primary user interaction screens to make your dashboard timeline easier to understand.

	@param nameToIgnore A name to add to the list of screens to ignore

	@note This is a convenience method for @c addScreenNamesToIgnore:\@[nameToIgnore]
 
 */
+ (void) addScreenNameToIgnore:(NSString*)nameToIgnore;

/**
	Add a list of names to the list of screens names that wont be added to the timeline in automatic screen name tagging mode

	This will not impact gesture or action recording - just that the timeline on the dashboard will not contain an entry for any of the screens in this list encountered after this call.
	Use this if you have view controllers that are presented but which are not primary user interaction screens to make your dashboard timeline easier to understand.

	@param namesToIgnore A list of screen names to add to the ignore list
 */
+ (void) addScreenNamesToIgnore:(NSArray<NSString*>*)namesToIgnore;

/**
	Remove the a name from the list of screens to be ignored in automatic screen name tagging mode

	@param nameToRemove The name to remove from the list of ignored screens
	@note This is a convenience method for @c removeScreenNamesToIgnore:\@[nameToRemove]
 */
+ (void) removeScreenNameToIgnore:(NSString*)nameToRemove;

/**
	Remove the a list of names from the list of screens to be ignored in automatic screen name tagging mode
 
	@param namesToRemove A list of names to remove from the ignore list
 */
+ (void) removeScreenNamesToIgnore:(NSArray<NSString*>*)namesToRemove;

/**
	Remove all entries from the list of screen names to be ignored in automatic screen name tagging mode
 */
+ (void) removeAllScreenNamesToIgnore;

/**
	Get the list of screen names that are being ignored in automatic screen name tagging mode
 */
+ (NSArray<NSString*>*)screenNamesBeingIgnored;


#pragma mark Methods for adding events and data to the current session

/**
	UXCam uses a unique number to tag a device.
	You can set a user identity for a device allowing you to more easily search for it on the dashboard and review their sessions further.

	@param userIdentity String to apply to this user (device) in this recording session
	@note Starting with SDK v2.5.11 there is no default for this value - to have the previous behaviour call @c [UXCam setUserIdentity:UIDevice.currentDevice.name];
 */
+ (void) setUserIdentity:(NSString*)userIdentity;


/**
	Add a key/value property for this user

	@param propertyName Name of the property to attach to the user
	@param value A value to associate with this property

	@note Only NSNumber and NSString value types are supported to a maximum size per entry of 1KiB
 */
+ (void) setUserProperty:(NSString*)propertyName value:(id)value;


/**
	Add a single key/value property to this session

	@param propertyName Name of the property to attach to the session recording
	@param value A value to associate with this property

	@note Only NSNumber and NSString value types are supported to a maximum size per entry of 1KiB
	
	@note This has never been fully supported on the dashboard - the mix of user properties and events has been enough, so from SDK 3.3.1 it can be considered deprecated.
 */
+ (void) setSessionProperty:(NSString*)propertyName value:(id)value;


/**
	Insert a general event into the timeline - stores the event with the timestamp when it was added.

	@param eventName Name of the event to attach to the session recording at the current time
*/
+ (void) logEvent:(NSString*)eventName;


/**
	Insert a general event, with associated properties, into the timeline - stores the event with the timestamp when it was added.
 
	@param eventName Name of the event to attach to the session recording at the current time
	@param properties An NSDictionary of properties to associate with this event
 
	@note Only NSNumber and NSString property types are supported to a maximum count of 100 and maximum size per entry of 1KiB in the properties dictionary
 */
+ (void) logEvent:(NSString*)eventName withProperties:(nullable NSDictionary<NSString*, id>*)properties;


/**
	Set the token to be used to send push notifications to the app
	@param pushToken  String from the deviceToken Data
 */
+ (void) setPushNotificationToken:(nullable NSString*)pushToken;


/**
    Insert push notification related event into the timeline - stores the event with the timestamp when it was added.
    If the notification is logged before session starts. The log is inserted at event time = 0.
    @param notification a UNNotification object that was obtained from Push Notification
 */
+ (void) logNotification:(UNNotification*)notification;


#pragma mark Methods for adding a problem report to your timeline
// SDK 3.3.0 - BETA methods - not yet reflected on Dashboard

/**
	Send a report of a problem your app encountered to be displayed in the dashboard
	@param name The name to call the proplem
	@param properties Properties associated with the report
	@note Only NSNumber and NSString property types are supported to a maximum count of 100 and maximum size per entry of 1KiB in the properties dictionary
 */
+ (void)reportBugEvent:(NSString*)name properties:(nullable NSDictionary<NSString*,id>*)properties;

/**
	Report an exception that your app encountered and handled. To be shown in an area of the dashboard
	@param exception The exception that was caught
	@param properties Properties to attach to this exception report
	@note Only NSNumber and NSString property types are supported to a maximum count of 100 and maximum size per entry of 1KiB in the properties dictionary
 */
+ (void)reportExceptionEvent:(NSException*)exception properties:(nullable NSDictionary<NSString*,id>*)properties;


#pragma mark Retrieve URLs for viewing sessions
/**
 *  Returns a URL path for showing all the current users sessions
 *
 *	@note This can be used for tying in the current user with other analytics systems
 *
 *  @return url path for all the users sessions or nil if no verified session is active
 */
+ (nullable NSString*) urlForCurrentUser;


/**
 *  Returns a URL path that shows the current session when it compeletes
 *
 *	@note This can be used for tying in the current session with other analytics systems
 *
 *  @return url path for current session or nil if no verified session is active
 */
+ (nullable NSString*) urlForCurrentSession;

#pragma mark Other methodss

/**
	Gets a list of the gesture recognizers that UXCam has added - might be needed if you are using the UIGestureRecognizerDelegate method @c shouldRecognizeSimultaneouslyWithGestureRecognizer
	Your gesture recognizer code should allow these to run alongside - they are all FALSE for @c cancelsTouchesInView, @c delaysTouchesBegan & @c delaysTouchesEnded so can run alongside other GRs
 */
+ (NSArray<UIGestureRecognizer*>*) GetGestureRecognizers;


#pragma mark Internal use only methods
/**
 *	 Used to identify non-native platform systems
 *   @note For internal use only
 */
+ (void) pluginType:(NSString*)type version:(NSString*)versionNumber;

/// Used by non-native view hybrid wrappers to pass in occlusion rects - An array where each item is an array of 4 NSNumber items - each define a rect: x, y, width, height
+ (void) occludeRectsOnNextFrame:(NSArray<NSArray<NSNumber*>*>*)rectList;

#pragma mark - Deprecated methods

+ (void) startWithKey:(NSString*)userAPIKey  __attribute__((deprecated("Use startWithConfiguration: instead")));
+ (void) startWithKey:(NSString*)userAPIKey
      completionBlock:(nullable void (^)(BOOL started))sessionStartedBlock __attribute__((deprecated("Use startWithConfiguration:completionBlock: instead")));
+ (void) startWithKey:(NSString*)userAPIKey
     multipleSessions:(BOOL)multiSession completionBlock:(nullable void (^)(BOOL started))sessionStartedBlock __attribute__((deprecated("Use startWithConfiguration:completionBlock: instead")));

+ (void) setMultiSessionRecord:(BOOL)recordMultipleSessions  __attribute__((deprecated("Set enableMultiSessionRecord on UXCam configuration object instead")));
+ (BOOL) getMultiSessionRecord  __attribute__((deprecated("Fetch using isMultiSessionRecordEnabled on UXCam configuration object instead")));
+ (void) disableCrashHandling:(BOOL)disable  __attribute__((deprecated("Set enableCrashHandling on UXCam configuration object instead. Note: Changed from set disabled to set enabled (enabled = YES by default)")));
+ (void) setAutomaticScreenNameTagging:(BOOL)enable  __attribute__((deprecated("Set enableAutomaticScreenNameTagging on UXCam configuration object instead")));
+ (void) EnableAdvancedGestureRecognizers:(BOOL)enable  __attribute__((deprecated("Set enableAdvancedGestureRecognizers on UXCam configuration object instead")));
+ (void) captureLogOutput  __attribute__((deprecated("Set using dashboard controls on the website")));

@end

NS_ASSUME_NONNULL_END
