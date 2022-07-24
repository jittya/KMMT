//
//  UXCamOcclusion.h
//  UXCam
//
//  Created by Ankit Karna on 19/01/2022.
//  Copyright © 2022 UXCam Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UXCam/UXCamOcclusionParameter.h>
#import <UXCam/UXCamOcclusionScreen.h>
#import <UXCam/UXCamBlurSetting.h>

NS_ASSUME_NONNULL_BEGIN

@interface UXCamOcclusion : NSObject

@property NSArray<UXCamOcclusionParameter *> *parameters;
@property NSArray <UXCamOcclusionScreen *> *screens;

- (instancetype)initWithSetting:(id<UXCamOcclusionSetting>)setting;
- (instancetype)initWithSettings:(NSArray<id<UXCamOcclusionSetting>> *)settings;

+ (UXBlurType)getBlurTypeFromName:(NSString *)name;
+ (NSString *)systemNameForBlurType:(UXBlurType)type;

+ (nullable id<UXCamOcclusionSetting>)getSettingFromJson:(NSDictionary *)json;

- (void)applySetting:(id<UXCamOcclusionSetting>)setting screens:(NSArray <NSString *> *)screens;
- (void)applySettings:(NSArray<id<UXCamOcclusionSetting>> *)settings
              screens:(NSArray <NSString *> *)screens excludeMentionedScreens:(BOOL)exclude;

- (BOOL)containsSettingOfType:(UXOcclusionType)type;

@end

NS_ASSUME_NONNULL_END
