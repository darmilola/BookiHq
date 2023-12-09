package widgets

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent
import components.welcomeLineGradientBlock

@Composable
fun ReviewsWidget() {

    val columnModifier = Modifier
        .padding(start = 10.dp, bottom = 10.dp)
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        val modifier = Modifier
            .fillMaxWidth()
        attachReviewHeader()
        AttachUserReviewsContent()
    }

}


@Composable
fun attachUserImage() {
    Box(
        Modifier
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(35.dp))
            .size(70.dp)
            .background(color = Color(0xFBFBFB))
    ) {
        val modifier = Modifier
            .padding(3.dp)
            .clip(shape = RoundedCornerShape(35.dp))
            .size(70.dp)
        ImageComponent(imageModifier = modifier, imageRes = "1.jpg")
    }

}

@Composable
fun attachReviewHeader() {
    val rowModifier = Modifier
        .fillMaxWidth()

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {

            attachUserImage()

            val columnModifier = Modifier
                .padding(start = 5.dp)
                .fillMaxWidth()
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
                val modifier = Modifier
                    .fillMaxWidth()
                attachUserName()
                attachReviewDate()
            }

        }
    }
}

@Composable
fun attachUserName(){
    val rowModifier = Modifier
        .padding(start = 5.dp)
        .wrapContentWidth()

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            TextComponent(
                text = "Margaret C.",
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
fun attachReviewDate(){
    val rowModifier = Modifier
        .padding(start = 5.dp)
        .wrapContentWidth()

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            TextComponent(
                text = "09 December 2024",
                fontSize = 15,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun AttachUserReviewsContent() {
    val columnModifier = Modifier
        .padding(bottom = 20.dp, top = 5.dp)
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        val modifier = Modifier
            .fillMaxWidth()
        TextComponent(
            textModifier = modifier, text = " \"Lorem ipsum dolor sit amet consectetuer adipiscing Aenean commodo ligula adipiscing Aenean commodo ligula adipiscing Aenean commodo ligula\" ", fontSize = 18, fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6, textColor = Color.DarkGray, textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold, lineHeight = 25)
    }
}
