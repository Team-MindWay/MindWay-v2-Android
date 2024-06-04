package com.chobo.presentation.view.event.screen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chobo.domain.emumtype.EventRequestListStatusType
import com.chobo.presentation.R
import com.chobo.presentation.view.component.icon.BookImage
import com.chobo.presentation.view.event.component.EventContent
import com.chobo.presentation.view.event.component.EventPager
import com.chobo.presentation.view.theme.MindWayAndroidTheme
import com.chobo.presentation.viewModel.event.EventViewModel
import com.chobo.presentation.viewModel.event.uistate.GetEventListUiState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun EventScreenRoute(
    modifier: Modifier = Modifier,
    navigateToDetailEvent: () -> Unit,
    eventViewModel: EventViewModel = viewModel(LocalContext.current as ComponentActivity)
) {
    val getEventListUiState by eventViewModel.getEventListUiState.collectAsStateWithLifecycle()
    val swipeRefreshLoading by eventViewModel.swipeRefreshLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)
    val pagerState = rememberPagerState { 2 }

    EventScreen(
        modifier = modifier,
        navigateToDetailEvent = navigateToDetailEvent,
        getEventListUiState = getEventListUiState,
        swipeRefreshState = swipeRefreshState,
        pagerState = pagerState,
        onCurrentEventClick = eventViewModel::onCurrentEventClick,
        onPastEventClick = eventViewModel::onPastEventClick,
        getEventList = eventViewModel::getEventList,
        getEventPastList = eventViewModel::getEventPastList,
        loadStuff = eventViewModel::loadStuff,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun EventScreen(
    modifier: Modifier = Modifier,
    getEventListUiState: GetEventListUiState,
    swipeRefreshState: SwipeRefreshState,
    pagerState: PagerState,
    onCurrentEventClick: (Int) -> Unit,
    onPastEventClick: (Int) -> Unit,
    navigateToDetailEvent: (Long) -> Unit,
    getEventList: (String) -> Unit,
    getEventPastList: (String) -> Unit,
    loadStuff: () -> Unit,
) {
    var storageNowStatus by remember { mutableStateOf(EventRequestListStatusType.NOW.name) }
    val storagePastStatus by remember { mutableStateOf(EventRequestListStatusType.PAST.name) }
    val scrollState = rememberScrollState()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            when (page) {
                0 -> storageNowStatus = EventRequestListStatusType.NOW.name
                1 -> storageNowStatus = EventRequestListStatusType.PAST.name
            }
        }
    }

    LaunchedEffect(Unit) {
        getEventList(storageNowStatus)
        getEventPastList(storagePastStatus)
    }

    MindWayAndroidTheme { colors, typography ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                loadStuff()
                if (storageNowStatus == EventRequestListStatusType.PAST.name) {
                    getEventPastList(storagePastStatus)
                } else {
                    getEventList(storageNowStatus)
                }
            }
        ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = colors.WHITE)
            ) {
                EventPager(
                    pagerState = pagerState,
                    onGoingEvent = {
                        when (getEventListUiState) {
                            GetEventListUiState.Empty -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .verticalScroll(scrollState),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        BookImage()
                                        Text(
                                            text = stringResource(R.string.is_no_ongoing_event),
                                            style = typography.bodyMedium,
                                            fontWeight = FontWeight.Normal,
                                            color = colors.GRAY500,
                                        )
                                    }
                                }
                            }
                            is GetEventListUiState.Fail -> {
                                EventContent(
                                    content = stringResource(R.string.is_no_ongoing_event),
                                    eventDataListIsEmpty = false,
                                    onIconClick = onCurrentEventClick,
                                    navigateToDetailEvent = navigateToDetailEvent,
                                )
                            }
                            is GetEventListUiState.Loading -> {
                                EventContent(
                                    content = stringResource(R.string.is_no_ongoing_event),
                                    eventDataListIsEmpty = false,
                                    onIconClick = onCurrentEventClick,
                                    navigateToDetailEvent = navigateToDetailEvent,
                                )
                            }
                            is GetEventListUiState.Success -> {
                                Box(modifier = modifier.verticalScroll(scrollState)) {
                                    EventContent(
                                        content = stringResource(R.string.is_no_ongoing_event),
                                        eventDataList = getEventListUiState.getEventListResponse,
                                        eventDataListIsEmpty = true,
                                        onIconClick = onCurrentEventClick,
                                        navigateToDetailEvent = navigateToDetailEvent,
                                    )
                                }
                            }
                        }
                    },
                    pastEvent = {
                        when (getEventListUiState) {
                            GetEventListUiState.Empty -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(scrollState),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    BookImage()
                                    Text(
                                        text = stringResource(R.string.is_no_past_event),
                                        style = typography.bodyMedium,
                                        fontWeight = FontWeight.Normal,
                                        color = colors.GRAY500,
                                    )
                                }
                            }
                            }
                            is GetEventListUiState.Fail -> {
                                EventContent(
                                    content = stringResource(R.string.is_no_past_event),
                                    eventDataListIsEmpty = false,
                                    onIconClick = onPastEventClick,
                                    navigateToDetailEvent = navigateToDetailEvent,
                                )
                            }
                            is GetEventListUiState.Loading -> {
                                EventContent(
                                    content = stringResource(R.string.is_no_past_event),
                                    eventDataListIsEmpty = false,
                                    onIconClick = onPastEventClick,
                                    navigateToDetailEvent = navigateToDetailEvent,
                                )
                            }
                            is GetEventListUiState.Success -> {
                                Box(modifier = modifier.verticalScroll(scrollState)) {
                                    EventContent(
                                        content = stringResource(R.string.is_no_past_event),
                                        eventDataList = getEventListUiState.getEventListResponse,
                                        eventDataListIsEmpty = true,
                                        onIconClick = onPastEventClick,
                                        navigateToDetailEvent = navigateToDetailEvent,
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun EventScreenPre() {
    EventScreenRoute(navigateToDetailEvent = { })
}