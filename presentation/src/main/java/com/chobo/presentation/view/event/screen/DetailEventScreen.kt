package com.chobo.presentation.view.event.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chobo.presentation.R
import com.chobo.presentation.view.component.icon.ChevronLeftIcon
import com.chobo.presentation.view.component.multipleEventsCutterManager.clickableSingle
import com.chobo.presentation.view.component.topBar.MindWayTopAppBar
import com.chobo.presentation.view.event.component.DetailEventContent
import com.chobo.presentation.view.theme.MindWayAndroidTheme
import com.chobo.presentation.viewModel.event.DetailEventViewModel

@Composable
internal fun DetailEventRoute(
    modifier: Modifier = Modifier,
    detailEventViewModel: DetailEventViewModel = viewModel(),
    navigateToBack: () -> Unit,
) {
    val title by detailEventViewModel.title.collectAsStateWithLifecycle()
    val content by detailEventViewModel.content.collectAsStateWithLifecycle()
    val date by detailEventViewModel.date.collectAsStateWithLifecycle()
    val imageRes by detailEventViewModel.imageRes.collectAsStateWithLifecycle()

    DetailEventScreen(
        modifier = modifier,
        title = title,
        content = content,
        date = date,
        imageRes = imageRes,
        navigateToBack = navigateToBack
    )
}

@Composable
internal fun DetailEventScreen(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    date: String,
    imageRes: Int,
    navigateToBack: () -> Unit,
) {
    MindWayAndroidTheme { colors, _ ->
        Column(modifier = modifier.background(color = colors.WHITE)) {
            MindWayTopAppBar(
                startIcon = { ChevronLeftIcon(modifier = Modifier.clickableSingle(onClick = navigateToBack)) },
                midText = stringResource(id = R.string.ongoing_event)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = "Event Image",
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                        .height(264.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                )
                DetailEventContent(
                    title = title,
                    content = content,
                    date = date,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailEventScreenPre() {
    DetailEventRoute(navigateToBack = { })
}