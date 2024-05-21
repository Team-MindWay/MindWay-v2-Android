package com.chobo.presentation.viewModel.book

import androidx.lifecycle.*
import com.chobo.domain.emumtype.OrderRequestBookType
import com.chobo.domain.emumtype.OrderRequestBookType.*
import com.chobo.domain.model.recommend.response.RecommendListResponseAllModel
import com.chobo.domain.usecase.recommend.GetRecommendBookUseCase
import com.chobo.presentation.viewModel.util.Event
import com.chobo.presentation.viewModel.util.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookScreenViewModel @Inject constructor(
    private val getRecommendBookUseCase: GetRecommendBookUseCase
) : ViewModel() {
    private val _novelDataList = MutableStateFlow<List<RecommendListResponseAllModel>>(listOf())
    val novelDataList: StateFlow<List<RecommendListResponseAllModel>> = _novelDataList.asStateFlow()

    private val _essayDataList = MutableStateFlow<List<RecommendListResponseAllModel>>(listOf())
    val essayDataList: StateFlow<List<RecommendListResponseAllModel>> = _essayDataList.asStateFlow()

    private val _isToastVisible = MutableStateFlow(false)
    val isToastVisible: StateFlow<Boolean> = _isToastVisible.asStateFlow()

    fun showToast() {
        _isToastVisible.value = true
        viewModelScope.launch {
            delay(2000)
            _isToastVisible.value = false
        }
    }

    fun getRecommendBook(type: OrderRequestBookType) = viewModelScope.launch {
        getRecommendBookUseCase(type = type.name)
            .onSuccess {
                it.catch { remoteError ->
                    remoteError.errorHandling<Unit>()
                }.collect { response ->
                    when (type) {
                        NOVEL -> _novelDataList.value = response

                        ESSAY -> _essayDataList.value = response
                    }
                    Event.Success(data = response)
                }
            }
            .onFailure {
                it.errorHandling<Unit>()
            }
    }

    init {
        getRecommendBook(type = NOVEL)
        getRecommendBook(type = ESSAY)
    }
}