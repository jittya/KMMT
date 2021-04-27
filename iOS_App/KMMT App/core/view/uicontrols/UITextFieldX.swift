//
//  TextFieldX.swift
//  KMMTApp
//
//  Created by Innovateq on 27/04/2021.
//

import UIKit
import SkyFloatingLabelTextField

public class UITextFieldX: SkyFloatingLabelTextFieldWithIcon {
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        self.setupTheme()
    }
    
    required public init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.setupTheme()
    }
    
    private func setupTheme() {
                
        font = .systemFont(ofSize: 14)
        if font != nil {
            placeholderFont = .systemFont(ofSize: 14)

        }
        self.titleFormatter = { $0 }

        titleFont = .systemFont(ofSize: 14)
        errorColor = UIColor.AppColors.colorRed ?? UIColor.init(red: 0.722, green: 0.000, blue: 0.000, alpha: 1.0)
        textColor = UIColor.AppColors.colorBlack5 ?? UIColor.init(red: 0.314, green: 0.314, blue: 0.314, alpha: 1.0)
        lineColor = UIColor.AppColors.colorPrimary ?? UIColor.init(red: 0.141, green: 0.557, blue: 0.702, alpha: 1.0)
        titleColor = UIColor.AppColors.colorPrimary ?? UIColor.init(red: 0.141, green: 0.557, blue: 0.702, alpha: 1.0)
        iconColor = UIColor.AppColors.colorPrimary ?? UIColor.init(red: 0.141, green: 0.557, blue: 0.702, alpha: 1.0)
        selectedTitleColor = UIColor.AppColors.colorPrimaryDark ?? UIColor.init(red: 0.141, green: 0.557, blue: 0.702, alpha: 1.0)
        selectedIconColor = UIColor.AppColors.colorPrimaryDark ?? UIColor.init(red: 0.141, green: 0.557, blue: 0.702, alpha: 1.0)
        selectedLineColor = UIColor.AppColors.colorPrimaryDark ?? UIColor.init(red: 0.141, green: 0.557, blue: 0.702, alpha: 1.0)

        iconWidth = 18
        iconMarginLeft = 7
        iconMarginBottom = 4
        lineHeight = 1.5
        selectedLineHeight = 4
        iconWidth = 30
        iconMarginLeft = 15
        iconMarginBottom = 10
        iconType = .image
//        iconImage = UIImage.init(systemName: "wand.and.rays")
        
    }
}
