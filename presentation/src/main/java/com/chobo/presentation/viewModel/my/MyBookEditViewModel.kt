package com.chobo.presentation.viewModel.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chobo.domain.model.order.OrderRequestBodyModel
import com.chobo.domain.usecase.order.OrderModifyByIdUseCase
import com.chobo.presentation.viewModel.my.uistate.OrderModifyByIdUiState
import com.chobo.presentation.viewModel.util.Event
import com.chobo.presentation.viewModel.util.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBookEditViewModel @Inject constructor(
    private val orderModifyByIdUseCase: OrderModifyByIdUseCase,
) : ViewModel() {
    private val _titleTextState = MutableStateFlow("")
    val titleTextState: StateFlow<String> = _titleTextState.asStateFlow()

    private val _writeTextState = MutableStateFlow("")
    val writeTextState: StateFlow<String> = _writeTextState.asStateFlow()

    private val _linkTextState = MutableStateFlow("")
    val linkTextState: StateFlow<String> = _linkTextState.asStateFlow()

    private val _titleTextStateIsEmpty = MutableStateFlow(false)
    val titleTextStateIsEmpty: StateFlow<Boolean> = _titleTextStateIsEmpty.asStateFlow()

    private val _writeTextStateIsEmpty = MutableStateFlow(false)
    val writeTextStateIsEmpty: StateFlow<Boolean> = _writeTextStateIsEmpty.asStateFlow()

    private val _linkTextStateIsEmpty = MutableStateFlow(false)
    val linkTextStateIsEmpty: StateFlow<Boolean> = _linkTextStateIsEmpty.asStateFlow()

    private val _orderModifyByIdUiState = MutableStateFlow<OrderModifyByIdUiState>(OrderModifyByIdUiState.Loading)
    val orderModifyByIdUiState: StateFlow<OrderModifyByIdUiState> = _orderModifyByIdUiState.asStateFlow()

    fun updateTitleTextState(input: String) {
        _titleTextStateIsEmpty.value = false
        _titleTextState.value = input
    }

    fun updateWriteTextState(input: String) {
        _writeTextStateIsEmpty.value = false
        _writeTextState.value = input
    }

    fun updateLinkTextState(input: String) {
        _linkTextStateIsEmpty.value = false
        _linkTextState.value = input
    }

    fun checkButtonOnClick() {
        _titleTextStateIsEmpty.value = _titleTextState.value.isEmpty()
        _writeTextStateIsEmpty.value = _writeTextState.value.isEmpty()
        _linkTextStateIsEmpty.value = _linkTextState.value.isEmpty()
        if (
            _titleTextState.value.isNotEmpty()
            && _writeTextState.value.isNotEmpty()
            && _linkTextState.value.isNotEmpty()
        ) {
            viewModelScope.launch {
                orderModifyByIdUseCase(
                    body = OrderRequestBodyModel(
                        title = titleTextState.value,
                        author = _writeTextState.value,
                        book_url = linkTextState.value
                    ),
                    orderId = "임시 Id"
                )
                    .onSuccess {
                        it.catch { remoteError ->
                            _orderModifyByIdUiState.value = OrderModifyByIdUiState.RemoteFail(exception = remoteError)
                            remoteError.errorHandling<Unit>()
                        }.collect { response ->
                            _orderModifyByIdUiState.value = OrderModifyByIdUiState.Success
                            Event.Success(data = response)
                        }
                    }
                    .onFailure {
                        _orderModifyByIdUiState.value = OrderModifyByIdUiState.RemoteFail(exception = it)
                        it.errorHandling<Unit>()
                    }
            }
        }
    }

    private fun getMyBookData(orderRequestBodyModel: OrderRequestBodyModel) {
        _titleTextState.value = orderRequestBodyModel.title
        _writeTextState.value = orderRequestBodyModel.author
        _linkTextState.value = orderRequestBodyModel.book_url
    }

    init {
        viewModelScope.launch {
            getMyBookData(
                // TODO: usecase 연결
                OrderRequestBodyModel(
                    title = "임시 데이터입니당",
                    author = "임시 데이터입니당임시 데이터입니당임시 데이터입니당",
                    book_url = "임시 데이터입니당임시 데이터입니당임시 데이터입니당"
                )
            )
        }
    }
}