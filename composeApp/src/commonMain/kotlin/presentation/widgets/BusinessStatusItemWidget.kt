package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import applications.videoplayer.VideoPlayer
import domain.Models.StatusImageModel
import domain.Models.StatusVideoModel
import domain.Models.VendorStatusModel
import domain.Models.VideoStatusViewMeta
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors
import utils.calculateStatusViewHeightPercent

class BusinessStatusItemWidget {
    @Composable
    fun getImageStatusWidget(
        imageUrl: String,
        vendorStatusModel: VendorStatusModel
    ) {
        val statusImage = vendorStatusModel.statusImage
        val heightRatio = calculateStatusViewHeightPercent(height = statusImage!!.height, width = statusImage.width)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Colors.primaryColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(heightRatio.toFloat()),
                        contentAlignment = Alignment.Center
                    ) {
                        ImageComponent(
                            imageModifier = Modifier.fillMaxSize(),
                            imageRes = imageUrl,
                            contentScale = ContentScale.Crop,
                            isAsync = true
                        )
                    }
                }
                   if (statusImage.caption!!.isNotEmpty()) {
                       Box(
                           modifier = Modifier.fillMaxWidth().wrapContentHeight()
                               .background(color = Color(0x80000000)),
                           contentAlignment = Alignment.BottomCenter
                       ) {
                           ImageStatusCaption(vendorStatusModel.statusImage)
                       }
                   }
            }
        }
    }

    @Composable
    fun ImageStatusCaption(imageModel: StatusImageModel) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            TextComponent(
                textModifier = Modifier.wrapContentSize(),
                text = imageModel.caption!!,
                fontSize = 17, fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 23,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

        }
    }

    @Composable
    fun VideoStatusCaption(videoModel: StatusVideoModel) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            TextComponent(
                textModifier = Modifier.wrapContentSize(),
                text = videoModel.caption!!,
                fontSize = 17, fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 23,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

        }
    }


    @Composable
    fun getVideoStatusWidget(
        videoModel: StatusVideoModel,
        videoStatusViewMeta: VideoStatusViewMeta
    ) {
        val heightRatio = calculateStatusViewHeightPercent(height = videoModel.height!!, width = videoModel.width)

        Column(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .background(color = Colors.primaryColor)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(heightRatio.toFloat()),
                        contentAlignment = Alignment.Center
                    ) {
                        VideoPlayer(
                            modifier = Modifier.fillMaxSize(),
                            url = videoModel.videoUrl,
                            videoStatusViewMeta = videoStatusViewMeta
                        )
                    }
                }
                    if (videoModel.caption.isNotEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                .background(color = Color(0x80000000)),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            if (videoModel.caption.isNotEmpty()) {
                                VideoStatusCaption(videoModel)
                            }
                        }
                    }
            }
        }
    }

    @Composable
    fun getTextStatusWidget(
        vendorStatusModel: VendorStatusModel
    ) {
        Column(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Colors.primaryColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {

                TextComponent(
                    text = vendorStatusModel.statusText?.body!!,
                    fontSize = 20,
                    fontFamily = GGSansSemiBold,
                    textStyle = TextStyle(),
                    textColor = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 30,
                    letterSpacing = 1,
                    textModifier = Modifier
                        .fillMaxWidth().padding(start = 40.dp, end = 40.dp)
                )
            }
        }
    }
}




