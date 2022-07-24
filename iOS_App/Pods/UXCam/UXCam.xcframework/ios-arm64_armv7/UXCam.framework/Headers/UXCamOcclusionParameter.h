//
//  UXCamOcclusionParameter.h
//  UXCam
//
//  Created by Ankit Karna on 22/01/2022.
//  Copyright © 2022 UXCam Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UXCam/UXCamOcclusionSetting.h>

NS_ASSUME_NONNULL_BEGIN

@interface UXCamOcclusionParameter : NSObject

@property (readonly) id<UXCamOcclusionSetting> setting;
@property NSArray<NSString *> *excludedScreens;

- (instancetype)initWithSetting:(id<UXCamOcclusionSetting>)setting;
- (instancetype)initWithSetting:(id<UXCamOcclusionSetting>)setting
                excludedScreens: (NSArray<NSString *> *)excludedScreens;

@end

NS_ASSUME_NONNULL_END
