package com.chobo.presentation.view.main.screen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chobo.presentation.view.component.icon.LogoIcon
import com.chobo.presentation.view.component.topBar.MindWayTopAppBar
import com.chobo.presentation.view.main.component.HomeGoalReadingChart
import com.chobo.presentation.view.main.component.HomeNoticeCard
import com.chobo.presentation.view.main.component.HomeReadersOfTheMonthChart
import com.chobo.presentation.view.theme.MindWayAndroidTheme
import com.chobo.presentation.viewModel.main.HomeViewModel
import com.chobo.presentation.viewModel.main.uistate.GetRankUiState
import com.chobo.presentation.viewModel.main.uistate.GetWeekendGoalUiState
import com.chobo.presentation.viewModel.main.uistate.NoticeGetUiState

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    navigateToGoalReading: () -> Unit,
) {
    val getWeekendGoalUIState by homeViewModel.getWeekendGoalUIState.collectAsStateWithLifecycle()
    val noticeGetUiState by homeViewModel.noticeGetUiState.collectAsStateWithLifecycle()
    val getRankUIState by homeViewModel.getRankUIState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        getWeekendGoalUIState = getWeekendGoalUIState,
        noticeGetUiState = noticeGetUiState,
        getRankUIState = getRankUIState,
        getNotice = homeViewModel::getNotice,
        getRank = homeViewModel::getRank,
        getWeekendGoal = homeViewModel::getWeekendGoal,
        navigateToGoalReading = navigateToGoalReading,
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    getWeekendGoalUIState: GetWeekendGoalUiState,
    noticeGetUiState: NoticeGetUiState,
    getRankUIState: GetRankUiState,
    getNotice: () -> Unit,
    getRank: () -> Unit,
    getWeekendGoal: () -> Unit,
    navigateToGoalReading: () -> Unit,
) {
    LaunchedEffect(Unit) {
        getNotice()
        getRank()
        getWeekendGoal()
    }

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
                    is NoticeGetUiState.Fail -> Unit
                    is NoticeGetUiState.Loading -> Unit
                    is NoticeGetUiState.Success -> {
                        HomeNoticeCard(
                            noticeAllModel = noticeGetUiState.data,
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth(),
                        )
                    }
                }
                when (getWeekendGoalUIState) {
                    is GetWeekendGoalUiState.Empty -> {
                        HomeGoalReadingChart(
                            isHasData = false,
                            onClick = navigateToGoalReading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(211.dp),
                        )
                    }

                    is GetWeekendGoalUiState.Fail -> {
                        HomeGoalReadingChart(
                            isHasData = false,
                            onClick = navigateToGoalReading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(211.dp),
                        )
                    }

                    is GetWeekendGoalUiState.Loading -> {
                        HomeGoalReadingChart(
                            isHasData = false,
                            onClick = navigateToGoalReading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(211.dp),
                        )
                    }

                    is GetWeekendGoalUiState.Success -> {
                        HomeGoalReadingChart(
                            getWeekendGoalModel = getWeekendGoalUIState.data,
                            isHasData = true,
                            onClick = navigateToGoalReading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(211.dp),
                        )
                    }
                }
                when (getRankUIState) {
                    is GetRankUiState.Empty -> {
                        HomeReadersOfTheMonthChart(
                            isHasData = false,
                            modifier = Modifier
                                .height(239.dp)
                                .fillMaxWidth(),
                        )
                    }

                    is GetRankUiState.Fail -> {
                        HomeReadersOfTheMonthChart(
                            isHasData = false,
                            modifier = Modifier
                                .height(239.dp)
                                .fillMaxWidth(),
                        )
                    }

                    is GetRankUiState.Loading -> {
                        HomeReadersOfTheMonthChart(
                            isHasData = false,
                            modifier = Modifier
                                .height(239.dp)
                                .fillMaxWidth(),
                        )
                    }

                    is GetRankUiState.Success -> {
                        HomeReadersOfTheMonthChart(
                            isHasData = true,
                            bookKingOfTheMonthData = getRankUIState.data,
                            modifier = Modifier
                                .height(239.dp)
                                .fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeRoute(
        navigateToGoalReading = { },
    )
}