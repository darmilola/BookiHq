package presentation.UserProfile.SwitchVendor


import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentation.components.ImageComponent
import presentations.components.TextComponent

@OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ConnectBusinessItemComponent(onBusinessClickListener: () -> Unit) {
        val columnModifier = Modifier
            .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
            .clickable {
                onBusinessClickListener()
            }
            .wrapContentHeight()
            Row(modifier = columnModifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BusinessLogo()
                BusinessNameAndHandle()
            }
        }


    @Composable
    fun BusinessLogo(size: Int = 70, borderStroke: BorderStroke? = null) {
        val imageModifier =
            Modifier
                .size(size.dp)
        Column(
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .background(color = Colors.lighterPrimaryColor, shape = CircleShape)
                .size(size = size.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(color = Color.Transparent, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                ImageComponent(
                    imageModifier = imageModifier,
                    imageRes = "drawable/slack_logo.png",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

    @Composable
    fun BusinessNameAndHandle(){
        val columnModifier = Modifier
            .padding(start = 5.dp, end = 10.dp)
            .fillMaxHeight()
            Column(
                modifier = columnModifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment  = Alignment.Start,
            ) {

                val modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()

                TextComponent(
                    text = "Jonjo Beauty and Spa Services r4uhriurh rj0or 3c4r3p0w",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 25,
                    textModifier = modifier
                )
                TextComponent(
                    text = "@JonjoServices",
                    fontSize = 18,
                    fontFamily = GGSansSemiBold,
                    textStyle = TextStyle(),
                    textColor = Color.LightGray,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 30,
                    textModifier = Modifier
                        .padding(end = 10.dp)
                        .wrapContentSize())
            }
        }
