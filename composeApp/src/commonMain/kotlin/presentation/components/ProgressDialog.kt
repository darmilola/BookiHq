package presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.Colors

@Composable
 fun IndeterminateCircularProgressBar(){
    CircularProgressIndicator(
        modifier = Modifier.size(40.dp),
        color = Colors.primaryColor,
        strokeWidth = 4.dp)

}