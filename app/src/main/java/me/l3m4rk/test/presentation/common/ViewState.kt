package me.l3m4rk.test.presentation.common

import me.l3m4rk.test.presentation.models.ArtistVO

sealed class ViewState {
    class Success(val artists: List<ArtistVO>) : ViewState()

    class Error(val message: String) : ViewState()

    object Progress : ViewState()

    object Initial : ViewState()
}

