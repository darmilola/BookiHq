package presentation.widgets

import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import presentations.components.ImageComponent


@Composable
fun AccountProfileImage(profileImageUrl: String, isAsync: Boolean = false, showEditIcon: Boolean = true, onUploadImageClicked: () -> Unit) {
    Box(Modifier.fillMaxWidth().height(220.dp), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .padding(top = 20.dp, bottom = 5.dp)
                .size(200.dp)
                .clip(CircleShape)
                .border(
                    width = (2.5).dp,
                    color = Colors.primaryColor,
                    shape = CircleShape
                )
                .background(color = Color.Transparent)
        ) {
            val modifier = Modifier
                .padding(3.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = CircleShape
                )
                .fillMaxSize()
            ImageComponent(imageModifier = modifier, imageRes = profileImageUrl, isAsync = isAsync)
        }
        if(showEditIcon) {
            EditProfilePictureButton() {
                onUploadImageClicked()
            }
        }

    }
}

@Composable
fun EditProfilePictureButton(onUploadImageClicked: () -> Unit) {
    Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.BottomEnd) {
        val modifier = Modifier
            .padding(end = 10.dp)
            .background(color = Colors.surfaceColor, shape = CircleShape)
            .size(width = 60.dp, height = 60.dp)

        Box(modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(imageModifier = Modifier.size(35.dp).clickable {
                  onUploadImageClicked()
            }, imageRes = "drawable/upload_icon.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
        }
    }
}