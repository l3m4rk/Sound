package me.l3m4rk.test.di.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment

inline fun <reified T : ViewModel> DaggerFragment.viewModel(crossinline f: () -> T): T =
    ViewModelProviders.of(this, viewModelFactory { f() })[T::class.java]

@Suppress("UNCHECKED_CAST")
inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = f() as T
    }