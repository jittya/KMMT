# UXCam iOS framework


## Release Notes ##

Version   | Changes
---------- | ----------
3.4.1   | Occlusion with blur Support
        | Fallback as error when verify response is other than 200
3.4.0	| Changes to how the SDK is started - use the `UXCamConfiguration` to setup how the SDK operates and pass that object to `startWith:` call.
		| Logging of network activity is available - off by default - see `captureNetworkLogs` in the `UXCamConfiguration`
		| The capture of crashes has been made more reliable by using PLCrashReporter
		| Improvements to capture of Alert boxes and ActivitiyIndicators in the schematic recording
		| Improved reliability of `allowShortBreak` video capture
        | Added new APIs for better control over 'short breaks' - `getAllowShortBreakStatus`, `setAllowShortBreakMaxDuration` and `getAllowShortBreakMaxDuration`     
		|
		| **Known issues:** 
		|	The logging of push notification details `+ (void) logNotification:(UNNotification*)notification;` requires furher server side changes before it will work fully.
		|
		| **NB:** XCode 13.0 or newer is required for UXCam framework from 3.4.0 onwards
		|
		|
3.3.9	| Internal update to fix problems with Cordova view occlusion
3.3.8	| Fix a race condition that could cause a crash
3.3.7	| Stop ANR recording while on `allowShortBreak`
		| Added  `+ (void)logNotification:(UNNotification*)notification` to record push notifications received into the timeline
		| Improved automatic screen naming when modal views are not full screen and reduced screen name noise around keyboard controllers
		|
3.3.6	| Same code as v3.3.5 but built with XCode 12.4 to support older Clang/Bitcode versions
		|
		| NB: XCode 12.4 or newer is required for UXCam framework from 3.3.6 onwards
		|
3.3.5	| UTI mimetype problem on M1 simulators fixed
		| More improvements in Cellular upload scheduling
		| Race condition on upload multiple sessions fixed
		| Improved signal handler exit process
		| NB: Needs XCode 12.5 - use 3.3.6 if you still need XCode 12.4 support
		|
3.3.4	| Don't try and startup if running in SwiftUI preview mode
		| Fixed a problem for settings in offline sessions recorded immediately after a cold start
		| Fixed a problem with offline settings not honouring some verify responses
3.3.3	| Add a method to limit gesture recognizing to cooperate with other SDKs that make bad assumptions about UIGestureRecognizers
		| Improve interaction of occludeSensitiveScreen API and screens listed in the ScreenNamesToIgnore APIs/dashboard exclude list
		|
		| NB: XCode 12 is required for SDKs from 3.3.3 onwards
		|
3.3.2	| Fix a timestamp issue in reported exceptions
3.3.1	| Fixed an issue where a manually occluded screen state was reset on session boundaries
		| Fixed a problem with gestures over sensitive view
3.3.0	| Added APIs for logging handled exceptions - 
		|	`+ (void)reportBugEvent:(NSString*)name properties:(nullable NSDictionary<NSString*,id>*)properties;` and
		|	`+ (void)reportExceptionEvent:(NSException*)exception properties:(nullable NSDictionary<NSString*,id>*)properties;`
		| Improvements to upload scheduling
		| Crashed sessions aren't filtered for minimum duration
		|
		| Distribution format changed to XCFramework for future proofing when Apple Silicon simulator versions are needed.
		| Swift package manager integration available in XCode12/Swift 5.3+, Cocoapods version 1.9.0+ needed
		| 
		| NB: Xcode 11.4 or higher is required to use the UXCam framework from 3.3.0 onwards
		|
3.2.5	| Refactor of offline session verify to reduce retries attempts when offline
		| Improved handling of cancelled and not started sessions
		| Report to dashboard when exhausted offline video allowance
		| Fixed some rare occurrences of negative time in a screen when transitioning from one session to another
3.2.4	| Double tap gesture handling changed to reduce delays in single tap processing
		| Removed all support/references to UI Web View for Cordova in light of Apple full deprecation of UI Web View
		| NB: Framework requires XCode 11.4 or higher to use fully
3.2.3	| Fix an issue where starting a new session when one is already running could cause a failure to record the new session
3.2.2	| Fix a problem with auto-occluded screens when using manual tagging 
3.2.1	| Extra traps to avoid running and creating a crash on iOS 9
		| Removed methods deprecated in v3.0
		| Further improvements to cancelled session reporting
3.2.0	| Refactored network code to a more modern library to give better control over uploads
		| Improved handling of cancelled sessions
		| Improved rendering of long text strings in schematic captures
		|
		| NB: Xcode 11 is required to use the UXCam framework from 3.2.0 onwards
		|
3.1.15	| Fixing more issues with some hybrid platforms
3.1.14	| Fixing a problem with newer Flutter SDKs
3.1.13	| Fixed a problem with occlusion rects on certain devices
		| Removed some debug events that were being added to some timelines
3.1.12	| Reduced Podspec minimum iOS version back to 9.0, but UXCam will not record sessions on iOS 9 device, iOS 10 required for session recording
		| Refinement of mobile upload strategy
3.1.11	| Fix a problem with empty text strings in the schematic rendering
		| Contents of UITextField and UITextView now captured on schematic recordings to match native recording
3.1.10	| Fix alignment of text in schematic keyboard view
		| Improve schematic rendering for clipToBounds views and attributed text
		| Fix a problem with rendering system fonts in schematic views on iOS13
		| Performance improvements for schematic rendering
		| Implemented 'upload data only' for mobile networks - ready for backend support
3.1.9	| Improved react-native schematic rendering
		| Improved handling of very short sessions and rapid restart of session
		| Restore screen name when a UIAlertController is dismissed
		| Improved uploading of large batches of sessions
		| Right to left text alignment handling
3.1.8	| Extra precautions when generating JSON to handle bad values 
3.1.7	| Added 'application not responding' (ANR) monitoring
		| Fix a problem where some recording settings were ignored for offline sessions
		| Limits on number and size of events that can be added
3.1.6	| iOS10 minimum deployment target
		| Added accessor method to get list of gesture recognizers that UXCam adds to the main app window
		| Support for upcoming dashboard features
3.1.5	| Additional symbols from 3rd party libraries changed to avoid clashes. 
3.1.4	| Added API to create a list of screen names to not add to the timeline in automatic screen tagging mode
3.1.3	| Added marker of keyboard location to the schematic recording
		| Added an 'ultra low' video quality setting
3.1.2 	| Fix to mobile data upload limits
		| Fix for symbol conflict in internal copy of 3rd party library
3.1.1	| Internal changes for performance and stability 
3.1.0	| Change screen capture to schematic capture process
		| Opt In changes to split out screen recording as specific option - **screen recording off by default** 
		| Fix session video when starting up with occluded screen
		| Add reasons why screen video hasn't been recorded
		|  
		|
3.0.6	| Add `occludeSensitiveViewWithoutGesture` and `occludeSensitiveScreen:hideGestures:` API methods
		| Adjust work queues for event capture 
		| Add nullable decoration to session and user url methods
		| Adjust some internal timers to handle external time changes
		| Fix an orientation regression with sessions that start in landscape orientation
3.0.5	| Stop recording gestures when screen recording is paused or full screen is occluded
		| Fix an issue on initial setup of data capture
3.0.4	| Improvements to filter handling when account is low on sessions left to record
3.0.3	| Fix a session management issue
3.0.2 	| Work around the iOS bug that causes excessive screen capture time on wide colour devices
3.0.1	| Fixing some header file deprecations to avoid ambiguous method errors in Swift
		| Improved handling of devices with low levels of available storage
3.0.0  	| Extensive refactoring of the internals of the SDK to support new features
		| Added session filters for screen name, session duration, number of interactions
		| Added support for offline session recording
		| Added support for data-only sessions (screens visited, number of interactions, event timeline etc. without a screen video)
		| Re-factored the UXCam API on iOS and Android to be more similar and iOS to better conform with standard naming practices
		| Re-factored the event recording system to include user and session events as well as general timeline events
		| Note: Sep 2018: Several v3 features require using the new dashboard that is coming soon - talk to support to get preview access 
		|
2.5.18	| Adding `unOccludeSensitiveView` method
		| Fixing a problem that exposed sensitives views for their first frame on screen in some circumstances
2.5.17	| Fixing a problem with tag collection from multiple threads
		| Fixing memory leaks when recording screens with video content
2.5.16	| Fixing a 2.5.15 introduced bug with network reachability
2.5.15	| Fixing some more iOS11 warnings about documentation in UXCam.h
		| Problems when recording iPad apps in split mode have been fixed
		| [BETA] `allowShortBreakForAnotherApp` method - will pause the recording while the user goes out to another.
		| [BETA] `StopRecordingScrollingOnStutterOS` method to work around iOS11.2 problems
2.5.14	| Fixing a warning in iOS11
2.5.13	| Adjusting the occlusion of sensitive views during animations
2.5.12	| Adjusting the occlusion of secure and/or UITextFields to be less sensitive to screen construction order
2.5.11	| Removed the default setting of `tagUsersName` from UIDevice.currentDevice.name - there is no default now
2.5.10	| Fixing a problem with screen names not being registered if no events occured on that screen
2.5.9	| Adding `occludeAllTextFields` method
		| Adding `SetMultiSessionRecord` and `GetMultiSessionRecord` methods
		| Adding `PauseScreenRecording` and `ResumeScreenRecording` methods
		| Adding `cancelCurrentSession` method
		| Restoring the `DisableCrashHandling` method
		| Adding method to access sessions awaiting upload. See `PendingUploads` for information
		| Adding method to upload any pending sessions. See `UploadingPendingSessions` for details
		| Improved occlusion of sensitive views in a moving scrollview
2.5.8	| Trapping nil value for screen name that would cause a crash 
		| Adding `SetAutomaticScreenNameTagging` to disable automatic screen name capture
2.5.7	| Capturing app version as well as build number for dashboard
2.5.6	| Fixing a problem with capturing Swift crashes
		| Fixing a problem with `NSCameraUsageDescription` when submitting apps from XCode8
		| Adding support for more quality settings
		| Optimising some code paths
2.5.5	| Fixing a bug that caused video problems on iOS8
2.5.4	| Improvements in gesture timeline
		| Improved capture of screen name at app start
		| Better handling of sensitive views after app comes out of background
		| Improved capture of crashed sessions
2.5.3	| Don't occlude sensitive views that are hidden
		| Fixing the `SessionURL` path
2.5.2	| Fixing a 3G/wifi upload issue
2.5.1	| Improving upload code
2.5.0	| Refactoring of sending multiple sessions in one upload
2.4.2	| Fixing user session URL
2.4.1	| Change in how user country is captured
2.4.0	| First version working as a Swift module
		| Requires XCode7
		| iOS7 is no longer tested against/supported, but should continue working
2.3.4	| Changing timer resets
2.3.3	| Adding verbose internal logging to SDK
2.3.1	| Internal improvements
2.3.0	| Ability to record just one session per run
2.2.2	| Trapping some crash handler errors
2.2.1	| Fixing log capture when more than one session in a run
2.2.0	| Adding log output capture and upload
2.1.1	| Fixing documenation links
2.1.0	| Adding app variant support to `startUXCam` methods
		| Exposing the occulde screen method to Cordova
		| Fixing session and user URLs
2.0.3	| Handle the case of an app having no icon file
2.0.2	| Fixing some version number values in the uploaded data
2.0.1	| Removed unused data fields from uploaded data
2.0.0	| Major re-engineering of the SDK in terms of backend used
