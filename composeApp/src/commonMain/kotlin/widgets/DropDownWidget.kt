package widgets

import GGSansRegular
import Styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
@Composable
fun DropDownWidgetView(menuItems: List<String>,
                   menuExpandedState: Boolean,
                   selectedIndex : Int,
                   updateMenuExpandStatus : () -> Unit,
                   onDismissMenuView : () -> Unit,
                   onMenuItemClick : (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                onClick = {
                    updateMenuExpandStatus()
                },
            ),
    ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
            ) {
                val textStyle: TextStyle = TextStyle(
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    fontFamily = GGSansRegular,
                    fontWeight = FontWeight.SemiBold
                )

                Box(modifier = Modifier.fillMaxHeight().width(50.dp), contentAlignment = Alignment.Center){
                    ImageComponent(imageModifier = Modifier
                        .size(24.dp), imageRes = "drawable/country_icon.png", colorFilter = ColorFilter.tint(color = Color.Gray))
                }

                Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.8f), contentAlignment = Alignment.Center){

                    TextComponent(
                        text =  if(selectedIndex != -1) menuItems.get(selectedIndex) else "Country of Residence",
                        fontSize = 18,
                        fontFamily = GGSansRegular,
                        textStyle = textStyle,
                        textColor = Color.Gray,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Black,
                        textModifier = Modifier.wrapContentHeight().fillMaxWidth().padding(end = 5.dp))
                }

                Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.Center){
                    val imageModifier = Modifier
                        .size(30.dp)
                        .padding(start = 10.dp, top = 3.dp)
                    ImageComponent(
                        imageModifier = imageModifier,
                        imageRes = "drawable/chevron_down_icon.png",
                        colorFilter = ColorFilter.tint(color = Colors.primaryColor)
                    )
                }



            }
        }


        val textStyle: TextStyle = TextStyle(
            fontSize = TextUnit(18f, TextUnitType.Sp),
            fontFamily = GGSansRegular,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Black,
        )

        DropdownMenu(
            expanded = menuExpandedState,
            onDismissRequest = { onDismissMenuView() },
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .background(Color.White)
        ) {
            menuItems.forEachIndexed { index, title ->
                DropdownMenuItem(
                    onClick = {
                        if (index != 0) {
                            onMenuItemClick(index)
                        }
                    }) {
                    SubtitleTextWidget(text = title, fontSize = 20)
                }
            }
        }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DropDownWidget(menuItems: List<String>) {

    val expandedMenuItem = remember { mutableStateOf(false) }

    val selectedMenuIndex = remember { mutableStateOf(-1) }

    val modifier  = Modifier
        .padding(end = 10.dp, start = 10.dp, top = 20.dp)
        .fillMaxWidth()
        .height(65.dp)
        .border(border = BorderStroke(2.dp, color  = Colors.primaryColor), shape = RoundedCornerShape(15.dp))
        .background(color = Colors.lightPrimaryColor, shape = RoundedCornerShape(15.dp))

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,

        ) {
        DropDownWidgetView(
            menuItems = menuItems,
            menuExpandedState = expandedMenuItem.value,
            selectedIndex = selectedMenuIndex.value,
            updateMenuExpandStatus = {
                expandedMenuItem.value = true
            },
            onDismissMenuView = {
                expandedMenuItem.value = false
            },
            onMenuItemClick = { index ->
                selectedMenuIndex.value = index
                expandedMenuItem.value = false
            }
        )
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun TextDropDownWidget(menuItems: List<String>) {

    val expandedMenuItem = remember { mutableStateOf(false) }

    val selectedMenuIndex = remember { mutableStateOf(-1) }

    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,

        ) {
        TextDownWidgetView(
            menuItems = menuItems,
            menuExpandedState = expandedMenuItem.value,
            selectedIndex = selectedMenuIndex.value,
            updateMenuExpandStatus = {
                expandedMenuItem.value = true
            },
            onDismissMenuView = {
                expandedMenuItem.value = false
            },
            onMenuItemClick = { index ->
                selectedMenuIndex.value = index
                expandedMenuItem.value = false
            }
        )
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun TextDownWidgetView(menuItems: List<String>,
                       menuExpandedState: Boolean,
                       selectedIndex : Int,
                       updateMenuExpandStatus : () -> Unit,
                       onDismissMenuView : () -> Unit,
                       onMenuItemClick : (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                onClick = {
                    updateMenuExpandStatus()
                },
            ),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            val textStyle: TextStyle = TextStyle(
                fontSize = TextUnit(23f, TextUnitType.Sp),
                fontFamily = GGSansRegular,
                fontWeight = FontWeight.SemiBold
            )

            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.8f), contentAlignment = Alignment.Center){

                TextComponent(
                    text =  if(selectedIndex != -1) menuItems.get(selectedIndex) else "+ 345",
                    fontSize = 23,
                    fontFamily = GGSansRegular,
                    textStyle = textStyle,
                    textColor = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black,
                    textModifier = Modifier.wrapContentHeight().fillMaxWidth().padding(end = 5.dp))
            }
        }
    }


    val textStyle: TextStyle = TextStyle(
        fontSize = TextUnit(18f, TextUnitType.Sp),
        fontFamily = GGSansRegular,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Black,
    )

    DropdownMenu(
        expanded = menuExpandedState,
        onDismissRequest = { onDismissMenuView() },
        modifier = Modifier
            .fillMaxWidth(0.90f)
            .background(Color.White)
    ) {
        menuItems.forEachIndexed { index, title ->
            DropdownMenuItem(
                onClick = {
                    if (index != 0) {
                        onMenuItemClick(index)
                    }
                }) {
                SubtitleTextWidget(text = title, fontSize = 20)
            }
        }
    }
}





