package AppTheme

import GGSansBold
import GGSansRegular
import GGSansSemiBold
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable


@Composable
public fun AppBoldTypography(): Typography {

    val defaultTypography = androidx.compose.material.Typography()

    return Typography(
        h1 = defaultTypography.h1.copy(fontFamily = GGSansBold),
        h2 = defaultTypography.h2.copy(fontFamily = GGSansBold),
        h3 = defaultTypography.h3.copy(fontFamily = GGSansBold),
        h4 = defaultTypography.h4.copy(fontFamily = GGSansBold),
        h5 = defaultTypography.h5.copy(fontFamily = GGSansBold),
        h6 = defaultTypography.h6.copy(fontFamily = GGSansBold),
        subtitle1 = defaultTypography.subtitle1.copy(fontFamily = GGSansBold),
        subtitle2 = defaultTypography.subtitle2.copy(fontFamily = GGSansBold),
        body1 = defaultTypography.body1.copy(fontFamily = GGSansBold),
        body2 = defaultTypography.body2.copy(fontFamily = GGSansBold),
        button = defaultTypography.button.copy(fontFamily = GGSansBold),
        caption = defaultTypography.caption.copy(fontFamily = GGSansBold),
        overline = defaultTypography.overline.copy(fontFamily = GGSansBold)
    )
}


@Composable
public fun AppRegularTypography(): Typography {

    val defaultTypography = androidx.compose.material.Typography()

    return Typography(
        h1 = defaultTypography.h1.copy(fontFamily = GGSansRegular),
        h2 = defaultTypography.h2.copy(fontFamily = GGSansRegular),
        h3 = defaultTypography.h3.copy(fontFamily = GGSansRegular),
        h4 = defaultTypography.h4.copy(fontFamily = GGSansRegular),
        h5 = defaultTypography.h5.copy(fontFamily = GGSansRegular),
        h6 = defaultTypography.h6.copy(fontFamily = GGSansRegular),
        subtitle1 = defaultTypography.subtitle1.copy(fontFamily = GGSansRegular),
        subtitle2 = defaultTypography.subtitle2.copy(fontFamily = GGSansRegular),
        body1 = defaultTypography.body1.copy(fontFamily = GGSansRegular),
        body2 = defaultTypography.body2.copy(fontFamily = GGSansRegular),
        button = defaultTypography.button.copy(fontFamily = GGSansRegular),
        caption = defaultTypography.caption.copy(fontFamily = GGSansRegular),
        overline = defaultTypography.overline.copy(fontFamily = GGSansRegular)
    )
}


@Composable
public fun AppSemiBoldTypography(): Typography {

    val defaultTypography = androidx.compose.material.Typography()

    return Typography(
        h1 = defaultTypography.h1.copy(fontFamily = GGSansSemiBold),
        h2 = defaultTypography.h2.copy(fontFamily = GGSansSemiBold),
        h3 = defaultTypography.h3.copy(fontFamily = GGSansSemiBold),
        h4 = defaultTypography.h4.copy(fontFamily = GGSansSemiBold),
        h5 = defaultTypography.h5.copy(fontFamily = GGSansSemiBold),
        h6 = defaultTypography.h6.copy(fontFamily = GGSansSemiBold),
        subtitle1 = defaultTypography.subtitle1.copy(fontFamily = GGSansSemiBold),
        subtitle2 = defaultTypography.subtitle2.copy(fontFamily = GGSansSemiBold),
        body1 = defaultTypography.body1.copy(fontFamily = GGSansSemiBold),
        body2 = defaultTypography.body2.copy(fontFamily = GGSansSemiBold),
        button = defaultTypography.button.copy(fontFamily = GGSansSemiBold),
        caption = defaultTypography.caption.copy(fontFamily = GGSansSemiBold),
        overline = defaultTypography.overline.copy(fontFamily = GGSansSemiBold)
    )
}



