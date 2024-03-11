package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun ReviewsWidget() {

    val columnModifier = Modifier
        .padding(start = 5.dp, bottom = 10.dp)
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        AttachReviewHeader()
        AttachUserReviewsContent()
    }

}


@Composable
fun AttachUserImage() {
    Box(
        Modifier
            .border(width = 1.dp, color = Colors.primaryColor, shape = CircleShape)
            .size(50.dp)
    ) {
        val modifier = Modifier
            .padding(1.dp)
            .clip(shape = RoundedCornerShape(35.dp))
            .size(50.dp)
        ImageComponent(imageModifier = modifier, imageRes = "drawable/1.jpg")
    }

}

@Composable
fun AttachReviewHeader() {
    val rowModifier = Modifier
        .fillMaxWidth()
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {
            AttachUserImage()
            val columnModifier = Modifier
                .padding(start = 3.dp)
                .fillMaxWidth()
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
                AttachUserName()
                AttachReviewDate()
            }

        }
    }

@Composable
fun AttachUserName(){
    val rowModifier = Modifier
        .padding(start = 5.dp)
        .wrapContentWidth()

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            TextComponent(
                text = "Margaret C.",
                fontSize = 16,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }


@Composable
fun AttachReviewDate(){
    val rowModifier = Modifier
        .padding(start = 5.dp, top = 7.dp)
        .wrapContentWidth()

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            TextComponent(
                text = "09 December 2024",
                fontSize = 14,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium
            )


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
            textModifier = modifier, text = "\"Lorem ipsum dolor sit amet consectetuer adipiscing Aenean commodo ligula adipiscing Aenean commodo ligula adipiscing Aenean commodo ligula\"", fontSize = 16, fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6, textColor = Color.DarkGray, textAlign = TextAlign.Left,
            fontWeight = FontWeight.Medium, lineHeight = 25)

    }
}
