package com.chobo.presentation.view.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chobo.presentation.view.component.icon.LogoIcon
import com.chobo.presentation.view.component.topBar.MindWayTopAppBar
import com.chobo.presentation.view.main.component.BookKingOfTheMonthData
import com.chobo.presentation.view.main.component.GoalReadingGraphData
import com.chobo.presentation.view.main.component.HomeGoalReadingChart
import com.chobo.presentation.view.main.component.HomeNoticeCard
import com.chobo.presentation.view.main.component.HomeReadersOfTheMonthChart
import com.chobo.presentation.view.theme.MindWayAndroidTheme
import com.chobo.presentation.viewModel.main.HomeViewModel
import com.chobo.presentation.viewModel.main.uistate.NoticeGetUiState

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(),
    navigateToGoalReading: () -> Unit,
    navigateToDetailEvent: () -> Unit,
) {
    val goalBookRead by homeViewModel.goalBookRead.collectAsStateWithLifecycle()
    val noticeGetUiState by homeViewModel.noticeGetUiState.collectAsStateWithLifecycle()
    val readingGoalGraphDataList by homeViewModel.goalReadingGraphDataList.collectAsStateWithLifecycle()
    val bookKingOfTheMonthDataList by homeViewModel.bookKingOfTheMonthDataList.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        goalBookRead = goalBookRead,
        noticeGetUiState = noticeGetUiState,
        readingGoalGraphDataList = readingGoalGraphDataList,
        bookKingOfTheMonthDataList = bookKingOfTheMonthDataList,
        navigateToGoalReading = navigateToGoalReading,
        navigateToDetailEvent = navigateToDetailEvent,
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    goalBookRead: Int,
    noticeGetUiState: NoticeGetUiState,
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
                when (noticeGetUiState) {
                    is NoticeGetUiState.Fail -> {}
                    is NoticeGetUiState.Loading -> {}
                    is NoticeGetUiState.Success -> {
                        HomeNoticeCard(
                            noticeAllModel = noticeGetUiState.data,
                            onClick = navigateToDetailEvent,
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth(),
                        )
                    }
                }
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