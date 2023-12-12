package widgets

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansBold
import GGSansSemiBold
import Models.AvailableSlotsUIModel
import Models.AvailableTherapistUIModel
import Models.TriangleShape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent

@Composable
fun attachUserProfileImage(availableTherapist: AvailableTherapistUIModel.AvailableTherapist) {
    var selectedVisibilityAlpha: Float = if (availableTherapist.isSelected) 1f else 0f
    Box(
        Modifier
            .padding(start = 20.dp, bottom = 10.dp)
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(15.dp))
            .size(100.dp)
            .background(color = Color(0xFBFBFB)),
        contentAlignment = Alignment.TopEnd
    ) {
        val modifier = Modifier
            .padding(4.dp)
                .clip(shape = RoundedCornerShape(12.dp))
            .size(100.dp)
        ImageComponent(imageModifier = modifier, imageRes = "1.jpg")
        Box(
           modifier =  Modifier
                .padding(4.dp)
                .alpha(selectedVisibilityAlpha)
                .clip(shape = RoundedCornerShape(12.dp))
                .size(100.dp)
                .background(color = Color(0x60FA2D65)),
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(imageModifier = Modifier
                .padding(top = 2.dp)
                .size(24.dp), imageRes = "check_mark_icon.png", colorFilter = ColorFilter.tint(color = Color.White))

        }
    }

}

@Composable
fun AttachTherapistWidget(availableTherapist: AvailableTherapistUIModel.AvailableTherapist, onTherapistSelectedListener: (AvailableTherapistUIModel.AvailableTherapist) -> Unit){
    val rowModifier = Modifier
        .width(130.dp)
        .clickable {
            onTherapistSelectedListener(availableTherapist)
        }
    Column(
        modifier = rowModifier
    ) {
        attachUserProfileImage(availableTherapist)
        UserFullNameComp()
    }
}

@Composable
fun UserFullNameComp(){
    val rowModifier = Modifier
        .padding(start = 10.dp)
        .width(130.dp)

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            TextComponent(
                text = "Margaret C.",
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black
            )
        }
    }
}
