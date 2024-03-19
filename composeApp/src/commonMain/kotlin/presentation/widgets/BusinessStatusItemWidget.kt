package presentation.widgets

import GGSansRegular
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Models.RecommendationType
import domain.Models.VendorRecommendation
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors

class BusinessStatusItemWidget {
    @Composable
    fun GetStatusWidget(imageRes: String) {
              Column (
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.80f),
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center
            ) {
              Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.60f)) {
                  ImageComponent(
                      imageModifier = Modifier.aspectRatio(ratio = 1f).fillMaxSize(),
                      imageRes = imageRes,
                      contentScale = ContentScale.Crop,
                      isAsync = true
                  )
                  Box(
                      modifier = Modifier
                          .padding(10.dp)
                          .size(50.dp).background(color = Color.White, shape = CircleShape),
                      contentAlignment = Alignment.Center
                  ) {
                      ImageComponent(imageModifier = Modifier.size(24.dp), imageRes = "drawable/like_icon.png", colorFilter = ColorFilter.tint(color = Colors.pinkColor))
                  }
              }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .background(color = Color.Transparent),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        StatusDescription()
                    }
                }

            }
        }

    @Composable
    fun StatusDescription() {
        Column(
            modifier = Modifier
                .padding(top = 15.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment  = Alignment.Start,
        ) {
            TextComponent(
                textModifier = Modifier.fillMaxWidth().padding(top = 5.dp, bottom = 10.dp),
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et" +
                        " dolore magna aliqua. Ut enim ad minim",
                fontSize = 15, fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 23,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)

        }

    }

    @Composable
    fun StatusInfo() {
        Row(modifier = Modifier.fillMaxHeight().padding(start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {

            Row(modifier = Modifier.fillMaxWidth(0.4f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                TextComponent(
                    text = " 400 Likes",
                    fontSize = 16,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.Gray,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 20,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textModifier = Modifier
                        .padding(top = 5.dp)
                        .wrapContentSize()
                )

            }

            Row(horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize().clickable {
                    }
                ) {
                    TextComponent(
                        text = "Save",
                        fontFamily = GGSansRegular,
                        textStyle = MaterialTheme.typography.h6,
                        textColor = Colors.primaryColor,
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 20,
                        maxLines = 2,
                        fontSize = 16,
                        overflow = TextOverflow.Ellipsis,
                        textModifier = Modifier.wrapContentSize().padding(end = 7.dp))

                    ImageComponent(imageModifier = Modifier.size(24.dp), imageRes = "drawable/fav_icon.png", colorFilter = ColorFilter.tint(color = Color.Gray))
                }

            }
        }
    }

}

