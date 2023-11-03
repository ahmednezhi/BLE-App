package com.ahmed_nezhi.bleapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmed_nezhi.bleapp.ui.theme.BLEAppTheme
import com.ahmed_nezhi.bleapp.viewmodel.BleViewModel

/**
 * Create by A.Nezhi on 03/11/2023.
 */
@Composable
fun BleScreen(bleViewModel: BleViewModel = hiltViewModel()) {
    // Collecting the state from the ViewModel
    val screenColor by bleViewModel.screenColor.collectAsState()

    // Update the UI based on the screenColor value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorFromValue(screenColor))
            .alpha(0.5f), contentAlignment = Alignment.Center
    ) {
        Column {
            CustomText(text = "Send 0 to change the color to : Yellow")
            CustomText(text = "Send 1 to change the color to : Green")
            CustomText(text = "Send 2 to change the color to : Blue")
            CustomText(text = "Send 3 to change the color to : Red")
            CustomText(text = "Send 4 to change the color to : Cyan")
            CustomText(text = "Otherwise, White")
        }
    }

}

@Composable
fun CustomText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .shadow(elevation = 14.dp, shape = RoundedCornerShape(6.dp))
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(24.dp),
        color = Color.Black
    )
}

// Helper function to convert the integer color value to a Color object
@Composable
fun colorFromValue(value: Int): Color {
    return when (value) {
        0 -> Color.Yellow
        1 -> Color.Green
        2 -> Color.Blue
        3 -> Color.Red
        4 -> Color.Cyan
        else -> Color.White // When the value is greater than 0, the background is white
    }
}


@Preview(showBackground = true)
@Composable
fun BleScreenPreview() {
    BLEAppTheme {
        BleScreen()
    }
}