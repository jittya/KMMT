//
//  UXCamOcclusionScreen.h
//  UXCam
//
//  Created by Ankit Karna on 22/01/2022.
//  Copyright © 2022 UXCam Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UXCam/UXCamOcclusionSetting.h>

NS_ASSUME_NONNULL_BEGIN

@interface UXCamOcclusionScreen : NSObject

@property (readonly) NSString *name;
@property NSMutableArray<id<UXCamOcclusionSetting>> *settings;

- (instancetype)initWithName:(NSString *)name settings:(NSArray<id<UXCamOcclusionSetting>> *)settings;

@end

NS_ASSUME_NONNULL_END
