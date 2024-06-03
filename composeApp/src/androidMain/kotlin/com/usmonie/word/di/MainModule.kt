package com.usmonie.word.di

import com.usmonie.word.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel() }
    single {

    }
}
