package presentation.components

import GGSansRegular
import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentations.components.ImageComponent
import presentations.components.TextComponent

@OptIn(ExperimentalMaterialApi::class)
@Composable
public fun ButtonComponent(modifier: Modifier, buttonText: String, borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle, onClick: ()-> Unit) {
       Button(
             onClick = {
                  onClick()
             },
             border = borderStroke,
             shape = shape,
             modifier = modifier,
             colors = colors,
             elevation =  ButtonDefaults.elevation(
               defaultElevation = 0.dp,
               pressedElevation = 0.dp,
               disabledElevation = 0.dp
           )
       ){
           TextComponent(
               text = buttonText, fontSize = fontSize, textStyle = style, textColor = textColor, textAlign = TextAlign.Center,
               fontWeight = FontWeight.SemiBold)
        }
}


@Composable
public fun IconButtonComponent(modifier: Modifier, buttonText: String, borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle, iconRes: String, iconSize: Int = 28, colorFilter: ColorFilter? = null, onClick: (() -> Unit)? = null) {
    val rowModifier = Modifier
        .fillMaxWidth().fillMaxHeight()

          Button(
                onClick = {
                    if (onClick != null) {
                        onClick()
                    }
                },
                border = borderStroke,
                shape = shape,
                modifier = modifier,
                colors = colors,
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                )
            ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {

                val iconModifier = Modifier
                .size(iconSize.dp)

                val iconBoxModifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.10f)

                val textModifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(end = 15.dp)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = iconBoxModifier
                ) {
                    ImageComponent(imageModifier = iconModifier, imageRes = iconRes, colorFilter = colorFilter)
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = textModifier
                ) {
                    TextComponent(
                        text = buttonText,
                        fontSize = fontSize,
                        textStyle = style,
                        textColor = textColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        textModifier = Modifier.fillMaxWidth().wrapContentHeight()
                    )
                }
        }
    }
}



@Composable
 fun FloatingActionButton(modifier: Modifier, colors: ButtonColors, iconRes: String, iconSize: Int = 32, colorFilter: ColorFilter? = null, onClick: (() -> Unit)? = null) {

     Button(
        onClick = {
            if (onClick != null) {
                onClick()
            }
        },
        modifier = modifier,
        colors = colors,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        )
    ) {
           val iconModifier = Modifier
                .size(iconSize.dp)

             val iconBoxModifier = Modifier
                 .clip(CircleShape)
                .fillMaxSize()

            Box(
               contentAlignment = Alignment.Center,
                modifier = iconBoxModifier
            ) {
                ImageComponent(imageModifier = iconModifier, imageRes = iconRes, colorFilter = colorFilter)
            }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun bottomSheetIconButtonComponent(modifier: Modifier, buttonText: String, borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle, iconRes: String, iconSize: Int = 28, colorFilter: ColorFilter? = null, onClick: (String) -> Unit) {
    val rowModifier = Modifier
        .fillMaxWidth()

    Button(
        onClick = {
            onClick(buttonText)
        },
        border = borderStroke,
        shape = shape,
        modifier = modifier,
        colors = colors,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {

            val iconModifier = Modifier
                .padding(top = 5.dp)
                .size(iconSize.dp)

            val iconBoxModifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.10f)

            val textModifier = Modifier
                .padding(end = 15.dp)
                .wrapContentHeight()
                .fillMaxWidth(0.90f)
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = iconBoxModifier
            ) {
                ImageComponent(imageModifier = iconModifier, imageRes = iconRes, colorFilter = colorFilter)
            }
            TextComponent(
                text = buttonText,
                fontSize = fontSize,
                textStyle = style,
                textColor = textColor,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                textModifier = textModifier
            )
        }
    }
}



@Composable
public fun RightIconButtonComponent(modifier: Modifier, buttonText: String, borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle, iconRes: String, iconSize: Int = 28, colorFilter: ColorFilter? = null, onClick: (() -> Unit)? = null) {
    val rowModifier = Modifier
        .fillMaxWidth()

    Button(
        onClick = {
            if (onClick != null) {
                onClick()
            }
        },
        border = borderStroke,
        shape = shape,
        modifier = modifier,
        colors = colors,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {

            val iconModifier = Modifier
                .padding(top = 5.dp)
                .size(iconSize.dp)

            val iconBoxModifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

            val textModifier = Modifier
                .padding(top = 7.dp, end = 5.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.90f)
            TextComponent(
                text = buttonText,
                fontSize = fontSize,
                textStyle = style,
                textColor = textColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                textModifier = textModifier
            )
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = iconBoxModifier
            ) {
                ImageComponent(imageModifier = iconModifier, imageRes = iconRes, colorFilter = colorFilter)
            }
        }
    }
}


@Composable
fun GradientButton(
    modifier: Modifier, buttonText: String, borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle, gradient : Brush, onClick: ()-> Unit) {
    Button(
        onClick = {
             onClick()
        },
        colors = colors,
        elevation =  ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
    )) {
        Box(
            modifier = modifier
                .clip(shape)
                .background(gradient),
                contentAlignment = Alignment.Center,
        ) {
            TextComponent(
                text = buttonText, fontSize = fontSize, textStyle = style, textColor = textColor, textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold)
        }
    }
}


@Composable
fun LocationToggleButton(borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors,fontSize: Int, style: TextStyle) {

    var isParlorChecked by remember { mutableStateOf(true) }

    val parlorTint  = if(isParlorChecked) Color(0xFFFA2D65) else Color.Transparent
    val homeTint  =  if(!isParlorChecked) Color(0xFFFA2D65) else Color.Transparent

    val parlorTextColor = if (isParlorChecked) Color.White else Color(0xFFFA2D65)

    val homeTextColor = if (isParlorChecked) Color(0xFFFA2D65) else Color.White



    val rowModifier = Modifier
        .padding(top = 15.dp)
        .fillMaxWidth()
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {

            IconToggleButton(
                checked = isParlorChecked,
                onCheckedChange = { isParlorChecked = it }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.50f)
                        .height(50.dp)
                        .padding(start = 5.dp, end = 5.dp)
                        .border(border = BorderStroke(1.dp, Color(color = 0xFFF43569)),shape = shape)
                        .background(parlorTint, shape = shape),
                    contentAlignment = Alignment.Center,
                ) {
                    TextComponent(
                        text = "Parlor",
                        fontSize = fontSize,
                        textStyle = style,
                        fontFamily = GGSansSemiBold,
                        textColor = parlorTextColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Black
                    )
                }
            }

        IconToggleButton(
                checked = !isParlorChecked,
                onCheckedChange = {
                    isParlorChecked = !it
                }
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(border = BorderStroke(1.dp, Color(color = 0xFFF43569)), shape = shape)
                        .background(homeTint, shape = shape),
                    contentAlignment = Alignment.Center,
                ) {
                    TextComponent(
                        text = "Home",
                        fontSize = fontSize,
                        textStyle = style,
                        fontFamily = GGSansSemiBold,
                        textColor = homeTextColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

    }



@Composable
fun ToggleButton(shape: Shape, onLeftClicked: () ->  Unit, isRightSelection: Boolean = false, onRightClicked: () ->  Unit, leftText: String, rightText: String, isDisabled: Boolean = false) {

    var isLeftChecked by remember { mutableStateOf(!isRightSelection) }

    val leftTint  = if(isLeftChecked && isDisabled) Colors.disabledPrimaryColor else if(isLeftChecked) Colors.darkPrimary else Color.Transparent
    val rightTint  =  if(!isLeftChecked && isDisabled) Colors.disabledPrimaryColor else if(!isLeftChecked) Colors.darkPrimary else Color.Transparent

    val leftTextColor = if (isLeftChecked) Color.White else Colors.primaryColor

    val rightTextColor = if (isLeftChecked) Colors.primaryColor else Color.White

    val rowModifier = Modifier
        .padding(top = 15.dp, end = 10.dp, start = 10.dp)
        .fillMaxWidth()
        .height(50.dp)
        .background(color = Colors.lightPrimaryColor, shape = shape)
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {

            IconToggleButton(
                modifier = Modifier.padding(start = 10.dp, top = 7.dp, bottom = 7.dp),
                checked = isLeftChecked,
                onCheckedChange = {
                    if(!isDisabled) {
                        onLeftClicked()
                        isLeftChecked = it
                    }
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.50f)
                        .height(45.dp)
                        .background(leftTint, shape = shape),
                    contentAlignment = Alignment.Center,
                ) {
                    TextComponent(
                        text = leftText,
                        fontSize = 15,
                        textStyle = MaterialTheme.typography.h6,
                        fontFamily = GGSansRegular,
                        textColor = leftTextColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            IconToggleButton(
                modifier = Modifier.padding(end = 10.dp, top = 7.dp, bottom = 7.dp),
                checked = !isLeftChecked,
                onCheckedChange = {
                    if(!isDisabled) {
                        onRightClicked()
                        isLeftChecked = !it
                    }
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(rightTint, shape = shape),
                    contentAlignment = Alignment.Center,
                ) {
                    TextComponent(
                        text = rightText,
                        fontSize = 15,
                        textStyle = MaterialTheme.typography.h6,
                        fontFamily = GGSansRegular,
                        textColor = rightTextColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
}





@Composable
fun RadioToggleButton(shape: Shape,title: String, actionLabel: ArrayList<String>, gridCount: Int = 2, onCheckedChangedListener:(Int)-> Unit) {

    var checkedId by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = title,
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            lineHeight = 20,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textModifier = Modifier
                .fillMaxWidth().padding(bottom = 10.dp)
        )


        LazyVerticalGrid(
                columns = GridCells.Fixed(gridCount),
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                items(actionLabel.size) { it ->
                    IconToggleButton(
                        checked = checkedId == it,
                        onCheckedChange = { it2 ->
                            if (it2){
                                checkedId = it
                                onCheckedChangedListener(checkedId)
                            }
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth()
                                .height(54.dp)
                                .background(
                                    if (checkedId == it) Colors.primaryColor else Colors.lightPrimaryColor, shape = shape
                                ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = checkedId == it,
                                onClick = {

                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.White,
                                    unselectedColor = Color.LightGray
                                )

                            )
                            TextComponent(
                                text = actionLabel[it],
                                textColor = if (checkedId == it) Color.White else Colors.primaryColor,
                                textAlign = TextAlign.Center,
                                fontSize = 18,
                                fontFamily = GGSansSemiBold,
                                textStyle = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 20,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }

            }
        }
}
