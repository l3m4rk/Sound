package me.l3m4rk.test.presentation.common

sealed class ViewState<out T> {
    class Success<T>(val data: T) : ViewState<T>()

    class Error<out T>(val message: String) : ViewState<T>()

    class Progress<out T> : ViewState<T>()

    class Initial<out T> : ViewState<T>()
}

