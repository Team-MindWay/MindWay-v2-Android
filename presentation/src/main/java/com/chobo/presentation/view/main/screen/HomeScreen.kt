package com.chobo.presentation.view.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chobo.presentation.view.component.icon.LogoIcon
import com.chobo.presentation.view.component.topBar.MindWayTopAppBar
import com.chobo.presentation.view.main.component.*
import com.chobo.presentation.view.theme.MindWayAndroidTheme
import com.chobo.presentation.viewModel.main.HomeViewModel

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(),
    navigateToGoalReading: () -> Unit,
    navigateToDetailEvent: () -> Unit,
) {
    val titleTextState by homeViewModel.titleTextState.collectAsStateWithLifecycle()
    val contentTextState by homeViewModel.contentTextState.collectAsStateWithLifecycle()
    val goalBookRead by homeViewModel.goalBookRead.collectAsStateWithLifecycle()
    val readingGoalGraphDataList by homeViewModel.goalReadingGraphDataList.collectAsStateWithLifecycle()
    val bookKingOfTheMonthDataList by homeViewModel.bookKingOfTheMonthDataList.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        titleTextState = titleTextState,
        contentTextState = contentTextState,
        goalBookRead = goalBookRead,
        readingGoalGraphDataList = readingGoalGraphDataList,
        bookKingOfTheMonthDataList = bookKingOfTheMonthDataList,
        navigateToGoalReading = navigateToGoalReading,
        navigateToDetailEvent = navigateToDetailEvent,
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    titleTextState: String,
    contentTextState: String,
    goalBookRead: Int,
    readingGoalGraphDataList: List<GoalReadingGraphData>,
    bookKingOfTheMonthDataList: List<BookKingOfTheMonthData>,
    navigateToGoalReading: () -> Unit,
    navigateToDetailEvent: () -> Unit,
) {
    MindWayAndroidTheme { colors, _ ->
        Column {
            MindWayTopAppBar(startIcon = { LogoIcon() })
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .background(color = colors.WHITE)
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                HomeNoticeCard(
                    titleText = titleTextState,
                    content = contentTextState,
                    onClick = navigateToDetailEvent,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(),
                )
                HomeGoalReadingChart(
                    goalBookRead = goalBookRead,
                    isHasData = readingGoalGraphDataList.isNotEmpty(),
                    readNumberList = readingGoalGraphDataList,
                    onClick = navigateToGoalReading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(211.dp),
                )
                HomeReadersOfTheMonthChart(
                    bookKingOfTheMonthData = bookKingOfTheMonthDataList,
                    isHasData = bookKingOfTheMonthDataList.isNotEmpty(),
                    modifier = Modifier
                        .height(239.dp)
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeRoute(
        navigateToGoalReading = { },
        navigateToDetailEvent = { }
    )
}