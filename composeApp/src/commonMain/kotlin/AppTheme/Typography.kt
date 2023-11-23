package AppTheme

import GGSansFontFamily
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable


@Composable
public fun AppTypography(): Typography {

    val defaultTypography = androidx.compose.material.Typography()

    return Typography(
        h1 = defaultTypography.h1.copy(fontFamily = GGSansFontFamily),
        h2 = defaultTypography.h2.copy(fontFamily = GGSansFontFamily),
        h3 = defaultTypography.h3.copy(fontFamily = GGSansFontFamily),
        h4 = defaultTypography.h4.copy(fontFamily = GGSansFontFamily),
        h5 = defaultTypography.h5.copy(fontFamily = GGSansFontFamily),
        h6 = defaultTypography.h6.copy(fontFamily = GGSansFontFamily),
        subtitle1 = defaultTypography.subtitle1.copy(fontFamily = GGSansFontFamily),
        subtitle2 = defaultTypography.subtitle2.copy(fontFamily = GGSansFontFamily),
        body1 = defaultTypography.body1.copy(fontFamily = GGSansFontFamily),
        body2 = defaultTypography.body2.copy(fontFamily = GGSansFontFamily),
        button = defaultTypography.button.copy(fontFamily = GGSansFontFamily),
        caption = defaultTypography.caption.copy(fontFamily = GGSansFontFamily),
        overline = defaultTypography.overline.copy(fontFamily = GGSansFontFamily)
    )
}


