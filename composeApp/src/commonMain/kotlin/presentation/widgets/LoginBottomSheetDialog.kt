package presentation.widgets

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginBottomSheetLayoutDialog(modifier: Modifier = Modifier, hideOnBackPress: Boolean = true,
                                       sheetShape: Shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                                       sheetElevation: Dp = ModalBottomSheetDefaults.Elevation,
                                       sheetBackgroundColor: Color = Color.DarkGray, sheetContentColor: Color = contentColorFor(sheetBackgroundColor)){


}