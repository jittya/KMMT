//
//  UXCamOcclusionSetting.h
//  UXCam
//
//  Created by Ankit Karna on 22/01/2022.
//  Copyright © 2022 UXCam Inc. All rights reserved.
//

#ifndef UXCamOcclusionSetting_h
#define UXCamOcclusionSetting_h

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, UXOcclusionType) {
    UXOcclusionTypeOccludeAllTextFields = 1,
    UXOcclusionTypeOverlay = 2,
    UXOcclusionTypeBlur = 3,
    UXOcclusionTypeUnknown = 4
};


typedef NS_ENUM(NSInteger, UXOcclusionCategory) {
    UXOcclusionCategoryTextOnly,
    UXOcclusionCategoryScreen
};

@protocol UXCamOcclusionSetting <NSObject>
@property (readonly) UXOcclusionType type;
@property (readonly) UXOcclusionCategory category;
@end


#endif /* UXCamOcclusionSetting_h */
