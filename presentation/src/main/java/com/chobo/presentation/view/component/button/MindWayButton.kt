package com.chobo.presentation.view.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chobo.presentation.view.theme.MindWayAndroidTheme
import com.chobo.presentation.view.theme.color.MindWayColor

@Composable
fun MindWayButton(
    modifier: Modifier = Modifier,
    text: String,
    buttonColor: Color = MindWayColor.MAIN,
    isClickable: Boolean = true,
    onClick: () -> Unit,
) {
    val clickableModifier =
        if (isClickable) Modifier.clickable { onClick() }
        else Modifier
    MindWayAndroidTheme { colors, typography ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(10.dp)
                .background(
                    color = buttonColor,
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .then(clickableModifier)
        ) {
            Text(
                text = text,
                style = typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = colors.WHITE,
                textAlign = TextAlign.Center,
            )
        }
    }
}