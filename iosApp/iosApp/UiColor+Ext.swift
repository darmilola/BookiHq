//
//  UiColor+Ext.swift
//  iosApp
//
//  Created by Damilola Akinterinwa on 29/03/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import UIKit
import Foundation


extension UIColor {
    
    /// UIColor extension to support initialising a UIColor object with String or Int and returns a non-optional value
    /// When initialising with Int, any value is accepted and will be masked if it's too large
    ///
    /// String formats supported are and can be prefixed with either a # or 0x:
    /// #   rgb
    /// #   argb
    /// #   rrggbb
    /// #   aarrggbb
    ///
    /// Ex:
    /// #   0xfff
    /// #   #ffff
    /// #   #ffffff
    /// #   0xffffffff
    
    convenience init (string color: String,
                      onFail fallbackColor: UIColor = .black) {
        
        let intValue: UInt
        if color.isRGB(orARGB: true) {
            var stringValue: String
            if color[color.startIndex] == "#" {
                
                stringValue = color[1..<color.count]
            } else {
                stringValue = color[2..<color.count]
            }
            stringValue.forceAARRGGBB()
            intValue = UInt(stringValue, radix: 16)!
        } else {
            intValue = fallbackColor.intValue
        }
        
        self.init(int: intValue)
    }
    
    convenience init (string color: String,
                      alpha: CGFloat,
                      onFail fallbackColor: UIColor = .black) {
        
        let intValue: UInt
        if color.isRGB() {
            var stringValue: String
            if color[color.startIndex] == "#" {
                stringValue = color[1..<color.count]
            } else {
                stringValue = color[2..<color.count]
            }
            stringValue.forceAARRGGBB()
            intValue = UInt(stringValue, radix: 16)!
        } else {
            intValue = fallbackColor.intValue
        }
        
        self.init(int: intValue, alpha: alpha)
    }
}

// MARK: - Init from Int
@objc extension UIColor {
    convenience init (int color: UInt) {
        let alpha: UInt
        if color > 0xffffff {
            alpha = color >> 24 & 0xff
        } else {
            alpha = 255
        }
        self.init(int: color & 0xffffff, alpha: CGFloat(alpha) / 255.0)
    }
        
    convenience init (int color: UInt, alpha: CGFloat) {
        let red = CGFloat(color >> 16 & 0xff) / 255.0
        let green = CGFloat(color >> 8 & 0xff) / 255.0
        let blue = CGFloat(color & 0xff) / 255.0
        
        if #available(iOS 10.0, *) {
            self.init(displayP3Red: red,
                      green: green,
                      blue: blue,
                      alpha: alpha)
        } else {
            self.init(red: red,
                      green: green,
                      blue: blue,
                      alpha: alpha)
        }
    }
}

// MARK: - Color to Int
private extension UIColor {
    var intValue: UInt {
        var rv: CGFloat = 0
        var gv: CGFloat = 0
        var bv: CGFloat = 0
        var av: CGFloat = 0
        
        self.getRed(&rv,
                    green: &gv,
                    blue: &bv,
                    alpha: &av)
        
        let red = UInt(rv * 0xff)
        let green = UInt(gv * 0xff)
        let blue = UInt(bv * 0xff)
        let alpha = UInt(av * 0xff)
        
        return alpha << 24 + red << 16 + green << 8 + blue
    }
}

// MARK: - Color Transformations

@objc extension UIColor {
    func darkened(by percent: CGFloat) -> UIColor {
        guard percent != 0 else { return self }
        
        let constrainedPercent = min(1, max(percent, -1))
        let value = 1 - constrainedPercent
        
        var rv: CGFloat = 0
        var gv: CGFloat = 0
        var bv: CGFloat = 0
        var av: CGFloat = 0
        
        self.getRed(&rv,
                    green: &gv,
                    blue: &bv,
                    alpha: &av)
        
        return UIColor(displayP3Red: rv * value,
                       green: gv * value,
                       blue: bv * value,
                       alpha: av)
    }
    
    func lightened(by percent: CGFloat) -> UIColor {
        return darkened(by: -percent)
    }
    
    func blend(with color2: UIColor?, alpha alpha2: CGFloat) -> UIColor? {
       var alpha2 = alpha2
       alpha2 = CGFloat(min(1.0, max(0.0, alpha2)))
       let beta = 1.0 - alpha2
       var r1 = CGFloat()
       var g1 = CGFloat()
       var b1 = CGFloat()
       var a1 = CGFloat()
       var r2 = CGFloat()
       var g2 = CGFloat()
       var b2 = CGFloat()
       var a2 = CGFloat()
       getRed(UnsafeMutablePointer<CGFloat>(mutating: &r1),
              green: UnsafeMutablePointer<CGFloat>(mutating: &g1),
              blue: UnsafeMutablePointer<CGFloat>(mutating: &b1),
              alpha: UnsafeMutablePointer<CGFloat>(mutating: &a1))
       color2?.getRed(UnsafeMutablePointer<CGFloat>(mutating: &r2),
                      green: UnsafeMutablePointer<CGFloat>(mutating: &g2),
                      blue: UnsafeMutablePointer<CGFloat>(mutating: &b2),
                      alpha: UnsafeMutablePointer<CGFloat>(mutating: &a2))
       let red = r1 * beta + r2 * alpha2
       let green = g1 * beta + g2 * alpha2
       let blue = b1 * beta + b2 * alpha2
       let alpha = a1 * beta + a2 * alpha2
       return UIColor(red: red, green: green, blue: blue, alpha: alpha)
   }
}

// MARK: - String Utils for Color
private extension String {
    var compareOptions: CompareOptions { [.regularExpression, .caseInsensitive] }
    
    func isARGB () -> Bool {
        return range(of: #"\A(0x|#)([a-f0-9]{4}){1,2}\Z"#, options: compareOptions) != nil
    }
    
    func isRGB (orARGB: Bool = false) -> Bool {
        if orARGB {
            return range(of: #"\A(0x|#)((([a-f0-9]{3}){1,2})|(([a-f0-9]{4}){1,2}))\Z"#, options: compareOptions) != nil
        }
        
        return range(of: #"\A(0x|#)([a-f0-9]{3}){1,2}\Z"#, options: compareOptions) != nil
    }
    
    mutating func forceAARRGGBB() {
        if self.count <= 4 {
            self = self.replacingOccurrences(of: #"[a-f0-9]"#,
                                             with: #"$0$0"#,
                                             options: compareOptions)
        }
    }
    
    subscript (bounds: CountableClosedRange<Int>) -> String {
        let start = index(startIndex, offsetBy: bounds.lowerBound)
        let end = index(startIndex, offsetBy: bounds.upperBound)
        return String(self[start...end])
    }

    subscript (bounds: CountableRange<Int>) -> String {
        let start = index(startIndex, offsetBy: bounds.lowerBound)
        let end = index(startIndex, offsetBy: bounds.upperBound)
        return String(self[start..<end])
    }
}

