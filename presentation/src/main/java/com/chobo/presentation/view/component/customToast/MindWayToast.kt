package com.chobo.presentation.view.component.customToast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chobo.presentation.view.component.icon.FailIcon
import com.chobo.presentation.view.component.icon.SuccessIcon
import com.chobo.presentation.view.theme.MindWayTypography
import com.chobo.presentation.view.theme.color.MindWayColor

@Composable
fun MindWayToast(
    modifier: Modifier = Modifier,
    text: String,
    isSuccess: Boolean,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .shadow(
                elevation = 20.dp,
                spotColor = MindWayColor.StatusShadow,
                ambientColor = MindWayColor.StatusShadow
            )
            .background(color = MindWayColor.WHITE, shape = RoundedCornerShape(size = 8.dp))
            .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 20.dp)
    ) {
        if (isSuccess) SuccessIcon()
        else FailIcon()
        Text(
            text = text,

            style =  MindWayTypography.labelLarge.copy(
                fontWeight = FontWeight(400),
                color = MindWayColor.Black,
            )
        )
    }
}