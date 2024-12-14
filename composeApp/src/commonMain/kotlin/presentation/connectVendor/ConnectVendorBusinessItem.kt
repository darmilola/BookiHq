package presentation.connectVendor


import GGSansRegular
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.Models.Vendor
import presentation.components.ButtonComponent
import presentation.components.StraightLine
import presentations.components.ImageComponent
import presentations.components.TextComponent

    @Composable
    fun SwitchVendorBusinessItemComponent(vendor: Vendor, onVendorClickListener: (Vendor) -> Unit) {
        val columnModifier = Modifier
            .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
            .clickable {
                onVendorClickListener(vendor)
            }
            .wrapContentHeight()
            Row(modifier = columnModifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(100.dp), contentAlignment = Alignment.Center) {
                    BusinessLogo(vendor.businessLogo!!)
                }
                Box(modifier = Modifier.fillMaxWidth(0.70f), contentAlignment = Alignment.Center) {
                    BusinessNameAndHandle(vendor)
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    val buttonStyle = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .background(color = Color.Transparent)
                        .height(40.dp)
                    ButtonComponent(modifier = buttonStyle, buttonText = "Connect", borderStroke = BorderStroke((1).dp, color = Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 14, shape = RoundedCornerShape(12.dp), textColor =  Colors.primaryColor, style = TextStyle(fontFamily = GGSansRegular)){
                    }
                }
            }
        }


    @Composable
    fun BusinessLogo(logoUrl: String) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                ImageComponent(
                    imageModifier = Modifier.fillMaxSize().clip(CircleShape).padding(3.dp)
                        .clip(CircleShape)
                        .border(BorderStroke(1.dp, color = Colors.primaryColor), shape = CircleShape),
                    imageRes = logoUrl,
                    isAsync = true
                )
            }
        }


    @Composable
    fun BusinessNameAndHandle(vendor: Vendor){
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
                    text = vendor.businessName!!,
                    fontSize = 16,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 25,
                    maxLines = 1,
                    textModifier = modifier)

                TextComponent(
                    text = vendor.businessAddress!!,
                    fontSize = 14,
                    fontFamily = GGSansRegular,
                    textStyle = TextStyle(),
                    textColor = Color.Gray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 17,
                    maxLines = 2,
                    textModifier = modifier)

                Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {

                    TextComponent(
                        text = "@"+vendor.businessHandle,
                        fontSize = 14,
                        fontFamily = GGSansRegular,
                        textStyle = TextStyle(),
                        textColor = Colors.primaryColor,
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 30,
                        textModifier = Modifier
                            .padding(end = 20.dp, top = 10.dp)
                            .wrapContentSize())

                }
                Box(modifier = Modifier.fillMaxWidth().height(15.dp), contentAlignment = Alignment.BottomCenter) {
                    StraightLine()
                }

            }
        }
