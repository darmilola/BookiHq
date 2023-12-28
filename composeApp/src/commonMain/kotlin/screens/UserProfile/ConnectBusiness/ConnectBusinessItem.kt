package screens.UserProfile.ConnectBusiness

import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent
import screens.UserProfile.UserOrders.OrderDetailItemDetail
import screens.UserProfile.UserOrders.OrderDetailItemImage
import screens.UserProfile.UserOrders.OrderDetailItemPriceInfoContent

 @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ConnectBusinessItemComponent(onBusinessClickListener: () -> Unit) {
        val columnModifier = Modifier
            .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
            .clickable {
                onBusinessClickListener()
            }
            .wrapContentHeight()
        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
            Row(modifier = columnModifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BusinessLogo()
                BusinessNameAndHandle()
            }
        }
    }

    @Composable
    fun BusinessLogo(size: Int = 70, borderStroke: BorderStroke? = null) {
        val imageModifier =
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        Card(
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .background(color = Color.Transparent)
                .size(size = size.dp),
            shape = CircleShape,
            border = borderStroke
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.Transparent, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                ImageComponent(
                    imageModifier = imageModifier,
                    imageRes = "woman2.jpg",
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
        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
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
    }