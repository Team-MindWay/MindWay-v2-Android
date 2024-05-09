package com.chobo.presentation.view.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chobo.presentation.R
import com.chobo.presentation.view.component.button.MindWayButton
import com.chobo.presentation.view.component.textField.MindWayTextField
import com.chobo.presentation.view.main.component.AddBookTopAppBar
import com.chobo.presentation.view.theme.MindWayAndroidTheme
import com.chobo.presentation.viewModel.main.HomeAddBookViewModel

@Composable
fun HomeEditBookScreen(
    modifier: Modifier = Modifier,
    homeAddBookViewModel: HomeAddBookViewModel = viewModel(),
    navigateToBack: () -> Unit
) {
    val titleTextState by homeAddBookViewModel.titleTextState.collectAsStateWithLifecycle()
    val contentTextState by homeAddBookViewModel.contentTextState.collectAsStateWithLifecycle()
    val titleTextStateIsEmpty by homeAddBookViewModel.titleTextStateIsEmpty.collectAsStateWithLifecycle()
    val contentTextStateIsEmpty by homeAddBookViewModel.contentTextStateIsEmpty.collectAsStateWithLifecycle()

    MindWayAndroidTheme { colors, _ ->
        Column(modifier = modifier.background(color = colors.WHITE)) {
            AddBookTopAppBar(startIconOnClick = { navigateToBack() })
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(
                            horizontal = 24.dp,
                            vertical = 28.dp
                        )
                        .fillMaxWidth()
                ) {
                    MindWayTextField(
                        title = stringResource(R.string.title),
                        textState = titleTextState,
                        placeholder = stringResource(R.string.please_enter_the_book_title),
                        overflowErrorMessage = stringResource(R.string.overFlowErrorMessage),
                        emptyErrorMessage = stringResource(R.string.please_enter_the_book_title),
                        updateTextValue = homeAddBookViewModel::updateTitleTextState,
                        isError = titleTextStateIsEmpty
                    )
                    MindWayTextField(
                        title = stringResource(R.string.content),
                        textState = contentTextState,
                        placeholder = stringResource(R.string.please_enter_the_book_content),
                        overflowErrorMessage = stringResource(R.string.overFlowErrorMessage),
                        emptyErrorMessage = stringResource(R.string.error_content),
                        lengthLimit = homeAddBookViewModel.contentTextMaxLength,
                        updateTextValue = homeAddBookViewModel::updateContentTextState,
                        isError = contentTextStateIsEmpty
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    MindWayButton(
                        text = stringResource(R.string.check),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        onClick = { homeAddBookViewModel.checkButton() }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBookEditScreenPreview() {
    HomeEditBookScreen(navigateToBack = { })
}