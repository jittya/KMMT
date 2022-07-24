//
//  UXCamBlurSetting.h
//  UXCam
//
//  Created by Ankit Karna on 19/01/2022.
//  Copyright © 2022 UXCam Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UXCam/UXCamOcclusionSetting.h>

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSInteger, UXBlurType) {
    UXBlurTypeGaussian,
    UXBlurTypeBox,
    UXBlurTypeBokeh
};

@interface UXCamBlurSetting : NSObject<UXCamOcclusionSetting>

@property (readonly) UXBlurType blurType;
@property (readonly) int radius;
@property (nonatomic) BOOL hideGestures;

- (instancetype)initWithRadius:(int)radius;
- (instancetype)initWithBlurType:(UXBlurType)type radius:(int)radius;


@end

NS_ASSUME_NONNULL_END
