package me.l3m4rk.test.presentation.common

sealed class ViewState {
    class Success<D>(val data: D) : ViewState()

    class Error(val message: String) : ViewState()

    object Progress : ViewState()
}

