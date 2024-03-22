package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import presentation.components.RightIconButtonComponent
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors

@Composable
fun ConsultationSchedule() {

    val boxBgModifier =
        Modifier
            .padding(bottom = 5.dp, top = 5.dp, start = 10.dp)
            .height(250.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Colors.darkPrimary)
            .width(250.dp)

    Box(modifier = boxBgModifier) {
        val columnModifier = Modifier
            .padding(start = 5.dp, bottom = 10.dp)
            .fillMaxWidth()
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = columnModifier
        ) {

            Row(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.60f)) {

                Row(
                    modifier = Modifier.fillMaxWidth(0.50f).fillMaxHeight()
                        .padding(start = 10.dp, top = 25.dp, end = 15.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start
                ) {

                    Box(
                        Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .background(color = Color(color = 0x30FFFFFF)),
                        contentAlignment = Alignment.Center
                    ) {
                        val modifier = Modifier
                            .size(24.dp)
                        ImageComponent(
                            imageModifier = modifier,
                            imageRes = "drawable/share_outline_icon.png",
                            colorFilter = ColorFilter.tint(color = Color.White)
                        )
                    }
                }


                  Column (modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        .padding(25.dp),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Top) {
                        TextComponent(
                            text = "April",
                            fontSize = 13,
                            fontFamily = GGSansRegular,
                            textStyle = TextStyle(),
                            textColor = Color(color = 0x90FFFFFF),
                            textAlign = TextAlign.Right,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 45,
                            letterSpacing = 1,
                            textModifier = Modifier
                                .fillMaxWidth())

                        TextComponent(
                            text = "30",
                            fontSize = 35,
                            fontFamily = GGSansSemiBold,
                            textStyle = TextStyle(),
                            textColor = Color.White,
                            textAlign = TextAlign.Right,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 45,
                            letterSpacing = 1,
                            textModifier = Modifier
                                .fillMaxWidth().padding(top = 5.dp))

                      TextComponent(
                          text = "5:30pm",
                          fontSize = 13,
                          fontFamily = GGSansRegular,
                          textStyle = TextStyle(),
                          textColor = Color(color = 0x90FFFFFF),
                          textAlign = TextAlign.Right,
                          fontWeight = FontWeight.Medium,
                          lineHeight = 45,
                          letterSpacing = 1,
                          textModifier = Modifier
                              .fillMaxWidth().padding(top = 5.dp))

                    }


            }

            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.CenterStart) {

                TextComponent(
                    text = "Family\nTherapy",
                    fontSize = 35,
                    fontFamily = GGSansSemiBold,
                    textStyle = TextStyle(),
                    lineHeight = 45,
                    letterSpacing = 1,
                    textColor = Color.White,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    textModifier = Modifier
                        .fillMaxWidth().padding(start = 20.dp, end = 20.dp))

            }
        }
    }
}