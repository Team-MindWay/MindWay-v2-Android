package com.chobo.presentation.view.component.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chobo.presentation.view.theme.MindWayAndroidTheme

@Composable
fun MindWayTextField(
    outSideModifier: Modifier,
    textFieldModifier: Modifier = Modifier,
    title: String,
    textState: MutableState<String>,
    placeholder: String,
    isError: Boolean,
    errorMessage: String,
    isTextRight: Boolean = false
) {
    MindWayAndroidTheme { colors, typography ->
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = outSideModifier
        ) {
            Text(
                text = title,
                style = typography.labelLarge,
                fontWeight = FontWeight.Normal,
                color = colors.GRAY400,
            )
            Box(
                modifier = textFieldModifier
                    .border(
                        width = 1.dp,
                        color = if (isError) colors.SYSTEM
                        else colors.GRAY100,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .background(
                        color = colors.GRAY100,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
            ) {
                BasicTextField(
                    onValueChange = { newText -> textState.value = newText },
                    value = textState.value,
                    textStyle = typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        color = colors.Black,
                        textAlign = if (isTextRight) TextAlign.End
                        else TextAlign.Start,
                    ),
                    cursorBrush = SolidColor(colors.MAIN),
                    modifier = Modifier
                        .fillMaxWidth(
                            if (isTextRight) 0.95f
                            else 1f
                        )
                        .padding(15.dp),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = if (isTextRight) Arrangement.End
                    else Arrangement.Start,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    if (!isTextRight && (textState.value == "")) {
                        Text(
                            text = placeholder,
                            style = typography.bodySmall,
                            fontWeight = FontWeight.Normal,
                            color = colors.GRAY400,
                        )
                    }
                    if (isTextRight) {
                        Text(
                            text = placeholder,
                            style = typography.bodySmall,
                            fontWeight = FontWeight.Normal,
                            color = colors.GRAY400,
                        )
                    }
                }
            }
            if (isError) {
                Text(
                    text = errorMessage,
                    style = typography.labelLarge,
                    fontWeight = FontWeight.Normal,
                    color = colors.SYSTEM
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    val textState = remember {
        mutableStateOf("")
    }
    MindWayTextField(
        title = "제목이다",
        textState = textState,
        placeholder = "힌트다",
        isError = true,
        errorMessage = "에러니까 고치셈",
        isTextRight = true,
        outSideModifier = Modifier.height(300.dp),
        textFieldModifier = Modifier.height(80.dp)
    )
}