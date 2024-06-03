package com.chobo.presentation.viewModel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chobo.domain.model.book.request.BookRequestBodyModel
import com.chobo.domain.usecase.book.BookDeleteByIdUseCase
import com.chobo.domain.usecase.book.GetBookByIdUseCase
import com.chobo.presentation.viewModel.goal.uistate.GetBookByIdUiState
import com.chobo.presentation.viewModel.util.result.Result
import com.chobo.presentation.viewModel.util.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewDetailViewModel @Inject constructor(
    private val getBookByIdUseCase: GetBookByIdUseCase,
    private val bookDeleteByIdUseCase: BookDeleteByIdUseCase,
) : ViewModel() {
    private val _getBookByIdUiState = MutableStateFlow<GetBookByIdUiState>(GetBookByIdUiState.Loading)
    val getBookByIdUiState: StateFlow<GetBookByIdUiState> = _getBookByIdUiState.asStateFlow()

    private val _checkBookDialogIsVisible = MutableStateFlow(false)
    val checkBookDialogIsVisible: StateFlow<Boolean> = _checkBookDialogIsVisible.asStateFlow()

    fun getBookById(id: Long) = viewModelScope.launch {
        getBookByIdUseCase(bookId = id)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getBookByIdUiState.value = GetBookByIdUiState.Loading
                    is Result.Success -> _getBookByIdUiState.value = GetBookByIdUiState.Success(data = result.data)
                    is Result.Fail -> _getBookByIdUiState.value = GetBookByIdUiState.Fail(result.exception)
                }
            }
    }

    fun bookDeleteById(id: Long) = viewModelScope.launch {
        bookDeleteByIdUseCase(bookId = id).asResult().collectLatest { }
    }

    fun toggleCheckBookDialogIsVisible() {
        _checkBookDialogIsVisible.value = !_checkBookDialogIsVisible.value
    }

    init {
        _getBookByIdUiState.value = GetBookByIdUiState.Success(
            data = BookRequestBodyModel(
                title = "임시데이터 임시데이터 임시데이터",
                plot = "임시데이터 임시데이터 임시데이터 임시데이터 임시데이터 임시데이터 임시데이터 임시데이터 임시데이터",
            )
        )
    }
}