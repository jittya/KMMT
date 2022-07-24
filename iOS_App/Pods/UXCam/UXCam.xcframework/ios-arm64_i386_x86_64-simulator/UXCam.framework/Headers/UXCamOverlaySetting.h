//
//  UXCamOverlaySetting.h
//  UXCam
//
//  Created by Ankit Karna on 21/02/2022.
//  Copyright © 2022 UXCam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <UXCam/UXCamOcclusionSetting.h>

NS_ASSUME_NONNULL_BEGIN

@interface UXCamOverlaySetting : NSObject<UXCamOcclusionSetting>

@property (strong, nonatomic) UIColor *color;
@property (nonatomic) BOOL hideGestures;

- (instancetype)initWithColor:(UIColor *)color;

@end

NS_ASSUME_NONNULL_END
