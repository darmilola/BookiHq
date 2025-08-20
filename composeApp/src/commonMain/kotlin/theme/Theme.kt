package com.assignment.moniepointtest.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import applications.font.font


@Composable
fun AppTheme(content: @Composable () -> Unit) {

    val poppinsRegular = FontFamily(
        font(
            "Poppins", "poppins_regular", FontWeight.Normal, FontStyle.Normal
        )
    )

    val poppinsSemiBold = FontFamily(
        font(
            "Poppins", "poppins_semi_bold", FontWeight.Normal, FontStyle.Normal
        )
    )
    val poppinsBold = FontFamily(
        font(
            "Poppins", "poppins_bold", FontWeight.Normal, FontStyle.Normal
        )
    )

    val typo = androidx.compose.material3.Typography(
        displayLarge = TextStyle(
            fontFamily = poppinsRegular,
            fontWeight = FontWeight.Bold,
            fontSize = 52.sp,
        ),
        displayMedium = TextStyle(
            fontFamily = poppinsBold,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        displaySmall = TextStyle(
            fontFamily = poppinsBold,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = poppinsBold,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = poppinsBold,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = poppinsSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = poppinsSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = poppinsRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = poppinsRegular, fontWeight = FontWeight.Normal, fontSize = 23.sp
        ),
        bodyMedium = TextStyle(fontFamily = poppinsRegular, fontSize = 17.sp),
        labelLarge = TextStyle(
            fontFamily = poppinsRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp
        ),
        labelMedium = TextStyle(
            fontFamily = poppinsRegular, fontWeight = FontWeight.Normal, fontSize = 8.sp
        ),
        labelSmall = TextStyle(
            fontFamily = poppinsRegular, fontWeight = FontWeight.Normal, fontSize = 12.sp
        )
    )

    MaterialTheme(
        typography = typo,
        content = content
    )
}