package Models

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


class TriangleShape : Shape { override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val trianglePath = Path().apply {
            // Moves to top center position
            moveTo(size.width / 2f, 0f)
            // Add line to right corner above circle
            lineTo(x = size.width, y = size.height)
            //Add line to left corner above circle
            lineTo(x = 0f, y = size.height)
        }
        return Outline.Generic(path = trianglePath)

    }
}
