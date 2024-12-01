package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.Models.ServiceTypeTherapists
import domain.Models.User
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun AttachTherapistProfileImage(availableTherapist: ServiceTypeTherapists) {
    val selectedVisibilityAlpha: Float = if (availableTherapist.isSelected) 1f else 0f
    Box(
        Modifier
            .padding(start = 20.dp, bottom = 10.dp)
            .border(width = 1.dp, color = Colors.primaryColor, shape = RoundedCornerShape(10.dp))
            .width(140.dp)
            .height(160.dp)
            .background(color = Color.White),
        contentAlignment = Alignment.TopEnd
    ) {

        val modifier = Modifier
            .padding(4.dp)
                .clip(shape = RoundedCornerShape(7.dp))
            .width(140.dp)
            .height(160.dp)

        ImageComponent(imageModifier = modifier, imageRes = availableTherapist.therapistInfo?.profileInfo?.profileImageUrl!!, isAsync = true)
        Box(
           modifier =  Modifier
                .padding(4.dp)
                .alpha(selectedVisibilityAlpha)
                .clip(shape = RoundedCornerShape(7.dp))
                .width(140.dp)
                .height(160.dp)
                .background(color = Colors.transparentPrimaryColor),
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(imageModifier = Modifier
                .padding(top = 2.dp)
                .size(24.dp), imageRes = "drawable/check_mark_icon.png", colorFilter = ColorFilter.tint(color = Color.White))

        }
    }

}

@Composable
fun AttachTherapistWidget(availableTherapist: ServiceTypeTherapists, onTherapistSelectedListener: (ServiceTypeTherapists) -> Unit){
    val rowModifier = Modifier
        .width(160.dp)
        .height(200.dp)
        .clickable {
            onTherapistSelectedListener(availableTherapist)
        }
    Column(
        modifier = rowModifier
    ) {
        AttachTherapistProfileImage(availableTherapist)
        TherapistName(availableTherapist.therapistInfo?.profileInfo!!)
    }
}

@Composable
fun TherapistName(profileInfo: User){
    val rowModifier = Modifier
        .padding(start = 20.dp)
        .width(140.dp)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {
            TextComponent(
                text = profileInfo.firstname+" "+ profileInfo.lastname?.first().toString()
                    .uppercase() +".",
                fontSize = 16,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
