package presentation.widgets

import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


class BusinessStatusProgressWidget {

 /*   @Composable
    fun GetCurrentProgressValue(): Float{
        val currentPageProgress by remember { mutableStateOf(0f) }
        return currentPageProgress
    }

    @Composable
    fun SetCurrentProgressValue(progress: Float = 0f): Float{
        val currentPageProgress by remember { mutableStateOf(progress) }
        return currentPageProgress
    }
*/

    @Composable
    fun GetStatusProgressBar(progress: Float = 0f, pageId: Int = 0, currentPage: Int = 0) {
        Row(
            Modifier
                .height(8.dp)
                .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth()
                .background(color = Color.LightGray, shape = CircleShape),
            horizontalArrangement = Arrangement.Start
        ) {
                 Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(color = Colors.primaryColor, shape = CircleShape)
                        .fillMaxWidth(if (pageId < currentPage) 1f else progress)
                )

            }
        }


/*    @Composable
    fun PopulateStatusProgress(progress: Float = 0f) {
        GetCurrentProgressValue(progress = progress)
    }*/
}